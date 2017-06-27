package com.example.biancaen.texicall.Passenger.Passenger_On_The_Way;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biancaen.texicall.Passenger.Passenger_Driver_Arrived.Passenger_Driver_Arrived_Activity;
import com.example.biancaen.texicall.R;

public class Passenger_On_The_Way_Activity extends AppCompatActivity {
    private static MyCountTimer myCountTimer;
    private TextView arriveTime ;
    private int TIME ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger__on__the__way_);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_OnTheWay);
        setSupportActionBar(toolbar);

        arriveTime = (TextView)findViewById(R.id.arriveTime);
        TextView arriveAddress = (TextView)findViewById(R.id.arriveAddress);

        ImageView imageView =  (ImageView)findViewById(R.id.cancelCar);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelCar_Dialog dialog = new CancelCar_Dialog(Passenger_On_The_Way_Activity.this);
                dialog.Create_CancelCar_Dialog();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle getBundle = this.getIntent().getExtras();
        TIME = getBundle.getInt("time")*1000;

        String destination = getBundle.getString("destination");

        Log.v("ppking" , " destination  : " +destination );

        arriveAddress.setText(destination);


        if (myCountTimer ==null) {
            Log.v("ppking" , " mycountTimer  == null ");
            myCountTimer = new MyCountTimer(TIME, 1000);
            myCountTimer.start();
        }
    }

    //自定義返回鍵位置
    @Override
    public boolean onSupportNavigateUp() {
        Intent it = new Intent(this , Passenger_On_The_Way_Rates_Activity.class);
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

