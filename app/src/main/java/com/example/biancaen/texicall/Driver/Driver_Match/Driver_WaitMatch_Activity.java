package com.example.biancaen.texicall.Driver.Driver_Match;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

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

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            driverData = (DriverData)bundle.getSerializable("driverData");
            phone = bundle.getString("phone");
            password = bundle.getString("password");
        }

        //經由推播進來時出現Dialog
        if (getTaskNumber!=null){

            ReLogin();

        }else {

            /**2017/06/28 取tasknumber*/
            HBMessageService.setOnGetTasknumber(new HBMessageService.OnStartTaskAlertListener() {
                @Override
                public void onGetTasknumber(String tasknumber) {
                    Log.v("ppking2897 ", "HBMessageService  :  " + tasknumber);
                    getTaskNumber = tasknumber;
                    GetTaskInfo();
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("driverData" , driverData);
        Log.v("ppking2897 ", "onSaveInstanceState driverData !! " + driverData);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.getSerializable("driverData");
        Log.v("ppking2897 ", "onRestoreInstanceState driverData !! " + driverData);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
                }else{
                    driver_matched_dialog = null;
                }
            }
        });
    }

    public void ClearTaskNumber(){
        getTaskNumber = null;
    }

    public void AcceptMission(){
        Connect_API.accepttask(Driver_WaitMatch_Activity.this, getTaskNumber, phone, driverData.getApiKey(), new Connect_API.OnGetConnectStatusListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , "Exception   : " + e);
                Log.v("ppking" , "jsonError   : " + jsonError);
            }

            @Override
            public void onSuccess(String isError, String message) {
                Log.v("ppking" , "isError   : " + isError);
                Log.v("ppking" , "message   : " + message);
                Bundle bundle = new Bundle();
                bundle.putSerializable("taskInfoData" , getTaskInfoData);
                Intent it = new Intent(Driver_WaitMatch_Activity.this , Driver_Passenger_Request_Activity.class );
                it.putExtras(bundle);
                Driver_WaitMatch_Activity.this.startActivity(it);
            }
        });
    }

    public void ReLogin(){
        SharedPreferences sharedPreferences = getSharedPreferences("driver" , MODE_PRIVATE);
        String newpassword =sharedPreferences.getString("password", null);
        String newphone =sharedPreferences.getString("phone", null);
        Log.v("ppking" , "password " + sharedPreferences.getString("password", null));
        Log.v("ppking" , "phone " + sharedPreferences.getString("phone", null));


        Connect_API.driverLogin(this, newphone, newpassword, new Connect_API.OnDriverLoginListener() {
            @Override
            public void onLoginSuccess(DriverData newDriverData) {

                driverData = newDriverData;
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
}
