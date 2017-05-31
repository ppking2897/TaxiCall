package com.example.biancaen.texicall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PassengerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger);
    }
    public void sign_in(View view){
        Intent it = new Intent(this , Passenger_Sign_in.class);
        startActivity(it);
    }
}
