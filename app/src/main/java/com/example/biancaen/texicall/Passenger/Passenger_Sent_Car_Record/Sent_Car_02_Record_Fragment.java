package com.example.biancaen.texicall.Passenger.Passenger_Sent_Car_Record;

import android.os.Bundle;
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

public class Sent_Car_02_Record_Fragment extends Fragment {
    private ArrayList<String> date = new ArrayList<>();
    private ArrayList<String> getOn = new ArrayList<>();
    private ArrayList<String> getOff = new ArrayList<>();
    private ArrayList<String> time = new ArrayList<>();
    private MySent_Car_Record_Adapter_02 myAdapter_02;

    private static UserData userData;
    private static String phoneNumber;
    private List<RecordPassengerData> listRecord;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sent_car_02 , container , false);

        Bundle getBundle = getActivity().getIntent().getExtras();
        userData = (UserData)getBundle.getSerializable("userData");
        phoneNumber = getBundle.getString("phoneNumber");

        GetRecord();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.sent_car_02_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        myAdapter_02 = new MySent_Car_Record_Adapter_02(getContext() , date , getOn , getOff , time );

        recyclerView.setAdapter(myAdapter_02);

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

                        date.add(listRecord.get(i).getCreated_at().substring(0 , 11));
                        getOn.add("上車地點 / " +listRecord.get(i).getAddr_start_addr());
                        getOff.add("下車地點 / " +listRecord.get(i).getAddr_end_addr());
                        time.add("乘車時間 / " +listRecord.get(i).getTime() + "分");

                        myAdapter_02.notifyDataSetChanged();
                    }

                }else{
                    Toast.makeText(getActivity() , "記錄取得失敗" ,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}





