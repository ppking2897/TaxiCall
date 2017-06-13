package com.example.biancaen.texicall.Passenger_On_The_Way;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.biancaen.texicall.Passenger_Car_Service.Passenger_Car_Service_Activity;
import com.example.biancaen.texicall.Passenger_Customer_Activity;
import com.example.biancaen.texicall.Passenger_Edit.Passenger_Info_Activity;
import com.example.biancaen.texicall.Passenger_Sent_Car_Record.Passenger_Sent_Car_Record_Activity;
import com.example.biancaen.texicall.R;

public class Passenger_On_The_Way_Rates_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger__on__the__way__fares_);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_OnWayFares);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_on_the_way_fares);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_on_the_way_fares);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_on_the_way_fares);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_car_service) {

            Intent it = new Intent(this ,Passenger_Car_Service_Activity.class);
            startActivity(it);

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_on_the_way_fares);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
