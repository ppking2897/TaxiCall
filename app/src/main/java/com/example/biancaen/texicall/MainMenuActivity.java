package com.example.biancaen.texicall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.view.View;
import android.view.Window;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new ChangeTransform());
        getWindow().setExitTransition(new ChangeTransform());
        setContentView(R.layout.mainmenu);
    }

    public void passenger(View view){
        Intent it = new Intent(this , PassengerActivity.class);
        startActivity(it);
    }
}
