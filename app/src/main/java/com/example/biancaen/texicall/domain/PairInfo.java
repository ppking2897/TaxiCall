package com.example.biancaen.texicall.domain;

import java.io.Serializable;

/**
 * Created by artlenceNB on 2017/6/12.
 */

public class PairInfo implements Serializable {

    private String driver;
    private String name;
    private String passenger;
    private String addr_start_addr;
    private String carnumber;
    private String carnshow;
    private String estimated_arrive_time;
    private String message;
    private boolean error;

    public String getDriver() {
        return driver;
    }

    public String getName() {
        return name;
    }

    public String getPassenger() {
        return passenger;
    }

    public String getAddr_start_addr() {
        return addr_start_addr;
    }

    public String getCarnumber() {
        return carnumber;
    }

    public String getCarnshow() {
        return carnshow;
    }

    public String getEstimated_arrive_time() {
        return estimated_arrive_time;
    }

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return error;
    }
}
