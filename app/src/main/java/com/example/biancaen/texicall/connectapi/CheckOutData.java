package com.example.biancaen.texicall.connectapi;

import java.io.Serializable;

/**
 * Created by artlenceNB on 2017/6/28.
 */

public class CheckOutData implements Serializable {

    /*"error": "false",
    "message": "搭乘金額",
    "distance": "12",
    "time": 7365.9333333333,
    "date": "2017-06-28 14:22:05",
    "misscatchprice": 0,
    "price": 14773,
    "remain_point": 2734.7
*/
    private String error;
    private String message;
    private String distance;
    private String time;
    private String date;
    private String misscatchprice;
    private String price;
    private String remain_point;

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getDistance() {
        return distance;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getMisscatchprice() {
        return misscatchprice;
    }

    public String getPrice() {
        return price;
    }

    public String getRemain_point() {
        return remain_point;
    }
}
