package com.example.biancaen.texicall.Beginning;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.view.Window;
import android.widget.ImageView;

import com.example.biancaen.texicall.Driver.Driver_Main_Menu.Driver_Main_Menu_Activity;
import com.example.biancaen.texicall.Driver.Driver_Trip.Driver_Arrived_Activity;
import com.example.biancaen.texicall.Driver.Driver_Trip.Driver_Google_Map_Activity;
import com.example.biancaen.texicall.Driver.Driver_Trip.Driver_On_The_Way_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Car_Service.Passenger_Car_Service_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Driver_Arrived.Passenger_Driver_Arrived_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_On_The_Way.Passenger_On_The_Way_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_TakeRide_And_Arrived.Passenger_In_The_Shuttle_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;

import java.util.Timer;
import java.util.TimerTask;

public class BeginningActivity extends AppCompatActivity {
    private ImageView logo ;

    private static final int TIME = 2000;
    private Timer timer;
    private boolean isExit = true;

    private String passengerApiKey;
    private String passengerTaskNumber;
    private String driverApiKey;
    private String driverTaskNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new ChangeTransform());
        setContentView(R.layout.activity_beginning);

        logo = (ImageView)findViewById(R.id.logo);
        ImageView welcome = (ImageView) findViewById(R.id.welcome);
        logo.animate().alpha(1.0f).setDuration(6000);
        welcome.animate().alpha(1.0f).setDuration(6000);


        SharedPreferences passengerPreferences = getSharedPreferences("passenger" , MODE_PRIVATE);
        passengerApiKey = passengerPreferences.getString("passengerApiKey", null);
        passengerTaskNumber = passengerPreferences.getString("taskNumber" , null);

        if (passengerApiKey !=null && passengerTaskNumber!=null){

            Connect_API.waittime(this, passengerTaskNumber, passengerApiKey, new Connect_API.OnWaitTimeListener() {
                @Override
                public void onFail(Exception e, String jsonError) {

                }

                @Override
                public void onSuccess(String isError, String task_status, String distance, int time) {
                    switch (task_status) {
                        case "2": {
                            Intent it = new Intent(BeginningActivity.this, Passenger_On_The_Way_Activity.class);
                            startActivity(it);
                            finish();
                            break;
                        }
                        case "3": {
                            Intent it = new Intent(BeginningActivity.this, Passenger_Driver_Arrived_Activity.class);
                            startActivity(it);
                            finish();
                            break;
                        }
                        case "4": {
                            Intent it = new Intent(BeginningActivity.this, Passenger_In_The_Shuttle_Activity.class);
                            startActivity(it);
                            finish();
                            break;
                        }
                        default: {
                            Intent it = new Intent(BeginningActivity.this, Passenger_Car_Service_Activity.class);
                            startActivity(it);
                            finish();
                            break;
                        }
                    }
                }
            });
        }

        SharedPreferences driverPreferences = getSharedPreferences("driver" , MODE_PRIVATE);
        driverApiKey = driverPreferences.getString("driverApiKey" , null);
        driverTaskNumber =driverPreferences.getString("tasknumber", null);
        if (driverApiKey !=null && driverTaskNumber != null){
            Connect_API.waittime(this, driverTaskNumber, driverApiKey, new Connect_API.OnWaitTimeListener() {
                @Override
                public void onFail(Exception e, String jsonError) {

                }

                @Override
                public void onSuccess(String isError, String task_status, String distance, int time) {
                    switch (task_status) {
                        case "2": {
                            Intent it = new Intent(BeginningActivity.this, Driver_On_The_Way_Activity.class);
                            startActivity(it);
                            finish();
                            break;
                        }
                        case "3": {
                            Intent it = new Intent(BeginningActivity.this, Driver_Arrived_Activity.class);
                            startActivity(it);
                            finish();
                            break;
                        }
                        case "4": {
                            Intent it = new Intent(BeginningActivity.this, Driver_Google_Map_Activity.class);
                            startActivity(it);
                            finish();
                            break;
                        }
                        default: {
                            Intent it = new Intent(BeginningActivity.this, Driver_Main_Menu_Activity.class);
                            startActivity(it);
                            finish();
                            break;
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        isExit = false;
        startTimer();
    }

    @Override
    public void onStop(){
        super.onStop();
        isExit = true;
        cancelTimer();
    }

    private void startTimer(){
        cancelTimer();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isExit) {
                            Intent it = new Intent(BeginningActivity.this, MainMenuActivity.class);
                            startActivity(it , ActivityOptions.makeSceneTransitionAnimation(BeginningActivity.this,logo,"logo").toBundle());
                            timer.cancel();
                            timer = null;
                            finish();
                        }
                    }
                });

            }
        }, TIME);
    }

    private void cancelTimer(){
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }
}
