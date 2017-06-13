package com.example.biancaen.texicall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeTransform;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;


import com.example.biancaen.texicall.Passenger_Sign_Menu.PassengerActivity;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new ChangeTransform());
        getWindow().setExitTransition(new ChangeTransform());
        setContentView(R.layout.activity_mainmenu);

        //測試用可須同步移除
        ImageView imageButton = (ImageView) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void passenger(View view) {
        Intent it = new Intent(this, PassengerActivity.class);
        startActivity(it);
    }
}