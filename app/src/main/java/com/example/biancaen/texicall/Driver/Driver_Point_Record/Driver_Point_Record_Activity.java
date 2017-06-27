package com.example.biancaen.texicall.Driver.Driver_Point_Record;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.biancaen.texicall.Driver.Driver_Info.Driver_Info_Activity;
import com.example.biancaen.texicall.Driver.Driver_Main_Menu.Driver_Main_Menu_Activity;
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
    private DriverData driverData;
    private String phone;
    private TextView driverName;
    private TextView remainPoint;
    private TextView reducePoint;
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_driver_point__record);
        navigationView.setNavigationItemSelectedListener(this);

        driverName = (TextView)findViewById(R.id.point_driver_name);
        remainPoint = (TextView)findViewById(R.id.point_remainPoint);
        reducePoint = (TextView)findViewById(R.id.point_reducePoint);

        Bundle bundle =getIntent().getExtras();
        driverData = (DriverData)bundle.getSerializable("driverData");
        phone = bundle.getString("phone");

        Log.v("ppking" , "  driverData  :  " + driverData);
        Log.v("ppking" , "  phone  :  " + phone);


        Connect_API.getPointRecord(this, phone, driverData.getApiKey() , new Connect_API.OnPointRecordListener() {
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
                }else{
                    date.add("資料無取失敗");
                    pointIfo.add("資料讀取失敗");
                }

            }
        });

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.driver_point_record_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Driver_Point_Record_Adapter adapter = new Driver_Point_Record_Adapter(this , date , pointIfo );
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_point__record);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_point__record);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
