package com.example.biancaen.texicall.Passenger.Passenger_Sent_Car_Record;

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
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.biancaen.texicall.Passenger.Passenger_Car_Service.Passenger_Car_Service_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Customer_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Edit.Passenger_Info_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.RecordPassengerData;
import com.example.biancaen.texicall.connectapi.UserData;

import java.util.ArrayList;
import java.util.List;

public class Passenger_Sent_Car_Record_Activity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener{

    private ViewPager myViewPager;
    private ToggleButton toggleButton;
    private ArrayList<Fragment> views = new ArrayList<>();
    private static UserData userData;
    private static String phoneNumber;
    private static String passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger__sent__car_record_);

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
        navigationView.setCheckedItem(R.id.nav_sent_car_record);
        navigationView.setNavigationItemSelectedListener(this);

        Sent_Car_01_Record_Fragment sent_car_01_Record_fragment = new Sent_Car_01_Record_Fragment();
        Sent_Car_02_Record_Fragment sent_car_02_Record_fragment = new Sent_Car_02_Record_Fragment();

        views.add(sent_car_01_Record_fragment);
        views.add(sent_car_02_Record_fragment);

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
    protected void onRestart() {
        super.onRestart();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_sent_car);
        navigationView.setCheckedItem(R.id.nav_sent_car_record);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_sent_car);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
