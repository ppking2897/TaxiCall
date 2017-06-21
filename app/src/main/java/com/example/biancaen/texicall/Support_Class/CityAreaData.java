package com.example.biancaen.texicall.Support_Class;

import java.io.Serializable;
import java.util.List;


public class CityAreaData implements Serializable {
    private String city_name;
    private List<String> district;

    public String getCityName(){
        return city_name;
    }

    public void setCityName(String city_name){
        this.city_name = city_name;
    }

    public List<String> getDistrict(){
        return district;
    }

    public void setDistrict(List<String> district){
        this.district = district;
    }
}
