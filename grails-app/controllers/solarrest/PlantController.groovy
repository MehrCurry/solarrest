package solarrest

class PlantController {
    static scaffold = true

    def save() {
        Plant p=new Plant(params)
        p.updateFromBaseVars()
        p.getMinDay()
        p.save()
        redirect(action: "list")
    }
}
