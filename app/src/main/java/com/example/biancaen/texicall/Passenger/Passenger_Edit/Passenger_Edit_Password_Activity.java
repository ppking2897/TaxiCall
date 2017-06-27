package com.example.biancaen.texicall.Passenger.Passenger_Edit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.UserData;

public class Passenger_Edit_Password_Activity extends AppCompatActivity {
    private EditText inputPassWord , inputPassWordAgain;
    private String phoneNumber;
    private String passWord;
    private UserData userData;
    private String passWordString , passWordAgainString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger__edit__password_);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_Edit_Password);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputPassWord = (EditText)findViewById(R.id.passWord);
        inputPassWordAgain = (EditText)findViewById(R.id.passWordAgain);



        Bundle getBundle = getIntent().getExtras();
        phoneNumber = getBundle.getString("phoneNumber");
        passWord = getBundle.getString("passWord");
        userData = (UserData)getBundle.getSerializable("userData");
    }
    public void edit_Password(View view){

        passWordString = inputPassWord.getText().toString();
        passWordAgainString = inputPassWordAgain.getText().toString();

        if (passWordString .equals(passWordAgainString) ){

            if(passWordString.equals("")) {
                Toast.makeText(this , "不得輸入空格" ,Toast.LENGTH_SHORT).show();

            }else {
                UpdatePassWord();
            }

        }else if(passWordString.equals("")||passWordAgainString.equals("")) {

            Toast.makeText(this , "不得輸入空格" ,Toast.LENGTH_SHORT).show();

        }else{

            Toast.makeText(this , "密碼不正確,請再次輸入新密碼" ,Toast.LENGTH_SHORT).show();
        }
    }

    public void UpdatePassWord() {
        Connect_API.modifyChange(this, userData.getEmail(), phoneNumber, passWord, passWordString, userData.getName(), userData.getApiKey(), new Connect_API.OnModifyChangeListener() {
            @Override
            public void onSuccess(String isFail, String msg) {
                if (isFail.equals("false")) {

                    Toast.makeText(Passenger_Edit_Password_Activity.this, "" + msg, Toast.LENGTH_SHORT).show();

                    Bundle bundle = new Bundle();
                    bundle.putString("passWord" , passWordString);
                    bundle.putSerializable("userData" , userData);
                    bundle.putString("phoneNumber" , phoneNumber);

                    Intent it = new Intent(Passenger_Edit_Password_Activity.this, Passenger_Info_Activity.class);
                    it.putExtras(bundle);
                    startActivity(it);
                    finish();
                } else {
                    Toast.makeText(Passenger_Edit_Password_Activity.this, "密碼更新失敗", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFail(Exception e, String jsonError) {
                Toast.makeText(Passenger_Edit_Password_Activity.this, "連線出現異常", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
