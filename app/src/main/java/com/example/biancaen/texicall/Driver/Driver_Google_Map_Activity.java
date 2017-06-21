package com.example.biancaen.texicall.Driver;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biancaen.texicall.Driver.Driver_Trip_Done.Driver_Trip_Done_Activity;
import com.example.biancaen.texicall.R;

public class Driver_Google_Map_Activity extends AppCompatActivity {

    private boolean start;
    private ImageView imageView;
    private TextView startTripText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__google__map_);
        startTripText = (TextView)findViewById(R.id.startTripText);
        imageView = (ImageView)findViewById(R.id.startTrip);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!start){
                    imageView.setImageResource(R.drawable.btn_ending);
                    startTripText.setVisibility(View.INVISIBLE);
                    start = true;
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=台中市大里區甲園街105號");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }else {
                    Intent it = new Intent(Driver_Google_Map_Activity.this , Driver_Trip_Done_Activity.class);
                    startActivity(it);
                }
            }
        });
    }

}
