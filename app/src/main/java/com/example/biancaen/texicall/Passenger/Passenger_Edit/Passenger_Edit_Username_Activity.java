package com.example.biancaen.texicall.Passenger.Passenger_Edit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.UserData;

import java.io.Serializable;
import java.util.Timer;
import java.util.concurrent.Delayed;

public class Passenger_Edit_Username_Activity extends AppCompatActivity {
    private EditText edit_Username;
    private String phoneNumber;
    private String passWord;
    private UserData userData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger__edit__username_);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_Edit_Username);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edit_Username = (EditText)findViewById(R.id.edit_Username);
        TextView userName = (TextView)findViewById(R.id.userName);

        Bundle getBundle = getIntent().getExtras();
        phoneNumber = getBundle.getString("phoneNumber");
        passWord = getBundle.getString("passWord");
        userData = (UserData)getBundle.getSerializable("userData");

        userName.setText("原用戶名稱 / " + (userData != null ? userData.getName() : null));

    }
    public void accept(View view){

        String name =  edit_Username.getText().toString();
        if (!name.equals("")){
            Connect_API.modifyChange(this, userData.getEmail(), phoneNumber, passWord, passWord, edit_Username.getText().toString(), userData.getApiKey(), new Connect_API.OnModifyChangeListener() {
                @Override
                public void onSuccess(String isFail, String msg) {
                    if (isFail.equals("false")){

                        Toast.makeText(Passenger_Edit_Username_Activity.this , ""+msg , Toast.LENGTH_SHORT).show();

                        Intent it = new Intent(Passenger_Edit_Username_Activity.this , Passenger_Info_Activity.class);
                        startActivity(it);
                        finish();
                    }else{
                        Toast.makeText(Passenger_Edit_Username_Activity.this , "名稱更新失敗" , Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFail(Exception e, String jsonError) {
                    Toast.makeText(Passenger_Edit_Username_Activity.this , "連線出現異常" , Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(this , "輸入不得空格" , Toast.LENGTH_SHORT).show();
        }
    }
}
