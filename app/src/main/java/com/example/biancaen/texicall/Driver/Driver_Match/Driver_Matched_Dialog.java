package com.example.biancaen.texicall.Driver.Driver_Match;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biancaen.texicall.Driver.Driver_Main_Menu.Driver_Main_Menu_Activity;
import com.example.biancaen.texicall.Driver.Driver_Passenger_Request.Driver_Passenger_Request_Activity;
import com.example.biancaen.texicall.R;

/**
 * Created by BiancaEN on 2017/6/16.
 */

public class Driver_Matched_Dialog {
    private Context context;
    public Driver_Matched_Dialog(Context context){
        this.context = context;
    }
    public void CreatMatchedDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context , R.style.Driver_Match_Dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_driver_match_dialog,null);

        TextView refuse =  (TextView) view.findViewById(R.id.refuse);
        TextView accept =  (TextView) view.findViewById(R.id.accept);
        ImageView closeDialog = (ImageView) view.findViewById(R.id.closeDialog);

        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context , Driver_Main_Menu_Activity.class );
                context.startActivity(it);
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context , Driver_Passenger_Request_Activity.class );
                context.startActivity(it);
            }
        });
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context , Driver_Main_Menu_Activity.class );
                context.startActivity(it);
            }
        });


    }
}
