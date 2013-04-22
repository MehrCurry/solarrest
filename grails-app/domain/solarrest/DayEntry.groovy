package solarrest

import org.joda.time.DateTime

class DayEntry {

    static belongsTo = [plant:Plant]
    static hasMany = [wrData:WrData]
    static constraints = {
    }

    DateTime dt
}
