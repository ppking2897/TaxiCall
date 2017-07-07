package com.example.biancaen.texicall.Passenger.Passenger_Car_Service;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aigestudio.wheelpicker.WheelPicker;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.FavoriteAddressData;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class Passenger_Car_Service_Fragment_04 extends Fragment {
    private String getOffRecord;
    private static String passengerApiKey;
    private static String phoneNumber;
    private List<String> data = new ArrayList<>();
    private WheelPicker record_picker_get_off;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_passenger_car_service_get_off_address_02, null);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("passenger" , MODE_PRIVATE);
        phoneNumber = sharedPreferences.getString("phoneNumber" , null);
        passengerApiKey = sharedPreferences.getString("passengerApiKey" , null);

        GetRecord();

        record_picker_get_off = (WheelPicker) view.findViewById(R.id.record_picker_get_off);

        record_picker_get_off.setOnWheelChangeListener(new WheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolled(int i) {

            }

            @Override
            public void onWheelSelected(int i) {
                getOffRecord = data.get(i);
            }

            @Override
            public void onWheelScrollStateChanged(int i) {

            }
        });

        return view;
    }
    public String getGetOffRecord(){
        return getOffRecord;
    }

    public void GetRecord(){
        Connect_API.getFavoriteAddress(getActivity(), phoneNumber, passengerApiKey, new Connect_API.OnGetFavoriteAddressListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , " fail error" );
                data.clear();
                data.add("並無常用地址紀錄");
                record_picker_get_off.setData(data);
            }

            @Override
            public void onSuccess(List<FavoriteAddressData> datas) {
                data.clear();
                if (datas.size() != 0){
                    for (int i = 0 ; i<datas.size() ; i++){
                        data.add(datas.get(i).getFavorite());
                        record_picker_get_off.setData(data);
                        getOffRecord = data.get(0);
                    }
                }else {
                    data.add("並無常用地址紀錄");
                    record_picker_get_off.setData(data);
                }
            }
        });
    }
}
