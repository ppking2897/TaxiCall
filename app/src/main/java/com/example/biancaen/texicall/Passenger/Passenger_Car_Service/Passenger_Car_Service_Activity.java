package com.example.biancaen.texicall.Passenger.Passenger_Car_Service;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biancaen.texicall.Beginning.MainMenuActivity;
import com.example.biancaen.texicall.Passenger.Passenger_On_The_Way.Passenger_On_The_Way_Activity;
import com.example.biancaen.texicall.Support_Class.Get_Location;
import com.example.biancaen.texicall.Passenger.Passenger_Customer_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Rates.Passenger_Rates_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Edit.Passenger_Info_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Sent_Car_Record.Passenger_Sent_Car_Record_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.UserData;
import com.google.android.gms.maps.model.LatLng;

import static android.app.AlertDialog.THEME_DEVICE_DEFAULT_DARK;
import static android.app.AlertDialog.THEME_HOLO_DARK;
import static android.app.AlertDialog.THEME_HOLO_LIGHT;

public class Passenger_Car_Service_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private int emptyTripCount;
    private int emptyTripPay ;
    private TextView passenger_Number;
    private EditText comment;
    private int number = 1;
    private static UserData userData;
    private Passenger_Car_Service_Fragment_01 f1;
    private Passenger_Car_Service_Fragment_02 f2;
    private Passenger_Car_Service_Fragment_03 f3;
    private Passenger_Car_Service_Fragment_04 f4;
    private FragmentManager fragmentManager;
    private boolean isGetOnRecordAddress , isGetOffRecordAddress;
    private String location;
    private String destination;
    private String startLng ;
    private String startLat ;
    private String endLng ;
    private String endLat ;
    private static String phoneNumber;
    private static String passWord;
    private static String passengerApiKey;
    private boolean isLogout;
    private TextView textView_emptyTripCount,textView_emptyTripPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_car__service_);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_car_service);
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_car_service);
        navigationView.setCheckedItem(R.id.nav_car_service);
        navigationView.setNavigationItemSelectedListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_car_service);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle
                (this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        passenger_Number = (TextView) findViewById(R.id.passenger_Number);
        comment = (EditText)findViewById(R.id.textView_Comment);

        textView_emptyTripCount = (TextView) findViewById(R.id.null_Empty_Trip_Count);
        textView_emptyTripPay = (TextView) findViewById(R.id.null_Empty_Trip_ToPay);

        InputOrSelect();

        InitialData();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //確保每次喚醒都能兩次返回登出
        isLogout = false;
        //確保每次喚醒滑頁能夠checked
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_car_service);
        navigationView.setCheckedItem(R.id.nav_car_service);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_car_service);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (!isLogout){

            Toast.makeText(this , "再按一次返回即可登出" , Toast.LENGTH_SHORT).show();
            isLogout = true;

        }else if (isLogout){

            logout();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_car_service) {

        } else if (id == R.id.nav_sent_car_record) {
            Intent it = new Intent(this , Passenger_Sent_Car_Record_Activity.class);
            it.putExtras(TransUserData());
            startActivity(it);

        } else if (id == R.id.nav_customer_service) {

            Intent it = new Intent(this , Passenger_Customer_Activity.class);
            it.putExtras(TransUserData());
            startActivity(it);

        } else if (id == R.id.nav_account) {

            Intent it = new Intent(this , Passenger_Info_Activity.class);
            it.putExtras(TransUserData());
            startActivity(it);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_car_service);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //人數增加以及減少按鍵
    public void btn_reduce(View view){
        if (number != 1){
            number -= 1;
        }
        passenger_Number.setText("" + number);
    }
    public void btn_plus(View view){
        number += 1;
        passenger_Number.setText("" + number);
    }

    //開始試算金額丟入資料
    public void rates(View view){
        //if (emptyTripCount >= 3){
//            AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.Contact_Dialog);
//            View dialogView = LayoutInflater.from(this).inflate(R.layout.layout_car_service_empty_stop_service , null);
//
//            LinearLayout knowButton = (LinearLayout)dialogView.findViewById(R.id.knowButton);
//
//            builder.setView(dialogView);
//            final AlertDialog alertDialog = builder.create();
//            alertDialog.show();
//
//            knowButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    alertDialog.dismiss();
//                }
//            });

        //}else {

            location = "";
            destination = "";

            boolean isEmpty = false;

            //判斷利用已記錄的地址或者利用輸入方式輸入地址是否為空格
            if (!isGetOnRecordAddress) {
                if (f1.getOnAddress().equals("")) {
                    Toast.makeText(this, "輸入不得空格!!", Toast.LENGTH_SHORT).show();
                    isEmpty = true;

                } else {
                    location = f1.getGetOnCity() + f1.getGetOnArea() + f1.getOnAddress();
                }
            } else {

                location = f2.getGetOnRecord();
                if (location.equals("並無常用地址紀錄")) {
                    isEmpty = true;
                }
            }

            if (!isGetOffRecordAddress) {
                if (f3.getOffAddress().equals("")) {
                    Toast.makeText(this, "輸入不得空格!!", Toast.LENGTH_SHORT).show();
                    isEmpty = true;

                } else {
                    destination = f3.getGetOffCity() + f3.getGetOffArea() + f3.getOffAddress();
                }
            } else {
                destination = f4.getGetOffRecord();
                if (destination.equals("並無常用地址紀錄")) {
                    isEmpty = true;
                }
            }

            //判斷都不是空格後再將地址丟入方法轉換經緯度
            if (!isEmpty || !location.equals("") || !destination.equals("")) {
                Get_Location get_location = new Get_Location();

                LatLng startLatLng = get_location.getLocationFromAddress(this, location);
                LatLng endLatLng = get_location.getLocationFromAddress(this, destination);

                if (startLatLng != null) {
                    startLat = String.valueOf(startLatLng.latitude);
                    startLng = String.valueOf(startLatLng.longitude);
                    Log.v("ppking", " startLat  +   startLng : " + startLat + "  ,  " + startLng);
                } else {
                    Toast.makeText(this, "請輸入詳細的上車地點", Toast.LENGTH_SHORT).show();
                }

                if (endLatLng != null) {
                    endLat = String.valueOf(endLatLng.latitude);
                    endLng = String.valueOf(endLatLng.longitude);
                    Log.v("ppking", " startLat  +   startLng : " + endLat + "  ,  " + endLng);
                } else {
                    Toast.makeText(this, "請輸入詳細的下車地點", Toast.LENGTH_SHORT).show();
                }
            }

            if (!isEmpty) {
                Bundle bundle = new Bundle();
                //資料
                bundle.putSerializable("userData", userData);
                bundle.putString("phoneNumber", phoneNumber);
                bundle.putInt("emptyTripCount", emptyTripCount);
                bundle.putInt("emptyTripPay", emptyTripPay);
                bundle.putString("passWord", passWord);

                //起始結束地址

                SharedPreferences sharedPreferences = getSharedPreferences("passenger", MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("location", location);
                edit.putString("destination", destination);
                edit.apply();

                bundle.putString("location", location);
                bundle.putString("destination", destination);

                //乘客人數、備註
                bundle.putString("passenger_number", passenger_Number.getText().toString());
                bundle.putString("comment", comment.getText().toString());

                //起始、結束經緯度
                bundle.putString("startLat", startLat);
                bundle.putString("startLng", startLng);
                bundle.putString("endLat", endLat);
                bundle.putString("endLng", endLng);

                Intent it = new Intent(Passenger_Car_Service_Activity.this, Passenger_Rates_Activity.class);
                it.putExtras(bundle);
                startActivity(it);
            }
       // }
    }

    //選擇住址或者已記錄住址
    public void InputOrSelect(){

        f1 = new Passenger_Car_Service_Fragment_01();
        f2 = new Passenger_Car_Service_Fragment_02();
        f3 = new Passenger_Car_Service_Fragment_03();
        f4 = new Passenger_Car_Service_Fragment_04();

        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragment01 ,f1)
                .replace(R.id.fragment01 , f1 ,"option01")
                .commit();
        fragmentManager.beginTransaction().add(R.id.fragment02 ,f3)
                .replace(R.id.fragment02 , f3 ,"option03")
                .commit();

        RadioButton radioButton01 = (RadioButton) findViewById(R.id.radioButton01);
        RadioButton radioButton03 = (RadioButton) findViewById(R.id.radioButton03);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.onClickRadio);
        RadioGroup radioGroup02 = (RadioGroup) findViewById(R.id.onClickRadio02);

        radioButton01.setChecked(true);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.radioButton01){
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment01 , f1 ,"option01")
                            .commit();
                    isGetOnRecordAddress = false;

                } else if (checkedId ==R.id.radioButton02){
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment01 , f2  ,"option02")
                            .commit();
                    isGetOnRecordAddress = true;
                }
            }
        });

        radioButton03.setChecked(true);
        radioGroup02.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.radioButton03){
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment02 , f3 ,"option03")
                            .commit();

                    isGetOffRecordAddress = false;

                } else if (checkedId ==R.id.radioButton04){
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment02 , f4 ,"option03")
                            .commit();
                    isGetOffRecordAddress = true;
                }
            }
        });
    }

    //傳送的資料
    public Bundle TransUserData(){

        Bundle bundle = new Bundle();
        bundle.putString("passWord" , passWord );
        bundle.putSerializable("userData" , userData);
        bundle.putString("phoneNumber" , phoneNumber);

        return bundle;
    }

    public void logout(){
        Connect_API.loginOut(this, phoneNumber, userData.getApiKey(), new Connect_API.OnLoginOutListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , " Exception : " + e.getMessage());
                Log.v("ppking" , " jsonError : " + jsonError);
                Toast.makeText(Passenger_Car_Service_Activity.this ,"連線異常,登出失敗" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(boolean isError, String message) {
                Log.v("ppking" , " isError : " + isError);
                Log.v("ppking" , " message : " + message);
                if (!isError){
                    Toast.makeText(Passenger_Car_Service_Activity.this ,""+message , Toast.LENGTH_SHORT).show();
                    //清除所有上一頁Activity
                    Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Toast.makeText(Passenger_Car_Service_Activity.this ,""+message , Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void InitialData(){
        Bundle getBundle = this.getIntent().getExtras();
        if (getBundle!=null) {
            userData = (UserData) getBundle.getSerializable("userData");
            phoneNumber = getBundle.getString("phoneNumber");
            passWord = getBundle.getString("passWord");
            passengerApiKey = userData.getApiKey();
        }else{
            SharedPreferences sharedPreferences = getSharedPreferences("passenger" , MODE_PRIVATE);
            phoneNumber = sharedPreferences.getString("phoneNumber" , null);
            passWord = sharedPreferences.getString("passWord" , null);
            passengerApiKey = sharedPreferences.getString("passengerApiKey", null);
            ReLogin();
        }

        Connect_API.getStatus(this, phoneNumber, passengerApiKey, new Connect_API.OnGetStatusListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , " Exception   :   " +e );
                Log.v("ppking" , " jsonError   :   " +jsonError );
                Toast.makeText(Passenger_Car_Service_Activity.this , "連線異常" , Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onSuccess(String isError, String result, int status, String tasknumber, int misscatch_time, int misscatch_price) {
                if (isError.equals("false")){

                    //空趟的次數以及實間
                    emptyTripCount = misscatch_time;
                    emptyTripPay = misscatch_price;

                    textView_emptyTripCount.setText(""+emptyTripCount);
                    textView_emptyTripPay.setText("" + emptyTripPay);

                    Log.v("ppking" , " status car service  :  " + status);
                    if (emptyTripCount != 0|| emptyTripPay !=0){

                        Empty_Dialog empty_dialog = new Empty_Dialog(Passenger_Car_Service_Activity.this , emptyTripCount , emptyTripPay);
                        empty_dialog.CreateEmptyDialog();

                    }
                    if (status == 2){

                        Intent it = new Intent(Passenger_Car_Service_Activity.this , Passenger_On_The_Way_Activity.class);
                        startActivity(it);
                    }
                }else{
                    Toast.makeText(Passenger_Car_Service_Activity.this , "資料提取異常" , Toast.LENGTH_SHORT ).show();
                }
            }
        });
    }
    public void ReLogin(){
        Connect_API.userLogin(this, phoneNumber, passWord, new Connect_API.OnUserLoginListener() {
            @Override
            public void onLoginSuccess(UserData newUserData) {
                userData = newUserData;
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