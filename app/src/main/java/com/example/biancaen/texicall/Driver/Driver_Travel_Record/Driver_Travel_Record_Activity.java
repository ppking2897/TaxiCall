package com.example.biancaen.texicall.Driver.Driver_Travel_Record;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.biancaen.texicall.Driver.Driver_Info.Driver_Info_Activity;
import com.example.biancaen.texicall.Driver.Driver_Main_Menu.Driver_Main_Menu_Activity;
import com.example.biancaen.texicall.Driver.Driver_Point_Record.Driver_Point_Record_Activity;
import com.example.biancaen.texicall.R;

import java.util.ArrayList;

public class Driver_Travel_Record_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<String> date = new ArrayList<>();
    private ArrayList<String> getOn = new ArrayList<>();
    private ArrayList<String> getOff = new ArrayList<>();
    private ArrayList<String> time = new ArrayList<>();
    private ArrayList<String> rate = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__travel__record_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_driver_travel_record);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_travel__record);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_driver_travel_record);
        navigationView.setNavigationItemSelectedListener(this);

        //todo 改變滑頁header textView
//        View header = navigationView.getHeaderView(0);
//        TextView textView = (TextView)header.findViewById(R.id.header_state);
//        textView.setText(123456+"");

        //ToDo 司機紀錄上車 下車 時間  金額 資料位置
        date.add("2017-04-20");
        date.add("2017-04-20");
        date.add("2017-04-20");
        getOn.add("上車地點/");
        getOn.add("上車地點/");
        getOn.add("上車地點/");
        getOff.add("下車地點/");
        getOff.add("下車地點/");
        getOff.add("下車地點/");
        time.add("乘車時間/");
        time.add("乘車時間/");
        time.add("乘車時間/");
        rate.add("金額/");
        rate.add("金額/");
        rate.add("金額/");

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.driver_travel_record_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Driver_Travel_Record_Adapter adapter = new Driver_Travel_Record_Adapter(this , date , getOn , getOff , time , rate);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_travel__record);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_travel__record);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
