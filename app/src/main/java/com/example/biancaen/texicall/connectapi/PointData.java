package com.example.biancaen.texicall.connectapi;

import java.io.Serializable;

/**
 * Created by artlenceNB on 2017/6/19.
 */

public class PointData implements Serializable {
    private String final_at;
    private double reduce_point;
    private String remain_point;

    public String getFinal_at() {
        return final_at;
    }

    public double getReduce_point() {
        return reduce_point;
    }

    public String getRemain_point() {
        return remain_point;
    }
}
