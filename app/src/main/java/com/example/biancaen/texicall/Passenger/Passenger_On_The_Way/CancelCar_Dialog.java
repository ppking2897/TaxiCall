package com.example.biancaen.texicall.Passenger.Passenger_On_The_Way;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.biancaen.texicall.Passenger.Passenger_Car_Service.Passenger_Car_Service_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;

/**
 * Created by BiancaEN on 2017/6/12.
 */

public class CancelCar_Dialog {
    private Context context;
    private static String taskNumber;
    private static String passengerApiKey;
    private Passenger_On_The_Way_Activity activity;

    CancelCar_Dialog(final Context context){
        this.context = context;
    }
    public void Create_CancelCar_Dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context , R.style.CustomDialog02);
        View view  = LayoutInflater.from(context).inflate(R.layout.layout_cancel_car_dialog , null);
        builder.setView(view);

        activity = new Passenger_On_The_Way_Activity();

        TextView cancel = (TextView)view.findViewById(R.id.cancel);
        TextView accept = (TextView)view.findViewById(R.id.accept);

        SharedPreferences sharedPreferences = context.getSharedPreferences("passenger" , context.MODE_PRIVATE);
        taskNumber = sharedPreferences.getString("taskNumber" , null);
        passengerApiKey = sharedPreferences.getString("passengerApiKey", null);


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
                Connect_API.terminateByPassenger(activity, taskNumber, passengerApiKey, new Connect_API.OnGetConnectStatusListener() {
                    @Override
                    public void onFail(Exception e, String jsonError) {
                        Log.v("ppking" , "terminateByPassenger Exception : " +e);
                        Log.v("ppking" , "terminateByPassenger jsonError : " +jsonError);
                    }

                    @Override
                    public void onSuccess(String isError, String message) {

                        Log.v("ppking" , "terminateByPassenger isError : " +isError);
                        Log.v("ppking" , "terminateByPassenger message : " +message);
                    }
                });

                Intent it = new Intent(context , Passenger_Car_Service_Activity.class);
                context.startActivity(it);
            }
        });
    }
}
