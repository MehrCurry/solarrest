import org.junit.Test
import solarrest.pogo.WrData

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
class WrDataTests {

    @Test
    void testParseString() {
        def wr=new WrData('1;2;3;4;5')
        assertEquals wr.ac,1.0
    }
}
