package com.example.biancaen.texicall.connectapi;

import java.io.Serializable;

/**
 * Created by artlenceNB on 2017/6/19.
 */

public class DriverData implements Serializable {

    private String error;
    private String type;
    private String account;
    private String email;
    private String apiKey;
    private int status;
    private String createdAt;
    private int savings;

    public String isError() {
        return error;
    }

    public String getAccount() {
        return account;
    }

    public String getEmail() {
        return email;
    }

    public String getApiKey() {
        return apiKey;
    }

    public int getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getType() {
        return type;
    }

    public int getSavings() {
        return savings;
    }
}
