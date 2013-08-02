package solarrest.pogo

class WrDataDays {

    Double wh,max

    WrDataDays(String s) {
        def data = s.split(';').collect { Double.parseDouble it }
        wh=data[0]
        max=data[1]
    }
}
