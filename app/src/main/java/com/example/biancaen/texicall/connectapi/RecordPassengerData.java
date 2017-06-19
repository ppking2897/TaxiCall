package com.example.biancaen.texicall.connectapi;

import java.io.Serializable;

/**
 * Created by artlenceNB on 2017/6/19.
 */

public class RecordPassengerData implements Serializable{

    private String tasknumber;
    private String addr_start_addr;
    private String addr_end_addr;
    private String time;
    private String created_at;
    private String final_at;

    public String getTasknumber() {
        return tasknumber;
    }

    public String getAddr_start_addr() {
        return addr_start_addr;
    }

    public String getAddr_end_addr() {
        return addr_end_addr;
    }

    public String getTime() {
        return time;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getFinal_at() {
        return final_at;
    }
}
