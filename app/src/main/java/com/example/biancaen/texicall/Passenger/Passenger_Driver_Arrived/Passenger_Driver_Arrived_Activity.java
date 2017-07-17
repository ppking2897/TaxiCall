package com.example.biancaen.texicall.Passenger.Passenger_Driver_Arrived;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biancaen.texicall.Beginning.MainMenuActivity;
import com.example.biancaen.texicall.Passenger.Passenger_Car_Service.Passenger_Car_Service_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Customer_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Edit.Passenger_Info_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_TakeRide_And_Arrived.Passenger_In_The_Shuttle_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Sent_Car_Record.Passenger_Sent_Car_Record_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.PairInfoData;
import com.example.biancaen.texicall.connectapi.UserData;
import com.example.biancaen.texicall.notificaiton.HBMessageService;

import java.util.Timer;
import java.util.TimerTask;

public class Passenger_Driver_Arrived_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AlertDialog alertDialog;
    private static PairInfoData pairInfoData;
    private Timer timer;
    private boolean isLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger__driver__arrived_);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_driver_arrived);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout_driver_arrived_);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this , drawerLayout , toolbar , R.string.navigation_drawer_open , R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view_driver_arrived_);
        navigationView.setCheckedItem(R.id.nav_car_service);
        navigationView.setNavigationItemSelectedListener(this);

        //空趟通知  由司機端取消後發出
//        Over_Time_Dialog over_time_dialog = new Over_Time_Dialog(this);
//        over_time_dialog.Create_Over_Timer_Dialog();

        if (timer == null){
            timer = new Timer();
            timer.schedule(new TakeRide() , 0 , 3000);
        }
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_arrived_);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (!isLogout){

            Toast.makeText(this , "再按一次返回即可退出應用程式" , Toast.LENGTH_SHORT).show();
            isLogout = true;

        }else if (isLogout){

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
            startActivity(it);

        } else if (id == R.id.nav_customer_service) {

            Intent it = new Intent(this , Passenger_Customer_Activity.class);
            startActivity(it);

        } else if (id == R.id.nav_account) {

            Intent it = new Intent(this , Passenger_Info_Activity.class);
            startActivity(it);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_arrived_);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void contactArrived(View view){

        SharedPreferences sharedPreferences = getSharedPreferences("passenger" , MODE_PRIVATE);
        final String driverPhone = sharedPreferences.getString("driverPhone" , null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this , R.style.Contact_Dialog);
        View layout = LayoutInflater.from(this).inflate(R.layout.layout_contact_dialog , null);

        TextView textView = (TextView)layout.findViewById(R.id.numberPhone);
        textView.setText("乘客電話號碼為:\n"+ driverPhone + "\n是否要移動到撥號畫面?");

        LinearLayout knowButton = (LinearLayout)layout.findViewById(R.id.knowButton);

        ImageView imageView = (ImageView)layout.findViewById(R.id.closeDialog);

        builder.setView(layout);

        alertDialog = builder.create();
        alertDialog.show();

        knowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( ContextCompat.checkSelfPermission(
                        Passenger_Driver_Arrived_Activity.this, Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Passenger_Driver_Arrived_Activity.this,
                            new String[]{Manifest.permission.CALL_PHONE},123);
                }else {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+driverPhone));
                    startActivity(intent);
                    alertDialog.dismiss();
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int grantResult : grantResults){
            SharedPreferences sharedPreferences = getSharedPreferences("passenger" , MODE_PRIVATE);
            String driverPhone = sharedPreferences.getString("driverPhone" , null);
            if (grantResult == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+driverPhone));
                startActivity(intent);
                alertDialog.dismiss();
            }
        }
    }

    public class TakeRide extends TimerTask{

        @Override
        public void run() {

            final SharedPreferences sharedPreferences = getSharedPreferences("passenger" , MODE_PRIVATE);
            final String taskNumber =sharedPreferences.getString("taskNumber", null);
            String phoneNumber =sharedPreferences.getString("phoneNumber", null);
            String passWord =sharedPreferences.getString("passWord", null);

            Connect_API.userLogin(Passenger_Driver_Arrived_Activity.this, phoneNumber, passWord, new Connect_API.OnUserLoginListener() {
                @Override
                public void onLoginSuccess(UserData userData) {

                    Connect_API.waittime(Passenger_Driver_Arrived_Activity.this, taskNumber, userData.getApiKey(), new Connect_API.OnWaitTimeListener() {

                        @Override
                        public void onFail(Exception e, String jsonError) {
                            Log.v("ppking" , "Exception" + e);
                            Log.v("ppking" , "jsonError" + jsonError);

                        }

                        @Override
                        public void onSuccess(String isError, String task_status, String distance, int time) {
                            Log.v("ppking" , "isError" + isError);
                            Log.v("ppking" , "task_status" + task_status);
                            Log.v("ppking" , "distance" + distance);
                            Log.v("ppking" , "time" + time);
                            sharedPreferences.edit().putString("task_status" , task_status).apply();

                            if (task_status.equals("4")){
                                if (timer!=null){
                                    timer.cancel();
                                    timer = null;
                                }
                                Intent it = new Intent(Passenger_Driver_Arrived_Activity.this , Passenger_In_The_Shuttle_Activity.class);
                                startActivity(it);
                                finish();
                            }else if(task_status.equals("0")){
                                if (timer!=null){
                                    timer.cancel();
                                    timer = null;
                                }
                                Over_Time_Dialog over_time_dialog = new Over_Time_Dialog(Passenger_Driver_Arrived_Activity.this);
                                over_time_dialog.Create_Over_Timer_Dialog();
                            }
                        }
                    });
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

    public void logout(){
        SharedPreferences sharedPreferences = getSharedPreferences("passenger" , MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("phoneNumber" , null);
        String passengerApiKey = sharedPreferences.getString("passengerApiKey", null);
        Connect_API.loginOut(this, phoneNumber, passengerApiKey, new Connect_API.OnLoginOutListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , " Exception : " + e.getMessage());
                Log.v("ppking" , " jsonError : " + jsonError);
                Toast.makeText(Passenger_Driver_Arrived_Activity.this ,"連線異常,登出失敗" , Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Passenger_Driver_Arrived_Activity.this ,""+message , Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}

