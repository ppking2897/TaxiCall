package com.example.biancaen.texicall.Passenger.Passenger_Rates;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biancaen.texicall.Passenger.Passenger_Car_Service.Passenger_Car_Service_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_On_The_Way.Passenger_On_The_Way_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.PairInfoData;
import com.example.biancaen.texicall.connectapi.TaskInfoFullData;
import com.example.biancaen.texicall.connectapi.UserData;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Passenger_Rates_Activity extends AppCompatActivity {
    private Bundle getBundle;
    private static int time;
    private static UserData userData;
    private static String phoneNumber;
    private static String password;
    private String location , destination , passenger_number , comment;
    private String startLat , startLng , endLat , endLng;
    private String taskNumber;
    private static int emptyTripCount , emptyTripPay;
    private static int price ;
    private android.app.FragmentManager fragmentManager;
    private Rates_01_Fragment rates_01_fragment;
    private Rates_02_Fragment rates_02_fragment;
    private Rates_03_Fragment rates_03_fragment;
    private Timer timer;
    private boolean isStartMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger__rates_);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_Fares);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView faresEmptyTripCount = (TextView)findViewById(R.id.fares_Empty_Trip_Count);
        TextView faresEmptyTripPay = (TextView)findViewById(R.id.fares_Empty_Trip_ToPay);

        //由Car_Service 傳來空趟的資料
        Bundle bundle = this.getIntent().getExtras();
        if (bundle!=null){
            emptyTripCount = bundle.getInt("emptyTripCount");
            emptyTripPay = bundle.getInt("emptyTripPay");

            faresEmptyTripCount.setText(""+emptyTripCount);
            faresEmptyTripPay.setText(""+emptyTripPay);
        }

        rates_01_fragment = new Rates_01_Fragment();
        rates_02_fragment = new Rates_02_Fragment();
        rates_03_fragment = new Rates_03_Fragment();

        fragmentManager = getFragmentManager();

        fragmentManager.beginTransaction().add(R.id.fragment , rates_01_fragment)
                .replace(R.id.fragment , rates_01_fragment , "rates01")
                .commit();

        GetDataAndNewTask();

    }


    //系統配對Task
    public void systemMatchEnd(){

        Connect_API.starttask(Passenger_Rates_Activity.this,  startLng  , startLat  , phoneNumber, taskNumber, userData.getApiKey(), new Connect_API.OnStartTaskListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , " Exception : " + e.getMessage());
                Log.v("ppking" , " jsonError : " + jsonError);
                Toast.makeText(Passenger_Rates_Activity.this , "配對異常，請聯絡客服人員" , Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(String isError, String message) {
                Log.v("ppking", " isError : " + isError);
                Log.v("ppking", " message : " + message);

                if (timer == null && isError.equals("false")){
                    timer = new Timer();
                    timer.schedule(new TaskAndGetDriverData() ,  0 , 4000);
                    isStartMatch = true;
                }else if (isError.equals("true")){
                    Toast.makeText(Passenger_Rates_Activity.this , ""+message , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void GetDataAndNewTask(){
        getBundle = Passenger_Rates_Activity.this.getIntent().getExtras();
        password = getBundle.getString("passWord");

        userData =(UserData)getBundle.getSerializable("userData");
        phoneNumber = getBundle.getString("phoneNumber");

        location = getBundle.getString("location");
        destination = getBundle.getString("destination");
        passenger_number = getBundle.getString("passenger_number");
        comment = getBundle.getString("comment");

        startLat = getBundle.getString("startLat");
        startLng = getBundle.getString("startLng");
        endLat = getBundle.getString("endLat");
        endLng = getBundle.getString("endLng");

        Connect_API.newTask(
        Passenger_Rates_Activity.this, startLng, startLat, location, endLng, endLat,
        destination, phoneNumber, passenger_number, comment, userData.getApiKey(),
        new Connect_API.OnNewTaskListener() {

            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , "Exception  : "+e.getMessage());
                Log.v("ppking" , " jsonError  : " + jsonError);
            }

            @Override
            public void onSuccess(String isError, String message, String tasknumber) {
                Log.v("ppking" , " isError  : " + isError);
                Log.v("ppking" , " message  : " + message);
                Log.v("ppking" , " tasknumber  : " + tasknumber);
                taskNumber = tasknumber;
                SharedPreferences sharedPreferences = getSharedPreferences("passenger" , MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("taskNumber" , taskNumber);
                editor.apply();
            }
        });
    }

    public void MainGetPrice(int price){
        Passenger_Rates_Activity.price = price;
    }

    public void MainGetTime(int time){
        Passenger_Rates_Activity.time = time;
    }

    public int GetMainPrice(){
        fragmentManager.beginTransaction().replace(R.id.fragment , rates_02_fragment).commit();
        return (price+emptyTripPay);
    }

    public class TaskAndGetDriverData extends TimerTask{

        @Override
        public void run() {
            Connect_API.getpairinfo(Passenger_Rates_Activity.this, taskNumber, userData.getApiKey(), new Connect_API.OnGetPairInfoListener() {
                @Override
                public void onFail(Exception e, String jsonError) {
                    Log.v("ppking" , "getpairinfo Exception : " + e.getMessage());
                    Log.v("ppking" , "getpairinfo jsonError : " + jsonError);
                }

                @Override
                public void onSuccessGetPairInfo(PairInfoData pairInfoData) {

                    if (pairInfoData.getMessage().equals("搜尋資訊成功")){
                        timer.cancel();
                        timer=null;

                        Bundle bundle = new Bundle();
                        bundle.putInt("time" , time);
                        bundle.putString("location" , location);
                        bundle.putSerializable("pairInfoData" , pairInfoData);
                        bundle.putSerializable("userData" , userData);
                        bundle.putString("taskNumber", taskNumber);
                        SharedPreferences sharedPreferences = getSharedPreferences("passenger" , MODE_PRIVATE);
                        sharedPreferences.edit().putString("taskNumber" , taskNumber).apply();
                        Intent it = new Intent(Passenger_Rates_Activity.this , Passenger_On_The_Way_Activity.class);
                        it.putExtras(bundle);
                        startActivity(it);
                        finish();
                    }
                }

                @Override
                public void onWaiting(String isError, String message) {
                    Log.v("ppking" , "getpairinfo isError : " + isError);
                    Log.v("ppking" , "getpairinfo message : " + message);
                }
            });
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (isStartMatch){
            timer = new Timer();
            timer.schedule(new TaskAndGetDriverData() ,  1000 , 4000);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null){
            timer.cancel();
            timer=null;
        }
    }

    @Override
    public void onBackPressed() {
        SharedPreferences sharedPreferences = getSharedPreferences("passenger" , MODE_PRIVATE);
        String passengerApiKey = sharedPreferences.getString("passengerApiKey" , null);
        Connect_API.cancelTask(this, taskNumber, passengerApiKey , new Connect_API.OnGetConnectStatusListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , "cancelTask Exception :  " + e);
                Log.v("ppking" , "cancelTask jsonError :  " + jsonError);
            }

            @Override
            public void onSuccess(String isError, String message) {
                Log.v("ppking" , "cancelTask isError :  " + isError);
                Log.v("ppking" , "cancelTask message :  " + message);
                if (isError.equals("false")){
                    Toast.makeText(Passenger_Rates_Activity.this , ""+message , Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(Passenger_Rates_Activity.this , Passenger_Car_Service_Activity.class);
                    startActivity(it);
                }else{
                    Toast.makeText(Passenger_Rates_Activity.this , ""+message , Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(Passenger_Rates_Activity.this , Passenger_Car_Service_Activity.class);
                    startActivity(it);
                }
            }
        });
    }
}