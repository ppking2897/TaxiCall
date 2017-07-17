package com.example.biancaen.texicall.Driver.Driver_Trip;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biancaen.texicall.Beginning.MainMenuActivity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.CheckOutData;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.TaskInfoData;

import java.util.Timer;
import java.util.TimerTask;

public class Driver_Google_Map_Activity extends AppCompatActivity {

    private static boolean start;
    private ImageView imageView;
    private TextView startTripText;
    private static TaskInfoData getTaskInfoData;
    private boolean isLogout;
    private String taskNumber;
    private String phone;
    private String passengerPhone;
    private String driverApiKey;
    private Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__google__map_);

        SharedPreferences sharedPreferences = getSharedPreferences("driver" , MODE_PRIVATE);
        taskNumber =sharedPreferences.getString("tasknumber", null);
        phone =sharedPreferences.getString("phone", null);
        driverApiKey = sharedPreferences.getString("driverApiKey" , null);
        passengerPhone = sharedPreferences.getString("passengerPhone" , null);


        Log.v("ppking" , " passengerPhone : " + passengerPhone);
        Log.v("ppking" , " phone : " + phone);

        Connect_API.taskinfo(Driver_Google_Map_Activity.this, taskNumber, driverApiKey, new Connect_API.OnTaskInfoListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , " onFail  Exception : " + e);
                Log.v("ppking" , " onFail  jsonError: " + jsonError);
            }

            @Override
            public void onSuccess(TaskInfoData taskInfoData) {
                getTaskInfoData = taskInfoData;
                Log.v("ppking" , " taskInfoData : " + getTaskInfoData);

            }
        });

        startTripText = (TextView)findViewById(R.id.startTripText);
        imageView = (ImageView)findViewById(R.id.startTrip);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (getTaskInfoData!=null){
                if (!start){
                    if (timer == null){
                        timer = new Timer();
                        timer.schedule(new RealPriceTask() , 0 , 3000);
                    }
                    imageView.setImageResource(R.drawable.btn_ending);
                    startTripText.setVisibility(View.INVISIBLE);
                    start = true;
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + getTaskInfoData.getAddr_end_addr());
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }else {
                    Connect_API.checkOut(Driver_Google_Map_Activity.this, taskNumber, passengerPhone, driverApiKey, new Connect_API.OnCheckOutListener() {
                        @Override
                        public void onFail(Exception e, String jsonError) {
                            Log.v("ppking" , "checkOut Exception  : " +e );
                            Log.v("ppking" , "checkOut jsonError  : " +jsonError );
                            Toast.makeText(Driver_Google_Map_Activity.this , "連線異常"  , Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onSuccess(CheckOutData data) {
                            if (timer!= null){
                                timer.cancel();
                                timer = null;
                            }

                            Log.v("ppking" , " CheckOutData getMisscatchprice   :  " + data.getMisscatchprice());
                            Log.v("ppking" , " CheckOutData getTime  :  " + data.getTime());
                            Log.v("ppking" , " CheckOutData getRemain_point  :  " + data.getRemain_point());
                            Log.v("ppking" , " CheckOutData getDate  :  " + data.getDate());
                            Log.v("ppking" , " CheckOutData getDistance  :  " + data.getDistance());
                            Log.v("ppking" , " CheckOutData getError  :  " + data.getError());
                            Log.v("ppking" , " CheckOutData getPrice  :  " + data.getPrice());

                            start=false;
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("CheckOutData" , data);
                            Intent it = new Intent(Driver_Google_Map_Activity.this , Driver_Trip_Done_Activity.class);
                            it.putExtras(bundle);
                            startActivity(it);
                        }
                    });
                }
            }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!isLogout){

            Toast.makeText(this , "再按一次返回即可退出應用程式" , Toast.LENGTH_SHORT).show();
            isLogout = true;

        }else if (isLogout){
            if (timer!=null){
                timer.cancel();
                timer = null;
                Logout();
            }
        }
    }
    public void Logout(){
        SharedPreferences sharedPreferences = getSharedPreferences("driver" , MODE_PRIVATE);
        String phone = sharedPreferences.getString("phone", null);

        Connect_API.loginOut(this, phone, driverApiKey, new Connect_API.OnLoginOutListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , "Exception : " +e);
                Log.v("ppking" , "jsonError : " +jsonError);
            }

            @Override
            public void onSuccess(boolean isError, String message) {
                if (!isError){
                    Intent intent = new Intent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                }else {
                    Toast.makeText(Driver_Google_Map_Activity.this ,""+message , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public class RealPriceTask extends TimerTask {

        @Override
        public void run() {
            Connect_API.realprice(Driver_Google_Map_Activity.this, taskNumber, driverApiKey, new Connect_API.OnRealPriceListener() {
                @Override
                public void onFail(Exception e, String jsonError) {
                    Log.v("ppking" , "realprice Exception  :  " +e);
                    Log.v("ppking" , "realprice jsonError  :  " +jsonError);
                }

                @Override
                public void onSuccess(String isError, String message, String distance) {
                    Log.v("ppking" , "realprice isError  :  " +isError);
                    Log.v("ppking" , "realprice message  :  " +message);
                    Log.v("ppking" , "realprice distance  :  " +distance);
                }
            });
        }
    }
}
