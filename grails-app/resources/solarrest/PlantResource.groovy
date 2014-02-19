package solarrest
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces

@Path('/api/plant')
@Produces(['application/xml','application/json'])
class PlantResource {
    def SolarLogService solarLogService

    @GET
    @Produces('text/plain')
    String getPlantRepresentation() {
        'Plant'
    }

    @GET
    def readAll() {
        Plant.findAll()
    }

    @GET
    @Path('/{id}')
    def show(@PathParam('id') int id) {
        Plant.findById(id)
    }

    @GET
    @Path('/{id}/total/{year}')
    def year(@PathParam('id') int id,@PathParam('year') int year) {
        def plant=Plant.findById(id)
        def data=solarLogService.getDaysHist(plant.url)
        def total=solarLogService.getSumByYear(year,data)
        println("$plant.name $year : $total")
        total
    }

    @GET
    @Path('/{id}/total/{year}/{month}')
    def month(@PathParam('id') int id,@PathParam('year') int year,@PathParam('month') int month) {
        def plant=Plant.findById(id)
        def data=solarLogService.getDaysHist(plant.url)
        def total=solarLogService.getSumByMonth(month,year,data)
        println("$plant.name $month.$year : $total")
        ['Total':total]
    }

}
