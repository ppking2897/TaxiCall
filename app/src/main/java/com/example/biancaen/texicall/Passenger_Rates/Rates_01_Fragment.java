package com.example.biancaen.texicall.Passenger_Rates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.example.biancaen.texicall.R;


public class Rates_01_Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_passenger_rates01_, null);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.waitFares);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0f , 1.0f);
        alphaAnimation.setDuration(800);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        linearLayout.setAnimation(alphaAnimation);
        return view;
    }

}
