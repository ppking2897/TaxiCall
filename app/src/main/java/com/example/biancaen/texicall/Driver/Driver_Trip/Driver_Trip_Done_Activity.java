package com.example.biancaen.texicall.Driver.Driver_Trip;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.example.biancaen.texicall.Beginning.MainMenuActivity;
import com.example.biancaen.texicall.Driver.Driver_Info.Driver_Info_Activity;
import com.example.biancaen.texicall.Driver.Driver_Main_Menu.Driver_Main_Menu_Activity;
import com.example.biancaen.texicall.Driver.Driver_Point_Record.Driver_Point_Record_Activity;
import com.example.biancaen.texicall.Driver.Driver_Travel_Record.Driver_Travel_Record_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.CheckOutData;
import com.example.biancaen.texicall.connectapi.Connect_API;

public class Driver_Trip_Done_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean isLogout;
    private CheckOutData checkOutData;
    private String phone;
    private String driverApiKey;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__trip__done_);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_driver_trip_done);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_trip_done_);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_driver_trip_done);
        navigationView.setNavigationItemSelectedListener(this);

        sharedPreferences = getSharedPreferences("driver" , MODE_PRIVATE);
        phone =sharedPreferences.getString("phone", null);
        driverApiKey = sharedPreferences.getString("driverApiKey" , null);

        ChangeStatus();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_trip_done_);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (!isLogout){

            Toast.makeText(this , "再按一次返回即可登出" , Toast.LENGTH_SHORT).show();
            isLogout = true;

        }else if (isLogout){

            Logout();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_driver_travel_record) {
            Intent it = new Intent(this, Driver_Travel_Record_Activity.class);
            startActivity(it);

        } else if (id == R.id.nav_driver_point_list) {
            Intent it = new Intent(this, Driver_Point_Record_Activity.class);
            startActivity(it);

        } else if (id == R.id.nav_id__info) {
            Intent it = new Intent(this, Driver_Info_Activity.class);
            startActivity(it);

        } else if (id == R.id.nav_home) {
            Intent it = new Intent(this, Driver_Main_Menu_Activity.class);
            startActivity(it);

        } else if (id == R.id.nav_logout) {
            Logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_trip_done_);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void settle(View view){
        sharedPreferences = getSharedPreferences("driver" , MODE_PRIVATE);
        final String destination =  sharedPreferences.getString("destination" , null);
        final String location =  sharedPreferences.getString("location" , null);

        Bundle bundle = getIntent().getExtras();
        checkOutData = (CheckOutData)bundle.getSerializable("CheckOutData");

        Driver_Trip_Done_Dialog driver_trip_done_dialog =
                new Driver_Trip_Done_Dialog(Driver_Trip_Done_Activity.this , checkOutData , location , destination ,
                        phone , driverApiKey);
        driver_trip_done_dialog.CreateTripDoneDialog();
    }

    public void Logout(){
        SharedPreferences sharedPreferences = getSharedPreferences("driver" , MODE_PRIVATE);
        String phone = sharedPreferences.getString("phone", null);
        String driverApiKey = sharedPreferences.getString("driverApiKey" , null);

        Connect_API.loginOut(this, phone, driverApiKey, new Connect_API.OnLoginOutListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , "Exception : " +e);
                Log.v("ppking" , "jsonError : " +jsonError);
            }

            @Override
            public void onSuccess(boolean isError, String message) {
                if (!isError){
                    Toast.makeText(Driver_Trip_Done_Activity.this ,""+message , Toast.LENGTH_SHORT).show();
                    //清除所有上一頁Activity
                    Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Toast.makeText(Driver_Trip_Done_Activity.this ,""+message , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void ChangeStatus(){
        Connect_API.putdriverstatus(this, phone, "1", driverApiKey, new Connect_API.OnPutDriverStatusListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , "putdriverstatus Exception  : " + e);
                Log.v("ppking" , "putdriverstatus jsonError  : " + jsonError);
            }

            @Override
            public void onSuccess(String isError, String result) {
                Log.v("ppking" , "putdriverstatus result  : " + result);
            }
        });
    }
}
