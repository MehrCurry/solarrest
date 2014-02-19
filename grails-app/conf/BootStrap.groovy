import solarrest.Plant

class BootStrap {

    def init = { servletContext ->
        def plant=new Plant(name: "Zockoll", url: "http://home11.solarlog-web.de/6764.html?file=")
        plant.save()
        plant.updateFromBaseVars()
    }
    def destroy = {
    }
}
