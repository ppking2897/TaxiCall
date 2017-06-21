package com.example.biancaen.texicall.Passenger.Passenger_Edit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.biancaen.texicall.R;

public class Passenger_Edit_Username_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger__edit__username_);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_Edit_Username);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
