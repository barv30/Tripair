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
    private Spinner arriveSpinnerDay;
    private Spinner arriveSpinnerMonth;
    private Spinner arriveSpinnerYear;
    private Spinner leftSpinnerDay;
    private Spinner leftSpinnerMonth;
    private Spinner leftSpinnerYear;
    private String selectedCountry;
    private int countryIndex;
    private String selectedCity;

    private ArrayList<String> countries = new ArrayList<>();
    private ArrayList<ArrayList<String>> cities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_settings);
        HandleJsonParsing();
        InitializeCountries();
        InitializeDays();
        InitializeMonths();
        InitializeYears();
    }

    private void InitializeCities()
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



    private void InitializeCountries()
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
            selectedCity = adapterView.getSelectedItem().toString();
            Log.i("Info", selectedCity);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void InitializeDays()
    {
        ArrayList<String> days = new ArrayList<>();
        for(Integer i=1;i<=31;i++)
        {
            days.add(i.toString());
        }

        arriveSpinnerDay = (Spinner)findViewById(R.id.daySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TripSettingsActivity.this,
                android.R.layout.simple_spinner_item,days);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arriveSpinnerDay.setAdapter(adapter);
        arriveSpinnerDay.setOnItemSelectedListener(this);

        leftSpinnerDay = (Spinner)findViewById(R.id.daySpinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(TripSettingsActivity.this,
                android.R.layout.simple_spinner_item,days);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leftSpinnerDay.setAdapter(adapter2);
        leftSpinnerDay.setOnItemSelectedListener(this);
    }

    private void InitializeMonths()
    {
        ArrayList<String> months = new ArrayList<>();
        for(Integer i=1;i<=12;i++)
        {
            months.add(i.toString());
        }

        arriveSpinnerMonth = (Spinner)findViewById(R.id.monthSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TripSettingsActivity.this,
                android.R.layout.simple_spinner_item,months);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arriveSpinnerMonth.setAdapter(adapter);
        arriveSpinnerMonth.setOnItemSelectedListener(this);

        leftSpinnerMonth = (Spinner)findViewById(R.id.monthSpinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(TripSettingsActivity.this,
                android.R.layout.simple_spinner_item,months);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leftSpinnerMonth.setAdapter(adapter2);
        leftSpinnerMonth.setOnItemSelectedListener(this);
    }

    private void InitializeYears()
    {
        ArrayList<String> years = new ArrayList<>();
        for(Integer i=2019;i<=2030;i++)
        {
            years.add(i.toString());
        }

        arriveSpinnerYear = (Spinner)findViewById(R.id.yearSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TripSettingsActivity.this,
                android.R.layout.simple_spinner_item,years);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arriveSpinnerYear.setAdapter(adapter);
        arriveSpinnerYear.setOnItemSelectedListener(this);

        leftSpinnerYear = (Spinner)findViewById(R.id.yearSpinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(TripSettingsActivity.this,
                android.R.layout.simple_spinner_item,years);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leftSpinnerYear.setAdapter(adapter2);
        leftSpinnerYear.setOnItemSelectedListener(this);
    }
}





