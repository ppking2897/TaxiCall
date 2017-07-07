package com.example.biancaen.texicall.Passenger.Passenger_Sign_Menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.biancaen.texicall.Passenger.Passenger_Car_Service.Passenger_Car_Service_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.UserData;

import java.util.TimerTask;

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

        final String phoneString = phone.getText().toString();
        final String passwordString = password.getText().toString();


        if (!(passwordString.equals("") || phoneString.equals(""))){
            Connect_API.userLogin(this, phoneString, passwordString, new Connect_API.OnUserLoginListener() {
                @Override
                public void onLoginSuccess(UserData userData) {

                    Sign_In_Success(userData , phoneString , passwordString);
                }

                @Override
                public void onLoginFail(String isFail, String msg) {

                    Sign_In_Fail();
                }

                @Override
                public void onFail(Exception e, String jsonError) {

                    LinkFail();
                }

            });

        }else {
            Toast.makeText(this, "輸入不得有空格!!" , Toast.LENGTH_SHORT).show();
        }
    }
    public void sign_in_sign_up(View view){
        Intent it = new Intent(this , Passenger_Sign_Up_Activity.class);
        startActivity(it);
    }
    public void sign_in_send_new_password(View view){
        Intent it = new Intent(this , ForgotPasswordActivity.class);
        startActivity(it);
    }

    public void Sign_In_Success(final UserData userData , final String phoneNumber , final String passWord){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (userData.getType().equals("passenger")){

                    Toast.makeText(Passenger_Sign_in_Activity.this , "登入成功" , Toast.LENGTH_SHORT).show();


                    //將資料丟入SharedPreferences 共用
                    SharedPreferences sharedPreferences = getSharedPreferences("passenger" , MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("phoneNumber" , phoneNumber);
                    editor.putString("passWord" , passWord);
                    editor.putString("passengerApiKey" , userData.getApiKey());
                    Log.v("ppking" , "passengerApiKey : "+ userData.getApiKey());
                    editor.putString("mail" , userData.getEmail());
                    editor.putString("name" , userData.getName());
                    editor.apply();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userData" , userData);
                    bundle.putString("phoneNumber" , phoneNumber);
                    bundle.putString("passWord" , passWord);

                    Intent it = new Intent(Passenger_Sign_in_Activity.this , Passenger_Car_Service_Activity.class);
                    it.putExtras(bundle);
                    startActivity(it);
                    finish();

                }else {
                    Sign_In_Fail();
                }
            }
        });
    }

    public void Sign_In_Fail(){

        Toast.makeText(this , "登入失敗，不正確的資料" , Toast.LENGTH_SHORT).show();
    }

    public void LinkFail(){

        Toast.makeText(this , "連線異常" , Toast.LENGTH_SHORT).show();
    }

}
