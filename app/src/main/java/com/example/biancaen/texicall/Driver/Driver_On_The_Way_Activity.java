package com.example.biancaen.texicall.Driver;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biancaen.texicall.Driver.Driver_Info.Driver_Info_Activity;
import com.example.biancaen.texicall.Driver.Driver_Main_Menu.Driver_Main_Menu_Activity;
import com.example.biancaen.texicall.Driver.Driver_Passenger_Request.Driver_Passenger_Request_Activity;
import com.example.biancaen.texicall.Driver.Driver_Point_Record.Driver_Point_Record_Activity;
import com.example.biancaen.texicall.Driver.Driver_Travel_Record.Driver_Travel_Record_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.DriverData;
import com.example.biancaen.texicall.connectapi.TaskInfoData;

public class Driver_On_The_Way_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static DriverData driverData;
    private static String phone;
    private static String password;
    private static TaskInfoData getTaskInfoData;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__on__the__way_);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_driver_on_the_way);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_on_the_way);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_driver_on_the_way);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            driverData = (DriverData)bundle.getSerializable("driverData");
            phone = bundle.getString("phone");
            password = bundle.getString("password");
            getTaskInfoData =(TaskInfoData)bundle.getSerializable("taskInfoData");
        }
        Log.v("ppking" , " getTaskInfoData  !!" + getTaskInfoData);
        TextView arriveAddress = (TextView)findViewById(R.id.arriveAddress);
        arriveAddress.setText(getTaskInfoData.getAddr_end_addr());
    }

    //跳轉google導航
    public void arriveAddress(View view){
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + getTaskInfoData.getAddr_end_addr());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    //聯絡乘客跳出視窗提醒
    public void contact(View view){
        CreateDialog();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_on_the_way);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_on_the_way);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void arrived(View view){

        Connect_API.getdriverstatus(this, phone, driverData.getApiKey(), new Connect_API.OnGetDriverStatusListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Toast.makeText(Driver_On_The_Way_Activity.this, "連線異常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String isError, String result, String status, String tasknumber, String carshow, String carnumber) {

                Log.v("ppking", "OnTheWay_tasknumber :  " + tasknumber);

                Connect_API.pickup(Driver_On_The_Way_Activity.this, tasknumber, driverData.getApiKey(), new Connect_API.OnGetConnectStatusListener() {
                    @Override
                    public void onFail(Exception e, String jsonError) {
                        Log.v("ppking" , " Exception : " + e);
                        Log.v("ppking" , " jsonError : " + jsonError);
                    }

                    @Override
                    public void onSuccess(String isError, String message) {
                        Log.v("ppking" , " isError : " + isError);
                        Log.v("ppking" , " message : " + message);
                    }
                });
            }
        });
//        Intent it = new Intent(this , Driver_Arrived_Activity.class);
//        startActivity(it);
    }

    public void CreateDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this , R.style.Contact_Dialog);
        View layout = LayoutInflater.from(this).inflate(R.layout.layout_contact_dialog , null);

        TextView textView = (TextView)layout.findViewById(R.id.numberPhone);
        textView.setText("乘客電話號碼為:\n"+ getTaskInfoData.getPassenger() + "\n是否要移動到撥號畫面?");

        LinearLayout knowButton = (LinearLayout)layout.findViewById(R.id.knowButton);

        ImageView imageView = (ImageView)layout.findViewById(R.id.closeDialog);

        builder.setView(layout);

        alertDialog = builder.create();
        alertDialog.show();

        knowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( ContextCompat.checkSelfPermission(
                        Driver_On_The_Way_Activity.this, Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Driver_On_The_Way_Activity.this,
                            new String[]{Manifest.permission.CALL_PHONE},123);
                }else {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+getTaskInfoData.getPassenger()));
                    startActivity(intent);
                    alertDialog.dismiss();
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int grantResult : grantResults){
            if (grantResult == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+getTaskInfoData.getPassenger()));
                startActivity(intent);
                alertDialog.dismiss();
            }
        }
    }
}
