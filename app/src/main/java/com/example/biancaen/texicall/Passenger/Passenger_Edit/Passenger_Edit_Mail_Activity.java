package com.example.biancaen.texicall.Passenger.Passenger_Edit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.UserData;

public class Passenger_Edit_Mail_Activity extends AppCompatActivity {
    private EditText edit_Mail;
    private String phoneNumber;
    private String passWord;
    private String passengerApiKey;
    private String name;
    private String mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger__edit__mail_);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_Edit_Mail);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences("passenger" , MODE_PRIVATE);
        phoneNumber = sharedPreferences.getString("phoneNumber" , null);
        passWord = sharedPreferences.getString("passWord" , null);
        passengerApiKey = sharedPreferences.getString("passengerApiKey" , null);
        name = sharedPreferences.getString("name" , null);
        mail = sharedPreferences.getString("mail" , null);

        TextView pastMail = (TextView)findViewById(R.id.pastMail);
        edit_Mail = (EditText)findViewById(R.id.edit_Mail);

        pastMail.setText("原信箱 / " + mail);
    }

    public void edit_Mail(View view){
        if (!edit_Mail.getText().toString().equals("")){
            Connect_API.modifyChange(this, edit_Mail.getText().toString(), phoneNumber, passWord, passWord, name, passengerApiKey, new Connect_API.OnModifyChangeListener() {
                @Override
                public void onSuccess(String isFail, String msg) {
                    if (isFail.equals("false")){

                        Toast.makeText(Passenger_Edit_Mail_Activity.this , ""+msg , Toast.LENGTH_SHORT).show();

                        Intent it = new Intent(Passenger_Edit_Mail_Activity.this , Passenger_Info_Activity.class);
                        startActivity(it);
                        finish();
                    }else{
                        Toast.makeText(Passenger_Edit_Mail_Activity.this , "信箱更新失敗" , Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFail(Exception e, String jsonError) {
                    Toast.makeText(Passenger_Edit_Mail_Activity.this , "連線出現異常" , Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(this , "輸入不得空格" ,Toast.LENGTH_SHORT).show();
        }
    }
}
