package com.example.biancaen.texicall.Driver.Driver_Main_Menu;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biancaen.texicall.Beginning.MainMenuActivity;
import com.example.biancaen.texicall.Driver.Driver_Info.Driver_Info_Activity;
import com.example.biancaen.texicall.Driver.Driver_Passenger_Request.Driver_Passenger_Request_Activity;
import com.example.biancaen.texicall.Driver.Driver_Point_Record.Driver_Point_Record_Activity;
import com.example.biancaen.texicall.Driver.Driver_Travel_Record.Driver_Travel_Record_Activity;
import com.example.biancaen.texicall.Driver.Driver_Match.Driver_WaitMatch_Activity;
import com.example.biancaen.texicall.Support_Class.MyService;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.DriverData;
import com.example.biancaen.texicall.connectapi.PointData;

import java.util.List;


public class Driver_Main_Menu_Activity extends AppCompatActivity {

    private static DriverData driverData;
    private static String phone;
    private static String password;
    private LinearLayout driverOnline;
    private ImageView onlineImg;
    private TextView onlineText;
    private String status;
    private String locationProvider;
    private String latitude ;
    private String longitude ;
    private boolean isLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__main__menu__activicy);

        driverOnline = (LinearLayout)findViewById(R.id.driverOnline);
        onlineImg = (ImageView)findViewById(R.id.onlineImg);
        onlineText = (TextView)findViewById(R.id.onlineText);

        InitData();

        GetRemainPoint();
        //建立GPS Provide
        UpdateLocation();

        MyService service = new MyService();

    }

    public void driver_travel_record(View view){

        Intent it = new Intent(this , Driver_Travel_Record_Activity.class);
        startActivity(it);
    }
    public void driver_point_record(View view){

        Intent it = new Intent(this , Driver_Point_Record_Activity.class);
        startActivity(it);
    }

    public void driver_info(View view){
        Intent it = new Intent(this , Driver_Info_Activity.class);
        startActivity(it);
    }

    //上線載客或者結束下線並判斷目前狀態是哪種
    public void driverOnline(View view) {

        Log.v("ppking", " driverData.getStatus() :  " + driverData.getStatus());
        if (driverData.getStatus() == 2 || driverData.getStatus() == 3) {
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

        Connect_API.putdriverstatus(this, phone, status, driverData.getApiKey(), new Connect_API.OnPutDriverStatusListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking", "" + e.getMessage());
                Log.v("ppking", "jsonError : " + jsonError);
                Toast.makeText(Driver_Main_Menu_Activity.this, "連線異常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String isError, String result) {
                Log.v("ppking", "isError :  " + isError);
                Log.v("ppking", "result : " + result);
                if (isError.equals("false")) {

                    ReLogin();
                    if (driverData.getStatus() == 1) {
                        Intent it = new Intent(Driver_Main_Menu_Activity.this, Driver_WaitMatch_Activity.class);
                        startActivity(it);
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!isLogout){

            Toast.makeText(this , "再按一次返回即可登出" , Toast.LENGTH_SHORT).show();
            isLogout = true;

        }else if (isLogout){

            Logout();
        }
    }

    public void driver_logout(View view){
        Logout();
    }

    public void ReLogin(){

        Connect_API.driverLogin(this, phone, password, new Connect_API.OnDriverLoginListener() {
            @Override
            public void onLoginSuccess(DriverData newDriverData) {
                driverData = newDriverData;
                Log.v("ppking" , " status" + driverData.getStatus());
                SharedPreferences sharedPreferences = getSharedPreferences("driver" , MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("driverApiKey" , driverData.getApiKey());
                editor.putInt("driverStatus" , driverData.getStatus());
                editor.apply();

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
                    Intent it = new Intent(Driver_Main_Menu_Activity.this , Driver_Passenger_Request_Activity.class);
                    startActivity(it);
                }
            }

            @Override
            public void onLoginFail(String isFail, String msg) {

            }

            @Override
            public void onFail(Exception e, String jsonError) {

            }
        });
    }

    public void UpdateLocation(){

        if (ContextCompat.checkSelfPermission(
            Driver_Main_Menu_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED){ActivityCompat.requestPermissions(Driver_Main_Menu_Activity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);
        }else {

            GetNewLocation();
        }
    }
    //取得開啟GPS定位認可
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int grantResult : grantResults){
            if (grantResult == PackageManager.PERMISSION_GRANTED){
                GetNewLocation();
            }
        }
    }

    public void GetNewLocation(){
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        List<String> providers = locationManager.getProviders(true);

        if (providers.contains(LocationManager.GPS_PROVIDER)) {

            locationProvider = LocationManager.GPS_PROVIDER;

        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {

            locationProvider = LocationManager.NETWORK_PROVIDER;

        } else {

            Toast.makeText(this, "請打開網路以及GPS定位，以便找尋你的位置", Toast.LENGTH_SHORT).show();

        }

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        } else {

            if (locationProvider != null){
                Location location = locationManager.getLastKnownLocation(locationProvider);
            }
        }

        LocationListener locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                Log.v("ppking", "location.getLatitude() : " + location.getLatitude());
                Log.v("ppking", "location.getLongitude(); : " + location.getLongitude());
                latitude = String.valueOf(location.getLatitude());
                longitude = String.valueOf(location.getLongitude());
                UpdateLocationNow();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.v("ppking" , "provider :  " + provider);
                Log.v("ppking" , "status :  " + status);
                Log.v("ppking" , "extras :  " + extras);

            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.v("ppking" , "provider :  " + provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.v("ppking" , "provider :  " + provider);
            }
        };
        if (locationProvider!=null){
            locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);
        }
    }
    public void UpdateLocationNow(){
        Connect_API.updateLocation(this, longitude, latitude, phone, driverData.getApiKey(), new Connect_API.OnGetConnectStatusListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" ,  "UpdateLocationNow Exception  :  " + e);
                Log.v("ppking" ,  "UpdateLocationNow jsonError  :  " + jsonError);
            }

            @Override
            public void onSuccess(String isError, String message) {
                Log.v("ppking" ,  "UpdateLocationNow isError  :  " + isError);
                Log.v("ppking" ,  "UpdateLocationNow message  :  " + message);

            }
        });
    }

    public void Logout(){

        Connect_API.loginOut(this, phone, driverData.getApiKey(), new Connect_API.OnLoginOutListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , "Exception : " +e);
                Log.v("ppking" , "jsonError : " +jsonError);
            }

            @Override
            public void onSuccess(boolean isError, String message) {
                if (!isError){
                    Toast.makeText(Driver_Main_Menu_Activity.this ,""+message , Toast.LENGTH_SHORT).show();
                    //清除所有上一頁Activity
                    Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Toast.makeText(Driver_Main_Menu_Activity.this ,""+message , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void GetRemainPoint(){
        if (driverData.getStatus()!=3){
            Connect_API.getPointRecord(this, phone, driverData.getApiKey() , new Connect_API.OnPointRecordListener() {
                @Override
                public void onFail(Exception e, String jsonError) {
                    Log.v("ppking" , e.getMessage());
                    Log.v("ppking" , " jsonError :  "+jsonError);
                    Toast.makeText(Driver_Main_Menu_Activity.this , "連線出現異常" , Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(String isError, String message, String name, String savings, double sum, List<PointData> data) {
                    Driver_Main_Menu_Dialog driverMainMenuDialog = new Driver_Main_Menu_Dialog(Driver_Main_Menu_Activity.this , savings );
                    driverMainMenuDialog.CreatePointDialog();
                }
            });
        }
    }

    public void InitData(){
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){

            driverData = (DriverData)bundle.getSerializable("driverData");
            phone = bundle.getString("phone");
            password = bundle.getString("password");

            SharedPreferences sharedPreferences = getSharedPreferences("driver" , MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("phone" , phone);
            editor.putString("password" , password);
            editor.putString("driverApiKey" , driverData.getApiKey());
            editor.putInt("driverStatus" , driverData.getStatus());
            editor.apply();

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
                Intent it = new Intent(this , Driver_Passenger_Request_Activity.class);
                startActivity(it);
            }
        }else{
            Log.v("ppking" , "Main menu No bundle !");
            ReLogin();
        }
    }
}
