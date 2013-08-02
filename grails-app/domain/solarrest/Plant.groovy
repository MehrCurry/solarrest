package solarrest

import javax.script.ScriptContext
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager

class Plant {
    static ScriptEngineManager factory = new ScriptEngineManager()
    // create a JavaScript engine
    static ScriptEngine engine = factory.getEngineByName("JavaScript")

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
}
