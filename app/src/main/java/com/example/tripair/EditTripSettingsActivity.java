package com.example.tripair;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.example.dataUser.User;
import com.example.recycleViewPack.ContactPOJO;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditTripSettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference();

    private Spinner arriveSpinnerDay;
    private Spinner arriveSpinnerMonth;
    private Spinner arriveSpinnerYear;
    private Spinner leftSpinnerDay;
    private Spinner leftSpinnerMonth;
    private Spinner leftSpinnerYear;

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

    private int m_tripToEditPosition;
    private Trip m_tripEditMode;
    private ArrayList<ContactPOJO> favPartnerOfTripEdit;
    private Partner partnerSettingOfTripEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip_settings);
        Intent intent = getIntent();
        m_tripToEditPosition = intent.getIntExtra("tripPosition",-1);
        m_user = (User) intent.getSerializableExtra("user");
        m_uid = m_user.getId();
        m_tripEditMode = (Trip) intent.getSerializableExtra("trip");

        TextView lineText = findViewById(R.id.headLine);
        CheckBox checkBoxArt = findViewById(R.id.checkBoxArt);
        CheckBox checkBoxNature = findViewById(R.id.checkBoxNature);
        CheckBox checkBoxRelax = findViewById(R.id.checkBoxRelax);
        for (String checkboxString : m_tripEditMode.getStyleTrip())
        {
            if (checkboxString.equals("Art and History"))
            {
                checkBoxArt.setChecked(true);
            }
           else if (checkboxString.equals("Tracks and Nature"))
            {
                checkBoxNature.setChecked(true);
            }
           else if (checkboxString.equals("Relax"))
            {
                checkBoxRelax.setChecked(true);
            }
        }
        lineText.setText("Edit Your Trip to " + m_tripEditMode.getCountry() + "," + m_tripEditMode.getCity());
        partnerSettingOfTripEdit = m_tripEditMode.getPartner();
        favPartnerOfTripEdit = m_tripEditMode.getFavPartner();


        Date currentDate= calendar.getTime();
        calendar.setTime(currentDate);
        typeTripArr = new ArrayList<>();

        InitializeDays();
        InitializeMonths();
        InitializeYears();


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch(adapterView.getId())
        {
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditTripSettingsActivity.this,
                android.R.layout.simple_spinner_item,days);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arriveSpinnerDay.setAdapter(adapter);
        arriveSpinnerDay.setOnItemSelectedListener(this);

        leftSpinnerDay = (Spinner)findViewById(R.id.spinnerLeftDay);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(EditTripSettingsActivity.this,
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditTripSettingsActivity.this,
                android.R.layout.simple_spinner_item,months);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arriveSpinnerMonth.setAdapter(adapter);
        arriveSpinnerMonth.setOnItemSelectedListener(this);

        leftSpinnerMonth = (Spinner)findViewById(R.id.spinnerLeftMonth);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(EditTripSettingsActivity.this,
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditTripSettingsActivity.this,
                android.R.layout.simple_spinner_item,years);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arriveSpinnerYear.setAdapter(adapter);
        arriveSpinnerYear.setOnItemSelectedListener(this);

        leftSpinnerYear = (Spinner)findViewById(R.id.spinnerLeftYear);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(EditTripSettingsActivity.this,
                android.R.layout.simple_spinner_item,years);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leftSpinnerYear.setAdapter(adapter2);
        leftSpinnerYear.setOnItemSelectedListener(this);
    }


    private Trip initTrip() {
        Trip trip = new Trip();
        trip.setM_ownerID(m_uid);
        trip.setCountry(m_tripEditMode.getCountry());
        trip.setCity(m_tripEditMode.getCity());
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
        CheckBox checkBoxOneWay  = (CheckBox)findViewById(R.id.oneway_ch);
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
        else if ((m_dayLeft == 1 && m_monthLeft == 1 && m_yearLeft == currentYear) && !checkBoxOneWay.isChecked())
        {
            return "You have to enter left date or click on One-Way option.";
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
            if(checkBoxOneWay.isChecked() ) //the user doesn't choose left date
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
            trip.setKeyOfCountriesFireBase(m_tripEditMode.getKeyOfCountriesFireBase());
            trip.setFavPartner(favPartnerOfTripEdit);
            trip.setPartner(partnerSettingOfTripEdit);
            m_user.getAllTrips().updatePartnerInSpecificTrip(m_tripToEditPosition,partnerSettingOfTripEdit);
            m_user.getAllTrips().updateFavPartnersInSpecificTrip(m_tripToEditPosition,favPartnerOfTripEdit);
            mRef.child("usersProfile").child(m_uid).child("allTrips").child("tripList").child(Integer.toString(m_tripToEditPosition)).setValue(trip);
            mRef.child("Countries").child(tripCountryKey).child(tripCityKey).child(m_tripEditMode.getKeyOfCountriesFireBase()).setValue(trip);

            Intent intent = new Intent(this, AllTripsActivity.class);
            intent.putExtra("user", m_user);
            startActivity(intent);
            this.finish();
        }
    }
    public void onOneWayClicked(View v)
    {
        Spinner spinnerDayLeft = (Spinner)findViewById(R.id.spinnerLeftDay);
        Spinner spinnerMonthLeft = (Spinner)findViewById(R.id.spinnerLeftMonth);
        Spinner spinnerYearLeft = (Spinner)findViewById(R.id.spinnerLeftYear);

        boolean checked = ((CheckBox) v).isChecked();
        if(checked)
        {
            spinnerDayLeft.setEnabled(false);
            spinnerMonthLeft.setEnabled(false);
            spinnerYearLeft.setEnabled(false);
        }
        else
        {
            spinnerDayLeft.setEnabled(true);
            spinnerMonthLeft.setEnabled(true);
            spinnerYearLeft.setEnabled(true);
        }
    }
}





