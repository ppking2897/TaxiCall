package com.example.biancaen.texicall;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;

public class Passenger_Car_Service_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private int emptyTripCount = 0;
    private int emptyTripPay = 0;
    private TextView passenger_Number;
    private int number = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_car__service_);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_car_service);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_car_service);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_car_service);
        navigationView.setNavigationItemSelectedListener(this);

        passenger_Number = (TextView) findViewById(R.id.passenger_Number);

        //Todo 空趟的次數已及要加收的價格資料存放位置 emptyTripCount emptyTripPay

        TextView textView_emptyTripCount = (TextView) findViewById(R.id.null_Empty_Trip_Count);
        TextView textView_emptyTripPay = (TextView) findViewById(R.id.null_Empty_Trip_ToPay);

        textView_emptyTripCount.setText(""+emptyTripCount);
        textView_emptyTripPay.setText("" + emptyTripPay);

        if (emptyTripCount != 0|| emptyTripPay !=0){
            Empty_Dialog empty_dialog = new Empty_Dialog(this , emptyTripCount , emptyTripPay);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_car_service);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_car_service);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    public void btn_reduce(View view){
        if (number != 1){
            number -= 1;
        }
        passenger_Number.setText("" + number);
    }
    public void btn_plus(View view){
        number += 1;
        passenger_Number.setText("" + number);
    }
    public void fares(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("emptyTripCount" , emptyTripCount);
        bundle.putInt("emptyTripPay" , emptyTripPay);

        Intent it = new Intent(this , Passenger_Fares_Activity.class);
        it.putExtras(bundle);
        startActivity(it);
    }
}

class Empty_Dialog{
    private Context context;
    private int dialogEmptyCont , dialogEmptyTripPay;
    Empty_Dialog(Context context , int dialogEmptyCont , int dialogEmptyTripPay ){
        this.context = context;
        this.dialogEmptyCont = dialogEmptyCont;
        this.dialogEmptyTripPay = dialogEmptyTripPay;
        final AlertDialog.Builder builder = new AlertDialog.Builder(context , R.style.CustomDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_empty_dialog,null);

        LinearLayout knowButton =  (LinearLayout) view.findViewById(R.id.knowButton);
        ImageButton closeDialog = (ImageButton) view.findViewById(R.id.closeDialog);

        TextView textViewDialogEmptyCont = (TextView) view.findViewById(R.id.dialogEmptyCont);
        TextView textViewDialogEmptyTripPay = (TextView) view.findViewById(R.id.dialogEmptyTripPay);

        textViewDialogEmptyCont.setText(""+dialogEmptyCont);
        textViewDialogEmptyTripPay.setText(""+dialogEmptyTripPay);

        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        knowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }
}
