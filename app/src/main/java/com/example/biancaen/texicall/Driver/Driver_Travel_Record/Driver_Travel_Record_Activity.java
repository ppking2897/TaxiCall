package com.example.biancaen.texicall.Driver.Driver_Travel_Record;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biancaen.texicall.Beginning.MainMenuActivity;
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
    private static String phone;
    private static String password;
    private static String driverApiKey;
    private Driver_Travel_Record_Adapter adapter;
    private int driverStatus;
    private NavigationView navigationView;
    private boolean isLogout;

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

        navigationView = (NavigationView) findViewById(R.id.nav_view_driver_travel_record);
        navigationView.setNavigationItemSelectedListener(this);

        InitData();

        HeaderTextView();

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
        } else{
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
            startActivity(it);

        } else if (id == R.id.nav_id__info) {
            Intent it = new Intent(this , Driver_Info_Activity.class);
            startActivity(it);

        } else if (id == R.id.nav_home) {
            Intent it = new Intent(this , Driver_Main_Menu_Activity.class);
            startActivity(it);

        } else if (id == R.id.nav_logout) {
            Logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_travel__record);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void GetTravelData(){
        Connect_API.getRecordListForDriver(this, phone, driverApiKey, new Connect_API.OnRecordListDriverListener() {
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
    public void HeaderTextView(){
        //改變滑頁header textView
        View header = navigationView.getHeaderView(0);
        TextView textView = (TextView)header.findViewById(R.id.header_state);
        ImageView imageView = (ImageView)header.findViewById(R.id.picture);

        if (driverStatus==2){

            textView.setText("結束下線");
            imageView.setImageResource(R.drawable.ic_offline_selected_side_drawer);
        }else if (driverStatus==1){

            textView.setText("上線載客");
            imageView.setImageResource(R.drawable.ic_online_selected_side_drawer);
        }
    }
    public void InitData(){
        SharedPreferences sharedPreferences = getSharedPreferences("driver" , MODE_PRIVATE);
        phone = sharedPreferences.getString("phone", null);
        password = sharedPreferences.getString("password" , null);
        driverApiKey = sharedPreferences.getString("driverApiKey" , null);
        driverStatus = sharedPreferences.getInt("driverStatus" , 0);
    }

    public void Logout(){
        Connect_API.loginOut(this, phone, driverApiKey, new Connect_API.OnLoginOutListener() {
            @Override
            public void onFail(Exception e, String jsonError) {
                Log.v("ppking" , "Exception : " +e);
                Log.v("ppking" , "jsonError : " +jsonError);
            }

            @Override
            public void onSuccess(boolean isError, String message) {
                if (!isError){
                    SharedPreferences sharedPreferences = getSharedPreferences("driver" , MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    Toast.makeText(Driver_Travel_Record_Activity.this ,""+message , Toast.LENGTH_SHORT).show();
                    //清除所有上一頁Activity
                    Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Toast.makeText(Driver_Travel_Record_Activity.this ,""+message , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
