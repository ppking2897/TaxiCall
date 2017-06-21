package com.example.biancaen.texicall.Passenger.Passenger_Car_Service;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aigestudio.wheelpicker.WheelPicker;
import com.example.biancaen.texicall.R;

import java.util.ArrayList;
import java.util.List;


public class Passenger_Car_Service_Fragment_04 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_passenger_car_service_get_off_address_02, null);

        WheelPicker record_picker_get_off = (WheelPicker) view.findViewById(R.id.record_picker_get_off);
        List<String> data = new ArrayList<>();

        data.add("臺中市烏日區");
        data.add("臺中市大里區");
        data.add("台北市XX區");
        data.add("台北市XX區");
        data.add("台北市XX區");
        data.add("台北市XX區");
        data.add("台北市XX區");
        record_picker_get_off.setData(data);

        return view;
    }
}
