package com.example.biancaen.texicall.Driver.Driver_Travel_Record;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.biancaen.texicall.Driver.Driver_Info.Driver_Info_Activity;
import com.example.biancaen.texicall.Driver.Driver_Main_Menu.Driver_Main_Menu_Activity;
import com.example.biancaen.texicall.Driver.Driver_Point_Record.Driver_Point_Record_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.DriverData;
import com.example.biancaen.texicall.connectapi.RecordDriverData;

import java.util.ArrayList;
import java.util.List;

public class Driver_Travel_Record_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<String> date = new ArrayList<>();
    private ArrayList<String> getOn = new ArrayList<>();
    private ArrayList<String> getOff = new ArrayList<>();
    private ArrayList<String> time = new ArrayList<>();
    private ArrayList<String> rate = new ArrayList<>();
    private static DriverData driverData;
    private static String phone;
    private static String password;
    private Driver_Travel_Record_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__travel__record_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_driver_travel_record);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_travel__record);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_driver_travel_record);
        navigationView.setNavigationItemSelectedListener(this);

        //todo 改變滑頁header textView
//        View header = navigationView.getHeaderView(0);
//        TextView textView = (TextView)header.findViewById(R.id.header_state);
//        textView.setText(123456+"");

        //ToDo 司機紀錄上車 下車 時間  金額 資料位置

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            driverData = (DriverData)bundle.getSerializable("driverData");
            phone = bundle.getString("phone");
            password = bundle.getString("password");

            Log.v("ppking" , "phone  :  " + phone);
            Log.v("ppking" , "driverData  :  " + driverData);
        }

        GetTravelData();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.driver_travel_record_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Driver_Travel_Record_Adapter(this , date , getOn , getOff , time , rate);

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        GetTravelData();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_travel__record);
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


        } else if (id == R.id.nav_driver_point_list) {
            Intent it = new Intent(this , Driver_Point_Record_Activity.class);
            it.putExtras(TransData());
            startActivity(it);

        } else if (id == R.id.nav_id__info) {
            Intent it = new Intent(this , Driver_Info_Activity.class);
            it.putExtras(TransData());
            startActivity(it);

        } else if (id == R.id.nav_home) {
            Intent it = new Intent(this , Driver_Main_Menu_Activity.class);
            it.putExtras(TransData());
            startActivity(it);

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_travel__record);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void GetTravelData(){
        Connect_API.getRecordListForDriver(this, phone, driverData.getApiKey(), new Connect_API.OnRecordListDriverListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , e.getMessage());
                Log.v("ppking" , " jsonError :  "+jsonError);
                Toast.makeText(Driver_Travel_Record_Activity.this , "連線異常" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String isError, String msg, List<RecordDriverData> data) {
                Log.v("ppking" , " jsonError :  "+isError);
                Log.v("ppking" , " msg :  "+msg);
                Log.v("ppking" , " data :  "+data.get(0));

                date.clear();
                getOn.clear();
                getOff.clear();
                time.clear();
                rate.clear();

                for ( int i = 0 ; i<data.size() ; i++){
                    if (isError.equals("false")){
                        date.add(data.get(i).getCreated_at().substring(0,11));
                        getOn.add("上車地點  /  \n" + data.get(i).getAddr_start_addr());
                        getOff.add("下車地點  /  \n" + data.get(i).getAddr_end_addr());
                        time.add("乘車時間  /  \n" + data.get(i).getTime());
                        rate.add("金額  /  \n" + data.get(i).getPrice() + "元");
                    }else {
                        String fail = "資料取得失敗";
                        date.add(fail);
                        getOn.add(fail);
                        getOff.add(fail);
                        time.add(fail);
                        rate.add(fail);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public Bundle TransData(){
        Bundle bundle = new Bundle();
        bundle.putString("phone" , phone);
        bundle.putString("password" , password);
        bundle.putSerializable("driverData" , driverData);
        return bundle;
    }
}
