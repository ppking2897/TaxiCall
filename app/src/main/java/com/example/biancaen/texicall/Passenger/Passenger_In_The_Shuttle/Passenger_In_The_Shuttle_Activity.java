package com.example.biancaen.texicall.Passenger.Passenger_In_The_Shuttle;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.biancaen.texicall.Passenger.Passenger_Customer_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Edit.Passenger_Info_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Rates.MyViewPager;
import com.example.biancaen.texicall.Passenger.Passenger_Sent_Car_Record.Passenger_Sent_Car_Record_Activity;
import com.example.biancaen.texicall.R;

import java.util.ArrayList;

public class Passenger_In_The_Shuttle_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ArrayList<Fragment> views = new ArrayList<>();
    MyViewPager shuttle_viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger__in__the__shuttle_);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_in_the_shuttle);
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view_in_the_shuttle);
        navigationView.setCheckedItem(R.id.nav_car_service);
        navigationView.setNavigationItemSelectedListener(this);

        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout_in_the_shuttle);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        shuttle_viewPager = (MyViewPager) findViewById(R.id.shuttle_ViewPager);
        Shuttle_Fragment_01 shuttle_fragment_01 = new Shuttle_Fragment_01();
        Shuttle_Fragment_02 shuttle_fragment_02 = new Shuttle_Fragment_02();

        views.add(shuttle_fragment_01);
        views.add(shuttle_fragment_02);

        FragmentPagerAdapter fragmentPagerAdapter = new ShuttleFragmentAdapter01(getSupportFragmentManager());
        shuttle_viewPager.setAdapter(fragmentPagerAdapter);
        shuttle_viewPager.setCurrentItem(0);
    }

    private class ShuttleFragmentAdapter01 extends FragmentPagerAdapter{

        public ShuttleFragmentAdapter01(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return views.get(position);
        }

        @Override
        public int getCount() {
            return views.size();
        }
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_in_the_shuttle);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_in_the_shuttle);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
