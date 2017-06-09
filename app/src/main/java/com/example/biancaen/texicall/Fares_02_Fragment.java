package com.example.biancaen.texicall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Fares_02_Fragment extends Fragment {
    Passenger_Fares_Activity activity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_passenger_fares02_,container,false);

        ImageView callTheCar = (ImageView) view.findViewById(R.id.callTheCar);

        activity = (Passenger_Fares_Activity)getActivity();

        callTheCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.viewPager.setCurrentItem(2);
                activity.isSystemMatchEnd(true);
            }
        });

        return view;
    }



}
