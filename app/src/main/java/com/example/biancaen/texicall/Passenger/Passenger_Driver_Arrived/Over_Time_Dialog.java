package com.example.biancaen.texicall.Passenger.Passenger_Driver_Arrived;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.biancaen.texicall.Passenger.Passenger_Car_Service.Passenger_Car_Service_Activity;
import com.example.biancaen.texicall.R;


class Over_Time_Dialog {
    private Context context;
    Over_Time_Dialog(Context context){
        this.context = context;

    }
    void Create_Over_Timer_Dialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context , R.style.Over_Time_Dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_over_time_dialog , null);

        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        ImageView reCallCar = (ImageView)view.findViewById(R.id.reCallCar);
        ImageView noThanks =(ImageView)view.findViewById(R.id.noThanks);
        ImageView close_Over_Time_Dialog =(ImageView)view.findViewById(R.id.close_Over_Time_Dialog);

        reCallCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, Passenger_Car_Service_Activity.class);
                context.startActivity(it);
            }
        });
        noThanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        close_Over_Time_Dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}
