package com.example.biancaen.texicall.Passenger;

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

import com.example.biancaen.texicall.Passenger.Passenger_Car_Service.Passenger_Car_Service_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Edit.Passenger_Info_Activity;
import com.example.biancaen.texicall.Passenger.Passenger_Sent_Car_Record.Passenger_Sent_Car_Record_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.UserData;

public class Passenger_Customer_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static UserData userData;
    private static String phoneNumber;
    private static String passWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger__customer_);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_Customer);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_customer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this ,drawerLayout , toolbar ,R.string.navigation_drawer_open , R.string.navigation_drawer_close );
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_customer);
        navigationView.setCheckedItem(R.id.nav_customer_service);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_customer);
        navigationView.setCheckedItem(R.id.nav_customer_service);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_customer);
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

            Intent it = new Intent(this ,Passenger_Car_Service_Activity.class);
            it.putExtras(TransData());
            startActivity(it);

        } else if (id == R.id.nav_sent_car_record) {

            Intent it = new Intent(this , Passenger_Sent_Car_Record_Activity.class);
            it.putExtras(TransData());
            startActivity(it);

        } else if (id == R.id.nav_customer_service) {

        } else if (id == R.id.nav_account) {

            Intent it = new Intent(this , Passenger_Info_Activity.class);
            it.putExtras(TransData());
            startActivity(it);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_customer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Bundle TransData(){

        Bundle bundle = getIntent().getExtras();
        passWord = bundle.getString("passWord");
        userData = (UserData)bundle.getSerializable("userData");
        phoneNumber = bundle.getString("phoneNumber");

        bundle.putString("passWord" , passWord );
        bundle.putSerializable("userData" , userData);
        bundle.putString("phoneNumber" , phoneNumber);

        return bundle;
    }
}
