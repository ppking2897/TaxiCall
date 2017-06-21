package com.example.biancaen.texicall.Passenger.Passenger_In_The_Shuttle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.biancaen.texicall.R;

/**
 * Created by BiancaEN on 2017/6/12.
 */

public class Shuttle_Fragment_02 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getContext()).inflate(R.layout.fragment_shuttle_02 , null);
        return view;
    }
}
