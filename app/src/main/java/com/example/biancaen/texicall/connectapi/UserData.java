package com.example.biancaen.texicall.connectapi;

import java.io.Serializable;

public class UserData implements Serializable {

    private String error;
	private String type;
    private String phone;
    private String email;
	private String name;
    private String apiKey;
    private int status;
    private String createdAt;
	
	public String getName() {
        return name;
    }

    public String isError() {
        return error;
    }

    public String getPhone() {
        return phone;
    }
	
	public String getType(){
		return type;

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
