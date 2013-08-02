import org.joda.time.DateTime
import org.junit.Test
import solarrest.pogo.DayEntry
import solarrest.pogo.WrData

import static org.junit.Assert.*

import static org.hamcrest.CoreMatchers.is
/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
class DayEntryTests {

    @Test
    void testSomething() {
        def entry=new DayEntry(dt: DateTime.now())
        assertThat(entry.wrData.size(),is(0))
        entry.addToWrData(new WrData('1;2;3;4;5'))
        assertThat(entry.wrData.size(),is(1))
        entry.addToWrData(new WrData('1;2;3;4;5'))
        assertThat(entry.wrData.size(),is(2))
    }
}
