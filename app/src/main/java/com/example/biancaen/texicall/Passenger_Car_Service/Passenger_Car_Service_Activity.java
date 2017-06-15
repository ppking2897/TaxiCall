package com.example.biancaen.texicall.Passenger_Car_Service;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.biancaen.texicall.Get_Location;
import com.example.biancaen.texicall.Passenger_Customer_Activity;
import com.example.biancaen.texicall.Passenger_On_The_Way.Passenger_On_The_Way_Activity;
import com.example.biancaen.texicall.Passenger_Rates.Passenger_Rates_Activity;
import com.example.biancaen.texicall.Passenger_Edit.Passenger_Info_Activity;
import com.example.biancaen.texicall.Passenger_Sent_Car_Record.Passenger_Sent_Car_Record_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.google.android.gms.maps.model.LatLng;

public class Passenger_Car_Service_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private int emptyTripCount = 1;
    private int emptyTripPay = 60;
    private TextView passenger_Number;
    private int number = 1;
    private EditText location , destination;

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
    public void fares(View view){
//        Bundle bundle = new Bundle();
//        bundle.putInt("emptyTripCount" , emptyTripCount);
//        bundle.putInt("emptyTripPay" , emptyTripPay);
//
//        Intent it = new Intent(this , Passenger_Rates_Activity.class);
//        it.putExtras(bundle);
//        startActivity(it);

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


            Connect_API.rate(startLat.toString(), startLng.toString(), endLat.toString(), endLng.toString(), apiKey, new Connect_API.OnRateListener() {
                @Override
                public void onFail(Exception e) {
                    Log.v("ppking" , "onFail : " + e.getMessage());
                }

                @Override
                public void onSuccess(boolean isErrorResult, int price, int time, String distance) {
                    Log.v("ppking" , " onSuccess  result : "+ isErrorResult);
                    Log.v("ppking" , " onSuccess  price : "+ price);
                    Log.v("ppking" , " onSuccess  time : "+ time);
                    Log.v("ppking" , " onSuccess  distance : "+ distance);
                    if (!isErrorResult){
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
}