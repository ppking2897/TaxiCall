package com.example.biancaen.texicall.Driver_Sign_Menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.biancaen.texicall.R;

public class DriverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
    }

    public void driver_sign_in(View view){
        Intent it = new Intent(this , Driver_Login_Activity.class);
        startActivity(it);
    }
    public void driver_send_new_password(View view){
        Intent it = new Intent(this , Driver_ForgotPassword_Activity.class);
        startActivity(it);
    }
}
