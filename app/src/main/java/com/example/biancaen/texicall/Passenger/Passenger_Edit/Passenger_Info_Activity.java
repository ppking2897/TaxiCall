package com.example.biancaen.texicall.Passenger.Passenger_Edit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biancaen.texicall.Beginning.MainMenuActivity;
import com.example.biancaen.texicall.Passenger.Passenger_Car_Service.Passenger_Car_Service_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Customer_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Sent_Car_Record.Passenger_Sent_Car_Record_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.UserData;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

public class Passenger_Info_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static UserData userData;
    private static String phoneNumber;
    private static String passWord;
    private static String passengerApiKey;
    private TextView userName , phone , passWordText , mail;
    private Timer myTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger__info_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_info);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_info);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_info);
        navigationView.setCheckedItem(R.id.nav_account);
        navigationView.setNavigationItemSelectedListener(this);


        userName = (TextView)findViewById(R.id.userName);
        phone = (TextView)findViewById(R.id.phone);
        passWordText = (TextView)findViewById(R.id.passWordText);
        mail = (TextView)findViewById(R.id.mail);

        SharedPreferences sharedPreferences = getSharedPreferences("passenger" , MODE_PRIVATE);
        phoneNumber = sharedPreferences.getString("phoneNumber" , null);
        passWord = sharedPreferences.getString("passWord" , null);
        passengerApiKey = sharedPreferences.getString("passengerApiKey" , null);

        if (myTimer==null){
            myTimer = new Timer();
            myTimer.schedule(new MyTimerTask() , 0 , 3000);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_info);
        navigationView.setCheckedItem(R.id.nav_account);
    }

    //只在這頁面才使用Timer 去更新帳戶資訊
    @Override
    protected void onStop() {
        super.onStop();
        if (myTimer!=null){
            myTimer.cancel();
            myTimer = null;
        }
    }

    public void logout(View view){
        Connect_API.loginOut(this, phoneNumber, userData.getApiKey(), new Connect_API.OnLoginOutListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , " Exception : " + e.getMessage());
                Log.v("ppking" , " jsonError : " + jsonError);
                Toast.makeText(Passenger_Info_Activity.this ,"連線異常,登出失敗" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(boolean isError, String message) {
                Log.v("ppking" , " isError : " + isError);
                Log.v("ppking" , " message : " + message);
                if (!isError){
                    SharedPreferences passengerPreferences = getSharedPreferences("passenger" , MODE_PRIVATE);
                    passengerPreferences.edit().clear().apply();

                    Toast.makeText(Passenger_Info_Activity.this ,""+message , Toast.LENGTH_SHORT).show();
                    //清除所有上一頁Activity
                    Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Toast.makeText(Passenger_Info_Activity.this ,""+message , Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_info);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_car_service) {

            Intent it = new Intent(this ,Passenger_Car_Service_Activity.class);
            startActivity(it);

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_info);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    public void passenger_edit_username(View view){

        Intent it = new Intent(this , Passenger_Edit_Username_Activity.class);
        startActivity(it);
    }

    public void passenger_edit_password(View view){
        Intent it = new Intent(this , Passenger_Edit_Password_Activity.class);
        startActivity(it);
    }

    public void passenger_edit_mail(View view){
        Intent it = new Intent(this , Passenger_Edit_Mail_Activity.class);
        startActivity(it);
    }

    public class MyTimerTask extends TimerTask{
        private String updatePhoneNumber;
        private String updatePassWord;
        @Override
        public void run() {

            this.updatePhoneNumber = phoneNumber;
            this.updatePassWord = passWord;

            Connect_API.userLogin(Passenger_Info_Activity.this, updatePhoneNumber, updatePassWord, new Connect_API.OnUserLoginListener() {
                @Override
                public void onLoginSuccess(UserData newUserData) {
                    Log.v("ppking" , " TImer!!!  ");
                    userData = newUserData;
                    userName.setText("用戶名 / " + newUserData.getName());
                    phone.setText("手機號碼 / " + updatePhoneNumber);
                    passWordText.setText(updatePassWord);
                    mail.setText("信箱 / " + newUserData.getEmail());

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
}
