package com.example.biancaen.texicall.Driver.Driver_Passenger_Request;

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
import android.widget.Toast;

import com.example.biancaen.texicall.Driver.Driver_Info.Driver_Info_Activity;
import com.example.biancaen.texicall.Driver.Driver_Main_Menu.Driver_Main_Menu_Activity;
import com.example.biancaen.texicall.Driver.Driver_On_The_Way_Activity;
import com.example.biancaen.texicall.Driver.Driver_Point_Record.Driver_Point_Record_Activity;
import com.example.biancaen.texicall.Driver.Driver_Travel_Record.Driver_Travel_Record_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.DriverData;
import com.example.biancaen.texicall.connectapi.TaskInfoData;

public class Driver_Passenger_Request_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static DriverData driverData;
    private static String phone;
    private static String password;
    private static TaskInfoData getTaskInfoData;
    private String gettasknumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__passenger__request_);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_driver_passenger_request);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_passenger_request);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_driver_passenger_request);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle!=null){
            driverData = (DriverData)bundle.getSerializable("driverData");
            phone = bundle.getString("phone");
            password = bundle.getString("password");
            if (bundle.getString("taskInfoData")!=null){
                getTaskInfoData =(TaskInfoData)bundle.getSerializable("taskInfoData");
            }else{
                GetTaskInfo();
            }
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_passenger_request);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_driver_passenger_request, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.confirm){
            Intent it = new Intent(this , Driver_On_The_Way_Activity.class);
            Bundle bundle = new Bundle();
            bundle.putString("phone" , phone);
            bundle.putSerializable("driverData" , driverData);
            bundle.putString("password" , password);
            bundle.putSerializable("taskInfoData" , getTaskInfoData);
            it.putExtras(bundle);
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
            Intent it = new Intent(this , Driver_Info_Activity.class);
            it.putExtras(TransData());
            startActivity(it);

        } else if (id == R.id.nav_home) {
            Intent it = new Intent(this , Driver_Main_Menu_Activity.class);
            it.putExtras(TransData());
            startActivity(it);
        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_passenger_request);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //如果在任務中重新登入，取得資料方法
    public void GetTaskInfo(){
        //先取得TaskNumber
        Connect_API.getdriverstatus(this, phone, driverData.getApiKey(), new Connect_API.OnGetDriverStatusListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Toast.makeText(Driver_Passenger_Request_Activity.this , "連線異常" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String isError, String result, String status, String tasknumber, String carshow, String carnumber) {

                gettasknumber = tasknumber;
                Connect_API.taskinfo(Driver_Passenger_Request_Activity.this , gettasknumber, driverData.getApiKey(), new Connect_API.OnTaskInfoListener() {
                    @Override
                    public void onFail(Exception e, String jsonError) {
                        Toast.makeText(Driver_Passenger_Request_Activity.this , "連線異常" , Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(TaskInfoData taskInfoData) {
                        getTaskInfoData = taskInfoData;
                        GetTaskData(taskInfoData);
                    }
                });
            }
        });
    }

    public void GetTaskData(TaskInfoData taskInfoData){

        TextView location = (TextView)findViewById(R.id.location);
        TextView destination = (TextView)findViewById(R.id.destination);
        TextView passenger_Number = (TextView)findViewById(R.id.passenger_Number);
        TextView comment = (TextView)findViewById(R.id.comment);

        location.setText(taskInfoData.getAddr_start_addr());
        destination.setText(taskInfoData.getAddr_end_addr());
        passenger_Number.setText(taskInfoData.getPassenger_number());
        comment.setText(taskInfoData.getComment());

        Log.v("ppking" , "Request taskInfoData :  " + taskInfoData.getAddr_start_addr());
    }

    public Bundle TransData(){
        Bundle bundle = new Bundle();
        bundle.putString("phone" , phone);
        bundle.putSerializable("driverData" , driverData);
        bundle.putString("password" , password);
        return bundle;
    }
}
