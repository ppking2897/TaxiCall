package com.example.biancaen.texicall;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class Passenger_Sent_Car_Activity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener{

    private ViewPager myViewPager;
    private ToggleButton toggleButton;
    private ArrayList<Fragment> views = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger__sent__car_);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sent_car);
        setSupportActionBar(toolbar);

        myViewPager = (ViewPager)findViewById(R.id.myViewPager);

        toggleButton = (ToggleButton)findViewById(R.id.toggleButton);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_sent_car);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_sent_car);
        navigationView.setNavigationItemSelectedListener(this);

        Sent_Car_01_Fragment sent_car_01_fragment = new Sent_Car_01_Fragment();
        Sent_Car_02_Fragment sent_car_02_fragment = new Sent_Car_02_Fragment();

        views.add(sent_car_01_fragment);
        views.add(sent_car_02_fragment);


        myViewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
        myViewPager.setCurrentItem(0);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked){
                    myViewPager.setCurrentItem(0);
                }else if(isChecked){
                    myViewPager.setCurrentItem(1);
                }
            }
        });


    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_sent_car);
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

        } else if (id == R.id.nav_sent_car_record) {

            Intent it = new Intent(this , Passenger_Sent_Car_Activity.class);
            startActivity(it);

        } else if (id == R.id.nav_customer_service) {

            Intent it = new Intent(this , Passenger_Customer_Activity.class);
            startActivity(it);

        } else if (id == R.id.nav_account) {

            Intent it = new Intent(this , Passenger_Info_Activity.class);
            startActivity(it);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_sent_car);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    public class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
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
}
