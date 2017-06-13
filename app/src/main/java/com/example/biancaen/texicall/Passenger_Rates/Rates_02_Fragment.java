package com.example.biancaen.texicall.Passenger_Rates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.biancaen.texicall.R;

public class Rates_02_Fragment extends Fragment {
    Passenger_Rates_Activity activity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_passenger_rates02_,container,false);

        activity = (Passenger_Rates_Activity)getActivity();


        Bundle bundle = activity.getIntent().getExtras();
        int price = bundle.getInt("price");

        ImageView callTheCar = (ImageView) view.findViewById(R.id.callTheCar);
        final ScrollView fares_ScrollView = (ScrollView)view.findViewById(R.id.fares_ScrollView);
        TextView payTheMoney = (TextView)view.findViewById(R.id.payTheMoney);

        payTheMoney.setText(""+price);

        fares_ScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        callTheCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.myViewPager.setCurrentItem(2);
                activity.isSystemMatchEnd(true);
            }
        });

        return view;
    }



}
