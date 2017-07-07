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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biancaen.texicall.Beginning.MainMenuActivity;
import com.example.biancaen.texicall.Driver.Driver_Main_Menu.Driver_Main_Menu_Activity;
import com.example.biancaen.texicall.Driver.Driver_Point_Record.Driver_Point_Record_Activity;
import com.example.biancaen.texicall.Driver.Driver_Travel_Record.Driver_Travel_Record_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.DriverData;

public class Driver_Info_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private static String phone;
    private static String password;
    private static String driverApiKey;
    private static int driverStatus;
    private TextView driverName , carShowText , carNumberText , driverPhone , driverMail;
    private NavigationView navigationView;
    private boolean isLogout;

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

        navigationView = (NavigationView) findViewById(R.id.nav_view_driver_info);
        navigationView.setNavigationItemSelectedListener(this);

        driverName = (TextView)findViewById(R.id.driverName);
        carShowText = (TextView)findViewById(R.id.carShowText);
        carNumberText = (TextView)findViewById(R.id.carNumberText);
        driverPhone = (TextView)findViewById(R.id.driverPhone);
        driverMail = (TextView)findViewById(R.id.driverMail);

        InitData();
        HeaderTextView();

        GetStatus();
        ReLogin();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_info);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (!isLogout){

            Toast.makeText(this , "再按一次返回即可登出" , Toast.LENGTH_SHORT).show();
            isLogout = true;

        }else if (isLogout){

            Logout();
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
            startActivity(it);

        } else if (id == R.id.nav_driver_point_list) {
            Intent it = new Intent(this , Driver_Point_Record_Activity.class);
            startActivity(it);

        } else if (id == R.id.nav_id__info) {


        } else if (id == R.id.nav_home) {
            Intent it = new Intent(this , Driver_Main_Menu_Activity.class);
            startActivity(it);
        } else if (id == R.id.nav_logout) {
            Logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_info);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void GetStatus(){
        Connect_API.getdriverstatus(this, phone, driverApiKey, new Connect_API.OnGetDriverStatusListener() {
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
        Connect_API.driverLogin(this, phone, password, new Connect_API.OnDriverLoginListener() {
            @Override
            public void onLoginSuccess(DriverData newDriverData) {
                driverName.setText(newDriverData.getName());
                driverPhone.setText(phone);
                driverMail.setText(password);
            }

            @Override
            public void onLoginFail(String isFail, String msg) {

            }

            @Override
            public void onFail(Exception e, String jsonError) {

            }
        });
    }
    public void HeaderTextView(){
        //改變滑頁header textView
        View header = navigationView.getHeaderView(0);
        TextView textView = (TextView)header.findViewById(R.id.header_state);
        ImageView imageView = (ImageView)header.findViewById(R.id.picture);

        if (driverStatus==2){

            textView.setText("結束下線");
            imageView.setImageResource(R.drawable.ic_offline_selected_side_drawer);
        }else if (driverStatus==1){

            textView.setText("上線載客");
            imageView.setImageResource(R.drawable.ic_online_selected_side_drawer);
        }
    }
    public void InitData(){
        SharedPreferences sharedPreferences = getSharedPreferences("driver" , MODE_PRIVATE);
        phone = sharedPreferences.getString("phone", null);
        password = sharedPreferences.getString("password" , null);
        driverApiKey = sharedPreferences.getString("driverApiKey" , null);
        driverStatus = sharedPreferences.getInt("driverStatus" , 0);
    }
    public void Logout(){
        Connect_API.loginOut(this, phone, driverApiKey, new Connect_API.OnLoginOutListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , "Exception : " +e);
                Log.v("ppking" , "jsonError : " +jsonError);
            }

            @Override
            public void onSuccess(boolean isError, String message) {
                if (!isError){
                    Toast.makeText(Driver_Info_Activity.this ,""+message , Toast.LENGTH_SHORT).show();
                    //清除所有上一頁Activity
                    Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Toast.makeText(Driver_Info_Activity.this ,""+message , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
