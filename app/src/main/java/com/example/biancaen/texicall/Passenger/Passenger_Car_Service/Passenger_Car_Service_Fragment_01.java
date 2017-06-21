package com.example.biancaen.texicall.Passenger.Passenger_Car_Service;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.biancaen.texicall.Support_Class.CityAreaData;
import com.example.biancaen.texicall.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Passenger_Car_Service_Fragment_01 extends android.app.Fragment {
    private List<String> cityDataArray;
    private List<CityAreaData> areaDataArray;
    private List<String> area;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_passenger_car_service_get_on_address_01, null);
        GetAddress(view);
        return view;
    }

    public void GetAddress(View view){
        Spinner citySpinner = (Spinner)view.findViewById(R.id.citySpinner);
        final Spinner areaSpinner = (Spinner)view.findViewById(R.id.areaSpinner);

        cityDataArray = new ArrayList<>();
        areaDataArray = new ArrayList<>();

        InitCityData();

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(view.getContext(),
                R.layout.layout_spiner_text_item,
                cityDataArray);

        cityAdapter.setDropDownViewResource(R.layout.layout_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdapter);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("ppking" , " areaSpinner01 :  " + cityDataArray.get(position));
                areaSpinner.setAdapter(null);
                area = areaDataArray.get(position).getDistrict();
                ArrayAdapter adapter = new ArrayAdapter<>(view.getContext() , R.layout.layout_spiner_text_item , area );
                adapter.setDropDownViewResource(R.layout.layout_spinner_dropdown_item);
                areaSpinner.setAdapter(adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("ppking" , " areaSpinner01 :  " + area.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //----------------------------------------
    }

    public void InitCityData(){

        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(getActivity().getAssets().open("area_data.json")));
            String line;
            while ((line = reader.readLine()) != null){
                stringBuilder.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();

        }finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        ArrayList<CityAreaData> dataArray = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            String json = jsonObject.getJSONArray("city").toString();

            Gson gson = new GsonBuilder().disableHtmlEscaping().create();

            dataArray = gson.fromJson(json , new TypeToken<List<CityAreaData>>() {}.getType());

            cityDataArray.clear();
            areaDataArray.clear();
            for (CityAreaData data : dataArray){
                cityDataArray.add(data.getCityName());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        areaDataArray = dataArray;
    }

}
