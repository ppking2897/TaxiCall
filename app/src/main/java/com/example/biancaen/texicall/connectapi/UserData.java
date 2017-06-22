package com.example.biancaen.texicall.connectapi;

import java.io.Serializable;

public class UserData implements Serializable {

    private String error;
    private String account;
    private String email;
    private String apiKey;
    private int status;
    private String createdAt;

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
}
