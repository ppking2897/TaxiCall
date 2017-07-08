package com.example.biancaen.texicall.Driver.Driver_Trip;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.biancaen.texicall.Beginning.MainMenuActivity;
import com.example.biancaen.texicall.Driver.Driver_Info.Driver_Info_Activity;
import com.example.biancaen.texicall.Driver.Driver_Main_Menu.Driver_Main_Menu_Activity;
import com.example.biancaen.texicall.Driver.Driver_Point_Record.Driver_Point_Record_Activity;
import com.example.biancaen.texicall.Driver.Driver_Travel_Record.Driver_Travel_Record_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.DriverData;

public class Driver_Arrived_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private MyCountDown mycountDown;
    private TextView countText;
    private boolean isLogout;
    private boolean isOverTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__arrived_);

        countText = (TextView)findViewById(R.id.countDown);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_driver_arrived);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_arrived_);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_driver_arrived);
        navigationView.setNavigationItemSelectedListener(this);

        if (mycountDown == null){
            mycountDown = new MyCountDown(600000 , 60000);
            mycountDown.start();
        }
    }
    public void cancel(View view){
        //if (!isOverTime){

        SharedPreferences sharedPreferences = getSharedPreferences("driver" , MODE_PRIVATE);
        String tasknumber = sharedPreferences.getString("tasknumber" , null);
        String driverApiKey = sharedPreferences.getString("driverApiKey" , null);
        Connect_API.terminateByDriver(this, tasknumber, driverApiKey, new Connect_API.OnGetConnectStatusListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , "terminateByDriver Exception : " +  e);
                Log.v("ppking" , "terminateByDriver jsonError : " +  jsonError);
            }

            @Override
            public void onSuccess(String isError, String message) {
                Log.v("ppking" , "terminateByDriver isError : " +  isError);
                Log.v("ppking" , "terminateByDriver message : " +  message);
            }
        });
        //}else{
        //    Toast.makeText(this , "請等候10分鐘後再取消此任務",Toast.LENGTH_SHORT).show();
        //}
    }

    public void getIn(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("driver" , MODE_PRIVATE);
        final String taskNumber =sharedPreferences.getString("tasknumber", null);
        String newPassword =sharedPreferences.getString("password", null);
        String newPhone =sharedPreferences.getString("phone", null);

        Connect_API.driverLogin(this, newPhone, newPassword, new Connect_API.OnDriverLoginListener() {
            @Override
            public void onLoginSuccess(DriverData newDriverData) {
               Connect_API.takeride(Driver_Arrived_Activity.this, taskNumber, newDriverData.getApiKey(), new Connect_API.OnGetConnectStatusListener() {
                   @Override
                   public void onFail(Exception e, String jsonError) {
                       Log.v("ppking" , "pickup Exception  : " + e);
                       Log.v("ppking" , "pickup jsonError  : " + jsonError);
                   }

                   @Override
                   public void onSuccess(String isError, String message) {
                       Log.v("ppking" , "pickup isError  : " + isError);
                       Log.v("ppking" , "pickup message  : " + message);
                       Intent it = new Intent(Driver_Arrived_Activity.this , Driver_Google_Map_Activity.class);
                       startActivity(it);
                   }
               });

            }

            @Override
            public void onLoginFail(String isFail, String msg) {
                Log.v("ppking" , "isFail  : " + isFail);
                Log.v("ppking" , "msg  : " + msg);
            }

            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , "Exception  : " + e);
                Log.v("ppking" , "jsonError  : " + jsonError);

            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_arrived_);
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
            Intent it = new Intent(this , Driver_Travel_Record_Activity.class);
            startActivity(it);

        } else if (id == R.id.nav_driver_point_list) {
            Intent it = new Intent(this , Driver_Point_Record_Activity.class);
            startActivity(it);

        } else if (id == R.id.nav_id__info) {
            Intent it = new Intent(this , Driver_Info_Activity.class);
            startActivity(it);

        } else if (id == R.id.nav_home) {
            Intent it = new Intent(this , Driver_Main_Menu_Activity.class);
            startActivity(it);

        } else if (id == R.id.nav_logout) {
            Logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_arrived_);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class MyCountDown extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            countText.setText(""+millisUntilFinished/60000);
        }

        @Override
        public void onFinish() {
            countText.setText(""+0);
            mycountDown.cancel();
            mycountDown = null;
        }
    }

    public void Logout(){
        SharedPreferences sharedPreferences = getSharedPreferences("driver" , MODE_PRIVATE);
        String newPhone =sharedPreferences.getString("phone", null);
        String driverApiKey = sharedPreferences.getString("driverApiKey" , null);

        Connect_API.loginOut(this, newPhone, driverApiKey, new Connect_API.OnLoginOutListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , "Exception : " +e);
                Log.v("ppking" , "jsonError : " +jsonError);
            }

            @Override
            public void onSuccess(boolean isError, String message) {
                if (!isError){
                    Toast.makeText(Driver_Arrived_Activity.this ,""+message , Toast.LENGTH_SHORT).show();
                    //清除所有上一頁Activity
                    Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Toast.makeText(Driver_Arrived_Activity.this ,""+message , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
