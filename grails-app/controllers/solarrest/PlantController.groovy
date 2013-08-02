package solarrest
import grails.converters.JSON

class PlantController {
    def solarLogService

    static scaffold = true

    def save() {
        Plant p=new Plant(params)
        p.updateFromBaseVars()
        p.save()
        redirect(action: "list")
    }

    def getMinDay() {
        def plant=Plant.findById(params.id)
        render solarLogService.getMinDay(plant.url) as JSON
    }
}
