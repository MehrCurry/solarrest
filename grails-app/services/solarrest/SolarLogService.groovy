package solarrest

import grails.plugin.cache.Cacheable
import groovyx.gpars.GParsPool
import org.apache.commons.lang3.Validate
import org.joda.time.DateMidnight
import org.joda.time.DateTime
import org.joda.time.base.BaseDateTime
import org.joda.time.format.DateTimeFormat
import solarrest.pogo.DayEntry
import solarrest.pogo.WrData
import solarrest.pogo.WrDataDays
import sun.org.mozilla.javascript.internal.NativeArray

import javax.script.ScriptContext
import javax.script.ScriptEngineManager

class SolarLogService {
    static yymmdd= DateTimeFormat.forPattern('yyMMdd')
    static ScriptEngineManager factory = new ScriptEngineManager()

    @Cacheable('minDay')
    def getMinDay(String url,DateTime aDate=DateMidnight.now()) {
        def script = url + getScriptName(aDate)
        // copyFile(script)
        println("Getting $script")

        def engine = factory.getEngineByName("JavaScript")
        def bindings=engine.getBindings(ScriptContext.ENGINE_SCOPE)
        def m = new NativeArray();
        bindings.put("m",m)
        bindings.put("mi",0)
        engine.eval new InputStreamReader(new URL(script).openStream())
        Validate.isTrue(m.size()>0,"Empty array $script")

        def parser=DateTimeFormat.forPattern('dd.MM.yy HH:mm:ss')
        def array = m.toArray(String[])
            array
                    .filter{ it }
                    .map {
                        Validate.notNull(it)
                        Validate.isInstanceOf(String.class,it)
                        def parts=it.split('\\|')
                        def dt=parser.parseDateTime(parts[0])
                        def entry=new DayEntry(dt: dt)
                        parts.tail().each {
                            entry.addToWrData(new WrData(it))
                        }
                        entry
                    }
                    .collection
    }

    def copyFile(String anUrl) {
        def parts=anUrl.split("/");
        Validate.notEmpty(parts)
        Validate.isTrue(parts.size()>0,"Could not split $anUrl")
        def source=new InputStreamReader(new URL(anUrl).openStream())
        def filename=parts.last()
        def dest=new File("data/$filename").newDataOutputStream()

        dest << source

        source.close()
        dest.close()
    }

    @Cacheable("days_hist")
    def getDaysHist(String url) {
        def engine = factory.getEngineByName("JavaScript")
        def bindings=engine.getBindings(ScriptContext.ENGINE_SCOPE)
        def da = new NativeArray();
        bindings.put("da",da)
        bindings.put("dx",0)
        def filename = url + "days_hist.js"
        engine.eval new InputStreamReader(new URL(filename).openStream())

        def parser=DateTimeFormat.forPattern("dd.MM.yy")
        da.toArray(String[]).collect {s ->
            def parts=s.split('\\|')
            def entry=new DayEntry(dt: parser.parseDateTime(parts[0]))
            parts.tail().each {
                entry.addToWrData(new WrDataDays(it))
            }
            entry
        }
    }

    def getFirstDay(coll) {
        GParsPool.withPool {
            coll.parallel.map { it.dt }.min {it}
        }
    }

    def getAllData(String url) {
        def day=getDaysHist(url).collect {it.dt}.min();
        def coll = [] as Set<DayEntry>
        while (!day.afterNow) {
            coll.add(day)
            day=day.plusDays(1);
        }

        coll.collect {getMinDay(url,it)}.flatten()
    }

    def getSumByYear(year,data) {
            data.findAll {it.dt.year == year}
                    .collect { it.getSum() }
                    .sum()
    }

    def getSumByMonth(month,year,data) {
            data.findAll {it.dt.year == year && it.dt.monthOfYear == month}
                    .collect { it.getSum() }
                    .sum()
    }

    String getScriptName(BaseDateTime aDate) {
        isToday(aDate) ?
            "min_day.js" : "min${aDate.toString(yymmdd)}.js"
    }

    boolean isToday(BaseDateTime aDate) {
        DateMidnight.now() equals aDate.toDateTime().toDateMidnight()
    }
}
