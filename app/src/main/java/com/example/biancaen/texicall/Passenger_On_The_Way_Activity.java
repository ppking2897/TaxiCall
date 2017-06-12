package com.example.biancaen.texicall;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class Passenger_On_The_Way_Activity extends AppCompatActivity {
    private static MyCountTimer myCountTimer;
    private TextView arriveTime ;
    private int TIME = 23000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger__on__the__way_);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_OnTheWay);
        setSupportActionBar(toolbar);


        arriveTime = (TextView)findViewById(R.id.arriveTime);

        ImageView imageView =  (ImageView)findViewById(R.id.cancelCar);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelCar_Dialog dialog = new CancelCar_Dialog(Passenger_On_The_Way_Activity.this);

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (myCountTimer ==null) {
            Log.v("ppking" , " mycountTimer  == null ");
            myCountTimer = new MyCountTimer(TIME, 1000);
            myCountTimer.start();
        }
    }
    //自定義返回鍵位置
    @Override
    public boolean onSupportNavigateUp() {
        Intent it = new Intent(this , Passenger_On_The_Way_Fares_Activity.class);
        startActivity(it);
        return true;
    }

    private class MyCountTimer extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        MyCountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Log.v("ppking" , "  millisUntilFinished/10000  :  " + millisUntilFinished/10000);
            arriveTime.setText("" + millisUntilFinished/1000);
        }

        @Override
        public void onFinish() {
            Intent it = new Intent(Passenger_On_The_Way_Activity.this , Passenger_Driver_Arrived_Activity.class);
            startActivity(it);
            myCountTimer.cancel();
            myCountTimer = null;
        }
    }
}

class CancelCar_Dialog {
    private Context context;
    CancelCar_Dialog(final Context context){
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
                Intent it = new Intent(context , Passenger_Car_Service_Activity.class);
                context.startActivity(it);
            }
        });
    }
}
