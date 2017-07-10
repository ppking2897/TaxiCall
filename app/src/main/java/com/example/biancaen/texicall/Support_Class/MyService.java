package com.example.biancaen.texicall.Support_Class;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.biancaen.texicall.Beginning.MainMenuActivity;
import com.example.biancaen.texicall.Driver.Driver_Main_Menu.Driver_Main_Menu_Activity;
import com.example.biancaen.texicall.connectapi.Connect_API;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        SharedPreferences sharedPreferences = getSharedPreferences("type" , MODE_PRIVATE);
        Boolean isDriver = sharedPreferences.getBoolean("isDriver" , false);

        Log.v("ppking" , " onTaskRemoved " +isDriver);

        Driver_Main_Menu_Activity activity = new Driver_Main_Menu_Activity();

        if (isDriver){
            activity.Logout();
            Log.v("ppking" , "is driver ");
        }

        stopSelf();
    }

}
