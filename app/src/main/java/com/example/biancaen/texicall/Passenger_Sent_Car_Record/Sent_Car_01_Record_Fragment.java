package com.example.biancaen.texicall.Passenger_Sent_Car_Record;

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

public class Sent_Car_01_Record_Fragment extends Fragment {
    private ArrayList<String> address_Record = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sent_car_01 , container , false);

        //-----Todo 地址紀錄預備位置-----
        address_Record.add("新北市板橋區中正路二段542巷18弄64之7號");
        address_Record.add("新北市三重區吳興街329號1樓");
        address_Record.add("台北市信義區忠孝東路二段398巷26號");
        address_Record.add("台北市信義區忠孝東路二段398巷26號");



        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.sent_car_01_recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        MySent_Car_Record_Adapter_01 myAdapter = new MySent_Car_Record_Adapter_01(getContext(),address_Record);

        recyclerView.setAdapter(myAdapter);

        return view;
    }

}



