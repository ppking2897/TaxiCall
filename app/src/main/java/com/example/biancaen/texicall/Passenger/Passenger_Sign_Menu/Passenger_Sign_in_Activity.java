package com.example.biancaen.texicall.Passenger.Passenger_Sign_Menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.biancaen.texicall.Passenger.Passenger_Car_Service.Passenger_Car_Service_Activity;
import com.example.biancaen.texicall.R;

public class Passenger_Sign_in_Activity extends AppCompatActivity {
    private EditText phone , password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_sign_in);

        phone = (EditText)findViewById(R.id.sign_In_Phone);
        password = (EditText)findViewById(R.id.sign_In_Password);
    }
    public void sign_in_sign_in(View view){

        String phoneString = phone.getText().toString();
        String passwordString = password.getText().toString();

        Intent it = new Intent(Passenger_Sign_in_Activity.this , Passenger_Car_Service_Activity.class);
        startActivity(it);

//        if (!(passwordString.equals("") || phoneString.equals(""))){
//            Connect_API.userLogin(this,phoneString, passwordString, new Connect_API.OnUserLoginListener() {
//
//                @Override
//                public void onLoginSuccess(UserData userData) {
//                    Log.v("ppking" , "onLoginSuccess :  " + userData.isError());
//                    Log.v("ppking" , "userData.apikey :  " + userData.getApiKey());
//                    Sign_In_Success(userData.isError() , userData.getApiKey());
//                }
//
//                @Override
//                public void onLoginFail(boolean isFail, String msg) {
//                    Log.v("ppking" , " msg : " + msg);
//                }
//
//                @Override
//                public void onFail(Exception e) {
//
//                }
//
//
//            });
//        }else {
//            Log.v("ppking" , " 輸出有空格  !!!! ");
//            Toast.makeText(this, "輸入不得有空格!!" , Toast.LENGTH_SHORT).show();
//        }
    }
    public void sign_in_sign_up(View view){
        Intent it = new Intent(this , Passenger_Sign_Up_Activity.class);
        startActivity(it);
    }
    public void sign_in_send_new_password(View view){
        Intent it = new Intent(this , ForgotPasswordActivity.class);
        startActivity(it);
    }

    public void Sign_In_Success(final boolean isError , final String apiKey){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isError){
                    Toast.makeText(Passenger_Sign_in_Activity.this , "登入成功" , Toast.LENGTH_SHORT).show();

                    Bundle bundle = new Bundle();
                    bundle.putString("apiKey" , apiKey );

                    Intent it = new Intent(Passenger_Sign_in_Activity.this , Passenger_Car_Service_Activity.class);
                    it.putExtras(bundle);
                    startActivity(it);

                }else{
                    Toast.makeText(Passenger_Sign_in_Activity.this , "帳號密碼錯誤!!" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
