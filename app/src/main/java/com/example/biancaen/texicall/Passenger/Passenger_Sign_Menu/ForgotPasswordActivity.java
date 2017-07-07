package com.example.biancaen.texicall.Passenger.Passenger_Sign_Menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_forgotpassword);

        phoneNumber = (EditText)findViewById(R.id.phoneNumber);
    }
    public void accept(View view){
        if (!phoneNumber.getText().toString().equals("")){
            Connect_API.passengerForgot(this, phoneNumber.getText().toString(), new Connect_API.OnGetConnectStatusListener() {
                @Override
                public void onFail(Exception e, String jsonError) {
                    Log.v("ppking" , "Exception : "+e.getMessage());
                    Log.v("ppking" , "jsonError : "+jsonError);
                    Toast.makeText(ForgotPasswordActivity.this , "輸入的資料有誤",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(String isError, String message) {
                    Log.v("ppking" , "isError : "+isError);
                    Log.v("ppking" , "message : "+message);
                    Toast.makeText(ForgotPasswordActivity.this , ""+message,Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(ForgotPasswordActivity.this , Passenger_Sign_in_Activity.class);
                    startActivity(it);
                    finish();
                }
            });
        }else{
            Toast.makeText(this , "輸入不得空格",Toast.LENGTH_SHORT).show();
        }

    }
}
