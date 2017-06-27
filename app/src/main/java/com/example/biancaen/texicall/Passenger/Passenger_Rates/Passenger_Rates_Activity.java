package com.example.biancaen.texicall.Passenger.Passenger_Rates;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.biancaen.texicall.Passenger.Passenger_On_The_Way.Passenger_On_The_Way_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.UserData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Passenger_Rates_Activity extends AppCompatActivity {
    private ArrayList<Fragment> views = new ArrayList<>();
    MyViewPager myViewPager;
    private boolean systemCountEnd = true;
    private boolean isExit;
    private Timer countEndTimer , matchEndTimer;
    private int TIME = 2000;
    private int TimeArrive = 4500 ;
    private Bundle getBundle;
    private int time;
    private UserData userData;
    private String phoneNumber;
    private String location , destination , passenger_number , comment;
    private String startLat , startLng , endLat , endLng;
    private String taskNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger__fares_);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_Fares);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView faresEmptyTripCount = (TextView)findViewById(R.id.fares_Empty_Trip_Count);
        TextView faresEmptyTripPay = (TextView)findViewById(R.id.fares_Empty_Trip_ToPay);


        //由Car_Service 傳來空趟的資料
//        Bundle bundle = this.getIntent().getExtras();
//        int emptyTripCount = bundle.getInt("emptyTripCount");
//        int emptyTripPay = bundle.getInt("emptyTripPay");

//        faresEmptyTripCount.setText(""+emptyTripCount);
//        faresEmptyTripPay.setText(""+emptyTripPay);

        myViewPager =(MyViewPager) findViewById(R.id.faresViewPager);

        Rates_01_Fragment rates_01_fragment = new Rates_01_Fragment();
        Rates_02_Fragment rates_02_fragment = new Rates_02_Fragment();
        Rates_03_Fragment rates_03_fragment = new Rates_03_Fragment();

        views.add(rates_01_fragment);
        views.add(rates_02_fragment);
        views.add(rates_03_fragment);


        FragmentPagerAdapter fragmentPagerAdapter = new FaresFragmentAdapter01(getSupportFragmentManager());
        myViewPager.setAdapter(fragmentPagerAdapter);
        myViewPager.setCurrentItem(0);
        GetDataAndNewTask();
    }


    private class FaresFragmentAdapter01 extends FragmentPagerAdapter{

        FaresFragmentAdapter01(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return views.get(position);
        }

        @Override
        public int getCount() {
            return views.size();
        }
    }


    @Override
    public void onStart(){
        super.onStart();
        isExit = false;
        systemCountEndTimer();
    }

    @Override
    public void onStop(){
        super.onStop();
        isExit = true;
        cancelTimer();
    }
    //系統計算Task
    private void systemCountEndTimer(){
        cancelTimer();
        countEndTimer = new Timer();
        countEndTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isExit) {
                            systemCountEnd = true;
                            if (systemCountEnd){
                                myViewPager.setCurrentItem(1);
                                countEndTimer.cancel();
                                countEndTimer = null;
                            }
                        }
                    }
                });

            }
        }, TIME , TimeArrive);
    }
    //系統配對Task
    public void systemMatchEnd(){

        Log.v("ppking" , " jump!!!!  ");

        Log.v("ppking" , " startLng :  " +startLng );
        Log.v("ppking" , " startLat :  " +startLat );
        Log.v("ppking" , " phoneNumber :  " +phoneNumber );
        Log.v("ppking" , " taskNumber :  " +taskNumber );
        Log.v("ppking" , " userData.getApiKey() :  " +userData.getApiKey() );


        Connect_API.starttask(Passenger_Rates_Activity.this,  startLng  , startLat , phoneNumber, taskNumber, userData.getApiKey(), new Connect_API.OnStartTaskListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , " Exception : " + e.getMessage());
                Log.v("ppking" , " jsonError : " + jsonError);
            }

            @Override
            public void onSuccess(String isError, String message) {
                Log.v("ppking" , " isError : " + isError);
                Log.v("ppking" , " message : " + message);

            }
        });
//        final Bundle bundle = new Bundle();
//        bundle.putInt("time" , time);
//        bundle.putString("destination" , destination);
//
//
//        Intent it = new Intent(Passenger_Rates_Activity.this , Passenger_On_The_Way_Activity.class);
//        it.putExtras(bundle);
//        startActivity(it);
//        finish();

        }


    private void cancelTimer(){
        if(countEndTimer != null){
            countEndTimer.cancel();
            countEndTimer = null;
        }
        if(matchEndTimer != null){
            matchEndTimer.cancel();
            matchEndTimer = null;
        }
    }

    public void GetDataAndNewTask(){
        getBundle = Passenger_Rates_Activity.this.getIntent().getExtras();
        time = getBundle.getInt("time");

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
                Log.v("ppking" , ""+e.getMessage());
                Log.v("ppking" , " jsonError  : " + jsonError);
            }

            @Override
            public void onSuccess(String isError, String message, String tasknumber) {
                Log.v("ppking" , " isError  : " + isError);
                Log.v("ppking" , " message  : " + message);
                Log.v("ppking" , " tasknumber  : " + tasknumber);
                taskNumber = tasknumber;
            }
        });

    }
}