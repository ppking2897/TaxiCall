package com.example.biancaen.texicall.Passenger_In_The_Shuttle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.biancaen.texicall.R;


public class Shuttle_Fragment_01 extends Fragment {
    private Passenger_In_The_Shuttle_Activity passengerInTheShuttleActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getContext()).inflate(R.layout.fragment_shuttle_01 ,null);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.inShuttle);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0f , 1.0f);
        alphaAnimation.setDuration(800);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        linearLayout.setAnimation(alphaAnimation);

        passengerInTheShuttleActivity = (Passenger_In_The_Shuttle_Activity)getActivity();

        Button button = (Button)view.findViewById(R.id.goal);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passengerInTheShuttleActivity.shuttle_viewPager.setCurrentItem(1);
            }
        });

        return view;
    }

}
