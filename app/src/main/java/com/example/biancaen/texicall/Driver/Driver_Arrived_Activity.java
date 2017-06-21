package com.example.biancaen.texicall.Driver;

import android.content.Intent;
import android.os.CountDownTimer;
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
import android.widget.TextView;

import com.example.biancaen.texicall.Driver.Driver_Info.Driver_Info_Activity;
import com.example.biancaen.texicall.Driver.Driver_Main_Menu.Driver_Main_Menu_Activity;
import com.example.biancaen.texicall.Driver.Driver_Point_Record.Driver_Point_Record_Activity;
import com.example.biancaen.texicall.Driver.Driver_Travel_Record.Driver_Travel_Record_Activity;
import com.example.biancaen.texicall.R;

public class Driver_Arrived_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private MyCountDown mycountDown;
    private TextView countText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__arrived_);

        countText = (TextView)findViewById(R.id.countDown);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_driver_arrived);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_arrived_);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_driver_arrived);
        navigationView.setNavigationItemSelectedListener(this);


        //todo 倒數10分鐘
        if (mycountDown == null){
            mycountDown = new MyCountDown(10000 , 1000);
            mycountDown.start();
        }
    }

    public void getIn(View view){
        Intent it = new Intent(Driver_Arrived_Activity.this , Driver_Google_Map_Activity.class);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_arrived_);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class MyCountDown extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            countText.setText(""+millisUntilFinished/1000);
        }

        @Override
        public void onFinish() {
            countText.setText(""+0);
            mycountDown.cancel();
            mycountDown = null;
        }
    }


}
