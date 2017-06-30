package com.example.biancaen.texicall.Driver.Driver_Trip;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biancaen.texicall.Driver.Driver_Main_Menu.Driver_Main_Menu_Activity;
import com.example.biancaen.texicall.Driver.Driver_Match.Driver_WaitMatch_Activity;
import com.example.biancaen.texicall.R;


public class Driver_Trip_Done_Dialog {
    private Context context;
    public Driver_Trip_Done_Dialog(Context context) {
        this.context = context;
    }
    public void CreateTripDoneDialog(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(context , R.style.Driver_Trip_Done_Dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_driver_trip_done_dialog,null);
        TextView offLine = (TextView)view.findViewById(R.id.offLine);
        ImageView closeDialog = (ImageView)view.findViewById(R.id.closeDialog);
        final TextView continueCarService = (TextView)view.findViewById(R.id.continueCarService);



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
                Intent it = new Intent(context , Driver_Main_Menu_Activity.class);
                context.startActivity(it);
                ((Activity)context).finish();
            }
        });

        continueCarService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context , Driver_WaitMatch_Activity.class);
                context.startActivity(it);
                //((Activity)context).finish();
            }
        });
    }
}
