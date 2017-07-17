package com.example.biancaen.texicall.Passenger.Passenger_TakeRide_And_Arrived;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.biancaen.texicall.Beginning.MainMenuActivity;
import com.example.biancaen.texicall.Passenger.Passenger_Car_Service.Passenger_Car_Service_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Customer_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Edit.Passenger_Info_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Sent_Car_Record.Passenger_Sent_Car_Record_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;

import com.example.biancaen.texicall.notificaiton.HBMessageService;

public class Passenger_In_The_Shuttle_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentManager fragmentManager;
    private boolean isLogout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger__in__the__shuttle_);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_in_the_shuttle);
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view_in_the_shuttle);
        navigationView.setCheckedItem(R.id.nav_car_service);
        navigationView.setNavigationItemSelectedListener(this);

        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout_in_the_shuttle);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();


        Shuttle_Fragment_01 shuttle_fragment_01 = new Shuttle_Fragment_01();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.shuttle_Fragment , shuttle_fragment_01)
                .replace(R.id.shuttle_Fragment , shuttle_fragment_01 , "shuttle01")
                .commit();

        //判斷經由通知進來是否換頁
        GetTaskStatus();

        HBMessageService.setOnGetPrive(new HBMessageService.OnStartPriceListener() {
            @Override
            public void onGetPrice(String price) {
                final Shuttle_Fragment_02 shuttle_fragment_02 = new Shuttle_Fragment_02();

                SharedPreferences sharedPreferences = getSharedPreferences("passenger" , MODE_PRIVATE);
                String phone = sharedPreferences.getString("phoneNumber" , null);
                String passengerApiKey = sharedPreferences.getString("passengerApiKey" , null);

                sharedPreferences.edit().putString("price" , price).apply();

                Log.v("ppking" , "sharedPreferences.getString :  " + sharedPreferences.getString("destination" , null));



                Connect_API.putstatus(Passenger_In_The_Shuttle_Activity.this, phone, "1", passengerApiKey, new Connect_API.OnPutStatusListener() {
                    @Override
                    public void onFail(Exception e, String jsonError) {

                    }

                    @Override
                    public void onSuccess(String isError, String result) {
                        fragmentManager.beginTransaction().add(R.id.shuttle_Fragment , shuttle_fragment_02)
                                .replace(R.id.shuttle_Fragment , shuttle_fragment_02 , "shuttle02")
                                .commit();
                    }
                });

            }
        });
    }


    @Override
    public void onBackPressed() {

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_in_the_shuttle);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (!isLogout){

            Toast.makeText(this , "再按一次返回即可退出應用程式" , Toast.LENGTH_SHORT).show();
            isLogout = true;

        }else if (isLogout){

            logout();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_car_service) {

            Intent it = new Intent(this , Passenger_Car_Service_Activity.class);
            startActivity(it);
            finish();

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_in_the_shuttle);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void GetTaskStatus(){
        SharedPreferences sharedPreferences = getSharedPreferences("passenger" , MODE_PRIVATE);
        String taskNumber =sharedPreferences.getString("taskNumber" , null);
        String passengerApiKey =sharedPreferences.getString("passengerApiKey" , null);
        Connect_API.waittime(this, taskNumber, passengerApiKey, new Connect_API.OnWaitTimeListener() {
            @Override
            public void onFail(Exception e, String jsonError) {

            }

            @Override
            public void onSuccess(String isError, String task_status, String distance, int time) {
                if (task_status.equals("5")){
                    Shuttle_Fragment_02 shuttle_fragment_02 = new Shuttle_Fragment_02();
                    fragmentManager.beginTransaction().add(R.id.shuttle_Fragment , shuttle_fragment_02)
                            .replace(R.id.shuttle_Fragment , shuttle_fragment_02 , "shuttle02")
                            .commit();
                }
            }
        });
    }
    public void logout(){
        SharedPreferences sharedPreferences = getSharedPreferences("passenger" , MODE_PRIVATE);
        String phone = sharedPreferences.getString("phoneNumber" , null);
        String passengerApiKey = sharedPreferences.getString("passengerApiKey" , null);
        Connect_API.loginOut(this, phone, passengerApiKey, new Connect_API.OnLoginOutListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , " Exception : " + e.getMessage());
                Log.v("ppking" , " jsonError : " + jsonError);
                Toast.makeText(Passenger_In_The_Shuttle_Activity.this ,"連線異常,登出失敗" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(boolean isError, String message) {
                Log.v("ppking" , " isError : " + isError);
                Log.v("ppking" , " message : " + message);
                if (!isError){
                    Intent intent = new Intent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                }else {
                    Toast.makeText(Passenger_In_The_Shuttle_Activity.this ,""+message , Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
