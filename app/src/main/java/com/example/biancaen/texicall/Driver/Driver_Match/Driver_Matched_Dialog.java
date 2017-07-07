package com.example.biancaen.texicall.Driver.Driver_Match;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biancaen.texicall.Driver.Driver_Main_Menu.Driver_Main_Menu_Activity;
import com.example.biancaen.texicall.Driver.Driver_Passenger_Request.Driver_Passenger_Request_Activity;
import com.example.biancaen.texicall.R;
import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.DriverData;
import com.example.biancaen.texicall.connectapi.TaskInfoData;

/**
 * Created by BiancaEN on 2017/6/16.
 */

public class Driver_Matched_Dialog {
    private Context context;
    private TaskInfoData taskInfoData;
    private String tasknumber;
    private String phone;
    private String password;
    private DriverData driverData;
    private Driver_WaitMatch_Activity activity;
    private AlertDialog alertDialog;


    public Driver_Matched_Dialog(Context context  , TaskInfoData taskInfoData ,
                                 DriverData driverData , String tasknumber ,
                                 String phone , String password){

        this.context = context;
        this.taskInfoData = taskInfoData;
        this.tasknumber = tasknumber;
        this.phone = phone;
        this.driverData = driverData;
        this.password = password;

    }

    public void CreateMatchedDialog(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(context , R.style.Driver_Match_Dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_driver_match_dialog,null);

        TextView refuse =  (TextView) view.findViewById(R.id.refuse);
        final TextView accept =  (TextView) view.findViewById(R.id.accept);

        TextView driverMatchArriveAddress = (TextView)view.findViewById(R.id.driverMatchArriveAddress);

        driverMatchArriveAddress.setText(taskInfoData.getAddr_start_addr());

        activity = new Driver_WaitMatch_Activity();

        ImageView closeDialog = (ImageView) view.findViewById(R.id.closeDialog);

        builder.setView(view);

        alertDialog = builder.create();
        alertDialog.show();

        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "1";
                Connect_API.putdriverstatus(activity , phone, status, driverData.getApiKey(), new Connect_API.OnPutDriverStatusListener() {
                    @Override
                    public void onFail(Exception e, String jsonError) {
                        Log.v("ppking", "" + e.getMessage());
                        Log.v("ppking", "jsonError : " + jsonError);

                    }

                    @Override
                    public void onSuccess(String isError, String result) {
                        Log.v("ppking", "isError :  " + isError);
                        Log.v("ppking", "result : " + result);
                        activity.ClearTaskNumber();
                        alertDialog.dismiss();
                        activity.finish();
                        Intent it = new Intent(context , Driver_Main_Menu_Activity.class );
                        context.startActivity(it);
                    }
                });
            }
        });
        //確定接任務
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Connect_API.accepttask(activity, tasknumber, phone, driverData.getApiKey(), new Connect_API.OnGetConnectStatusListener() {
                @Override
                public void onFail(Exception e, String jsonError) {
                    Log.v("ppking" , "Exception   : " + e);
                    Log.v("ppking" , "jsonError   : " + jsonError);
                }

                @Override
                public void onSuccess(String isError, String message) {
                    Log.v("ppking" , "isError   : " + isError);
                    Log.v("ppking" , "message   : " + message);

                    alertDialog.dismiss();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("taskInfoData" , taskInfoData);
                    bundle.putString("phone" , phone);
                    bundle.putSerializable("driverData" , driverData);
                    bundle.putString("password" , password);
                    Intent it = new Intent(context , Driver_Passenger_Request_Activity.class );
                    it.putExtras(bundle);
                    context.startActivity(it);
                    activity.finish();
                }
            });
            }
        });
        //右上角離開，reload一次activity
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "2";
                Connect_API.putdriverstatus(activity , phone, status, driverData.getApiKey(), new Connect_API.OnPutDriverStatusListener() {
                    @Override
                    public void onFail(Exception e, String jsonError) {
                        Log.v("ppking", "" + e.getMessage());
                        Log.v("ppking", "jsonError : " + jsonError);

                    }

                    @Override
                    public void onSuccess(String isError, String result) {
                        Log.v("ppking", "isError :  " + isError);
                        Log.v("ppking", "result : " + result);
                        activity.ClearTaskNumber();
                        alertDialog.dismiss();
                        activity.finish();
                        Intent it = new Intent(context , Driver_WaitMatch_Activity.class );
                        context.startActivity(it);

                    }
                });
            }
        });
    }
}
