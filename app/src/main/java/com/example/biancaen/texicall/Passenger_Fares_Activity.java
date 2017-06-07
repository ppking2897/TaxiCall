package com.example.biancaen.texicall;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class Passenger_Fares_Activity extends AppCompatActivity {
    private ArrayList<Fragment> views = new ArrayList<>();
    private ViewPager viewPager;
    private boolean systemCountEnd = true;
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
        Bundle bundle = this.getIntent().getExtras();
        int emptyTripCount = bundle.getInt("emptyTripCount");
        int emptyTripPay = bundle.getInt("emptyTripPay");

        faresEmptyTripCount.setText(""+emptyTripCount);
        faresEmptyTripPay.setText(""+emptyTripPay);


        Log.v("ppking" , "emptyTripCount　: " +emptyTripCount);

        Log.v("ppking" , "emptyTripPay　: " +emptyTripPay);

        viewPager =(ViewPager)findViewById(R.id.faresViewPager);

        Fares_01_Fragment fares_01_fragment = new Fares_01_Fragment();
        Fares_02_Fragment fares_02_fragment = new Fares_02_Fragment();

        views.add(fares_01_fragment);
        views.add(fares_02_fragment);

        FragmentPagerAdapter fragmentPagerAdapter = new FaresFragmentAdapter01(getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setCurrentItem(0);


        //Todo 判斷系統試算是否結束，若判斷結束則直接進入試算結果頁面
        if (systemCountEnd){
            viewPager.setCurrentItem(1);
        }

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

}
