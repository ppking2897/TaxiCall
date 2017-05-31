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

    private static final int TIME = 2000;
    private Timer timer;
    private boolean isExit = true;

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
    }

    @Override
    public void onStart(){
        super.onStart();
        isExit = false;
        startTimer();
    }

    @Override
    public void onStop(){
        super.onStop();
        isExit = true;
        cancelTimer();
    }

    private void startTimer(){
        cancelTimer();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isExit) {
                            Intent it = new Intent(BeginningActivity.this, MainMenuActivity.class);
                            startActivity(it , ActivityOptions.makeSceneTransitionAnimation(BeginningActivity.this,logo,"logo").toBundle());
                            timer.cancel();
                            timer = null;
                            finish();
                        }
                    }
                });

            }
        }, TIME);
    }

    private void cancelTimer(){
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }
}
