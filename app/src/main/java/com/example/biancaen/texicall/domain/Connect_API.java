package com.example.biancaen.texicall.domain;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 *2017/06/12  artlence created
 *  不可再主執行序下操作此類別
 */
public class Connect_API{

    private static String debugTag = Connect_API.class.getName();

    private static final String API_HOST = "http://188.166.213.209/hb";
    private static final String API_VERSION = "/v1";

    private static final String REGISTER = "/register";
    private static final String LOGIN = "/login";
    private static final String GET_STATUS = "/getstatus";
    private static final String PUT_STATUS = "/putstatus";
    private static final String GET_DRIVER_STATUS = "/getdriverstatus";
    private static final String PUT_DRIVER_STATUS = "/putdriverstatus";
    private static final String NEW_TASK = "/newtask";
    private static final String START_TASK = "/starttask";

    //客戶 計費
    private static final String RATE = "/rate";

    private static Request request;
    private static RequestBody body;

    /**
     * 會員註冊
     * @param  name
     * @param password
     * @param email
     * @param phone
     */
    public static void isRegister(String name , String password , String email , String phone , Callback callback){
        body = new FormEncodingBuilder()
                .add("name" , name)
                .add("password" , password)
                .add("email" , email)
                .add("phone" , phone).build();
        request = new Request.Builder().url(API_HOST+API_VERSION+REGISTER).post(body).build();
        new OkHttpClient().newCall(request).enqueue(callback);
        release();
    }

    /**
     * 登入
     * @param phone
     * @param password
     * @return  true is OK*/
    public static void login(String phone , String password , @NonNull final OnLoginListener l){
        body = new FormEncodingBuilder()
                .add("password" , password)
                .add("phone" , phone).build();

        request = new Request.Builder().url(API_HOST+API_VERSION+LOGIN).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                l.onLoginFail(e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String body = response.body().string();
                Gson gson = new Gson();
                l.onLoginSuccess(gson.fromJson(body, UserData.class));
            }
        });
        release();
    }

    /**
     * 客戶端打開App
     * @param phone*/
    public static void getStatus(String phone , Callback callback){
        body = new FormEncodingBuilder()
                .add("phone" , phone)
                .add("os" , "1").build();
        request = new Request.Builder().url(API_HOST+API_VERSION+GET_STATUS).header("Authorization" ,""+ AppUtility.apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(callback);
        release();
    }

    /**客戶端上傳狀態
     *@param  phone
     * status class自動載入*/
    public static void putstatus(String phone ,Callback callback){
        body = new FormEncodingBuilder()
                .add("phone" , phone)
                .add("status" , String.valueOf(AppUtility.status)).build();
        request = new Request.Builder().url(API_HOST+API_VERSION+PUT_STATUS).header("Authorization",AppUtility.apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(callback);
        release();
    }

    /**司機端打開App(更新)
     * @param phone*/
    public static void getdriverstatus(String phone , Callback callback){
        body = new FormEncodingBuilder()
                .add("phone" , phone)
                .add("os" , "1").build();
        request = new Request.Builder().url(API_HOST+API_VERSION+GET_DRIVER_STATUS).header("Authorization",AppUtility.apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(callback);
        release();
    }

    /**司機端上傳狀態
        * @param phone*/
    public static void putdriverstatus(String phone , Callback callback){
        body = new FormEncodingBuilder()
                .add("phone" , phone)
                .add("os" , "1").build();
        request = new Request.Builder().url(API_HOST+API_VERSION+PUT_DRIVER_STATUS).header("Authorization",AppUtility.apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(callback);
        release();
    }

    //--------------------計費--------------------------
    /** 客戶發出派車試算需求
     * @param start_lon
     * @param start_lat
     * @param end_lon
     * @param end_lat
     * 起始經緯度及終點經緯度
     */
    public static void rate(String start_lon , String start_lat,
                            String end_lon , String end_lat , Callback callback){
        body = new FormEncodingBuilder()
                .add("addr_start_lon" , start_lon)
                .add("addr_start_lat" , start_lat)
                .add("addr_end_lon" , end_lon)
                .add("addr_end_lat" , end_lat).build();
        request = new Request.Builder().url(API_HOST+API_VERSION+RATE).header("Authorization" , AppUtility.apiKey ).post(body).build();
        new OkHttpClient().newCall(request).enqueue(callback);
//        release();
    }

    /**客戶發出派車需求
     * @param start_lon
     * @param start_lat
     *  @param start_addr
     * @param end_lon
     * @param end_lat
     * @param end_addr
     * @param phone
     * @param passenger_number
     * @param comment*/
    public static void newTask(String start_lon , String start_lat , String start_addr ,
                               String end_lon , String end_lat , String end_addr ,
                               String phone , String passenger_number , String comment ,
                               Callback callback){
        body = new FormEncodingBuilder()
                .add("addr_start_lon" , start_lon)
                .add("addr_start_lat" , start_lat)
                .add("addr_start_addr" , start_addr)
                .add("addr_end_lon" , end_lon)
                .add("addr_end_lat" , end_lat)
                .add("addr_end_addr" , end_addr)
                .add("phone" , phone)
                .add("passenger_number" , passenger_number)
                .add("comment" , comment).build();
        request = new Request.Builder().url(API_HOST+API_VERSION+NEW_TASK).post(body).build();
        new OkHttpClient().newCall(request).enqueue(callback);
        release();
    }

    /**開始配對
     * @param start_lon
     * @param start_lat
     * @param phone*/
    public static void starttask(String start_lon , String start_lat ,
                                 String phone , String tasknumber , Callback callback){
        body = new FormEncodingBuilder( )
                .add("addr_start_lon" , start_lon)
                .add("addr_start_lat" , start_lat)
                .add("phone" , phone)
                .add("tasknumber" , tasknumber).build();
        request = new Request.Builder().url(API_HOST+API_VERSION+GET_DRIVER_STATUS).header("Authorization",AppUtility.apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(callback);
        release();
    }

    private static void release(){
        body = null;
        request = null;
        System.gc();
    }
}
