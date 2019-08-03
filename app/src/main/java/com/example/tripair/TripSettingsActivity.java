package com.example.tripair;

import android.content.Context;
import android.content.Intent;
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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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
    private String m_selectedCountry;
    private int countryIndex;
    private String m_selectedCity;
    private int m_dayArrive;
    private int m_monthArrive;
    private int m_yearArrive;
    private int m_dayLeft;
    private int m_monthLeft;
    private int m_yearLeft;
    private ArrayList<String> typeTripArr;
    Calendar calendar = Calendar.getInstance();


    private ArrayList<String> countries = new ArrayList<>();
    private ArrayList<ArrayList<String>> cities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_settings);
        Date currentDate= calendar.getTime();
        calendar.setTime(currentDate);
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

        switch(adapterView.getId())
        {
            case R.id.countrySpinner:
            {
                Log.i("Info", "spinnerCountry");
                m_selectedCountry = adapterView.getSelectedItem().toString();
                countryIndex = countries.indexOf(m_selectedCountry);
                Log.i("Info", m_selectedCountry);
                String str = String.valueOf(countryIndex);
                Log.i("Info", str);
                InitializeCities();
            }
            case R.id.citiesSpinner:
            {
                Log.i("Info", "spinnerCity");
                m_selectedCity = adapterView.getSelectedItem().toString();
                Log.i("Info", m_selectedCity);
                break;
            }
            case R.id.spinnerArriveDay:
            {
                Log.i("Info", "spinnerArriveDay");
                m_dayArrive = Integer.parseInt(adapterView.getSelectedItem().toString());
                Log.i("Info", String.valueOf(m_dayArrive));
                break;
            }
            case R.id.spinnerArriveMonth:
            {
                Log.i("Info", "spinnerArriveMonth");
                m_monthArrive = Integer.parseInt(adapterView.getSelectedItem().toString());
                Log.i("Info", String.valueOf(m_monthArrive));
                break;
            }
            case R.id.spinnerArriveYear:
            {
                Log.i("Info", "spinnerArriveYear");
                m_yearArrive = Integer.parseInt(adapterView.getSelectedItem().toString());
                Log.i("Info", String.valueOf(m_yearArrive));
                break;
            }
            case R.id.spinnerLeftDay:
            {
                Log.i("Info", "spinnerLeftDay");
                m_dayLeft = Integer.parseInt(adapterView.getSelectedItem().toString());
                Log.i("Info", String.valueOf(m_dayLeft));
                break;
            }
            case R.id.spinnerLeftMonth:
            {
                Log.i("Info", "spinnerLeftMonth");
                m_monthLeft = Integer.parseInt(adapterView.getSelectedItem().toString());
                Log.i("Info", String.valueOf(m_monthLeft));
                break;
            }
            case R.id.spinnerLeftYear:
            {
                Log.i("Info", "spinnerLeftYear");
                m_yearLeft = Integer.parseInt(adapterView.getSelectedItem().toString());
                Log.i("Info", String.valueOf(m_yearLeft));
                break;
            }
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void InitializeDays()
    {

        Integer currentDay=calendar.get(Calendar.DATE);
        ArrayList<String> days = new ArrayList<>();
        for(Integer i=currentDay;i<=31;i++)
        {
            days.add(i.toString());
        }

        arriveSpinnerDay = (Spinner)findViewById(R.id.spinnerArriveDay);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TripSettingsActivity.this,
                android.R.layout.simple_spinner_item,days);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arriveSpinnerDay.setAdapter(adapter);
        arriveSpinnerDay.setOnItemSelectedListener(this);

        leftSpinnerDay = (Spinner)findViewById(R.id.spinnerLeftDay);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(TripSettingsActivity.this,
                android.R.layout.simple_spinner_item,days);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leftSpinnerDay.setAdapter(adapter2);
        leftSpinnerDay.setOnItemSelectedListener(this);
    }

    private void InitializeMonths()
    {
        Integer currentMonth=calendar.get(Calendar.MONTH) +1 ;
        ArrayList<String> months = new ArrayList<>();
        for(Integer i=currentMonth;i<=12;i++)
        {
            months.add(i.toString());
        }

        arriveSpinnerMonth = (Spinner)findViewById(R.id.spinnerArriveMonth);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TripSettingsActivity.this,
                android.R.layout.simple_spinner_item,months);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arriveSpinnerMonth.setAdapter(adapter);
        arriveSpinnerMonth.setOnItemSelectedListener(this);

        leftSpinnerMonth = (Spinner)findViewById(R.id.spinnerLeftMonth);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(TripSettingsActivity.this,
                android.R.layout.simple_spinner_item,months);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leftSpinnerMonth.setAdapter(adapter2);
        leftSpinnerMonth.setOnItemSelectedListener(this);
    }

    private void InitializeYears()
    {
        Integer currentYear=calendar.get(Calendar.YEAR);
        ArrayList<String> years = new ArrayList<>();
        for(Integer i=currentYear;i<=2030;i++)
        {
            years.add(i.toString());
        }

        arriveSpinnerYear = (Spinner)findViewById(R.id.spinnerArriveYear);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TripSettingsActivity.this,
                android.R.layout.simple_spinner_item,years);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arriveSpinnerYear.setAdapter(adapter);
        arriveSpinnerYear.setOnItemSelectedListener(this);

        leftSpinnerYear = (Spinner)findViewById(R.id.spinnerLeftYear);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(TripSettingsActivity.this,
                android.R.layout.simple_spinner_item,years);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leftSpinnerYear.setAdapter(adapter2);
        leftSpinnerYear.setOnItemSelectedListener(this);
    }

    public void onContinueButtonClicked(View v)
    {
    Intent intent = new Intent(this, PartnerSettingsActivity.class);
    startActivity(intent);
    }

}





