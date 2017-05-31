package com.example.biancaen.texicall;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.view.Window;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class BeginningActivity extends AppCompatActivity {
    private ImageView logo ;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new ChangeTransform());
        setContentView(R.layout.beginning);

        logo = (ImageView)findViewById(R.id.logo);
        ImageView welcome = (ImageView) findViewById(R.id.welcome);
        logo.animate().alpha(1.0f).setDuration(6000);
        welcome.animate().alpha(1.0f).setDuration(6000);
        timer.schedule(new MyTimerTask() , 3000 , 1000);

    }

    private class MyTimerTask extends TimerTask{

        @Override
        public void run() {
            //出現Can't create handler inside thread that has not called Looper.prepare() 利用執行緒
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent it = new Intent(BeginningActivity.this, MainMenuActivity.class);
                    startActivity(it , ActivityOptions.makeSceneTransitionAnimation(BeginningActivity.this,logo,"logo").toBundle());
                    timer.cancel();
                    timer = null;
                    finish();
                }
            });

        }
    }
}
