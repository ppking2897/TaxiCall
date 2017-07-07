/*2017/06/22 全部替換部分
* error值型別全變更為String
* 這部分更動會記錄在此
* 所有 onFail() 的方法後面新增通知用字串
* 非型別轉換錯誤的屬於API問題
* 屬於型別錯誤可以取出來原型字串數據
* ************************************
* 2017/06/26 更換 忘記密碼須帶入的項目
* *************************************
* 2017/06/28 新增窗口
* 新增 司機資料變更 功能
* 更新 結算 功能
* 新增 司機忘記密碼 功能
* 新增 設定常用地址 功能
* 新增 取得常用地址 功能
* 新增 取得網路回傳用(常用型)監聽器，替換原設計監聽器
* 替換項目: 忘記密碼(乘客端)，取消配對，接受任務(司機端)
* ，即時更新司機座標，客戶取消任務，客戶取消任務，司機取消任務
* ，司機已抵達，乘客已上車。
* ************************************************************
* 2017/06/29 新增在rate功能當超過金額後的另一個判斷
* ************************************************************/

package com.example.biancaen.texicall.connectapi;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.firebase.iid.FirebaseInstanceId;
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

    private static String debugTAG_ValueType = "JSON轉換型別異常 => ";
    private static String debugTAG_NOT_ValueType = "非型別錯誤，API連線產生的IO例外錯誤";

    //HOST位置
    private static final String API_HOST = "http://188.166.213.209/hb";
    //應該是版本
    private static final String API_VERSION = "/v1";

    //註冊及會員相關
    private static final String REGISTER = "/register";
    private static final String LOGIN = "/login";
    private static final String FORGOT = "/forget";
    private static final String FORGOT_DRIVER = "/forgetdriver";
    private static final String LOGIN_OUT = "/loginout";
    private static final String MODIFY = "/modify";
    private static final String DRIVER_MODIFY = "/modifydriver";
    private static final String GET_STATUS = "/getstatus";
    private static final String PUT_STATUS = "/putstatus";
    private static final String GET_DRIVER_STATUS = "/getdriverstatus";
    private static final String PUT_DRIVER_STATUS = "/putdriverstatus";
    private static final String RECORD_LIST = "/recordlist";
    private static final String RECORD_LIST_DRIVER = "/recordlistdriver";
    private static final String GET_TASK_INFO_FULL = "/gettaskfullinfo";
    private static final String SET_FAVORITE = "/setfavorite";
    private static final String GET_FAVORITE= "/getfavorite";

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


    /**新增常用型監聽器 2017/06/28*/
    public interface OnGetConnectStatusListener{
        void onFail(Exception e, String jsonError);
        void onSuccess(String isError, String message);
    }


    public interface OnRegisterListener{
        void onRegisterSuccessListener(String isFail, String message);
        void onRegisterFailListener(Exception e, String jsonError);
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
                        listener.onRegisterFailListener(e , debugTAG_NOT_ValueType);
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
                            listener.onRegisterSuccessListener(jsonObject.getString("error"),jsonObject.getString("message"));
                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onRegisterFailListener(e , jsonError);
                        }
                    }
                });
            }
        });
    }


    public interface OnUserLoginListener {
         void onLoginSuccess(UserData userData);
         void onLoginFail(String isFail, String msg);
         void onFail(Exception e, String jsonError);
     }
     /**乘客登入2017/06/19 更動*/
    public static void userLogin(@NonNull final Activity activity, final String phone , String password , @NonNull final OnUserLoginListener listener){
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
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            if (object.getString("error").equals("true")){
                                listener.onLoginFail(object.getString("error"),object.getString("message"));
                            }else {
                                Gson gson = new Gson();
                                UserData userData = gson.fromJson(body, UserData.class);

                                //TODO 登入後送出推播用參數
                                getStatus(activity, phone, userData.getApiKey(), new OnGetStatusListener() {
                                    @Override
                                    public void onFail(Exception e, String jsonError) {

                                    }

                                    @Override
                                    public void onSuccess(String isError, String result, int status, String tasknumber, int misscatch_time, int misscatch_price) {

                                    }
                                });

                                listener.onLoginSuccess(userData);
                            }
                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });
            }
        });
    }


    /**忘記密碼(乘客端) 2017/06/22 新增
	2017/06/26 修正*/
    public static void passengerForgot(@NonNull final Activity activity, String phone, @NonNull final OnGetConnectStatusListener listener){
        RequestBody body = new FormEncodingBuilder().add("phone" , phone).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+FORGOT).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e , debugTAG_NOT_ValueType);
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
                            listener.onSuccess(object.getString("error"),object.getString("message"));
                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });
            }
        });
    }

    /**忘記密碼(司機端) 2017/06/28 新增*/
    public static void driverForgot(@NonNull final Activity activity, String phone, @NonNull final OnGetConnectStatusListener listener){
        RequestBody body = new FormEncodingBuilder().add("phone" , phone).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+FORGOT_DRIVER).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e , debugTAG_NOT_ValueType);
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
                            listener.onSuccess(object.getString("error"),object.getString("message"));
                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });
            }
        });
    }


    public interface OnDriverLoginListener {
        void onLoginSuccess(DriverData driverData);
        void onLoginFail(String isFail, String msg);
        void onFail(Exception e, String jsonError);
    }
    /**駕駛登入 2017/06/19 更動*/
    public static void driverLogin(@NonNull final Activity activity, final String phone , String password , @NonNull final OnDriverLoginListener listener){
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
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                                listener.onLoginFail(object.getString("error"),object.getString("message"));
                            }else {
                                //推播數據送出
                                DriverData driverData = gson.fromJson(body, DriverData.class);
                                getdriverstatus(activity, phone, driverData.getApiKey(), new OnGetDriverStatusListener() {
                                    @Override
                                    public void onFail(Exception e, String jsonError) {

                                    }

                                    @Override
                                    public void onSuccess(String isError, String result, String status, String tasknumber, String carshow, String carnumber) {

                                    }
                                });

//                                /**上傳位置測試用*/
//                                updateLocation(activity, "120.6650702", "24.1315638", phone, driverData.getApiKey(), new OnGetConnectStatusListener() {
//                                    @Override
//                                    public void onFail(Exception e, String jsonError) {
//
//                                    }
//
//                                    @Override
//                                    public void onSuccess(String isError, String message) {
//                                    }
//                                });

                                listener.onLoginSuccess(driverData);
                            }
                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });

            }
        });
    }


    public interface OnModifyChangeListener{
        void onSuccess(String isFail, String msg);
        void onFail(Exception e, String jsonError);
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
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            listener.onSuccess(jsonObject.getString("error"),jsonObject.getString("message"));

                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });

            }
        });
    }


    /**司機資料變更 2017/06/28*/
    public static void driverModify(@NonNull final Activity activity, String email, String phone, String oldpassword, String password,
                                    String name, String carnumber, String carshow, String apiKey , @NonNull final OnModifyChangeListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("email" , email)
                .add("oldpassword" , oldpassword)
                .add("password" , password)
                .add("name" , name)
                .add("phone" , phone)
                .add("carnumber" , carnumber)
                .add("carshow" , carshow).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+DRIVER_MODIFY).header("Authorization" ,""+ apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            listener.onSuccess(jsonObject.getString("error"),jsonObject.getString("message"));

                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });

            }
        });

    }

    public interface  OnGetStatusListener{
        void onFail(Exception e, String jsonError);
        /**@param  status
                *1->非任務中
                *2->任務中*/
        void onSuccess(String isError, String result, int status, String tasknumber, int misscatch_time, int misscatch_price);
    }
    /**客戶端打開App 2017/06/22 修改*/
    public static void getStatus(@NonNull final Activity activity, String phone, String apiKey , @NonNull final OnGetStatusListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("phone" , phone)
                .add("os" , "1")
                .add("token", FirebaseInstanceId.getInstance().getToken()).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+GET_STATUS).header("Authorization" ,""+ apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            String error = jsonObject.getString("error");
                            String result = jsonObject.getString("result");
                            int status = jsonObject.getInt("status");
                            int misscatch_time = jsonObject.getInt("misscatch_time");
                            int misscatch_price = jsonObject.getInt("misscatch_price");
                            if (status == 2){
                                String tasknumber = jsonObject.getString("tasknumber");
                                listener.onSuccess(error,result,status,tasknumber,misscatch_time,misscatch_price);
                                return;
                            }
                            listener.onSuccess(error,result,status,null,misscatch_time,misscatch_price);
                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });
            }
        });
    }


    public interface OnPutStatusListener {
        void onFail(Exception e, String jsonError);
        void onSuccess(String isError, String result);
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
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            listener.onSuccess(jsonObject.getString("error"),jsonObject.getString("result"));
                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });
            }
        });
    }


    public interface OnGetDriverStatusListener{
        void onFail(Exception e, String jsonError);
        /**@param  status
                *1->下線
                * 2->上線
                * 3->任務中*/
        void onSuccess(String isError, String result, String status, String tasknumber, String carshow, String carnumber);
    }
    /**司機端打開App(更新)  2017/06/29 修正*/
    public static void getdriverstatus(@NonNull final Activity activity, String phone, String apiKey , @NonNull final OnGetDriverStatusListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("phone" , phone)
                .add("os" , "1")
                .add("token",FirebaseInstanceId.getInstance().getToken()).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+GET_DRIVER_STATUS).header("Authorization",""+apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            String isError = jsonObject.getString("error");
                            String result = jsonObject.getString("result");
                            String status = jsonObject.getString("status");
                            String carshow = jsonObject.getString("carshow");
                            String carnumber = jsonObject.getString("carnumber");
                            if (status.equals("3")){
                                String tasknumber = jsonObject.getString("tasknumber");
                                listener.onSuccess(isError,result,status,tasknumber,carshow,carnumber);
                                return;
                            }
                            listener.onSuccess(isError,result,status,null,carshow,carnumber);
                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });

            }
        });
    }


    public interface OnPutDriverStatusListener{
        void onFail(Exception e, String jsonError);
        void onSuccess(String isError, String result);
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
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            listener.onSuccess(jsonObject.getString("error"),jsonObject.getString("result"));
                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });
            }
        });
    }


    public interface OnRateListener{
        void onFail(Exception e, String jsonError);
        void onFailToResult(String isErrorResult, String errorMessage);
        void onSuccess(String isErrorResult, int price, int time, String distance);
    }
    /**
     客戶發出派車試算需求
     起始經緯度及終點經緯度
     2017/06/22 更新*/
    public static void rate(@NonNull final Activity activity, String start_lon , String start_lat,
                            String end_lon, String end_lat, String phone, String apiKey , @NonNull final OnRateListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("addr_start_lat" , start_lon)
                .add("addr_start_lon" , start_lat)
                .add("addr_end_lat" , end_lon)
                .add("addr_end_lon" , end_lat)
                .add("phone" , phone).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+RATE).header("Authorization" , ""+ apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e,debugTAG_NOT_ValueType);
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String body = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(body);
                            listener.onSuccess(
                                    jsonObject.getString("result"),
                                    jsonObject.getInt("price"),
                                    jsonObject.getInt("time"),
                                    jsonObject.getString("distance"));
                        } catch (JSONException e) {
                            try {
                                if (jsonObject != null)
                                    listener.onFailToResult(jsonObject.getString("result"),jsonObject.getString("price"));
                            }catch (Exception je){
                                String jsonError = debugTAG_ValueType + body;
                                listener.onFail(je , jsonError);
                            }
                        }
                    }
                });
            }
        });
    }

    public interface OnNewTaskListener{
        void onFail(Exception e, String jsonError);
        void onSuccess(String isError, String message, String tasknumber);
    }
    /**客戶發出派車需求*/
    public static void newTask(@NonNull final Activity activity,
                               String start_lon , String start_lat , String start_addr ,
                               String end_lon , String end_lat , String end_addr ,
                               String phone , String passenger_number , String comment ,
                               String apiKey ,@NonNull final OnNewTaskListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("addr_start_lat" , start_lon)
                .add("addr_start_lon" , start_lat)
                .add("addr_start_addr" , start_addr)
                .add("addr_end_lat" , end_lon)
                .add("addr_end_lon" , end_lat)
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
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            listener.onSuccess(jsonObject.getString("error"),jsonObject.getString("message"),jsonObject.getString("tasknumber"));
                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });
            }
        });
    }


    public interface OnStartTaskListener{
        void onFail(Exception e, String jsonError);
        void onSuccess(String isError, String message);
    }
    /**開始配對*/
    public static void starttask(@NonNull final Activity activity, String start_lon , String start_lat ,
                                 String phone , String tasknumber , String apiKey ,
                                 @NonNull final OnStartTaskListener listener){
        RequestBody body = new FormEncodingBuilder( )
                .add("addr_start_lat" , start_lon)
                .add("addr_start_lon" , start_lat)
                .add("phone" , phone)
                .add("tasknumber" , tasknumber).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+START_TASK).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            listener.onSuccess(jsonObject.getString("error"),jsonObject.getString("message"));
                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });
            }
        });
    }


    /**取消配對*/
    public static void cancelTask(@NonNull final Activity activity, String tasknumber , String apiKey , @NonNull final OnGetConnectStatusListener listener){
        RequestBody body = new FormEncodingBuilder().add("tasknumber" ,tasknumber).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+CANCEL_TASK).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            listener.onSuccess(jsonObject.getString("error"),jsonObject.getString("message"));
                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });
            }
        });
    }


    public interface OnGetPairInfoListener{
        void onFail(Exception e, String jsonError);
        void onSuccessGetPairInfo(PairInfoData pairInfoData);
        void onWaiting(String isError, String message);
    }
    /**取得結果*/
    public static void getpairinfo(@NonNull final Activity activity, String tasknumber , String apiKey , @NonNull final OnGetPairInfoListener listener){
        RequestBody body = new FormEncodingBuilder().add("tasknumber" ,tasknumber).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+GET_PAIR_INFO).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                                listener.onWaiting(object.getString("error"),object.getString("message"));
                            }else {
                                Gson gson = new Gson();
                                listener.onSuccessGetPairInfo(gson.fromJson(body, PairInfoData.class));
                            }
                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });
            }
        });
    }


    /**接受任務(司機端)*/
    public static void accepttask(@NonNull final Activity activity, String tasknumber , String phone , String apiKey , @NonNull final OnGetConnectStatusListener listener){
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
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            listener.onSuccess(jsonObject.getString("error"),jsonObject.getString("message"));
                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });
            }
        });
    }


    public interface OnTaskInfoListener{
        void onFail(Exception e, String jsonError);
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
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });
            }
        });
    }


    /**即時更新司機座標(司機端)
     * 皆放入司機端訊息*/
    public static void updateLocation(@NonNull final Activity activity, String lon , String lat , String phone , String apiKey , @NonNull  final OnGetConnectStatusListener listener){
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
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            listener.onSuccess(jsonObject.getString("error"),jsonObject.getString("message"));
                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });

            }
        });
    }


    public interface OnWaitTimeListener{
        void onFail(Exception e, String jsonError);
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
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            listener.onSuccess(jsonObject.getString("error"),jsonObject.getString("task_status"),jsonObject.getString("distance"),jsonObject.getInt("time"));
                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });
            }
        });
    }


    /**客戶取消任務*/
    public static void terminateByPassenger(@NonNull final Activity activity, String tasknumber, String apiKey, final OnGetConnectStatusListener listener){
        RequestBody body = new FormEncodingBuilder().add("tasknumber" ,tasknumber).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+TERMINATE_PASSENGER).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            listener.onSuccess(jsonObject.getString("error"),jsonObject.getString("message"));
                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });
            }
        });
    }


    /**司機取消任務*/
    public static void terminateByDriver(@NonNull final Activity activity, String tasknumber, String apiKey, @NonNull final OnGetConnectStatusListener listener){
        RequestBody body = new FormEncodingBuilder().add("tasknumber" ,tasknumber).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+TERMINATE_DRIVER).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            listener.onSuccess(jsonObject.getString("error"),jsonObject.getString("message"));
                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });
            }
        });
    }


    /**司機已抵達*/
    public static void pickup(@NonNull final Activity activity, String tasknumber , String apiKey , @NonNull final OnGetConnectStatusListener listener){
        RequestBody body = new FormEncodingBuilder().add("tasknumber" ,tasknumber).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+PICKUP).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            listener.onSuccess(jsonObject.getString("error"),jsonObject.getString("message"));
                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });
            }
        });
    }


    /**乘客已上車*/
    public static void takeride(@NonNull final Activity activity, String tasknumber , String apiKey , @NonNull final OnGetConnectStatusListener listener){
        RequestBody body = new FormEncodingBuilder().add("tasknumber" ,tasknumber).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+TAKE_RIDE).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            listener.onSuccess(jsonObject.getString("error"),jsonObject.getString("message"));
                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });

            }
        });
    }


    public interface OnRealPriceListener{
        void onFail(Exception e, String jsonError);
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
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            listener.onSuccess(jsonObject.getString("error"), jsonObject.getString("message"), jsonObject.getString("distance"));
                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });

            }
        });
    }

    //TODO ======================== 2017/06/19 更新=============================

    public interface OnCheckOutListener{
        void onFail(Exception e, String jsonError);
        void onSuccess(CheckOutData data);
    }
    /**結算 2017/06/28 更新*/
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
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            listener.onSuccess(gson.fromJson(body,CheckOutData.class));
                        } catch (Exception e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });

            }
        });
    }


    public interface OnRecordListListener{
        void onFail(Exception e, String jsonError);
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
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            List<RecordPassengerData> datas = new ArrayList<>();
                            Gson gson = new Gson();
                            for (int i = 0 ; i < jsonArray.length() ; i++ ){
                                datas.add(gson.fromJson(jsonArray.getJSONObject(i).toString(),RecordPassengerData.class));
                            }
                             listener.onSuccess(jsonObject.getString("error"), jsonObject.getString("message"), datas);
                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });

            }
        });
    }


    public interface OnRecordListDriverListener{
        void onFail(Exception e, String jsonError);
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
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            List<RecordDriverData> datas = new ArrayList<>();
                            Gson gson = new Gson();
                            for (int i = 0 ; i < jsonArray.length() ; i++ ){
                                datas.add(gson.fromJson(jsonArray.getJSONObject(i).toString(),RecordDriverData.class));
                            }
                            listener.onSuccess(jsonObject.getString("error"), jsonObject.getString("message"), datas);
                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });

            }
        });
    }


    public interface OnPointRecordListener{
        void onFail(Exception e, String jsonError);
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
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            List<PointData> datas = new ArrayList<>();
                            Gson gson = new Gson();
                            for (int i = 0 ; i < jsonArray.length() ; i++ ){
                                datas.add(gson.fromJson(jsonArray.getJSONObject(i).toString(),PointData.class));
                            }
                            listener.onSuccess(jsonObject.getString("error"), jsonObject.getString("message"), jsonObject.getString("name"),
                                    jsonObject.getString("savings"), jsonObject.getDouble("sum"), datas);
                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });

            }
        });
    }


    public interface OnGetFullInfoListener{
        void onFail(Exception e, String jsonError);
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
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });

            }
        });
    }


    public interface OnLoginOutListener{
        void onFail(Exception e, String jsonError);
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
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });

            }
        });
    }

    /* ********************-----------2017/06/28 更新新項目--------------****************************/

    /**設定常用地址 2017/06/28 新增*/
    public static void setFavoriteAddress(@NonNull final Activity activity, String phone, String tasknumber, String apiKey, @NonNull final OnGetConnectStatusListener listener){
        RequestBody body = new FormEncodingBuilder()
                .add("tasknumber",tasknumber)
                .add("phone",phone).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+SET_FAVORITE).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            listener.onSuccess(object.getString("error"),object.getString("message"));

                        } catch (JSONException e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });

            }
        });
    }


    public interface OnGetFavoriteAddressListener{
        void onFail(Exception e, String jsonError);
        void onSuccess(List<FavoriteAddressData> datas);
    }
    /**取得常用地址 2017/06/28 新增*/
    public static void getFavoriteAddress(@NonNull final Activity activity, String phone, String apiKey, @NonNull final OnGetFavoriteAddressListener listener){
        RequestBody body = new FormEncodingBuilder().add("phone",phone).build();
        Request request = new Request.Builder().url(API_HOST+API_VERSION+GET_FAVORITE).header("Authorization",apiKey).post(body).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail(e,debugTAG_NOT_ValueType);
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
                            List<FavoriteAddressData> list = new ArrayList<>();
                            JSONObject object = new JSONObject(body);
                            JSONArray src = object.getJSONArray("data");
                            for (int i = 0 ; i < src.length() ; i++){
                                list.add(gson.fromJson(src.getJSONObject(i).toString(),FavoriteAddressData.class));
                            }
                            listener.onSuccess(list);
                        } catch (Exception e) {
                            String jsonError = debugTAG_ValueType + body;
                            listener.onFail(e , jsonError);
                        }
                    }
                });

            }
        });
    }
}
