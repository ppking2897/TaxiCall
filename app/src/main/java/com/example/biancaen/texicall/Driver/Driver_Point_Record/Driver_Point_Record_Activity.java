package com.example.biancaen.texicall.Driver.Driver_Point_Record;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biancaen.texicall.Beginning.MainMenuActivity;
import com.example.biancaen.texicall.Driver.Driver_Info.Driver_Info_Activity;
import com.example.biancaen.texicall.Driver.Driver_Main_Menu.Driver_Main_Menu_Activity;
import com.example.biancaen.texicall.Driver.Driver_Passenger_Request.Driver_Passenger_Request_Activity;
import com.example.biancaen.texicall.Driver.Driver_Travel_Record.Driver_Travel_Record_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.DriverData;
import com.example.biancaen.texicall.connectapi.PointData;

import java.util.ArrayList;
import java.util.List;

public class Driver_Point_Record_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<String> date = new ArrayList<>();
    private ArrayList<String> pointIfo = new ArrayList<>();
    private static String phone;
    private static String password;
    private static String driverApiKey;
    private static int driverStatus;
    private TextView driverName;
    private TextView remainPoint;
    private TextView reducePoint;
    private Driver_Point_Record_Adapter adapter;
    private NavigationView navigationView;
    private boolean isLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__point__record_);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_driver_point_record);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_point__record);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view_driver_point__record);
        navigationView.setNavigationItemSelectedListener(this);

        driverName = (TextView)findViewById(R.id.point_driver_name);
        remainPoint = (TextView)findViewById(R.id.point_remainPoint);
        reducePoint = (TextView)findViewById(R.id.point_reducePoint);

        InitData();
        HeaderTextView();

        GetPointRecord();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.driver_point_record_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Driver_Point_Record_Adapter(this , date , pointIfo );
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_point__record);
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

        } else if (id == R.id.nav_id__info) {
            Intent it = new Intent(this , Driver_Info_Activity.class);
            startActivity(it);

        } else if (id == R.id.nav_home) {
            Intent it = new Intent(this , Driver_Main_Menu_Activity.class);
            startActivity(it);
        } else if (id == R.id.nav_logout) {
            Logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_point__record);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void GetPointRecord(){
        Connect_API.getPointRecord(this, phone, driverApiKey , new Connect_API.OnPointRecordListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , e.getMessage());
                Log.v("ppking" , " jsonError :  "+jsonError);
                Toast.makeText(Driver_Point_Record_Activity.this , "連線出現異常" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String isError, String message, String name, String savings, double sum, List<PointData> data) {

                driverName.setText("司機名字 : " + name);
                remainPoint.setText("目前點數 : " + savings);
                reducePoint.setText("已用點數 : " + sum);


                date.clear();
                pointIfo.clear();
                if (isError.equals("false")){
                    for (int i = 0 ; i < data.size() ; i++ ){

                        date.add(data.get(i).getFinal_at());
                        pointIfo.add("扣除點數" + data.get(i).getReduce_point() + "\n剩餘點數" + data.get(i).getRemain_point() );
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    date.add("資料讀取失敗");
                    pointIfo.add("資料讀取失敗");
                }
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
                    Toast.makeText(Driver_Point_Record_Activity.this ,""+message , Toast.LENGTH_SHORT).show();
                    //清除所有上一頁Activity
                    Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Toast.makeText(Driver_Point_Record_Activity.this ,""+message , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
