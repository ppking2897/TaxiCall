package com.example.biancaen.texicall.Driver_Main_Menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.biancaen.texicall.Driver_Info.Driver_Info_Activity;
import com.example.biancaen.texicall.Driver_Point_Record.Driver_Point_Record_Activity;
import com.example.biancaen.texicall.Driver_Travel_Record.Driver_Travel_Record_Activity;
import com.example.biancaen.texicall.Driver_Match.Driver_WaitMatch_Activity;
import com.example.biancaen.texicall.R;

public class Driver_Main_Menu_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__main__menu__activicy);

        Driver_Main_Menu_Dialog driverMainMenuDialog = new Driver_Main_Menu_Dialog(this);
        driverMainMenuDialog.CreatePointDialog();

    }
    public void driver_travel_record(View view){
        Intent it = new Intent(this , Driver_Travel_Record_Activity.class);
        startActivity(it);
    }
    public void driver_point_record(View view){
        Intent it = new Intent(this , Driver_Point_Record_Activity.class);
        startActivity(it);
    }
    public void driver_info(View view){
        Intent it = new Intent(this , Driver_Info_Activity.class);
        startActivity(it);
    }
    public void driverOnline(View view){
        Intent it = new Intent(this , Driver_WaitMatch_Activity.class);
        startActivity(it);
    }

}
