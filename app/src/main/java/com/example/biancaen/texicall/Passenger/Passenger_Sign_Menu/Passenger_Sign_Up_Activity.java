package com.example.biancaen.texicall.Passenger.Passenger_Sign_Menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.biancaen.texicall.Passenger.Passenger_Car_Service.Passenger_Car_Service_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;

public class Passenger_Sign_Up_Activity extends AppCompatActivity {
    private EditText name , password , mail , phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_sign_up);
        name = (EditText)findViewById(R.id.sign_Up_Name);
        password = (EditText)findViewById(R.id.sign_Up_Password);
        mail = (EditText)findViewById(R.id.sign_Up_mail);
        phone = (EditText)findViewById(R.id.sign_Up_Phone);

    }
    public void sign_up(View view){

        String nameString = name.getText().toString();
        String passwordString = password.getText().toString();
        String mailString = mail.getText().toString();
        String phoneString = phone.getText().toString();

        if (!(nameString.equals("") || passwordString.equals("") || mailString.equals("") || phoneString.equals(""))) {

            Connect_API.register(this ,nameString, passwordString, mailString, phoneString, new Connect_API.OnRegisterListener() {
                @Override
                public void onRegisterSuccessListener(String isFail, String message) {
                    Log.v("ppking" , " onRegisterSuccess  :  " + message);
                    Log.v("ppking" , " onRegisterSuccess  :  " + isFail);

                    Success(isFail.equals("false") , message);
                }

                @Override
                public void onRegisterFailListener(Exception e, String jsonError) {
                    Log.v("ppking" , " onRegisterFail  :  " + e);
                }
            });

        }else{
            Log.v("ppking" , " 不得有空格 !!");
            Toast.makeText(this,"輸入不得有空格!!",Toast.LENGTH_SHORT).show();
        }

    }
    public void Success(final boolean isFail, final String message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isFail){
                    Toast.makeText(Passenger_Sign_Up_Activity.this , ""+message + "!!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Passenger_Sign_Up_Activity.this , "註冊成功!!", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(Passenger_Sign_Up_Activity.this , Passenger_Car_Service_Activity.class);
                    startActivity(it);
                }
            }
        });
    }
}
