package com.example.biancaen.texicall.Driver.Driver_Trip;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biancaen.texicall.Driver.Driver_Main_Menu.Driver_Main_Menu_Activity;
import com.example.biancaen.texicall.Driver.Driver_Match.Driver_WaitMatch_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.CheckOutData;
import com.example.biancaen.texicall.connectapi.Connect_API;


public class Driver_Trip_Done_Dialog {
    private Context context;
    private CheckOutData checkOutData;
    private String location;
    private String destination;
    private String phone;
    private String driverApiKey;
    private Driver_Trip_Done_Activity activity;


    public Driver_Trip_Done_Dialog(Context context , CheckOutData checkOutData ,String location , String destination , String phone , String driverApiKey) {
        this.context = context;
        this.checkOutData = checkOutData;
        this.location = location;
        this.destination = destination;
        this.phone = phone;
        this.driverApiKey = driverApiKey;
    }
    public void CreateTripDoneDialog(){

        activity = new Driver_Trip_Done_Activity();
        final AlertDialog.Builder builder = new AlertDialog.Builder(context , R.style.Driver_Trip_Done_Dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_driver_trip_done_dialog,null);

        TextView continueCarService = (TextView)view.findViewById(R.id.continueCarService);
        TextView offLine = (TextView)view.findViewById(R.id.offLine);
        TextView driverDoneDate = (TextView)view.findViewById(R.id.driverDoneDate);
        TextView start = (TextView)view.findViewById(R.id.location);
        TextView end = (TextView)view.findViewById(R.id.destination);
        TextView time = (TextView)view.findViewById(R.id.time);
        TextView price = (TextView)view.findViewById(R.id.price);
        TextView totalPrice = (TextView)view.findViewById(R.id.totalPrice);

        ImageView closeDialog = (ImageView)view.findViewById(R.id.closeDialog);

        Log.v("ppking" , " checkOutData.getMisscatchprice() : " + checkOutData.getMisscatchprice());

        start.setText(location);
        end.setText(destination);
        driverDoneDate.setText(checkOutData.getDate());
        time.setText((int)Double.parseDouble(checkOutData.getTime())+"分");

        if (checkOutData.getMisscatchprice().equals("0")){
            totalPrice.setText("金額 :\n");
            price.setText((int)Double.parseDouble(checkOutData.getPrice())+"點");
        }else {
            totalPrice.setText("金額 :\n(此趟包含 "+checkOutData.getMisscatchprice() +" 空趟費)");
            price.setText((int)Double.parseDouble(checkOutData.getPrice())+"點");
        }

        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        offLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            alertDialog.dismiss();
            Intent it = new Intent(context , Driver_Main_Menu_Activity.class);
            context.startActivity(it);
            ((Activity)context).finish();

            }
        });

        continueCarService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            alertDialog.dismiss();
            Intent it = new Intent(context , Driver_Main_Menu_Activity.class);
            context.startActivity(it);
            ((Activity)context).finish();

            }
        });
    }
}
