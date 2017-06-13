package com.example.biancaen.texicall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeTransform;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.biancaen.texicall.connectapi.Connect_API;
import com.example.biancaen.texicall.connectapi.UserData;

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
                Connect_API.login("0981363763", "red75325", new Connect_API.OnLoginListener() {
                    @Override
                    public void onLoginSuccess(UserData userData) {
                        String apiKey = userData.getApiKey();

                        Connect_API.rate("24.1297139", "120.6674467", "24.131516", "120.6693776", apiKey, new Connect_API.OnRateListener() {
                            @Override
                            public void onFail(Exception e) {
                                Log.i("錯誤計算",e.getMessage());
                            }

                            @Override
                            public void onSuccess(String result, int price, int time, String distance) {
                                Log.i("測試價錢", String.valueOf(price));
                            }

                        });
                    }

                    @Override
                    public void onLoginFail(Exception e) {
                        Log.i("錯誤登入",e.getMessage());
                    }
                });
            }
        });
    }

    public void passenger(View view){
        Intent it = new Intent(this , PassengerActivity.class);
        startActivity(it);
    }

}
