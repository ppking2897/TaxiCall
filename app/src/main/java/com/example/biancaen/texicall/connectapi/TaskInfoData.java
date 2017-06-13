package com.example.biancaen.texicall.connectapi;

import java.io.Serializable;

/**
 * Created by artlenceNB on 2017/6/13.
 */

public class TaskInfoData implements Serializable {


    private String message;
    private String passenger;
    private String addr_start_addr;
    private String addr_end_addr;
    private String passenger_number;
    private String comment;
    private String addr_start_lat;
    private String addr_start_lon;
    private String addr_end_lat;
    private String addr_end_lon;
    private boolean error;

    public String getMessage() {
        return message;
    }

    public String getPassenger() {
        return passenger;
    }

    public String getAddr_start_addr() {
        return addr_start_addr;
    }

    public String getAddr_end_addr() {
        return addr_end_addr;
    }

    public String getPassenger_number() {
        return passenger_number;
    }

    public String getComment() {
        return comment;
    }

    public String getAddr_start_lat() {
        return addr_start_lat;
    }

    public String getAddr_start_lon() {
        return addr_start_lon;
    }

    public String getAddr_end_lat() {
        return addr_end_lat;
    }

    public String getAddr_end_lon() {
        return addr_end_lon;
    }

    public boolean isError() {
        return error;
    }
}
