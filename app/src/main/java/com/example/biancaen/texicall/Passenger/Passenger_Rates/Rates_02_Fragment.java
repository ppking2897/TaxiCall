package com.example.biancaen.texicall.Passenger.Passenger_Rates;

import android.app.FragmentTransaction;
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

public class Rates_02_Fragment extends android.app.Fragment {
    Passenger_Rates_Activity activity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_passenger_rates02_,container,false);

        activity = (Passenger_Rates_Activity)getActivity();

        //試算出來價格
        int price = activity.GetMainPrice();


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
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment , new Rates_03_Fragment() );
                ft.commit();
                activity.systemMatchEnd();
            }
        });
        return view;
    }

}