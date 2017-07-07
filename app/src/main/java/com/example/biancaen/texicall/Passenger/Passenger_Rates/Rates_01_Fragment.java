package com.example.biancaen.texicall.Passenger.Passenger_Rates;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.biancaen.texicall.Passenger.Passenger_Car_Service.Passenger_Car_Service_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.UserData;


public class Rates_01_Fragment extends android.app.Fragment {

    private static String startLng ,startLat;
    private static String endLng ,endLat;
    private static String phoneNumber;
    private static UserData userData;
    private Passenger_Rates_Activity activity;
    private LinearLayout linearLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_passenger_rates01_, null);

        linearLayout = (LinearLayout) view.findViewById(R.id.waitFares);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0f , 1.0f);
        alphaAnimation.setDuration(800);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        linearLayout.setAnimation(alphaAnimation);

        activity = (Passenger_Rates_Activity)getActivity();

        Bundle bundle = activity.getIntent().getExtras();
        startLng = bundle.getString("startLng");
        startLat = bundle.getString("startLat");
        endLng = bundle.getString("endLng");
        endLat = bundle.getString("endLat");
        phoneNumber = bundle.getString("passenger_number");
        userData = (UserData)bundle.getSerializable("userData");

        Log.v("ppking" , "　fragment rate" );

        RateCount();

        return view;
    }

    public void RateCount(){

        Log.v("ppking" , "　fragment RateCount" );
        Connect_API.rate(getActivity() , startLng  , startLat , endLng  , endLat , phoneNumber , userData.getApiKey(), new Connect_API.OnRateListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , "onFail : " + e.getMessage());
                Log.v("ppking" , "jsonError : " + jsonError);

                Toast.makeText(getActivity() , "車程橫跨過多縣市，HB不提供此服務" , Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onFailToResult(String isErrorResult, String errorMessage) {

                Log.v("ppking" , "　isErrorResult  : " + isErrorResult);
                Log.v("ppking" , "　errorMessage  : " + errorMessage);
                Toast.makeText(getActivity() , "" + errorMessage , Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onSuccess(String isErrorResult, int price, int time, String distance) {
                Log.v("ppking" , "isErrorResult :  "+ isErrorResult);
                Log.v("ppking" , "price :  "+ price);
                Log.v("ppking" , "time :  "+ time);
                Log.v("ppking" , "distance :  "+ distance);

                if (isErrorResult.equals("false")){
                    linearLayout.clearAnimation();
                    Log.v("ppking" , " 試算成功 !!");
                    activity.MainGetPrice(price);
                    activity.MainGetTime(time);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment , new Rates_02_Fragment() );
                    ft.commit();

                }else {
                    Toast.makeText(getActivity(),"試算結果錯誤，請確認地址是否正確!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        Log.v("ppking" , " RateCountRateCountRateCountRateCount" );
    }

}
