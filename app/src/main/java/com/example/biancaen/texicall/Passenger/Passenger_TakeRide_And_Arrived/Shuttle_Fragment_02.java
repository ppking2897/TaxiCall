package com.example.biancaen.texicall.Passenger.Passenger_TakeRide_And_Arrived;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biancaen.texicall.Passenger.Passenger_Car_Service.Passenger_Car_Service_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.UserData;


public class Shuttle_Fragment_02 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getContext()).inflate(R.layout.fragment_shuttle_02 , null);

        TextView price = (TextView)view.findViewById(R.id.price);
        TextView address = (TextView)view.findViewById(R.id.address);
        ImageView recordAddress = (ImageView)view.findViewById(R.id.recordAddress);

        final Passenger_In_The_Shuttle_Activity activity =(Passenger_In_The_Shuttle_Activity) getActivity();

        SharedPreferences sharedPreferences = activity.getSharedPreferences("passenger" , Context.MODE_PRIVATE);

        final String phoneNumber =  sharedPreferences.getString("phoneNumber" , null);
        final String passWord = sharedPreferences.getString("passWord" , null);
        final String taskNumber = sharedPreferences.getString("taskNumber" , null);
        String priceText = sharedPreferences.getString("price" , null);
        String destination = sharedPreferences.getString("destination" , null);

        price.setText(priceText);
        address.setText(destination);

        recordAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Connect_API.userLogin(activity, phoneNumber, passWord, new Connect_API.OnUserLoginListener() {
                    @Override
                    public void onLoginSuccess(UserData userData) {
                        Connect_API.setFavoriteAddress(activity, phoneNumber, taskNumber, userData.getApiKey(), new Connect_API.OnGetConnectStatusListener() {
                            @Override
                            public void onFail(Exception e, String jsonError) {

                            }

                            @Override
                            public void onSuccess(String isError, String message) {
                                if (isError.equals("false")){
                                    Toast.makeText(activity , ""+message , Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(activity , ""+message , Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                    @Override
                    public void onLoginFail(String isFail, String msg) {

                     }

                    @Override
                    public void onFail(Exception e, String jsonError) {

                    }
                });
            }
        });
        return view;
    }
}
