package com.example.biancaen.texicall.connectapi;

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
 */
public class Connect_API{

    private static String debugTag = Connect_API.class.getName();
    //HOST位置
    private static final String API_HOST = "http://188.166.213.209/hb";
    //應該是版本
    private static final String API_VERSION = "/v1";

    //註冊及會員相關
    private static final String REGISTER = "/register";
    private static final String LOGIN = "/login";
    private static final String GET_STATUS = "/getstatus";
    private static final String PUT_STATUS = "/putstatus";
    private static final String GET_DRIVER_STATUS = "/getdriverstatus";
    private static final String PUT_DRIVER_STATUS = "/putdriverstatus";

    //計費
    private static final String RATE = "/rate";
    private static final String NEW_TASK = "/newtask";
    private static final String START_TASK = "/starttask";
    private static final String CANCEL_TASK = "/canceltask";
    private static final String GET_PAIR_INFO = "/getpairinfo";
    private static final String ACCEPT_TASK = "/accepttask";
    private static final String TASK_INFO = "/taskinfo";

    //等待及更新距離
    private static final String UPDATE_LOCATION = "/updateLocation";
    private static final String WAIT_TIME = "/waittime";
    private static final String PICKUP = "/pickup";
    private static final String TAKE_RIDE = "/takeride";
    private static final String REAL_PRICE = "/realprice";

    public interface OnRegisterListener{
        void onRegisterSuccessListener(boolean isFail , String message);
        void onRegisterFailListener(Exception e);
    }
    /** 會員註冊*/
    public static void register(String name , String password , String email , String phone ,@NonNull final OnRegisterListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("name" , name)
                .add("password" , password)
                .add("email" , email)
                .add("phone" , phone).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+REGISTER).post(body).build();
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

    public interface OnLoginListener {
         void onLoginSuccess(UserData userData);
         void onLoginFail(Exception e);
     }
     /**登入*/
    public static void login(String phone , String password , @NonNull final OnLoginListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("password" , password)
                .add("phone" , phone).build();

        Request request = new Request.Builder().url(API_HOST+API_VERSION+LOGIN).post(body).build();
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

    public interface  OnGetStatusListener{
        void onFail(Exception e);
        void onSuccess(String result , int status);
    }
    /**客戶端打開App*/
    public static void getStatus(String phone , String apiKey ,@NonNull final OnGetStatusListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("phone" , phone)
                .add("os" , "1").build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+GET_STATUS).header("Authorization" ,""+ apiKey).post(body).build();
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

    public interface OnPutStatusListener {
        void onFail(Exception e);
        void onSuccess(boolean isFail , String result);
    }
    /**客戶端上傳狀態*/
    public static void putstatus(String phone , String status, String apiKey,@NonNull final OnPutStatusListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("phone" , phone)
                .add("status" , status).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+PUT_STATUS).header("Authorization",""+apiKey).post(body).build();
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

    public interface OnGetDriverStatusListener{
        void onFail(Exception e);
        void onSuccess(String result , int status);
    }
    /**司機端打開App(更新)*/
    public static void getdriverstatus(String phone ,String apiKey ,@NonNull final OnGetDriverStatusListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("phone" , phone)
                .add("os" , "1").build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+GET_DRIVER_STATUS).header("Authorization",""+apiKey).post(body).build();
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

    public interface OnPutDriverStatusListener{
        void onFail(Exception e);
        void onSuccess(boolean isFail , String result);
    }
    /**司機端上傳狀態*/
    public static void putdriverstatus(String phone , String status , String apiKey ,@NonNull final OnPutDriverStatusListener listener ){
        RequestBody body = new FormEncodingBuilder()
                .add("phone" , phone)
                .add("status" , status).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+PUT_DRIVER_STATUS).header("Authorization",""+apiKey).post(body).build();

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

    public interface OnRateListener{
        void onFail(Exception e);
        void onSuccess(boolean isErrorResult, int price, int time, String distance);
    }
    /**
     客戶發出派車試算需求
     起始經緯度及終點經緯度
     */
    public static void rate(String start_lon , String start_lat,
                            String end_lon , String end_lat , String apiKey ,@NonNull final OnRateListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("addr_start_lon" , start_lon)
                .add("addr_start_lat" , start_lat)
                .add("addr_end_lon" , end_lon)
                .add("addr_end_lat" , end_lat).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+RATE).header("Authorization" , ""+ apiKey).post(body).build();
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

    public interface OnNewTaskListener{
        void onFail(Exception e);
        void onSuccess(boolean isFail, String message, String tasknumber);
    }
    /**客戶發出派車需求*/
    public static void newTask(String start_lon , String start_lat , String start_addr ,
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

    public interface OnStartTaskListener{
        void onFail(Exception e);
        void onSuccess(boolean isFail, String message);
    }
    /**開始配對*/
    public static void starttask(String start_lon , String start_lat ,
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

    public interface OnCancelTaskListener{
        void onFail(Exception e);
        void onSuccess(boolean isFail, String message);
    }
    /**取消配對*/
    public static void cancelTask(String tasknumber , String apiKey ,@NonNull final OnCancelTaskListener listener){
        RequestBody body = new FormEncodingBuilder().add("tasknumber" ,tasknumber).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+CANCEL_TASK).header("Authorization",apiKey).post(body).build();
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

    public interface OnGetPairInfoListener{
        void onFail(Exception e);
        void onSuccessGetPairInfo(PairInfoData pairInfoData);
        void onWaiting(boolean isError , String message);
    }
    /**取得結果*/
    public void getpairinfo(String tasknumber ,String apiKey ,@NonNull final OnGetPairInfoListener listener){
        RequestBody body = new FormEncodingBuilder().add("tasknumber" ,tasknumber).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+GET_PAIR_INFO).header("Authorization",apiKey).post(body).build();
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
                            listener.onSuccessGetPairInfo(gson.fromJson(s, PairInfoData.class));
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

    public interface OnAcceptTaskListener{
        void onFail(Exception e);
        void onSuccess(boolean isError ,String message);
    }
    /**接受任務*/
    public static void accepttask(String tasknumber , String phone , String apiKey ,@NonNull final OnAcceptTaskListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("phone" ,phone)
                .add("tasknumber" ,tasknumber)
                .build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+ACCEPT_TASK).header("Authorization",apiKey).post(body).build();
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

    public interface OnTaskInfoListener{
        void onFail(Exception e);
        void onSuccess(TaskInfoData taskInfoData);
    }
    /**司機端取得任務詳細資料*/
    public static void taskinfo(String tasknumber , String apiKey ,@NonNull  final OnTaskInfoListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("tasknumber" ,tasknumber)
                .build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+TASK_INFO).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onFail(e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String s = response.body().string();
                Gson gson = new Gson();
                listener.onSuccess(gson.fromJson(s,TaskInfoData.class));
            }
        });
    }

    public interface OnUpdateLocationListener{
        void onFail(Exception e);
        void onSuccess(boolean isError ,String message);
    }
    /**即時更新司機座標(司機端)
     * 皆放入司機端訊息*/
    public static void updateLocation(String lon , String lat , String phone , String apiKey ,@NonNull  final OnUpdateLocationListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("driver_lon" ,lon)
                .add("driver_lat" ,lat)
                .add("phone" ,phone)
                .build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+UPDATE_LOCATION).header("Authorization",apiKey).post(body).build();
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

    public interface OnWaitTimeListener{
        void onFail(Exception e);
        void onSuccess(boolean isError ,String task_status ,String distance ,String time);
    }
    /**客戶等待司機抵達*/
    public static void waittime(String tasknumber ,String apiKey ,@NonNull final OnWaitTimeListener listener){
        RequestBody body = new FormEncodingBuilder().add("tasknumber" ,tasknumber).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+WAIT_TIME).header("Authorization",apiKey).post(body).build();
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
                    listener.onSuccess(jsonObject.getBoolean("error"),jsonObject.getString("task_status"),jsonObject.getString("distance"),jsonObject.getString("time"));
                } catch (JSONException e) {
                    listener.onFail(e);
                }
            }
        });
    }

    public interface OnPickupListener{
        void onFail(Exception e);
        void onSuccess(boolean isError ,String message);
    }
    /**司機已抵達*/
    public static void pickup(String tasknumber ,String apiKey ,@NonNull final OnPickupListener listener){
        RequestBody body = new FormEncodingBuilder().add("tasknumber" ,tasknumber).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+PICKUP).header("Authorization",apiKey).post(body).build();
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

    public interface OnTakeRideListener{
        void onFail(Exception e);
        void onSuccess(boolean isError ,String message);
    }
    /**乘客已上車*/
    public static void takeride(String tasknumber ,String apiKey ,@NonNull final OnTakeRideListener listener){
        RequestBody body = new FormEncodingBuilder().add("tasknumber" ,tasknumber).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+TAKE_RIDE).header("Authorization",apiKey).post(body).build();
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

    public interface OnRealPriceListener{
        void onFail(Exception e);
        void onSuccess(boolean isError ,String message ,String distance ,String time ,String price);
    }
    /**乘客搭乘途中,實際距離和時間計算*/
    public static void realprice(String tasknumber ,String apiKey ,@NonNull final OnRealPriceListener listener){
        RequestBody body = new FormEncodingBuilder().add("tasknumber" ,tasknumber).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+REAL_PRICE).header("Authorization",apiKey).post(body).build();
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
                    listener.onSuccess(jsonObject.getBoolean("error"),jsonObject.getString("message"),
                            jsonObject.getString("distance"),jsonObject.getString("time"),jsonObject.getString("price"));
                } catch (JSONException e) {
                    listener.onFail(e);
                }
            }
        });
    }
}
