package com.example.biancaen.texicall.Beginning;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeTransform;
import android.view.View;
import android.view.Window;

import com.example.biancaen.texicall.Driver.Driver_Main_Menu.Driver_Main_Menu_Activity;
import com.example.biancaen.texicall.Driver.Driver_Sign_Menu.DriverActivity;
import com.example.biancaen.texicall.Driver.Driver_Sign_Menu.Driver_Login_Activity;
import com.example.biancaen.texicall.Driver.Driver_Trip.Driver_Arrived_Activity;
import com.example.biancaen.texicall.Driver.Driver_Trip.Driver_Google_Map_Activity;
import com.example.biancaen.texicall.Driver.Driver_Trip.Driver_On_The_Way_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Car_Service.Passenger_Car_Service_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Driver_Arrived.Passenger_Driver_Arrived_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_On_The_Way.Passenger_On_The_Way_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Sign_Menu.Passenger_Sign_in_Activity;
import com.example.biancaen.texicall.Support_Class.MyService;
import com.example.biancaen.texicall.Passenger.Passenger_Sign_Menu.PassengerActivity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.DriverData;
import com.example.biancaen.texicall.connectapi.UserData;


public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new ChangeTransform());
        getWindow().setExitTransition(new ChangeTransform());
        setContentView(R.layout.activity_mainmenu);

        //當強制關閉此App時，進入Service做登出動作
        Intent it = new Intent(this , MyService.class);
        startService(it);
        //推播測試階段
//        Log.i("get token   ", FirebaseInstanceId.getInstance().getToken());

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void passenger(View view) {
        Intent it = new Intent(this, PassengerActivity.class);
        startActivity(it);
    }
    public void driver(View view){
        Intent it = new Intent(this , DriverActivity.class);
        startActivity(it);
    }
}