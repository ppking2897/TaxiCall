package com.example.biancaen.texicall.Passenger.Passenger_Sent_Car_Record;

import android.app.Activity;
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
import com.example.biancaen.texicall.connectapi.RecordPassengerData;
import com.example.biancaen.texicall.connectapi.UserData;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Sent_Car_01_Record_Fragment extends Fragment {
    private ArrayList<String> address_Record = new ArrayList<>();
    private static UserData userData;
    private static String phoneNumber;
    private List<RecordPassengerData> listRecord;
    private MySent_Car_Record_Adapter_01 myAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sent_car_01 , container , false);

        //-----Todo 地址紀錄預備位置-----


        Bundle getBundle = getActivity().getIntent().getExtras();
        userData = (UserData)getBundle.getSerializable("userData");
        phoneNumber = getBundle.getString("phoneNumber");


        GetRecord();


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.sent_car_01_recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        myAdapter = new MySent_Car_Record_Adapter_01(getContext(),address_Record);

        recyclerView.setAdapter(myAdapter);

        return view;
    }

    public void GetRecord(){
        Connect_API.getRecordListForPassenger(getActivity(), phoneNumber, userData.getApiKey(), new Connect_API.OnRecordListListener() {
            @Override
            public void onFail(Exception e, String jsonError) {

                Toast.makeText(getActivity() , "連線異常" ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String isError, String msg, List<RecordPassengerData> data) {


                if (isError.equals("false")){
                    Toast.makeText(getActivity() , ""+msg ,Toast.LENGTH_SHORT).show();
                    listRecord = data;
                    for (int i = 0; i < listRecord.size(); i++) {
                        address_Record.add(listRecord.get(i).getAddr_end_addr());
                        myAdapter.notifyDataSetChanged();

                    }

                }else{
                    Toast.makeText(getActivity() , "記錄取得失敗" ,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}



