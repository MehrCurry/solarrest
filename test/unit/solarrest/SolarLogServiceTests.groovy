package solarrest
import grails.test.mixin.TestFor
import org.apache.commons.lang3.time.StopWatch
import org.joda.time.DateMidnight
import org.joda.time.DateTime
import org.junit.Ignore

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(SolarLogService)
class SolarLogServiceTests {


    // private static final String TEST_URL = "http://monitoring.norderstedt-energie.de/1064/"
    private static final String TEST_URL = "http://monitoring.norderstedt-energie.de/1002/"
    // private static final String TEST_URL = new File("./data/").toURI().toURL().toString();

    void testCache() {
        def sw=new StopWatch();
        sw.start()
        def r1=service.getDaysHist(TEST_URL)
        sw.stop()
        def t1=sw.getTime()
        assertNotNull(r1)
        println(sw)
        sw.reset()
        sw.start()
        def r2=service.getDaysHist(TEST_URL)
        sw.stop()
        def t2=sw.getTime()
        assertNotNull(r2)
        println(sw)
        assertTrue(t2<t1)
    }

    void testIsToday() {
        def service=new SolarLogService()
        assertTrue service.isToday(DateTime.now())
        assertTrue service.isToday(DateMidnight.now())
        assertFalse service.isToday(DateTime.now().minusDays(1))
        assertFalse service.isToday(DateTime.now().plusDays(1))
    }

    void testGetScriptName() {
        def service=new SolarLogService()
        assertEquals("min_day.js", service.getScriptName(DateTime.now()))
        assertEquals("min130105.js", service.getScriptName(DateTime.parse("2013-01-05")))
    }

    void testGetDaysHist(){
        def service=new SolarLogService()
        def result=service.getDaysHist(TEST_URL)
        assertNotNull(result)
    }

    @Ignore
    void testGetAllData(){
        def t=new StopWatch();
        t.start()
        def service=new SolarLogService()
        def result=service.getAllData(TEST_URL)
        t.stop()
        println(t)
        assertNotNull(result)
        println(service.getSumByYear(2012,result))

    }

    void testGetSumByYear() {
        def service=new SolarLogService()
        def data=service.getDaysHist(TEST_URL)
        assertNotNull(data)
        def watch=new StopWatch()
        watch.start()
        def result=service.getSumByYear(2012,data)
        watch.stop()
        println(watch)
        assertNotNull(result)
        println(service.getSumByYear(2012,data))
        println(service.getSumByYear(2013,data))

    }

    void testGetSumByMonth() {
        def service=new SolarLogService()
        def data=service.getDaysHist(TEST_URL)
        assertNotNull(data)
        def watch=new StopWatch()
        watch.start()
        def result=service.getSumByMonth(4,2012,data)
        watch.stop()
        println(watch)
        assertNotNull(result)
        (2010..2013).each { year ->
            (1..12).each { month ->
                def max=data.findAll {it.dt.year == year}.findAll { it.dt.monthOfYear == month}.collect {it.getSum()}.max()
                println("$month/$year: ${service.getSumByMonth(month,year,data)}")
                println("$month/$year: $max")
            }
        }
    }

    void testGetFirstDay() {
        def service=new SolarLogService()
        def coll=service.getDaysHist(TEST_URL)
        def result=service.getFirstDay(coll);
        assertNotNull(result)
    }
}
