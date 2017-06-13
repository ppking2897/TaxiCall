package com.example.biancaen.texicall.Passenger_Rates;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.biancaen.texicall.Passenger_On_The_Way.Passenger_On_The_Way_Activity;
import com.example.biancaen.texicall.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Passenger_Rates_Activity extends AppCompatActivity {
    private ArrayList<Fragment> views = new ArrayList<>();
    MyViewPager myViewPager;
    private boolean systemCountEnd = true;
    private boolean systemMatchEnd;
    private boolean isExit;
    private Timer countEndTimer , matchEndTimer;
    private int TIME = 2000;
    private int TimeArrive = 2000 ;
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

                            //Todo 判斷系統試算是否結束，若判斷結束則直接進入試算結果頁面
                            systemCountEnd = true;
                            Log.v("ppking" , " setCurrentItem : 0 !!! NO JUMP  ");
                            if (systemCountEnd){
                                myViewPager.setCurrentItem(1);
                                Log.v("ppking" , " setCurrentItem : 1  ");
                                countEndTimer.cancel();
                                countEndTimer = null;
                                systemMatchEndTimer();
                            }
                        }
                    }
                });

            }
        }, TIME , TimeArrive);
    }
    //系統配對Task
    private void systemMatchEndTimer(){
        cancelTimer();
        matchEndTimer = new Timer();
        matchEndTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isExit) {
                            Log.v("ppking" , " NO NO jump!!!!  ");
                            //Todo 判斷系統配對是否結束，若判斷結束則直接進入試算結果頁面
                            if (systemMatchEnd){
                                Log.v("ppking" , " jump!!!!  ");
                                matchEndTimer.cancel();
                                matchEndTimer = null;

                                Bundle getbundle = Passenger_Rates_Activity.this.getIntent().getExtras();
                                int time = getbundle.getInt("time");
                                Log.v("ppking" , " TIMEEEEEE" + time);

                                Bundle bundle = new Bundle();
                                bundle.putInt("time" , time);

                                Intent it = new Intent(Passenger_Rates_Activity.this , Passenger_On_The_Way_Activity.class);
                                it.putExtras(bundle);
                                startActivity(it);
                                finish();
                            }
                        }
                    }
                });

            }
        }, TIME , TimeArrive);
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


    //Todo 藉由此方法配斷是否配對成功
    public void isSystemMatchEnd (boolean systemMatchEnd){
        this.systemMatchEnd = systemMatchEnd;
    }

}
