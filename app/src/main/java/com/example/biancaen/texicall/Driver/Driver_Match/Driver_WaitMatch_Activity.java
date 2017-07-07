package com.example.biancaen.texicall.Driver.Driver_Match;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.biancaen.texicall.Driver.Driver_Main_Menu.Driver_Main_Menu_Activity;
import com.example.biancaen.texicall.Driver.Driver_Passenger_Request.Driver_Passenger_Request_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.DriverData;
import com.example.biancaen.texicall.connectapi.TaskInfoData;
import com.example.biancaen.texicall.notificaiton.HBMessageService;

public class Driver_WaitMatch_Activity extends AppCompatActivity {
    private static String getTaskNumber;
    private static DriverData driverData;
    private static String phone;
    private static String password;
    private static TaskInfoData getTaskInfoData;
    private Driver_Matched_Dialog driver_matched_dialog;
    private SharedPreferences sharedPreferences;

    //由dialog傳送資料並intent到另一個activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__wait_match_);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.driverWaitMatch);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0f , 1.0f);
        alphaAnimation.setDuration(800);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        linearLayout.setAnimation(alphaAnimation);

        sharedPreferences = getSharedPreferences("driver" , MODE_PRIVATE);
        phone = sharedPreferences.getString("phone" , null);
        password =  sharedPreferences.getString("password" , null);


        //經由推播進來時出現Dialog
        if (getTaskNumber!=null){

            ReLogin();
            Log.v("ppking ", "getTaskNumber  :  " + getTaskNumber);

        }else {

            /**2017/06/28 取tasknumber*/
            HBMessageService.setOnGetTasknumber(new HBMessageService.OnStartTaskAlertListener() {
                @Override
                public void onGetTasknumber(String tasknumber) {
                    Log.v("ppking ", "HBMessageService  :  " + tasknumber);
                    ReLogin();
                    getTaskNumber = tasknumber;
                    sharedPreferences = getSharedPreferences("driver" , MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("tasknumber" , tasknumber);
                    editor.apply();

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent(this , Driver_Main_Menu_Activity.class );
        startActivity(it);
    }

    public void GetTaskInfo(){
        Connect_API.taskinfo(Driver_WaitMatch_Activity.this, getTaskNumber, driverData.getApiKey(), new Connect_API.OnTaskInfoListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , "Exception   : " + e);
                Log.v("ppking" , "jsonError   : " + jsonError);
            }

            @Override
            public void onSuccess(TaskInfoData taskInfoData) {
                Log.v("ppking", "taskInfoData   : " + taskInfoData);

                getTaskInfoData = taskInfoData;

                if (driver_matched_dialog == null) {
                    driver_matched_dialog =
                            new Driver_Matched_Dialog(Driver_WaitMatch_Activity.this, taskInfoData, driverData, getTaskNumber, phone, password);

                    driver_matched_dialog.CreateMatchedDialog();
                    getTaskNumber = null;
                }else{
                    driver_matched_dialog = null;
                }
            }
        });
    }

    public void ClearTaskNumber(){
        getTaskNumber = null;
    }

    public void ReLogin(){

        Connect_API.driverLogin(this, phone, password, new Connect_API.OnDriverLoginListener() {
            @Override
            public void onLoginSuccess(DriverData newDriverData) {

                driverData = newDriverData;
                ChangeStatus("1");
                GetTaskInfo();
            }

            @Override
            public void onLoginFail(String isFail, String msg) {

            }

            @Override
            public void onFail(Exception e, String jsonError) {

            }
        });
    }

    //改變目前狀態為1  並且暫時不接收tasknumber  以便不重複接收

    public void ChangeStatus(String status){
        Connect_API.putdriverstatus(this, phone, status, driverData.getApiKey(), new Connect_API.OnPutDriverStatusListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking", "" + e.getMessage());
                Log.v("ppking", "jsonError : " + jsonError);

            }

            @Override
            public void onSuccess(String isError, String result) {
                Log.v("ppking", "isError :  " + isError);
                Log.v("ppking", "result : " + result);
            }
        });
    }
}
