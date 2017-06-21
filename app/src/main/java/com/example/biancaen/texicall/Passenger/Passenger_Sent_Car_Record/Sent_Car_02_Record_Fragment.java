package com.example.biancaen.texicall.Passenger.Passenger_Sent_Car_Record;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.biancaen.texicall.R;

import java.util.ArrayList;

public class Sent_Car_02_Record_Fragment extends Fragment {
    private ArrayList<String> date = new ArrayList<>();
    private ArrayList<String> getOn = new ArrayList<>();
    private ArrayList<String> getOff = new ArrayList<>();
    private ArrayList<String> time = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sent_car_02 , container , false);

        //Todo 預計顯示乘車資訊存放位置
        date.add("2017-04-20");
        date.add("2017-04-20");
        date.add("2017-04-20");
        getOn.add("上車地點/");
        getOn.add("上車地點/");
        getOn.add("上車地點/");
        getOff.add("下車地點/");
        getOff.add("下車地點/");
        getOff.add("下車地點/");
        time.add("乘車時間/");
        time.add("乘車時間/");
        time.add("乘車時間/");


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.sent_car_02_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        MySent_Car_Record_Adapter_02 myAdapter_02 = new MySent_Car_Record_Adapter_02(getContext() , date , getOn , getOff , time );

        recyclerView.setAdapter(myAdapter_02);

        return view;
    }
}





