package com.example.biancaen.texicall.Passenger.Passenger_On_The_Way;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biancaen.texicall.Passenger.Passenger_Driver_Arrived.Passenger_Driver_Arrived_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_TakeRide_And_Arrived.Passenger_In_The_Shuttle_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.PairInfoData;
import com.example.biancaen.texicall.connectapi.UserData;

import java.util.Timer;
import java.util.TimerTask;

public class Passenger_On_The_Way_Activity extends AppCompatActivity {
    private Timer timer;
    private TextView arriveTime ;
    private static PairInfoData pairInfoData;
    private static UserData userData;
    private static String taskNumber;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger__on__the__way_);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_OnTheWay);
        setSupportActionBar(toolbar);

        ImageView imageView =  (ImageView)findViewById(R.id.cancelCar);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelCar_Dialog dialog = new CancelCar_Dialog(Passenger_On_The_Way_Activity.this);
                dialog.Create_CancelCar_Dialog();
            }
        });

        UpdateUserStatus();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        InitView();

    }

    public void contactDriver(View view){

        SharedPreferences sharedPreferences = getSharedPreferences("driver" , MODE_PRIVATE);
        final String driverPhone = sharedPreferences.getString("driverPhone" , null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this , R.style.Contact_Dialog);
        View layout = LayoutInflater.from(this).inflate(R.layout.layout_contact_dialog , null);

        TextView textView = (TextView)layout.findViewById(R.id.numberPhone);
        textView.setText("乘客電話號碼為:\n"+driverPhone + "\n是否要移動到撥號畫面?");

        LinearLayout knowButton = (LinearLayout)layout.findViewById(R.id.knowButton);

        ImageView imageView = (ImageView)layout.findViewById(R.id.closeDialog);

        builder.setView(layout);

        alertDialog = builder.create();
        alertDialog.show();

        knowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( ContextCompat.checkSelfPermission(
                        Passenger_On_The_Way_Activity.this, Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Passenger_On_The_Way_Activity.this,
                            new String[]{Manifest.permission.CALL_PHONE},123);
                }else {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+driverPhone));
                    startActivity(intent);
                    alertDialog.dismiss();
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int grantResult : grantResults){
            if (grantResult == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+pairInfoData.getDriver()));
                startActivity(intent);
                alertDialog.dismiss();
            }
        }
    }

    //自定義返回鍵位置
    @Override
    public boolean onSupportNavigateUp() {
        Intent it = new Intent(this , Passenger_On_The_Way_Rates_Activity.class);
        startActivity(it);
        return true;
    }

    public void InitView(){
        arriveTime = (TextView)findViewById(R.id.arriveTime);
        final TextView arriveAddress = (TextView)findViewById(R.id.arriveAddress);
        final TextView carType = (TextView)findViewById(R.id.carType);
        final TextView carIdNumber = (TextView)findViewById(R.id.carIdNumber);
        final TextView driverName = (TextView)findViewById(R.id.name);

        final SharedPreferences sharedPreferences = getSharedPreferences("passenger" , MODE_PRIVATE);
        final String location =sharedPreferences.getString("location", null);

        taskNumber =sharedPreferences.getString("taskNumber" , null);
        String phone = sharedPreferences.getString("phoneNumber" , null);
        final String password = sharedPreferences.getString("passWord" , null);

        Connect_API.userLogin(this, phone, password, new Connect_API.OnUserLoginListener() {
            @Override
            public void onLoginSuccess(UserData newUserData) {
                userData = newUserData;
                timer = new Timer();
                timer.schedule(new TaskWaitTime() , 0 , 1000);
                Connect_API.getpairinfo(Passenger_On_The_Way_Activity.this, taskNumber, userData.getApiKey(), new Connect_API.OnGetPairInfoListener() {
                    @Override
                    public void onFail(Exception e, String jsonError) {

                    }

                    @Override
                    public void onSuccessGetPairInfo(PairInfoData newPairInfoData) {
                        pairInfoData = newPairInfoData;
                        arriveAddress.setText(pairInfoData.getAddr_start_addr());
                        carType.setText(pairInfoData.getCarnshow());
                        carIdNumber.setText(pairInfoData.getCarnumber());
                        driverName.setText(pairInfoData.getName());
                        sharedPreferences.edit().putString("driverPhone" , newPairInfoData.getDriver()).apply();
                    }

                    @Override
                    public void onWaiting(String isError, String message) {

                    }
                });
            }

            @Override
            public void onLoginFail(String isFail, String msg) {

            }

            @Override
            public void onFail(Exception e, String jsonError) {

            }
        });
    }

    public class TaskWaitTime extends TimerTask{

        @Override
        public void run() {
            Connect_API.waittime(Passenger_On_The_Way_Activity.this, taskNumber, userData.getApiKey(), new Connect_API.OnWaitTimeListener() {

                @Override
                public void onFail(Exception e, String jsonError) {
                    Log.v("ppking" , "Exception" + e);
                    Log.v("ppking" , "jsonError" + jsonError);

                }

                @Override
                public void onSuccess(String isError, String task_status, String distance, int time) {
                    Log.v("ppking" , "isError" + isError);
                    Log.v("ppking" , "task_status" + task_status);
                    Log.v("ppking" , "distance" + distance);
                    Log.v("ppking" , "time" + time);

                    SharedPreferences sharedPreferences = getSharedPreferences("passenger" , MODE_PRIVATE);
                    sharedPreferences.edit().putString("task_status" , task_status).apply();

                    //task_status 2 配對成功  3 司機抵達
                    arriveTime.setText(""+time);
                    if (task_status.equals("3")){
                        if (timer!=null){
                            timer.cancel();
                            timer = null ;
                        }
                        Intent it = new Intent(Passenger_On_The_Way_Activity.this , Passenger_Driver_Arrived_Activity.class);
                        startActivity(it);
                        finish();
                    }else if (task_status.equals("4")){
                        if (timer!=null){
                            timer.cancel();
                            timer = null ;
                        }
                        Intent it = new Intent(Passenger_On_The_Way_Activity.this , Passenger_In_The_Shuttle_Activity.class);
                        startActivity(it);
                        finish();
                    }
                }
            });
        }
    }

    public void UpdateUserStatus(){
        SharedPreferences sharedPreferences = getSharedPreferences("passenger" , MODE_PRIVATE);
        final String phoneNumber =sharedPreferences.getString("phoneNumber", null);
        String passWord =sharedPreferences.getString("passWord", null);

        Connect_API.userLogin(this, phoneNumber, passWord, new Connect_API.OnUserLoginListener() {
            @Override
            public void onLoginSuccess(UserData userData) {
                Connect_API.putstatus(Passenger_On_The_Way_Activity.this, phoneNumber, "2", userData.getApiKey(), new Connect_API.OnPutStatusListener() {
                    @Override
                    public void onFail(Exception e, String jsonError) {

                    }

                    @Override
                    public void onSuccess(String isError, String result) {
                        Log.v("ppking" ," isError : "+isError );
                        Log.v("ppking" ," result : "+result );
                    }
                });
            }

            @Override
            public void onLoginFail(String isFail, String msg) {

            }

            @Override
            public void onFail(Exception e, String jsonError) {
                Toast.makeText(Passenger_On_The_Way_Activity.this , "連線異常" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null){
            timer.cancel();
            timer = null;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        timer = new Timer();
        timer.schedule(new TaskWaitTime() , 0 , 1000);
    }
}

