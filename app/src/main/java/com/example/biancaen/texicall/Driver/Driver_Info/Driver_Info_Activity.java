package com.example.biancaen.texicall.Driver.Driver_Info;

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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.biancaen.texicall.Driver.Driver_Main_Menu.Driver_Main_Menu_Activity;
import com.example.biancaen.texicall.Driver.Driver_Point_Record.Driver_Point_Record_Activity;
import com.example.biancaen.texicall.Driver.Driver_Travel_Record.Driver_Travel_Record_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.DriverData;

public class Driver_Info_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private static DriverData driverData;
    private static String phone;
    private static String password;
    private String carShowData;
    private String carNumberData;
    private TextView driverName , carShowText , carNumberText , driverPhone , driverMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__info_);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_driver_info);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_info);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_driver_info);
        navigationView.setNavigationItemSelectedListener(this);

        driverName = (TextView)findViewById(R.id.driverName);
        carShowText = (TextView)findViewById(R.id.carShowText);
        carNumberText = (TextView)findViewById(R.id.carNumberText);
        driverPhone = (TextView)findViewById(R.id.driverPhone);
        driverMail = (TextView)findViewById(R.id.driverMail);


        Bundle bundle = getIntent().getExtras();

        if (bundle!=null){
            driverData = (DriverData)bundle.getSerializable("driverData");
            phone = bundle.getString("phone");
            password = bundle.getString("password");
        }

        GetStatus();

        driverName.setText(driverData.getName());
        carShowText.setText(carShowData);
        carNumberText.setText(carNumberData);
        driverPhone.setText(phone);
        driverMail.setText(driverData.getEmail());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_info);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_driver_info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.infoOption){
            Log.v("ppking" , " Edit info !! ");
            Intent it = new Intent(this , Driver_Info_Edit_Activity.class);
            startActivity(it);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_driver_travel_record) {
            Intent it = new Intent(this , Driver_Travel_Record_Activity.class);
            it.putExtras(TransData());
            startActivity(it);

        } else if (id == R.id.nav_driver_point_list) {
            Intent it = new Intent(this , Driver_Point_Record_Activity.class);
            it.putExtras(TransData());
            startActivity(it);

        } else if (id == R.id.nav_id__info) {


        } else if (id == R.id.nav_home) {
            Intent it = new Intent(this , Driver_Main_Menu_Activity.class);
            it.putExtras(TransData());
            startActivity(it);
        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_info);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void GetStatus(){
        Connect_API.getdriverstatus(this, phone, driverData.getApiKey(), new Connect_API.OnGetDriverStatusListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , "getdriverstatus !!");
                Log.v("ppking" , "Exception  : " + e);
                Log.v("ppking" , "jsonError  : " + jsonError);
            }

            @Override
            public void onSuccess(String isError, String result, String status, String tasknumber, String carshow, String carnumber) {
                Log.v("ppking" , "getdriverstatus !!");
                Log.v("ppking" , "isError  : " + isError);
                Log.v("ppking" , "result  : " + result);
                Log.v("ppking" , "status  : " + status);
                Log.v("ppking" , "tasknumber  : " + tasknumber);
                Log.v("ppking" , "carshow  : " + carshow);
                Log.v("ppking" , "carnumber  : " + carnumber);

                carShowData = carshow;
                carNumberData = carnumber;
            }
        });
    }

    public Bundle TransData(){
        Bundle bundle = new Bundle();
        bundle.putString("phone" , phone);
        bundle.putSerializable("driverData" , driverData);
        bundle.putString("password" , password);
        return bundle;
    }
}
