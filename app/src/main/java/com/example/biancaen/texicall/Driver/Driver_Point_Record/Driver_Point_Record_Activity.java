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
import android.view.MenuItem;

import com.example.biancaen.texicall.Driver.Driver_Info.Driver_Info_Activity;
import com.example.biancaen.texicall.Driver.Driver_Main_Menu.Driver_Main_Menu_Activity;
import com.example.biancaen.texicall.Driver.Driver_Travel_Record.Driver_Travel_Record_Activity;
import com.example.biancaen.texicall.R;

import java.util.ArrayList;

public class Driver_Point_Record_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<String> date = new ArrayList<>();
    private ArrayList<String> pointIfo = new ArrayList<>();
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

        //Todo 司機點數資料位置
        date.add("2017-04-20");
        date.add("2017-04-20");
        date.add("2017-04-20");
        pointIfo.add("扣除點數 " + 1234 + "點" +"，剩餘 "+1234+" 點");
        pointIfo.add("扣除點數 " + 1234 + "點" +"，剩餘 "+1234+" 點");
        pointIfo.add("扣除點數 " + 1234 + "點" +"，剩餘 "+1234+" 點");

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
