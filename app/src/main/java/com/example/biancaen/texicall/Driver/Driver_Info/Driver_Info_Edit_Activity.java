package com.example.biancaen.texicall.Driver.Driver_Info;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.biancaen.texicall.Driver.Driver_Point_Record.Driver_Point_Record_Activity;
import com.example.biancaen.texicall.Driver.Driver_Travel_Record.Driver_Travel_Record_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.DriverData;
import com.example.biancaen.texicall.connectapi.UserData;

public class Driver_Info_Edit_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static String getPhone;
    private static String getPassword;
    private static String driverApiKey;
    private EditText name , carShowText , carNumberText , phone , password;
    private DriverData getDriverData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__info__edit_);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_driver_info_edit);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_info_edit);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_driver_info_edit);
        navigationView.setNavigationItemSelectedListener(this);

        name = (EditText)findViewById(R.id.name);
        carShowText = (EditText)findViewById(R.id.carShow);
        carNumberText = (EditText)findViewById(R.id.carNumber);
        phone = (EditText)findViewById(R.id.phone);
        password = (EditText)findViewById(R.id.password);

        SharedPreferences sharedPreferences = getSharedPreferences("driver" , MODE_PRIVATE);
        getPhone = sharedPreferences.getString("phone", null);
        getPassword = sharedPreferences.getString("password" , null);
        driverApiKey = sharedPreferences.getString("driverApiKey" , null);


        GetStatus();
        ReLogin();
        phone.setText(getPhone);
        password.setText(getPassword);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_info_edit);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_driver_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.infoEditOption){
            ChangeInfo();
            Intent it = new Intent(this , Driver_Info_Activity.class);
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
            startActivity(it);

        } else if (id == R.id.nav_driver_point_list) {
            Intent it = new Intent(this , Driver_Point_Record_Activity.class);
            startActivity(it);

        } else if (id == R.id.nav_id__info) {
            Intent it = new Intent(this , Driver_Info_Activity.class);
            startActivity(it);

        } else if (id == R.id.nav_home) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_info_edit);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void ChangeInfo(){

        Connect_API.driverModify(this, getDriverData.getEmail(), getPhone, getPassword
                , password.getText().toString(), name.getText().toString(), carNumberText.getText().toString(),
                carShowText.getText().toString(), driverApiKey, new Connect_API.OnModifyChangeListener() {
                    @Override
                    public void onSuccess(String isFail, String msg) {
                        Log.v("ppking" , " isFail  : "+isFail);
                        Log.v("ppking" , " msg  : "+msg);
                        if (isFail.equals("false")){
                            Toast.makeText(Driver_Info_Edit_Activity.this , ""+msg , Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Driver_Info_Edit_Activity.this , ""+msg , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFail(Exception e, String jsonError) {
                        Log.v("ppking" , " Exception  : "+e);
                        Log.v("ppking" , " jsonError  : "+jsonError);
                    }
                });
    }

    public void GetStatus(){
        Connect_API.getdriverstatus(this, getPhone, driverApiKey, new Connect_API.OnGetDriverStatusListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , "getdriverstatus !!");
                Log.v("ppking" , "Exception  : " + e);
                Log.v("ppking" , "jsonError  : " + jsonError);
            }

            @Override
            public void onSuccess(String isError, String result, String status, String tasknumber, String carshow, String carnumber) {
                carShowText.setText(carshow);
                carNumberText.setText(carnumber);
            }
        });
    }

    public void ReLogin(){
        Connect_API.driverLogin(this, getPhone, getPassword, new Connect_API.OnDriverLoginListener() {
            @Override
            public void onLoginSuccess(DriverData driverData) {
                getDriverData = driverData;
                name.setText(driverData.getName());

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
