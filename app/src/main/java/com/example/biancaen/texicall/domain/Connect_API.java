package com.example.biancaen.texicall.domain;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

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

    //計費及派車
    private static final String RATE = "/rate";
    private static final String NEW_TASK = "/newtask";
    private static final String START_TASK = "/starttask";
    private static final String CANCEL_TASK = "/canceltask";
    private static final String GET_PAIR_INFO = "/getpairinfo";
    private static final String ACCEPT_TASK = "/accepttask";

    private static Request request;
    private static RequestBody body;

    // 會員註冊
    public interface OnRegisterListener{
        void onRegisterSuccessListener(boolean isFail , String message);
        void onRegisterFailListener(Exception e);
    }
    public static void register(String name , String password , String email , String phone ,@NonNull final OnRegisterListener listener){
        body = new FormEncodingBuilder()
                .add("name" , name)
                .add("password" , password)
                .add("email" , email)
                .add("phone" , phone).build();
        request = new Request.Builder().url(API_HOST+API_VERSION+REGISTER).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onRegisterFailListener(e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    listener.onRegisterSuccessListener(jsonObject.getBoolean("error"),jsonObject.getString("message"));
                } catch (JSONException e) {
                    listener.onRegisterFailListener(e);
                }
            }
        });
    }

     // 登入
    public interface OnLoginListener {
         void onLoginSuccess(UserData userData);
         void onLoginFail(Exception e);
     }
    public static void login(String phone , String password , @NonNull final OnLoginListener listener){
        body = new FormEncodingBuilder()
                .add("password" , password)
                .add("phone" , phone).build();

        request = new Request.Builder().url(API_HOST+API_VERSION+LOGIN).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onLoginFail(e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String body = response.body().string();
                Gson gson = new Gson();
                listener.onLoginSuccess(gson.fromJson(body, UserData.class));
            }
        });
    }

    //客戶端打開App
    public interface  OnGetStatusListener{
        void onFail(Exception e);
        void onSuccess(String result , int status);
    }
    public static void getStatus(String phone , String apiKey ,@NonNull final OnGetStatusListener listener){
        body = new FormEncodingBuilder()
                .add("phone" , phone)
                .add("os" , "1").build();
        request = new Request.Builder().url(API_HOST+API_VERSION+GET_STATUS).header("Authorization" ,""+ apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onFail(e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    listener.onSuccess(jsonObject.getString("result"),jsonObject.getInt("status"));

                } catch (JSONException e) {
                    listener.onFail(e);
                }
            }
        });
    }

    //客戶端上傳狀態
    public interface OnPutStatusListener {
        void onFail(Exception e);
        void onSuccess(boolean isFail , String result);
    }
    public static void putstatus(String phone , String status, String apiKey,@NonNull final OnPutStatusListener listener){
        body = new FormEncodingBuilder()
                .add("phone" , phone)
                .add("status" , status).build();
        request = new Request.Builder().url(API_HOST+API_VERSION+PUT_STATUS).header("Authorization",""+apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onFail(e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    listener.onSuccess(jsonObject.getBoolean("error"),jsonObject.getString("result"));
                } catch (JSONException e) {
                    listener.onFail(e);
                }
            }
        });
    }

    //司機端打開App(更新)
    public interface OnGetDriverStatusListener{
        void onFail(Exception e);
        void onSuccess(String result , int status);
    }
    public static void getdriverstatus(String phone ,String apiKey ,@NonNull final OnGetDriverStatusListener listener){
        body = new FormEncodingBuilder()
                .add("phone" , phone)
                .add("os" , "1").build();
        request = new Request.Builder().url(API_HOST+API_VERSION+GET_DRIVER_STATUS).header("Authorization",""+apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onFail(e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    listener.onSuccess(jsonObject.getString("result"),jsonObject.getInt("status"));
                } catch (JSONException e) {
                    listener.onFail(e);
                }
            }
        });
    }


    //司機端上傳狀態
    public interface OnPutDriverStatusListener{
        void onFail(Exception e);
        void onSuccess(boolean isFail , String result);
    }
    public static void putdriverstatus(String phone , String status , String apiKey ,@NonNull final OnPutDriverStatusListener listener ){
        body = new FormEncodingBuilder()
                .add("phone" , phone)
                .add("status" , status).build();
        request = new Request.Builder().url(API_HOST+API_VERSION+PUT_DRIVER_STATUS).header("Authorization",""+apiKey).post(body).build();

        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onFail(e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    listener.onSuccess(jsonObject.getBoolean("error"),jsonObject.getString("result"));
                } catch (JSONException e) {
                    listener.onFail(e);
                }
            }
        });
    }

    //--------------------計費--------------------------
    /*
    客戶發出派車試算需求
    起始經緯度及終點經緯度
    */
    public interface OnRateListener{
        void onFail(Exception e);
        void onSuccess(String result, int price, int time, String distance);
    }
    public static void rate(String start_lon , String start_lat,
                            String end_lon , String end_lat , String apiKey ,@NonNull final OnRateListener listener){
        body = new FormEncodingBuilder()
                .add("addr_start_lon" , start_lon)
                .add("addr_start_lat" , start_lat)
                .add("addr_end_lon" , end_lon)
                .add("addr_end_lat" , end_lat).build();
        request = new Request.Builder().url(API_HOST+API_VERSION+RATE).header("Authorization" , ""+ apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onFail(e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    listener.onSuccess(
                            jsonObject.getString("result"),
                            jsonObject.getInt("price"),
                            jsonObject.getInt("time"),
                            jsonObject.getString("distance"));
                } catch (JSONException e) {
                    listener.onFail(e);
                }
            }
        });
    }

    //客戶發出派車需求
    public interface OnNewTaskListener{
        void onFail(Exception e);
        void onSuccess(boolean isFail, String message, String tasknumber);
    }
    public static void newTask(String start_lon , String start_lat , String start_addr ,
                               String end_lon , String end_lat , String end_addr ,
                               String phone , String passenger_number , String comment ,
                               String apiKey ,@NonNull final OnNewTaskListener listener){
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
        request = new Request.Builder().url(API_HOST+API_VERSION+NEW_TASK).header("Authorization" , ""+ apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onFail(e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    listener.onSuccess(jsonObject.getBoolean("error"),jsonObject.getString("message"),jsonObject.getString("tasknumber"));
                } catch (JSONException e) {
                    listener.onFail(e);
                }
            }
        });
    }

    //開始配對
    public interface OnStartTaskListener{
        void onFail(Exception e);
        void onSuccess(boolean isFail, String message);
    }
    public static void starttask(String start_lon , String start_lat ,
                                 String phone , String tasknumber , String apiKey ,
                                 @NonNull final OnStartTaskListener listener){
        body = new FormEncodingBuilder( )
                .add("addr_start_lon" , start_lon)
                .add("addr_start_lat" , start_lat)
                .add("phone" , phone)
                .add("tasknumber" , tasknumber).build();
        request = new Request.Builder().url(API_HOST+API_VERSION+START_TASK).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onFail(e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    listener.onSuccess(jsonObject.getBoolean("error"),jsonObject.getString("message"));
                } catch (JSONException e) {
                    listener.onFail(e);
                }
            }
        });
    }

    //取消配對
    public interface OnCancelTaskListener{
        void onFail(Exception e);
        void onSuccess(boolean isFail, String message);
    }
    public static void cancelTask(String tasknumber , String apiKey ,@NonNull final OnCancelTaskListener listener){
        body = new FormEncodingBuilder().add("tasknumber" ,tasknumber).build();
        request = new Request.Builder().url(API_HOST+API_VERSION+CANCEL_TASK).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onFail(e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    listener.onSuccess(jsonObject.getBoolean("error"),jsonObject.getString("message"));
                } catch (JSONException e) {
                    listener.onFail(e);
                }
            }
        });
    }

    //取得結果
    public interface OnGetPairInfoListener{
        void onFail(Exception e);
        void onSuccessGetPairInfo(PairInfo pairInfo);
        void onWaiting(boolean isError , String message);
    }
    public void getpairinfo(String tasknumber ,String apiKey ,@NonNull final OnGetPairInfoListener listener){
        body = new FormEncodingBuilder().add("tasknumber" ,tasknumber).build();
        request = new Request.Builder().url(API_HOST+API_VERSION+GET_PAIR_INFO).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onFail(e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    try {
                        if (jsonObject.getString("driver").contains("09")) {
                            Gson gson = new Gson();
                            listener.onSuccessGetPairInfo(gson.fromJson(s, PairInfo.class));
                        }
                    }catch (NullPointerException npe){
                        listener.onWaiting(jsonObject.getBoolean("error"),jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    listener.onFail(e);
                }
            }
        });
    }

    //接受任務
    public interface OnAcceptTaskListener{
        void onFail(Exception e);
        void onSuccess(boolean isError ,String message);
    }
    public static void accepttask(String tasknumber , String phone , String apiKey ,@NonNull final OnAcceptTaskListener listener){
        body = new FormEncodingBuilder()
                .add("phone" ,phone)
                .add("tasknumber" ,tasknumber)
                .build();
        request = new Request.Builder().url(API_HOST+API_VERSION+ACCEPT_TASK).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onFail(e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    listener.onSuccess(jsonObject.getBoolean("error"),jsonObject.getString("message"));
                } catch (JSONException e) {
                    listener.onFail(e);
                }
            }
        });
    }

}
