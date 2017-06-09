package com.example.biancaen.texicall;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Passenger_On_The_Way_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger__on__the__way_);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_OnTheWay);
        setSupportActionBar(toolbar);

        ImageView imageView =  (ImageView)findViewById(R.id.cancelCar);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelCar_Dialog dialog = new CancelCar_Dialog(Passenger_On_The_Way_Activity.this);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
    //
    @Override
    public boolean onSupportNavigateUp() {
        Intent it = new Intent(this , Passenger_On_The_Way_Fares_Activity.class);
        startActivity(it);
        return true;
    }
}

class CancelCar_Dialog {
    private Context context;
    public CancelCar_Dialog (final Context context){
        this.context = context;
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
                Intent it = new Intent(context , Passenger_Menu_Activity.class);
                context.startActivity(it);
            }
        });
    }
}
