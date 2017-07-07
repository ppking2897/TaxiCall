package com.example.biancaen.texicall.Driver.Driver_Sign_Menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;

public class Driver_ForgotPassword_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__forgot_password_);
    }
    public void driver_signin_signup(View view){
        EditText phoneNumber = (EditText)findViewById(R.id.phoneNumber);

        Connect_API.driverForgot(this, phoneNumber.getText().toString(), new Connect_API.OnGetConnectStatusListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , "Exception  : " +e);
                Log.v("ppking" , "jsonError  : " +jsonError);
            }

            @Override
            public void onSuccess(String isError, String message) {
                if (isError.equals("fals")){
                    Toast.makeText(Driver_ForgotPassword_Activity.this , ""+message , Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(Driver_ForgotPassword_Activity.this , Driver_Login_Activity.class);
                    startActivity(it);
                }else{
                    Toast.makeText(Driver_ForgotPassword_Activity.this , ""+message , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
