package com.example.biancaen.texicall.Passenger.Passenger_On_The_Way;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.biancaen.texicall.Passenger.Passenger_Car_Service.Passenger_Car_Service_Activity;
import com.example.biancaen.texicall.R;

/**
 * Created by BiancaEN on 2017/6/12.
 */

public class CancelCar_Dialog {
    private Context context;
    CancelCar_Dialog(final Context context){
        this.context = context;
    }
    public void Create_CancelCar_Dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context , R.style.CustomDialog02);
        View view  = LayoutInflater.from(context).inflate(R.layout.layout_cancel_car_dialog , null);
        builder.setView(view);

        TextView cancel = (TextView)view.findViewById(R.id.cancel);
        TextView accept = (TextView)view.findViewById(R.id.accept);


        final AlertDialog dialog = builder.create();

        dialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context , Passenger_Car_Service_Activity.class);
                context.startActivity(it);
            }
        });
    }
}
