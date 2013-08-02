package solarrest.pogo

import org.joda.time.DateTime

class DayEntry {

    def wrData=[]

    def DateTime dt

    def addToWrData(wd) {
        wrData << wd
    }

    def getSum() {
        wrData.sum {it.wh}
    }
}
