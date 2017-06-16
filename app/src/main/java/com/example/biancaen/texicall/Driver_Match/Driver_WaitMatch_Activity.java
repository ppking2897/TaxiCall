package com.example.biancaen.texicall.Driver_Match;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.example.biancaen.texicall.R;

public class Driver_WaitMatch_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__wait_match_);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.driverWaitMatch);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0f , 1.0f);
        alphaAnimation.setDuration(800);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        linearLayout.setAnimation(alphaAnimation);
    }
    public void driverMatchTest(View view){
        Driver_Matched_Dialog driver_matched_dialog = new Driver_Matched_Dialog(this);
        driver_matched_dialog.CreatMatchedDialog();
    }

}
