package com.example.biancaen.texicall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Passenger_Sign_in extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_sign_in);
    }
    public void sign_in_sign_up(View view){
        Intent it = new Intent(this , Passenger_Sign_Up.class);
        startActivity(it);
    }
    public void sign_in_send_new_password(View view){
        Intent it = new Intent(this , ForgotPassword.class);
        startActivity(it);
    }
}
