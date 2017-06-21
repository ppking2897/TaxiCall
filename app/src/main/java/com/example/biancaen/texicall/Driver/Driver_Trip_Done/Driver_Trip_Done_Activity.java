package com.example.biancaen.texicall.Driver.Driver_Trip_Done;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.biancaen.texicall.Driver.Driver_Info.Driver_Info_Activity;
import com.example.biancaen.texicall.Driver.Driver_Main_Menu.Driver_Main_Menu_Activity;
import com.example.biancaen.texicall.Driver.Driver_Point_Record.Driver_Point_Record_Activity;
import com.example.biancaen.texicall.Driver.Driver_Travel_Record.Driver_Travel_Record_Activity;
import com.example.biancaen.texicall.R;

public class Driver_Trip_Done_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__trip__done_);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_driver_trip_done);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_trip_done_);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_driver_trip_done);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_trip_done_);
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
            Intent it = new Intent(this, Driver_Travel_Record_Activity.class);
            startActivity(it);

        } else if (id == R.id.nav_driver_point_list) {
            Intent it = new Intent(this, Driver_Point_Record_Activity.class);
            startActivity(it);

        } else if (id == R.id.nav_id__info) {
            Intent it = new Intent(this, Driver_Info_Activity.class);
            startActivity(it);

        } else if (id == R.id.nav_home) {
            Intent it = new Intent(this, Driver_Main_Menu_Activity.class);
            startActivity(it);

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_trip_done_);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void settle(View view){
        Driver_Trip_Done_Dialog driver_trip_done_dialog = new Driver_Trip_Done_Dialog(this);
        driver_trip_done_dialog.CreateTripDoneDialog();
    }
}
