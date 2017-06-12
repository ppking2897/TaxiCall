package com.example.biancaen.texicall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeTransform;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.biancaen.texicall.domain.AppUtility;
import com.example.biancaen.texicall.domain.Connect_API;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainMenuActivity extends AppCompatActivity {
    private TextView textView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new ChangeTransform());
        getWindow().setExitTransition(new ChangeTransform());
        setContentView(R.layout.activity_mainmenu);
    }

    public void passenger(View view){
        Intent it = new Intent(this , PassengerActivity.class);
        startActivity(it);
    }

    /*測試使用*/
    public void onClickTest(View view){
        Connect_API.login("0981363763", "red75325", new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.i("測試" , response.body().string());
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    AppUtility.apiKey = jsonObject.getString("apiKey");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Connect_API.rate("24.1297139", "120.6674467", "24.131516", "120.6693776", new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.i("測試" , response.body().string());
            }
        });

    }
}
