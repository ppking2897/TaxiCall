package com.example.biancaen.texicall.Driver.Driver_Sign_Menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.biancaen.texicall.Driver.Driver_Main_Menu.Driver_Main_Menu_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.DriverData;

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

        if (!phone.getText().toString().equals("") && !password.getText().toString().equals("")){
            Connect_API.driverLogin(this, phone.getText().toString(), password.getText().toString(), new Connect_API.OnDriverLoginListener() {
                @Override
                public void onLoginSuccess(DriverData driverData) {

                    if (driverData.getType().equals("driver")){

                        SharedPreferences sharedPreferences = getSharedPreferences("type" , MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isDriver" , true);
                        editor.apply();

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("driverData" , driverData);
                        bundle.putString("phone" , phone.getText().toString());
                        bundle.putString("password" , password.getText().toString());
                        Intent it = new Intent(Driver_Login_Activity.this , Driver_Main_Menu_Activity.class);
                        it.putExtras(bundle);
                        startActivity(it);

                    }else {
                        Toast.makeText(Driver_Login_Activity.this , "輸入資料有誤" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onLoginFail(String isFail, String msg) {

                    Log.v("ppking" , " isFail  :  " + isFail);
                    Log.v("ppking" , " msg  :  " + msg);

                    Toast.makeText(Driver_Login_Activity.this , ""+msg , Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFail(Exception e, String jsonError) {

                    Toast.makeText(Driver_Login_Activity.this , "連線異常" , Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            Toast.makeText(this , "輸入不得空格" , Toast.LENGTH_SHORT).show();
        }

    }

    public void driver_sign_in_send_new_password(View view){
        Intent it = new Intent(this , Driver_ForgotPassword_Activity.class);
        startActivity(it);
    }
}
