package com.example.biancaen.texicall.domain;

public interface OnLoginListener {
    void onLoginSuccess(UserData userData);
    void onLoginFail(Exception e);
}
