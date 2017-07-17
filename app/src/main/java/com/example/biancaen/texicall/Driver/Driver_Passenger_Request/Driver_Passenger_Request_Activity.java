package com.example.biancaen.texicall.Driver.Driver_Passenger_Request;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biancaen.texicall.Beginning.MainMenuActivity;
import com.example.biancaen.texicall.Driver.Driver_Trip.Driver_Arrived_Activity;
import com.example.biancaen.texicall.Driver.Driver_Trip.Driver_Google_Map_Activity;
import com.example.biancaen.texicall.Driver.Driver_Info.Driver_Info_Activity;
import com.example.biancaen.texicall.Driver.Driver_Main_Menu.Driver_Main_Menu_Activity;
import com.example.biancaen.texicall.Driver.Driver_Trip.Driver_On_The_Way_Activity;
import com.example.biancaen.texicall.Driver.Driver_Point_Record.Driver_Point_Record_Activity;
import com.example.biancaen.texicall.Driver.Driver_Travel_Record.Driver_Travel_Record_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.DriverData;
import com.example.biancaen.texicall.connectapi.TaskInfoData;
import com.example.biancaen.texicall.connectapi.TaskInfoFullData;
import com.example.biancaen.texicall.notificaiton.HBMessageService;

public class Driver_Passenger_Request_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static DriverData driverData;
    private static String phone;
    private static String password;
    private static TaskInfoData getTaskInfoData;
    private String gettasknumber;
    private boolean isLogout;


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


        SharedPreferences sharedPreferences = getSharedPreferences("driver" , MODE_PRIVATE);
        phone = sharedPreferences.getString("phone" , null);
        password = sharedPreferences.getString("password" , null);


        //強制關閉APP  再次登入會依狀態到目前App位置
        sharedPreferences = getSharedPreferences("type" , MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isDriver" , true);
        editor.apply();

        GetTaskStatusAndDriverData();
        PassengerTerminateListener();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_passenger_request);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (!isLogout){

            Toast.makeText(this , "再按一次返回即可退出應用程式" , Toast.LENGTH_SHORT).show();
            isLogout = true;

        }else if (isLogout){

            Logout();
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
            startActivity(it);

        } else if (id == R.id.nav_driver_point_list) {
            Intent it = new Intent(this , Driver_Point_Record_Activity.class);
            startActivity(it);

        } else if (id == R.id.nav_id__info) {
            Intent it = new Intent(this , Driver_Info_Activity.class);
            startActivity(it);

        } else if (id == R.id.nav_home) {


        } else if (id == R.id.nav_logout) {
            LogoutAndClear();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_passenger_request);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void GetTaskStatusAndDriverData(){

        Connect_API.driverLogin(this, phone, password, new Connect_API.OnDriverLoginListener() {
            @Override
            public void onLoginSuccess(DriverData newDriverData) {
                driverData = newDriverData;
                GetTaskInfo();
            }

            @Override
            public void onLoginFail(String isFail, String msg) {

            }

            @Override
            public void onFail(Exception e, String jsonError) {

            }
        });
    }

    //如果在任務中重新登入，取得資料方法
    public void GetTaskInfo(){
        //先取得TaskNumber
        Connect_API.getdriverstatus(this, phone, driverData.getApiKey(), new Connect_API.OnGetDriverStatusListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Toast.makeText(Driver_Passenger_Request_Activity.this , "連線異常" , Toast.LENGTH_SHORT).show();
            }

            //判斷任務狀態為何
            @Override
            public void onSuccess(String isError, String result, String status, String tasknumber, String carshow, String carnumber) {

                Connect_API.getFullTaskInfo(Driver_Passenger_Request_Activity.this, tasknumber, driverData.getApiKey(), new Connect_API.OnGetFullInfoListener() {
                    @Override
                    public void onFail(Exception e, String jsonError) {

                    }

                    /**判斷目前是什麼狀態 **/
                    @Override
                    public void onSuccess(TaskInfoFullData data) {
                        Log.v("ppking" , " TaskInfoFullData : " + data.getTask_status());
                        if (data.getTask_status().equals("4")){
                            Intent it = new Intent(Driver_Passenger_Request_Activity.this , Driver_Google_Map_Activity.class);
                            startActivity(it);
                        }else if (data.getTask_status().equals("3")){
                            Intent it = new Intent(Driver_Passenger_Request_Activity.this , Driver_Arrived_Activity.class);
                            startActivity(it);
                        }
                    }
                });

                //將取得資料顯示在畫面上
                gettasknumber = tasknumber;
                Connect_API.taskinfo(Driver_Passenger_Request_Activity.this , gettasknumber, driverData.getApiKey(), new Connect_API.OnTaskInfoListener() {
                    @Override
                    public void onFail(Exception e, String jsonError) {
                        Toast.makeText(Driver_Passenger_Request_Activity.this , "連線異常" , Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(TaskInfoData taskInfoData) {
                        getTaskInfoData = taskInfoData;
                        TextView location = (TextView)findViewById(R.id.location);
                        TextView destination = (TextView)findViewById(R.id.destination);
                        TextView passenger_Number = (TextView)findViewById(R.id.passenger_Number);
                        TextView comment = (TextView)findViewById(R.id.comment);

                        SharedPreferences sharedPreferences = getSharedPreferences("driver" ,MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("location" , taskInfoData.getAddr_start_addr());
                        editor.putString("destination" , taskInfoData.getAddr_end_addr());
                        editor.apply();

                        location.setText(taskInfoData.getAddr_start_addr());
                        destination.setText(taskInfoData.getAddr_end_addr());
                        passenger_Number.setText(taskInfoData.getPassenger_number());
                        comment.setText(taskInfoData.getComment());

                        Log.v("ppking" , "Request taskInfoData :  " + taskInfoData.getAddr_start_addr());
                    }
                });
            }
        });
    }
    public void Logout(){
        Connect_API.loginOut(this, phone, driverData.getApiKey(), new Connect_API.OnLoginOutListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , "Exception : " +e);
                Log.v("ppking" , "jsonError : " +jsonError);
            }

            @Override
            public void onSuccess(boolean isError, String message) {
                if (!isError){
                    Toast.makeText(Driver_Passenger_Request_Activity.this ,""+message , Toast.LENGTH_SHORT).show();
                    //清除所有上一頁Activity
                    Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Toast.makeText(Driver_Passenger_Request_Activity.this ,""+message , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void LogoutAndClear(){
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
                    SharedPreferences sharedPreferences = getSharedPreferences("driver" , MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    Toast.makeText(Driver_Passenger_Request_Activity.this ,""+message , Toast.LENGTH_SHORT).show();
                    //清除所有上一頁Activity
                    Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Toast.makeText(Driver_Passenger_Request_Activity.this ,""+message , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void PassengerTerminateListener(){
        HBMessageService.setOnStartDriverTerminateListener(new HBMessageService.OnStartDriverTerminateListener() {
            @Override
            public void onGetDriverTerminate(String title, String body, int REQUEST_CODE, int INTENT_ID) {
                Connect_API.putdriverstatus(Driver_Passenger_Request_Activity.this, phone, "1", driverData.getApiKey(), new Connect_API.OnPutDriverStatusListener() {
                    @Override
                    public void onFail(Exception e, String jsonError) {
                        Log.v("ppking" , "Exception  :  "+ e);
                        Log.v("ppking" , "jsonError  :  "+ jsonError);
                    }

                    @Override
                    public void onSuccess(String isError, String result) {
                        Log.v("ppking" , "isError  :  "+ isError);
                        Log.v("ppking" , "result  :  "+ result);
                    }
                });
                CreateTerminateDialog();
            }
        });
    }

    public void CreateTerminateDialog(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Driver_Passenger_Request_Activity.this , R.style.Contact_Dialog);
                View view = LayoutInflater.from(Driver_Passenger_Request_Activity.this).inflate(R.layout.layout_passenger_ask_terminate_task_dialog , null);

                LinearLayout knowButton = (LinearLayout)view.findViewById(R.id.knowButton);

                builder.setView(view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                knowButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        Intent it = new Intent(Driver_Passenger_Request_Activity.this , Driver_Main_Menu_Activity.class);
                        startActivity(it);
                        finish();
                    }
                });
            }
        });
    }
}
