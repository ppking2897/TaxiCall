package com.example.biancaen.texicall.connectapi;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *2017/06/12  artlence created
 */
public class Connect_API{

//    private static String debugTag = Connect_API.class.getName();
    //HOST位置
    private static final String API_HOST = "http://188.166.213.209/hb";
    //應該是版本
    private static final String API_VERSION = "/v1";

    //註冊及會員相關
    private static final String REGISTER = "/register";
    private static final String LOGIN = "/login";
    private static final String LOGIN_OUT = "/loginout";
    private static final String MODIFY = "/modify";
    private static final String GET_STATUS = "/getstatus";
    private static final String PUT_STATUS = "/putstatus";
    private static final String GET_DRIVER_STATUS = "/getdriverstatus";
    private static final String PUT_DRIVER_STATUS = "/putdriverstatus";
    private static final String RECORD_LIST = "/recordlist";
    private static final String RECORD_LIST_DRIVER = "/recordlistdriver";
    private static final String GET_TASK_INFO_FULL = "/gettaskfullinfo";

    /**司機專用*/
    private static final String POINT_RECORD = "/pointrecord";

    //計費
    private static final String RATE = "/rate";
    private static final String NEW_TASK = "/newtask";
    private static final String START_TASK = "/starttask";
    private static final String CANCEL_TASK = "/canceltask";
    private static final String GET_PAIR_INFO = "/getpairinfo";
    private static final String ACCEPT_TASK = "/accepttask";
    private static final String TASK_INFO = "/taskinfo";
    private static final String CHECK_OUT = "/checkout";

    //等待及更新距離
    private static final String TERMINATE_PASSENGER = "/terminatebypassenger";
    private static final String TERMINATE_DRIVER = "/terminatebydriver";
    private static final String UPDATE_LOCATION = "/updateLocation";
    private static final String WAIT_TIME = "/waittime";
    private static final String PICKUP = "/pickup";
    private static final String TAKE_RIDE = "/takeride";
    private static final String REAL_PRICE = "/realprice";

    public interface OnRegisterListener{
        void onRegisterSuccessListener(boolean isFail, String message);
        void onRegisterFailListener(Exception e);
    }
    /** 會員註冊*/
    public static void register(@NonNull final Activity activity, String name , String password , String email , String phone , @NonNull final OnRegisterListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("name" , name)
                .add("password" , password)
                .add("email" , email)
                .add("phone" , phone).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+REGISTER).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onRegisterFailListener(e);
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            listener.onRegisterSuccessListener(jsonObject.getBoolean("error"),jsonObject.getString("message"));
                        } catch (JSONException e) {
                            listener.onRegisterFailListener(e);
                        }
                    }
                });
            }
        });
    }


    public interface OnUserLoginListener {
         void onLoginSuccess(UserData userData);
         void onLoginFail(boolean isFail, String msg);
         void onFail(Exception e);
     }
     /**乘客登入2017/06/19 更動*/
    public static void userLogin(@NonNull final Activity activity, String phone , String password , @NonNull final OnUserLoginListener listener){
        final RequestBody body = new FormEncodingBuilder()
                .add("password" , password)
                .add("phone" , phone).build();

        Request request = new Request.Builder().url(API_HOST+API_VERSION+LOGIN).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String body = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject object = new JSONObject(body);
                            if (object.getBoolean("error")){
                                listener.onLoginFail(object.getBoolean("error"),object.getString("message"));
                            }else {
                                Gson gson = new Gson();
                                listener.onLoginSuccess(gson.fromJson(body, UserData.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


    public interface OnDriverLoginListener {
        void onLoginSuccess(DriverData driverData);
        void onLoginFail(boolean isFail, String msg);
        void onFail(Exception e);
    }
    /**駕駛登入 2017/06/19 更動*/
    public static void driverLogin(@NonNull final Activity activity, String phone , String password , @NonNull final OnDriverLoginListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("password" , password)
                .add("phone" , phone).build();

        Request request = new Request.Builder().url(API_HOST+API_VERSION+LOGIN).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });

            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String body = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        try {
                            JSONObject object = new JSONObject(body);
                            if (object.getBoolean("error")){
                                listener.onLoginFail(object.getBoolean("error"),object.getString("message"));
                            }else {
                                listener.onLoginSuccess(gson.fromJson(body, DriverData.class));
                            }
                        } catch (JSONException e) {
                            listener.onFail(e);
                        }
                    }
                });

            }
        });
    }


    public interface OnModifyChangeListener{
        void onSuccess(boolean isFail, String msg);
        void onFail(Exception e);
    }
    /**會員資料修改 2017/06/19 更動*/
    public static void modifyChange(@NonNull final Activity activity, String email, String phone, String oldpassword, String password,
                                    String name, String apiKey , @NonNull final OnModifyChangeListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("email" , email)
                .add("oldpassword" , oldpassword)
                .add("password" , password)
                .add("name" , name)
                .add("phone" , phone).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+MODIFY).header("Authorization" ,""+ apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            listener.onSuccess(jsonObject.getBoolean("error"),jsonObject.getString("message"));

                        } catch (JSONException e) {
                            listener.onFail(e);
                        }
                    }
                });

            }
        });
    }


    public interface  OnGetStatusListener{
        void onFail(Exception e);
        /**@param  status
                *1->非任務中
                *2->任務中*/
        void onSuccess(boolean isFail, String result, int status ,int misscatch_time, int misscatch_price);
    }
    /**客戶端打開App*/
    public static void getStatus(@NonNull final Activity activity, String phone , String apiKey , @NonNull final OnGetStatusListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("phone" , phone)
                .add("os" , "1").build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+GET_STATUS).header("Authorization" ,""+ apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            listener.onSuccess(jsonObject.getBoolean("error"),jsonObject.getString("result"),jsonObject.getInt("status"),
                                    jsonObject.getInt("misscatch_time"),jsonObject.getInt("misscatch_price"));

                        } catch (JSONException e) {
                            listener.onFail(e);
                        }
                    }
                });
            }
        });
    }


    public interface OnPutStatusListener {
        void onFail(Exception e);
        void onSuccess(boolean isError, String result);
    }
    /**客戶端上傳狀態
     * @param  status
     * 1->非任務中
     * 2->任務中
     */
    public static void putstatus(@NonNull final Activity activity, String phone , String status, String apiKey, @NonNull final OnPutStatusListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("phone" , phone)
                .add("status" , status).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+PUT_STATUS).header("Authorization",""+apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            listener.onSuccess(jsonObject.getBoolean("error"),jsonObject.getString("result"));
                        } catch (JSONException e) {
                            listener.onFail(e);
                        }
                    }
                });
            }
        });
    }


    public interface OnGetDriverStatusListener{
        void onFail(Exception e);
        /**@param  status
                *1->下線
                * 2->上線
                * 3->任務中*/
        void onSuccess(boolean isError, String result, String status);
    }
    /**司機端打開App(更新)*/
    public static void getdriverstatus(@NonNull final Activity activity, String phone , String apiKey , @NonNull final OnGetDriverStatusListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("phone" , phone)
                .add("os" , "1").build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+GET_DRIVER_STATUS).header("Authorization",""+apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            listener.onSuccess(jsonObject.getBoolean("error"),jsonObject.getString("result"),jsonObject.getString("status"));
                        } catch (JSONException e) {
                            listener.onFail(e);
                        }
                    }
                });

            }
        });
    }


    public interface OnPutDriverStatusListener{
        void onFail(Exception e);
        void onSuccess(boolean isFail, String result);
    }
    /**司機端上傳狀態
     * @param  status
        * 1->下線
        * 2->上線
        * 3->任務中
        */
    public static void putdriverstatus(@NonNull final Activity activity, String phone , String status , String apiKey , @NonNull final OnPutDriverStatusListener listener ){
        RequestBody body = new FormEncodingBuilder()
                .add("phone" , phone)
                .add("status" , status).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+PUT_DRIVER_STATUS).header("Authorization",""+apiKey).post(body).build();

        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            listener.onSuccess(jsonObject.getBoolean("error"),jsonObject.getString("result"));
                        } catch (JSONException e) {
                            listener.onFail(e);
                        }
                    }
                });
            }
        });
    }


    public interface OnRateListener{
        void onFail(Exception e);
        void onSuccess(boolean isErrorResult, int price, int time, String distance);
    }
    /**
     客戶發出派車試算需求
     起始經緯度及終點經緯度
     */
    public static void rate(@NonNull final Activity activity, String start_lon , String start_lat,
                            String end_lon , String end_lat , String apiKey , @NonNull final OnRateListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("addr_start_lon" , start_lon)
                .add("addr_start_lat" , start_lat)
                .add("addr_end_lon" , end_lon)
                .add("addr_end_lat" , end_lat).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+RATE).header("Authorization" , ""+ apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            listener.onSuccess(
                                    jsonObject.getBoolean("result"),
                                    jsonObject.getInt("price"),
                                    jsonObject.getInt("time"),
                                    jsonObject.getString("distance"));
                        } catch (JSONException e) {
                            listener.onFail(e);
                        }
                    }
                });
            }
        });
    }


    public interface OnNewTaskListener{
        void onFail(Exception e);
        void onSuccess(boolean isFail, String message, String tasknumber);
    }
    /**客戶發出派車需求*/
    public static void newTask(@NonNull final Activity activity,
                               String start_lon , String start_lat , String start_addr ,
                               String end_lon , String end_lat , String end_addr ,
                               String phone , String passenger_number , String comment ,
                               String apiKey ,@NonNull final OnNewTaskListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("addr_start_lon" , start_lon)
                .add("addr_start_lat" , start_lat)
                .add("addr_start_addr" , start_addr)
                .add("addr_end_lon" , end_lon)
                .add("addr_end_lat" , end_lat)
                .add("addr_end_addr" , end_addr)
                .add("phone" , phone)
                .add("passenger_number" , passenger_number)
                .add("comment" , comment).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+NEW_TASK).header("Authorization" , ""+ apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            listener.onSuccess(jsonObject.getBoolean("error"),jsonObject.getString("message"),jsonObject.getString("tasknumber"));
                        } catch (JSONException e) {
                            listener.onFail(e);
                        }
                    }
                });
            }
        });
    }


    public interface OnStartTaskListener{
        void onFail(Exception e);
        void onSuccess(boolean isFail, String message);
    }
    /**開始配對*/
    public static void starttask(@NonNull final Activity activity, String start_lon , String start_lat ,
                                 String phone , String tasknumber , String apiKey ,
                                 @NonNull final OnStartTaskListener listener){
        RequestBody body = new FormEncodingBuilder( )
                .add("addr_start_lon" , start_lon)
                .add("addr_start_lat" , start_lat)
                .add("phone" , phone)
                .add("tasknumber" , tasknumber).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+START_TASK).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            listener.onSuccess(jsonObject.getBoolean("error"),jsonObject.getString("message"));
                        } catch (JSONException e) {
                            listener.onFail(e);
                        }
                    }
                });
            }
        });
    }


    public interface OnCancelTaskListener{
        void onFail(Exception e);
        void onSuccess(boolean isFail, String message);
    }
    /**取消配對*/
    public static void cancelTask(@NonNull final Activity activity, String tasknumber , String apiKey , @NonNull final OnCancelTaskListener listener){
        RequestBody body = new FormEncodingBuilder().add("tasknumber" ,tasknumber).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+CANCEL_TASK).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            listener.onSuccess(jsonObject.getBoolean("error"),jsonObject.getString("message"));
                        } catch (JSONException e) {
                            listener.onFail(e);
                        }
                    }
                });
            }
        });
    }


    public interface OnGetPairInfoListener{
        void onFail(Exception e);
        void onSuccessGetPairInfo(PairInfoData pairInfoData);
        void onWaiting(boolean isError, String message);
    }
    /**取得結果*/
    public void getpairinfo(@NonNull final Activity activity, String tasknumber , String apiKey , @NonNull final OnGetPairInfoListener listener){
        RequestBody body = new FormEncodingBuilder().add("tasknumber" ,tasknumber).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+GET_PAIR_INFO).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String body = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject object = new JSONObject(body);
                            if (object.getBoolean("error")){
                                listener.onWaiting(object.getBoolean("error"),object.getString("message"));
                            }else {
                                Gson gson = new Gson();
                                listener.onSuccessGetPairInfo(gson.fromJson(body, PairInfoData.class));
                            }
                        } catch (JSONException e) {
                            listener.onFail(e);
                        }
                    }
                });
            }
        });
    }


    public interface OnAcceptTaskListener{
        void onFail(Exception e);
        void onSuccess(boolean isError, String message);
    }
    /**接受任務(司機端)*/
    public static void accepttask(@NonNull final Activity activity, String tasknumber , String phone , String apiKey , @NonNull final OnAcceptTaskListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("phone" ,phone)
                .add("tasknumber" ,tasknumber)
                .build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+ACCEPT_TASK).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            listener.onSuccess(jsonObject.getBoolean("error"),jsonObject.getString("message"));
                        } catch (JSONException e) {
                            listener.onFail(e);
                        }
                    }
                });
            }
        });
    }


    public interface OnTaskInfoListener{
        void onFail(Exception e);
        void onSuccess(TaskInfoData taskInfoData);
    }
    /**司機端取得任務詳細資料*/
    public static void taskinfo(@NonNull final Activity activity, String tasknumber , String apiKey , @NonNull  final OnTaskInfoListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("tasknumber" ,tasknumber)
                .build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+TASK_INFO).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String body = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Gson gson = new Gson();
                            listener.onSuccess(gson.fromJson(body,TaskInfoData.class));
                        }catch (Exception e){
                            listener.onFail(e);
                        }
                    }
                });
            }
        });
    }


    public interface OnUpdateLocationListener{
        void onFail(Exception e);
        void onSuccess(boolean isError, String message);
    }
    /**即時更新司機座標(司機端)
     * 皆放入司機端訊息*/
    public static void updateLocation(@NonNull final Activity activity, String lon , String lat , String phone , String apiKey , @NonNull  final OnUpdateLocationListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("driver_lon" ,lon)
                .add("driver_lat" ,lat)
                .add("phone" ,phone)
                .build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+UPDATE_LOCATION).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });

            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            listener.onSuccess(jsonObject.getBoolean("error"),jsonObject.getString("message"));
                        } catch (JSONException e) {
                            listener.onFail(e);
                        }
                    }
                });

            }
        });
    }


    public interface OnWaitTimeListener{
        void onFail(Exception e);
        void onSuccess(String isError, String task_status, String distance, int time);
    }
    /**客戶等待司機抵達*/
    public static void waittime(@NonNull final Activity activity, String tasknumber , String apiKey , @NonNull final OnWaitTimeListener listener){
        RequestBody body = new FormEncodingBuilder().add("tasknumber" ,tasknumber).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+WAIT_TIME).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            listener.onSuccess(jsonObject.getString("error"),jsonObject.getString("task_status"),jsonObject.getString("distance"),jsonObject.getInt("time"));
                        } catch (JSONException e) {
                            listener.onFail(e);
                        }
                    }
                });
            }
        });
    }


    public interface OnTerminatePassengerListener{
        void onFail(Exception e);
        void onSuccess(String isFail, String msg);
    }
    /**客戶取消任務*/
    public static void terminateByPassenger(@NonNull final Activity activity, String tasknumber, String apiKey, final OnTerminatePassengerListener listener){
        RequestBody body = new FormEncodingBuilder().add("tasknumber" ,tasknumber).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+TERMINATE_PASSENGER).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            listener.onSuccess(jsonObject.getString("error"),jsonObject.getString("message"));
                        } catch (JSONException e) {
                            listener.onFail(e);
                        }
                    }
                });
            }
        });
    }


    public interface OnTerminateDriverListener{
        void onFail(Exception e);
        void onSuccess(String isFail, String msg);
    }
    /**司機取消任務*/
    public static void terminateByDriver(@NonNull final Activity activity, String tasknumber, String apiKey, @NonNull final OnTerminateDriverListener listener){
        RequestBody body = new FormEncodingBuilder().add("tasknumber" ,tasknumber).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+TERMINATE_DRIVER).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            listener.onSuccess(jsonObject.getString("error"),jsonObject.getString("message"));
                        } catch (JSONException e) {
                            listener.onFail(e);
                        }
                    }
                });
            }
        });
    }


    public interface OnPickupListener{
        void onFail(Exception e);
        void onSuccess(String isError, String message);
    }
    /**司機已抵達*/
    public static void pickup(@NonNull final Activity activity, String tasknumber , String apiKey , @NonNull final OnPickupListener listener){
        RequestBody body = new FormEncodingBuilder().add("tasknumber" ,tasknumber).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+PICKUP).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            listener.onSuccess(jsonObject.getString("error"),jsonObject.getString("message"));
                        } catch (JSONException e) {
                            listener.onFail(e);
                        }
                    }
                });
            }
        });
    }


    public interface OnTakeRideListener{
        void onFail(Exception e);
        void onSuccess(String isError, String message);
    }
    /**乘客已上車*/
    public static void takeride(@NonNull final Activity activity, String tasknumber , String apiKey , @NonNull final OnTakeRideListener listener){
        RequestBody body = new FormEncodingBuilder().add("tasknumber" ,tasknumber).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+TAKE_RIDE).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });

            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            listener.onSuccess(jsonObject.getString("error"),jsonObject.getString("message"));
                        } catch (JSONException e) {
                            listener.onFail(e);
                        }
                    }
                });

            }
        });
    }


    public interface OnRealPriceListener{
        void onFail(Exception e);
        void onSuccess(String isError, String message, String distance);
    }
    /**乘客搭乘途中,實際距離和時間計算*/
    public static void realprice(@NonNull final Activity activity, String tasknumber , String apiKey , @NonNull final OnRealPriceListener listener){
        RequestBody body = new FormEncodingBuilder().add("tasknumber" ,tasknumber).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+REAL_PRICE).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });

            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            listener.onSuccess(jsonObject.getString("error"), jsonObject.getString("message"), jsonObject.getString("distance"));
                        } catch (JSONException e) {
                            listener.onFail(e);
                        }
                    }
                });

            }
        });
    }

    //TODO ======================== 2017/06/19 更新=============================

    public interface OnCheckOutListener{
        void onFail(Exception e);
        void onSuccess(String isError, String message, String distance, String time, String price);
    }
    /**結算*/
    public static void checkOut(@NonNull final Activity activity, String tasknumber, String phone, String apiKey , @NonNull final OnCheckOutListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("phone",phone)
                .add("tasknumber" ,tasknumber).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+CHECK_OUT).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });

            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            listener.onSuccess(jsonObject.getString("error"),
                                    jsonObject.getString("message"), jsonObject.getString("distance"),
                                    jsonObject.getString("time"),jsonObject.getString("price"));
                        } catch (JSONException e) {
                            listener.onFail(e);
                        }
                    }
                });

            }
        });
    }


    public interface OnRecordListListener{
        void onFail(Exception e);
        void onSuccess(String isError, String msg, List<RecordPassengerData> data);
    }
    /**乘客任務紀錄*/
    public static void getRecordListForPassenger(@NonNull final Activity activity, String phone, String apiKey, @NonNull final OnRecordListListener listener){
        RequestBody body = new FormEncodingBuilder().add("phone",phone).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+RECORD_LIST).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });

            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            List<RecordPassengerData> datas = new ArrayList<>();
                            Gson gson = new Gson();
                            for (int i = 0 ; i < jsonArray.length() ; i++ ){
                                datas.add(gson.fromJson(jsonArray.getJSONObject(i).toString(),RecordPassengerData.class));
                            }
                             listener.onSuccess(jsonObject.getString("error"), jsonObject.getString("message"), datas);
                        } catch (JSONException e) {
                            listener.onFail(e);
                        }
                    }
                });

            }
        });
    }


    public interface OnRecordListDriverListener{
        void onFail(Exception e);
        void onSuccess(String isError, String msg, List<RecordDriverData> data);
    }
    /**司機任務紀錄*/
    public static void getRecordListForDriver(@NonNull final Activity activity, String phone, String apiKey, @NonNull final OnRecordListDriverListener listener){
        RequestBody body = new FormEncodingBuilder().add("phone",phone).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+RECORD_LIST_DRIVER).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });

            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            List<RecordDriverData> datas = new ArrayList<>();
                            Gson gson = new Gson();
                            for (int i = 0 ; i < jsonArray.length() ; i++ ){
                                datas.add(gson.fromJson(jsonArray.getJSONObject(i).toString(),RecordDriverData.class));
                            }
                            listener.onSuccess(jsonObject.getString("error"), jsonObject.getString("message"), datas);
                        } catch (JSONException e) {
                            listener.onFail(e);
                        }
                    }
                });

            }
        });
    }


    public interface OnPointRecordListener{
        void onFail(Exception e);
        void onSuccess(String isError, String message, String name, String savings, double sum, List<PointData> data);
    }
    /**司機點數紀錄*/
    public static void getPointRecord(@NonNull final Activity activity, String phone, String apiKey, @NonNull final OnPointRecordListener listener){
        RequestBody body = new FormEncodingBuilder().add("phone",phone).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+POINT_RECORD).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });

            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            List<PointData> datas = new ArrayList<>();
                            Gson gson = new Gson();
                            for (int i = 0 ; i < jsonArray.length() ; i++ ){
                                datas.add(gson.fromJson(jsonArray.getJSONObject(i).toString(),PointData.class));
                            }
                            listener.onSuccess(jsonObject.getString("error"), jsonObject.getString("message"), jsonObject.getString("name"),
                                    jsonObject.getString("savings"), jsonObject.getDouble("sum"), datas);
                        } catch (JSONException e) {
                            listener.onFail(e);
                        }
                    }
                });

            }
        });
    }


    public interface OnGetFullInfoListener{
        void onFail(Exception e);
        void onSuccess(TaskInfoFullData data);
    }
    /**任務詳細資訊(乘客端和司機端共用)*/
    public static void getFullTaskInfo(@NonNull final Activity activity, String tasknumber, String apiKey, @NonNull final OnGetFullInfoListener listener){
        RequestBody body = new FormEncodingBuilder().add("tasknumber",tasknumber).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+GET_TASK_INFO_FULL).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });

            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String body = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Gson gson = new Gson();
                            listener.onSuccess(gson.fromJson(body,TaskInfoFullData.class));
                        } catch (Exception e) {
                            listener.onFail(e);
                        }
                    }
                });

            }
        });
    }


    public interface OnLoginOutListener{
        void onFail(Exception e);
        void onSuccess(boolean isError, String message);
    }
    /**會員登出(更新)*/
    public static void loginOut(@NonNull final Activity activity, String phone, String apiKey, @NonNull final OnLoginOutListener listener){
        RequestBody body = new FormEncodingBuilder().add("phone",phone).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+LOGIN_OUT).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e);
                    }
                });

            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String body = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(body);
                            listener.onSuccess(jsonObject.getBoolean("error"),jsonObject.getString("message"));
                        } catch (JSONException e) {
                            listener.onFail(e);
                        }
                    }
                });

            }
        });
    }
}