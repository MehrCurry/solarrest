package solarrest

import org.joda.time.format.DateTimeFormat
import sun.org.mozilla.javascript.internal.NativeArray

import javax.script.ScriptContext
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager

class Plant {
    static ScriptEngineManager factory = new ScriptEngineManager()
    // create a JavaScript engine
    static ScriptEngine engine = factory.getEngineByName("JavaScript")
    static fmt=DateTimeFormat.forPattern('dd.MM.yy HH:mm:ss')

    static hasMany = [minDay:DayEntry]

    static constraints = {
        name(unique: true)
        url(url:true)
    }

    String name
    String url
    String title
    String plz
    Integer wp
    Integer demandYear
    Integer inverterCount

    def updateFromBaseVars() {
        def script=new URL(url+"base_vars.js");
        engine.eval new InputStreamReader(script.openStream())

        def bindings=engine.getBindings(ScriptContext.ENGINE_SCOPE)
        println(bindings.get("AnlagenKWP"))
        demandYear=bindings.get("SollYearKWP")
        wp=bindings.get("AnlagenKWP")
        inverterCount=bindings.get("AnzahlWR")
        title=bindings.get("HPTitel")
    }

    def getMinDay() {
        def script=new URL(url+"min_day.js?nocache");
        def bindings=engine.getBindings(ScriptContext.ENGINE_SCOPE)
        def m = new NativeArray();
        bindings.put("m",m)
        bindings.put("mi",0)
        engine.eval new InputStreamReader(script.openStream())

        bindings.get("da")
        def data = m.toArray(String[]).collect {s ->
            def parts=s.split('\\|')
            def dt=fmt.parseDateTime(parts[0])
            def entry=new DayEntry(dt: dt)
            entry.addToWrData()
            entry.addToWrData(new WrData(parts.tail()))
            println(entry)
        }
    }
}
