package com.example.biancaen.texicall.Passenger_Driver_Arrived;

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

import com.example.biancaen.texicall.Passenger_Customer_Activity;
import com.example.biancaen.texicall.Passenger_Edit.Passenger_Info_Activity;
import com.example.biancaen.texicall.Passenger_In_The_Shuttle.Passenger_In_The_Shuttle_Activity;
import com.example.biancaen.texicall.Passenger_Sent_Car_Record.Passenger_Sent_Car_Record_Activity;
import com.example.biancaen.texicall.R;

public class Passenger_Driver_Arrived_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

        Over_Time_Dialog over_time_dialog = new Over_Time_Dialog(this);
        over_time_dialog.Create_Over_Timer_Dialog();

    }

    //測試已接送
    public void testArrive(View view){
        Intent it = new Intent(this , Passenger_In_The_Shuttle_Activity.class);
        startActivity(it);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_arrived_);
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
}

