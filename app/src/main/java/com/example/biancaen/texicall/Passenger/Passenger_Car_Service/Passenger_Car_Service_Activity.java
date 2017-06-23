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

import com.example.biancaen.texicall.Support_Class.CityAreaData;
import com.example.biancaen.texicall.Support_Class.Get_Location;
import com.example.biancaen.texicall.Passenger.Passenger_Customer_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Rates.Passenger_Rates_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Edit.Passenger_Info_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Sent_Car_Record.Passenger_Sent_Car_Record_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.List;

public class Passenger_Car_Service_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private int emptyTripCount = 1;
    private int emptyTripPay = 60;
    private TextView passenger_Number;
    private int number = 1;
    private EditText location , destination;
    private List<String> cityDataArray = new ArrayList<>();
    private List<CityAreaData> areaDataArray = new ArrayList<>();
    private List<String> area ;
    private Passenger_Car_Service_Fragment_01 f1;
    private Passenger_Car_Service_Fragment_02 f2;
    private Passenger_Car_Service_Fragment_03 f3;
    private Passenger_Car_Service_Fragment_04 f4;
    private FragmentManager fragmentManager;

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
        location = (EditText)findViewById(R.id.location);
        destination = (EditText)findViewById(R.id.destination);

        //Todo 空趟的次數已及要加收的價格資料存放位置 emptyTripCount emptyTripPay

        TextView textView_emptyTripCount = (TextView) findViewById(R.id.null_Empty_Trip_Count);
        TextView textView_emptyTripPay = (TextView) findViewById(R.id.null_Empty_Trip_ToPay);

        textView_emptyTripCount.setText(""+emptyTripCount);
        textView_emptyTripPay.setText("" + emptyTripPay);

        if (emptyTripCount != 0|| emptyTripPay !=0){
            Empty_Dialog empty_dialog = new Empty_Dialog(this , emptyTripCount , emptyTripPay);
            empty_dialog.CreateEmptyDialog();
        }

        InputOrSelect();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_car_service);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_car_service) {

        } else if (id == R.id.nav_sent_car_record) {
            Intent it = new Intent(this , Passenger_Sent_Car_Record_Activity.class);
            startActivity(it);

        } else if (id == R.id.nav_customer_service) {

            Intent it = new Intent(this , Passenger_Customer_Activity.class);
            startActivity(it);

        } else if (id == R.id.nav_account) {

            Intent it = new Intent(this , Passenger_Info_Activity.class);
            startActivity(it);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_car_service);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

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
    //開始試算
    public void rates(View view){

        if (!(location.getText().toString().equals("") || destination.getText().toString().equals(""))){
            Get_Location get_location = new Get_Location();
            LatLng startLatLng = get_location.getLocationFromAddress(this , location.getText().toString());

            Double startLat = startLatLng.latitude;
            Double startLng = startLatLng.longitude;

            LatLng endLatLng = get_location.getLocationFromAddress(this ,destination.getText().toString());

            Double endLat = endLatLng.latitude;
            Double endLng = endLatLng.longitude;

            Bundle getBundle = this.getIntent().getExtras();
            String apiKey = getBundle.getString("apiKey");


            Connect_API.rate(this,startLat.toString(), startLng.toString(), endLat.toString(), endLng.toString(),null, apiKey, new Connect_API.OnRateListener() {
                @Override
                public void onFail(Exception e, String jsonError) {
                    Log.v("ppking" , "onFail : " + e.getMessage());
                }

                @Override
                public void onSuccess(String isErrorResult, int price, int time, String distance) {
                    Log.v("ppking" , " onSuccess  result : "+ isErrorResult);
                    Log.v("ppking" , " onSuccess  price : "+ price);
                    Log.v("ppking" , " onSuccess  time : "+ time);
                    Log.v("ppking" , " onSuccess  distance : "+ distance);
                    if (isErrorResult.equals("false")){
                        DataIntent(price , time);
                    }else {
                        Toast.makeText(Passenger_Car_Service_Activity.this,"試算結果錯誤，請確認地址是否正確!!",Toast.LENGTH_SHORT).show();
                    }

                }

            });

        }else {
            Toast.makeText(this , "輸入不得空格!!" , Toast.LENGTH_SHORT).show();
        }
    }

    public void DataIntent (final int price , final int time){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putInt("price" , price);
                bundle.putInt("time" , time);
                Intent it = new Intent(Passenger_Car_Service_Activity.this , Passenger_Rates_Activity.class);
                it.putExtras(bundle);
                startActivity(it);
            }
        });
    }

    public void InputOrSelect(){

        f1 = new Passenger_Car_Service_Fragment_01();
        f2 = new Passenger_Car_Service_Fragment_02();
        f3 = new Passenger_Car_Service_Fragment_03();
        f4 = new Passenger_Car_Service_Fragment_04();

        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment01 , f1 ,"option01")
                .addToBackStack(null)
                .commit();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment02 , f3 ,"option03")
                .addToBackStack(null)
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
                            .addToBackStack(null)
                            .commit();

                } else if (checkedId ==R.id.radioButton02){
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment01 , f2  ,"option02")
                            .addToBackStack(null)
                            .commit();
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
                            .addToBackStack(null)
                            .commit();
                } else if (checkedId ==R.id.radioButton04){
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment02 , f4 ,"option03")
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }


}