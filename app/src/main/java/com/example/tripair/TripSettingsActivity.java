package com.example.tripair;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class TripSettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerCountry;
    private Spinner spinnerCity;
    private String selectedCountry;
    private int countryIndex;
    private String selectrdCity;

    private ArrayList<String> countries = new ArrayList<>();
    private ArrayList<ArrayList<String>> cities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_settings);
        HandleJsonParsing();
        InitializeCountries();
    }

    public void InitializeCities()
    {

        Collections.sort(cities.get(countryIndex));

        spinnerCity = (Spinner)findViewById(R.id.citiesSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TripSettingsActivity.this,
                android.R.layout.simple_spinner_item,cities.get(countryIndex));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(adapter);
        spinnerCity.setOnItemSelectedListener(this);
    }

    public void HandleJsonParsing()
    {
        JSONObject obj;
        try {
            obj = new JSONObject(loadJSONFromAsset(this));
            Iterator keys = obj.keys();
            int i=0;
            while(keys.hasNext()) {

                String countryKey = (String)keys.next();
                // Log.i("Info" , countryKey);
                countries.add(i, countryKey);

                JSONArray cityPerCountryKey = obj.getJSONArray(countryKey);

                ArrayList<String> tmp = new ArrayList<>();

                for (int j=0;j<cityPerCountryKey.length();j++){
                    tmp.add(cityPerCountryKey.getString(j));
                }
                cities.add(i,tmp);

                i++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String loadJSONFromAsset(Context context) {
        Log.i("Info","In loadJson");
        String json = null;
        try {
            InputStream is = context.getAssets().open("countries_cities.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }



    public void InitializeCountries()
    {

        Collections.sort(countries);

        spinnerCountry = (Spinner)findViewById(R.id.countrySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TripSettingsActivity.this,
                android.R.layout.simple_spinner_item,countries);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(adapter);
        spinnerCountry.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.countrySpinner) {
            Log.i("Info", "spinnerCountry");
            selectedCountry = adapterView.getSelectedItem().toString();
            countryIndex = countries.indexOf(selectedCountry);
            Log.i("Info", selectedCountry);
            String str = String.valueOf(countryIndex);
            Log.i("Info", str);
            InitializeCities();
        }

        if(adapterView.getId() == R.id.citiesSpinner) {
            Log.i("Info", "spinnerCity");
            selectrdCity = adapterView.getSelectedItem().toString();
            Log.i("Info", selectrdCity);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}





