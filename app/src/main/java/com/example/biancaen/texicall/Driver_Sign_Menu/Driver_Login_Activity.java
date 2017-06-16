package com.example.biancaen.texicall.Driver_Sign_Menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.biancaen.texicall.Driver_Main_Menu.Driver_Main_Menu_Activity;
import com.example.biancaen.texicall.R;

public class Driver_Login_Activity extends AppCompatActivity {
    private EditText phone , password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__login_);
        phone = (EditText)findViewById(R.id.driver_sign_In_Phone);
        password = (EditText)findViewById(R.id.driver_sign_In_Password);
    }
    public void driver_sign_in_sign_in(View view){
        Intent it = new Intent(this , Driver_Main_Menu_Activity.class);
        startActivity(it);
    }

    public void driver_sign_in_send_new_password(View view){
        Intent it = new Intent(this , Driver_ForgotPassword_Activity.class);
        startActivity(it);
    }
}
