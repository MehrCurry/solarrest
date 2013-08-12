package solarrest

class SolarLogController {

    static expose = ['cxf']
    def solarLogService

    Plant[] getPlants() {
        Plant.list() as Plant[]
    }
}
