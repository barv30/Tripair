package com.example.tripair;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dataUser.Partner;
import com.example.dataUser.Trip;
import com.example.dataUser.TripManager;
import com.example.dataUser.User;
import com.example.recycleViewPack.ContactPOJO;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

public class TripSettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference();
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
    private String m_uid;
    private User m_user;

    private ArrayList<String> countries = new ArrayList<>();
    private ArrayList<ArrayList<String>> cities = new ArrayList<>();

    private ArrayList<ContactPOJO> favPartnerOfTripEdit;
    private Partner partnerSettingOfTripEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_settings);
        Intent intent = getIntent();
        m_uid = intent.getStringExtra("userUid");
        m_user = (User) intent.getSerializableExtra("user");

        TextView lineText = findViewById(R.id.headLine);

        Date currentDate= calendar.getTime();
        calendar.setTime(currentDate);
        typeTripArr = new ArrayList<>();
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
        for(Integer i=1;i<=31;i++)
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
        ArrayList<String> months = new ArrayList<>();
        for(Integer i=1;i<=12;i++)
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


    private Trip initTrip() {
        Trip trip = new Trip();
        trip.setM_ownerID(m_uid);
        trip.setCountry(m_selectedCountry);
        trip.setCity(m_selectedCity);
        trip.setArriveDay(m_dayArrive);
        trip.setArriveMonth(m_monthArrive);
        trip.setArriveYear(m_yearArrive);
        trip.setStyleTrip(typeTripArr);
        if(m_dayLeft!=0 && m_monthLeft!=0 && m_yearLeft!=0)
        {
            trip.setLeftDay(m_dayLeft);
            trip.setLeftYear(m_yearLeft);
            trip.setLeftMonth(m_monthLeft);
        }
        return trip;
    }

    private String checkIfInputFromUserIsValid()
    {
        Integer currentDay=calendar.get(Calendar.DATE);
        Integer currentMonth=calendar.get(Calendar.MONTH) +1 ;
        Integer currentYear=calendar.get(Calendar.YEAR);
        CheckBox checkBoxRelax = (CheckBox) findViewById(R.id.checkBoxRelax);
        CheckBox checkBoxTracks = (CheckBox) findViewById(R.id.checkBoxNature);
        CheckBox checkBoxArt = (CheckBox) findViewById(R.id.checkBoxArt);
        boolean checkedRelax = checkBoxRelax.isChecked();
        boolean checkedArt = checkBoxArt.isChecked();
        boolean checkedTracks = checkBoxTracks.isChecked();
        if(!checkedRelax && !checkedTracks && !checkedArt)
        {
            return "You have to choose style trip!";
        }
        else if (m_yearLeft < m_yearArrive &&m_yearLeft !=currentYear || m_yearLeft == m_yearArrive && m_monthLeft<m_monthArrive || m_yearLeft == m_yearArrive &&m_dayLeft<m_dayArrive && m_monthLeft==m_monthArrive)
        {
            return "Your left date is invalid!";
        }
        else  if(m_dayArrive < currentDay && m_monthArrive == currentMonth &&  m_yearArrive ==currentYear|| m_monthArrive <currentMonth && m_yearArrive == currentYear)
        {
            return "Your arrive date is invalid!";
        }
        else
        {
            if(checkedArt)
            {
                typeTripArr.add("Art and History");
            }
            if(checkedRelax)
            {
                typeTripArr.add("Relax");

            }
            if (checkedTracks)
            {
                typeTripArr.add("Tracks and Nature");
            }
            if(m_dayLeft == 1 && m_monthLeft == 1 && m_yearLeft == currentYear) //the user doesn't choose left date
            {
                m_yearLeft=0;
                m_dayLeft=0;
                m_monthLeft=0;
            }


        }


        return null;
    }
    public void onContinueButtonClicked(View v)
    {
        String isValid = checkIfInputFromUserIsValid();
        if(isValid!= null)
        {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            CharSequence text = isValid;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        else {
            Trip trip = initTrip();
            String tripCountryKey = trip.getCountry();
            String tripCityKey = trip.getCity();


            Intent intent = new Intent(this, PartnerSettingsActivity.class);
            intent.putExtra("trip",trip);
            intent.putExtra("userUid", m_uid);
            intent.putExtra("tripCountryKey", tripCountryKey);
            intent.putExtra("tripCityKey", tripCityKey);
            intent.putExtra("user", m_user);
            startActivity(intent);
            this.finish();
        }
    }

}





