package solarrest

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces

@Path('/api/test')
class TestResource {

    @GET
    @Produces(['application/xml','application/json'])
    String getTestRepresentation() {
        'Test'
    }
}
