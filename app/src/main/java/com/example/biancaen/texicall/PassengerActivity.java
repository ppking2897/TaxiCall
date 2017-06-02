package com.example.biancaen.texicall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PassengerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);
    }
    public void passenger_sign_in(View view){
        Intent it = new Intent(this , Passenger_Sign_in_Activity.class);
        startActivity(it);
    }
    public void passenger_sign_up(View view){
        Intent it = new Intent(this , Passenger_Sign_Up_Activity.class);
        startActivity(it);
    }
    public void passenger_send_new_password(View view){
        Intent it = new Intent(this , ForgotPasswordActivity.class);
        startActivity(it);
    }
}
