package solarrest.pogo

import groovy.transform.ToString
import org.joda.time.DateTime

@ToString
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
