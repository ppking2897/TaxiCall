package com.example.biancaen.texicall.Beginning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeTransform;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.biancaen.texicall.Driver.Driver_Sign_Menu.DriverActivity;
import com.example.biancaen.texicall.Passenger.Passenger_Sign_Menu.PassengerActivity;
import com.example.biancaen.texicall.R;
import com.google.firebase.iid.FirebaseInstanceId;


public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new ChangeTransform());
        getWindow().setExitTransition(new ChangeTransform());
        setContentView(R.layout.activity_mainmenu);

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