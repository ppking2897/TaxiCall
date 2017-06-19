package com.example.biancaen.texicall.connectapi;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.Serializable;

/**
 * Created by artlenceNB on 2017/6/19.
 */

public class TaskInfoFullData implements Serializable {

    private String error;
    private String message;
    private String addr_start_addr;
    private String addr_end_addr;
    private String passenger_number;
    private String comment;
    private String task_status;
    private String driver;
    private String driver_name;
    private String driver_status;
    private String carshow;
    private String carnumber;
    private String passenger;
    private String passenger_name;
    private String passenger_status;

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
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

    public String getTask_status() {
        return task_status;
    }

    public String getDriver() {
        return driver;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public String getDriver_status() {
        return driver_status;
    }

    public String getCarshow() {
        return carshow;
    }

    public String getCarnumber() {
        return carnumber;
    }

    public String getPassenger() {
        return passenger;
    }

    public String getPassenger_name() {
        return passenger_name;
    }

    public String getPassenger_status() {
        return passenger_status;
    }
}
