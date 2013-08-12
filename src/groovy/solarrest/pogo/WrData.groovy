package solarrest.pogo

import groovy.transform.ToString

@ToString
class WrData {

    Double ac,dc1,dc2,dc3,wh,u1,u2,u3,temp

    WrData(String s) {
        def data = s.split(';').collect { Double.parseDouble it }
        ac=data[0]
        switch (data.size()) {
            case 5:
                temp=data[4]
            case 4:
                dc1=data[1]
                wh=data[2]
                u1=data[3]
                break
            case 7:
                temp=data[6]
            case 6:
                dc1=data[1]
                dc2=data[2]
                wh=data[3]
                u1=data[4]
                u2=data[5]
                break
            case 9:
                temp=data[8]
            case 8:
                dc1=data[1]
                dc2=data[2]
                dc3=data[3]
                wh=data[4]
                u1=data[5]
                u2=data[6]
                u3=data[7]
                break
            default:
                log.error("Unknown WR Data:" + data)
        }
    }
}
