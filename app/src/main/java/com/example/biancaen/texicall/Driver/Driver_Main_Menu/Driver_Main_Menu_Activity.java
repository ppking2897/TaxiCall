package com.example.biancaen.texicall.Driver.Driver_Main_Menu;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biancaen.texicall.Driver.Driver_Info.Driver_Info_Activity;
import com.example.biancaen.texicall.Driver.Driver_Point_Record.Driver_Point_Record_Activity;
import com.example.biancaen.texicall.Driver.Driver_Sign_Menu.Driver_Login_Activity;
import com.example.biancaen.texicall.Driver.Driver_Travel_Record.Driver_Travel_Record_Activity;
import com.example.biancaen.texicall.Driver.Driver_Match.Driver_WaitMatch_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.DriverData;
import com.example.biancaen.texicall.connectapi.PointData;
import com.example.biancaen.texicall.connectapi.RecordDriverData;

import java.util.List;

public class Driver_Main_Menu_Activity extends AppCompatActivity {

    private static DriverData driverData;
    private static String phone;
    private static String password;
    private LinearLayout driverOnline;
    private ImageView onlineImg;
    private TextView onlineText;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__main__menu__activicy);

        Driver_Main_Menu_Dialog driverMainMenuDialog = new Driver_Main_Menu_Dialog(this);
        driverMainMenuDialog.CreatePointDialog();

        Bundle bundle = getIntent().getExtras();

        driverData = (DriverData)bundle.getSerializable("driverData");
        phone = bundle.getString("phone");
        password = bundle.getString("password");

        driverOnline = (LinearLayout)findViewById(R.id.driverOnline);
        onlineImg = (ImageView)findViewById(R.id.onlineImg);
        onlineText = (TextView)findViewById(R.id.onlineText);

        //判斷目前driver狀態
        if (driverData.getStatus()==1){
            //1 下線狀態
            driverOnline.setBackgroundResource(R.drawable.shape_online_driver);
            onlineImg.setImageResource(R.drawable.ic_car_online_btn_homesreen);
            onlineText.setText("上線載客");

        }else if (driverData.getStatus()==2){
            //2上線狀態
            driverOnline.setBackgroundResource(R.drawable.shape_offline_driver);
            onlineImg.setImageResource(R.drawable.ic_offline_btn_homescreen);
            onlineText.setText("結束下線");
        }else if (driverData.getStatus()==3){
            driverOnline.setBackgroundResource(R.drawable.shape_offline_driver);
            onlineImg.setImageResource(R.drawable.ic_offline_btn_homescreen);
            onlineText.setText("結束下線");
        }


        Log.v("ppking", " MaindriverData isError :  " + driverData.isError());
        Log.v("ppking", " MaindriverData getAccount :  " + driverData.getAccount());
        Log.v("ppking", " MaindriverData getEmail :  " + driverData.getEmail());
        Log.v("ppking", " MaindriverData getApiKey :  " + driverData.getApiKey());
        Log.v("ppking", " MaindriverData getStatus :  " + driverData.getStatus());
        Log.v("ppking", " MaindriverData getCreatedAt :  " + driverData.getCreatedAt());
        Log.v("ppking", " MaindriverData getType :  " + driverData.getType());
        Log.v("ppking", " MaindriverData getSavings :  " + driverData.getSavings());



    }
    public void driver_travel_record(View view){

        Bundle bundle = new Bundle();
        bundle.putString("phone" , phone);
        bundle.putSerializable("driverData" , driverData);
        Intent it = new Intent(this , Driver_Travel_Record_Activity.class);
        it.putExtras(bundle);
        startActivity(it);
    }
    public void driver_point_record(View view){
        Bundle bundle = new Bundle();
        bundle.putString("phone" , phone);
        bundle.putSerializable("driverData" , driverData);
        Intent it = new Intent(this , Driver_Point_Record_Activity.class);
        it.putExtras(bundle);
        startActivity(it);
    }
    public void driver_info(View view){
        Intent it = new Intent(this , Driver_Info_Activity.class);
        startActivity(it);
    }
    public void driverOnline(View view) {

        Log.v("ppking" , " driverData.getStatus() :  " + driverData.getStatus());
        if (driverData.getStatus() == 2) {
            driverOnline.setBackgroundResource(R.drawable.shape_online_driver);
            onlineImg.setImageResource(R.drawable.ic_car_online_btn_homesreen);
            onlineText.setText("上線載客");
            status = "1";

        } else if (driverData.getStatus() == 1) {
            //按下判斷
            driverOnline.setBackgroundResource(R.drawable.shape_offline_driver);
            onlineImg.setImageResource(R.drawable.ic_offline_btn_homescreen);
            onlineText.setText("結束下線");
            status = "2";
        }

        Connect_API.putdriverstatus(this, phone, status, driverData.getApiKey() , new Connect_API.OnPutDriverStatusListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , "" + e.getMessage());
                Log.v("ppking" , "jsonError : " + jsonError);
                Toast.makeText(Driver_Main_Menu_Activity.this , "連線異常" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String isError, String result) {
                Log.v("ppking" , "isError :  " + isError);
                Log.v("ppking" , "result : " + result);
                if (isError.equals("false")){

                    ReLogin();
                    if (driverData.getStatus() == 1){
                        Intent it = new Intent(Driver_Main_Menu_Activity.this , Driver_WaitMatch_Activity.class);
                        startActivity(it);
                    }
                }
            }
        });
    }

    public void ReLogin(){
        Connect_API.driverLogin(this, phone, password, new Connect_API.OnDriverLoginListener() {
            @Override
            public void onLoginSuccess(DriverData newDriverData) {

                driverData = newDriverData;
            }

            @Override
            public void onLoginFail(String isFail, String msg) {


            }

            @Override
            public void onFail(Exception e, String jsonError) {

            }
        });
    }
}
