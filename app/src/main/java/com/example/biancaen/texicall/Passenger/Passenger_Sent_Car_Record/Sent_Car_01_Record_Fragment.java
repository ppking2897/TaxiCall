package com.example.biancaen.texicall.Passenger.Passenger_Sent_Car_Record;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.FavoriteAddressData;
import com.example.biancaen.texicall.connectapi.RecordPassengerData;
import com.example.biancaen.texicall.connectapi.UserData;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.MODE_PRIVATE;

public class Sent_Car_01_Record_Fragment extends Fragment {
    private ArrayList<String> address_Record = new ArrayList<>();
    private static String passengerApiKey;
    private static String phoneNumber;
    private MySent_Car_Record_Adapter_01 myAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sent_car_01 , container , false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("passenger" , MODE_PRIVATE);
        phoneNumber = sharedPreferences.getString("phoneNumber" , null);
        passengerApiKey = sharedPreferences.getString("passengerApiKey" , null);

        GetRecord();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.sent_car_01_recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        myAdapter = new MySent_Car_Record_Adapter_01(getContext(),address_Record);

        recyclerView.setAdapter(myAdapter);

        return view;
    }

    public void GetRecord(){
        Connect_API.getFavoriteAddress(getActivity(), phoneNumber, passengerApiKey, new Connect_API.OnGetFavoriteAddressListener() {
            @Override
            public void onFail(Exception e, String jsonError) {

                Log.v("ppking" , " Exception  : " +e);
                Log.v("ppking" , " jsonError  : " +jsonError);

                Toast.makeText(getActivity() , "連線異常" ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(List<FavoriteAddressData> datas) {

                for (int i = 0; i < datas.size(); i++) {
                    address_Record.add(datas.get(i).getFavorite());
                    myAdapter.notifyDataSetChanged();
                }
            }
        });
    }

}



