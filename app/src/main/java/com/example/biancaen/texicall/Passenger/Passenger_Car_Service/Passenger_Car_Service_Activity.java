package com.example.biancaen.texicall.Passenger.Passenger_Car_Service;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biancaen.texicall.Beginning.MainMenuActivity;
import com.example.biancaen.texicall.Support_Class.Get_Location;
import com.example.biancaen.texicall.Passenger.Passenger_Customer_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Rates.Passenger_Rates_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Edit.Passenger_Info_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Sent_Car_Record.Passenger_Sent_Car_Record_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.UserData;
import com.google.android.gms.maps.model.LatLng;

public class Passenger_Car_Service_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private int emptyTripCount = 1;
    private int emptyTripPay = 60;
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
    private boolean isLogout;

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

        //Todo 空趟的次數已及要加收的價格資料存放位置 emptyTripCount emptyTripPay

        TextView textView_emptyTripCount = (TextView) findViewById(R.id.null_Empty_Trip_Count);
        TextView textView_emptyTripPay = (TextView) findViewById(R.id.null_Empty_Trip_ToPay);

        textView_emptyTripCount.setText(""+emptyTripCount);
        textView_emptyTripPay.setText("" + emptyTripPay);

        if (emptyTripCount != 0|| emptyTripPay !=0){
            Empty_Dialog empty_dialog = new Empty_Dialog(this , emptyTripCount , emptyTripPay);
            empty_dialog.CreateEmptyDialog();
        }

        Bundle getBundle = this.getIntent().getExtras();
        userData = (UserData)getBundle.getSerializable("userData");
        phoneNumber = getBundle.getString("phoneNumber");
        passWord = getBundle.getString("passWord");

        InputOrSelect();

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

            super.onBackPressed();
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

        location = "";
        destination = "";

        boolean isEmpty = false;

        if (!isGetOnRecordAddress){
            if (f1.getOnAddress().equals("")){
                Toast.makeText(this , "輸入不得空格!!" , Toast.LENGTH_SHORT).show();
                isEmpty = true ;

            }else{
                location = f1.getGetOnCity() + f1.getGetOnArea() + f1.getOnAddress();
            }
        } else {

            location = f2.getGetOnRecord();
        }


        if (!isGetOffRecordAddress){
            if (f3.getOffAddress().equals("")){
                Toast.makeText(this , "輸入不得空格!!" , Toast.LENGTH_SHORT).show();
                isEmpty = true ;

            }else{
                destination = f3.getGetOffCity() + f3.getGetOffArea() + f3.getOffAddress();
            }
        }else {
            destination = f4.getGetOffRecord();
        }

        if (!isEmpty || !location.equals("") || !destination.equals("")){
            Get_Location get_location = new Get_Location();

            LatLng startLatLng = get_location.getLocationFromAddress(this , location);
            LatLng endLatLng = get_location.getLocationFromAddress(this ,destination);


            if (startLatLng != null){
                startLat = String.valueOf(startLatLng.latitude);
                startLng = String.valueOf(startLatLng.longitude);
                Log.v("ppking" , " startLat  +   startLng : " +  startLat  +"  ,  " + startLng);
            }else{
                Toast.makeText(this , "請輸入詳細的上車地點" , Toast.LENGTH_SHORT).show();
            }


            if (endLatLng != null){
                endLat = String.valueOf(endLatLng.latitude);
                endLng = String.valueOf(endLatLng.longitude);
                Log.v("ppking" , " startLat  +   startLng : " +  endLat  +"  ,  " + endLng);
            }else{
                Toast.makeText(this , "請輸入詳細的下車地點" , Toast.LENGTH_SHORT).show();
            }
        }

        final boolean finalIsEmpty = isEmpty;
        Connect_API.rate(this , startLng  , startLat , endLng  , endLat , phoneNumber , userData.getApiKey(), new Connect_API.OnRateListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , "onFail : " + e.getMessage());
                if (!finalIsEmpty){
                    Toast.makeText(Passenger_Car_Service_Activity.this , "車程橫跨過多縣市，HB不提供此服務" , Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onSuccess(String isErrorResult, int price, int time, String distance) {

                if (isErrorResult.equals("false")){
                    DataIntent(price , time);
                }else {
                    Toast.makeText(Passenger_Car_Service_Activity.this,"試算結果錯誤，請確認地址是否正確!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //資料丟入成功後回傳的資料，並傳到下一個activity
    public void DataIntent (final int price , final int time){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putSerializable("userData" , userData);
                bundle.putInt("price" , price);
                bundle.putInt("time" , time);
                bundle.putString("phoneNumber" , phoneNumber);

                bundle.putString("location" , location);
                bundle.putString("destination" , destination);
                bundle.putString("passenger_number" , passenger_Number.getText().toString());
                bundle.putString("comment" , comment.getText().toString());

                bundle.putString("startLat" , startLat);
                bundle.putString("startLng" , startLng);
                bundle.putString("endLat" , endLat);
                bundle.putString("endLng" , endLng);

                Intent it = new Intent(Passenger_Car_Service_Activity.this , Passenger_Rates_Activity.class);
                it.putExtras(bundle);
                startActivity(it);
            }
        });
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

}