package com.example.tripair;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dataUser.Partner;
import com.example.dataUser.Trip;
import com.example.dataUser.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class PartnerSettingsActivity extends AppCompatActivity{
    FirebaseDatabase database =  FirebaseDatabase.getInstance();
    private int m_minAge;
    private int m_maxAge;
    private boolean m_isSmoking;
    private String m_gender;
    private String m_uid;
     String m_tripCountryKey;
     String m_tripCityKey;
    User m_user;
    Trip m_trip;
    private int m_tripToEditPosition;
    private String m_mode_edit;
    String keyOfCountriesFireBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_settings);
        Intent intent =getIntent();
        m_mode_edit = intent.getStringExtra("isEditMode");
        m_tripToEditPosition = intent.getIntExtra("tripPosition",-1);
        m_trip = (Trip) intent.getSerializableExtra("trip");
        m_tripCountryKey = intent.getStringExtra("tripCountryKey");
        m_tripCityKey = intent.getStringExtra("tripCityKey");
        m_user = (User) intent.getSerializableExtra("user");
        m_uid = m_user.getId();

        if (m_mode_edit!= null && m_mode_edit.equals("edit") && m_tripToEditPosition != -1)
        {
            EditText minText = findViewById(R.id.minAge);
            EditText maxText = findViewById(R.id.maxAge);
            RadioButton female =  findViewById(R.id.female);
            RadioButton male =  findViewById(R.id.male);
            RadioButton no_metter =  findViewById(R.id.noMatter);
            RadioButton dontWantSmoking =  findViewById(R.id.dontWant);
            RadioButton dontCareSmoking =  findViewById(R.id.dontCare);
            Partner partner = m_user.getAllTrips().getTripByPosition(m_tripToEditPosition).getPartner();
            minText.setText(Integer.toString(partner.getMinAge()));
            maxText.setText(Integer.toString(partner.getMaxAge()));
            if (partner.isSmoking()) {
                dontCareSmoking.setChecked(true);
                dontWantSmoking.setChecked(false);
            }
            else {
                dontCareSmoking.setChecked(false);
                dontWantSmoking.setChecked(true);
            }

            if (partner.getGender().equals("Female"))
            {
                female.setChecked(true);
                male.setChecked(false);
                no_metter.setChecked(false);
            }
            else if (partner.getGender().equals("Male"))
            {
                female.setChecked(false);
                male.setChecked(true);
                no_metter.setChecked(false);
            }
            else if (partner.getGender().equals("no matter"))
            {
                female.setChecked(false);
                male.setChecked(false);
                no_metter.setChecked(true);
            }
                keyOfCountriesFireBase = m_trip.getKeyOfCountriesFireBase();

        }

    }

    private Partner initPartner()
    {
        Partner newPartner= new Partner();
        newPartner.setGender(m_gender);
        newPartner.setMaxAge(m_maxAge);
        newPartner.setMinAge(m_minAge);
        newPartner.setSmoking(m_isSmoking);
        return newPartner;
    }


    private String checkIfInputFromUserIsValid() {
        EditText minAge = (EditText) findViewById(R.id.minAge);
        if(minAge.getText().toString().equals( "") )
        {
            return "You have to enter minimum age!";
        }
        EditText maxAge = (EditText) findViewById(R.id.maxAge);
        if(maxAge.getText().toString().equals(""))
        {
            return "You have to enter maximum age!";
        }
        int minAgeNumber = Integer.parseInt(minAge.getText().toString());
        int maxAgeNumber = Integer.parseInt(maxAge.getText().toString());
        if(maxAgeNumber > 120)
        {
            return "You have to enter maximum age below 120!";
        }
        RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.genderGroup);
        int radioButtonGenderID = radioGroup1.getCheckedRadioButtonId();
        RadioButton radioButtonGender = (RadioButton)radioGroup1.findViewById(radioButtonGenderID);
        RadioGroup radioGroup2 = (RadioGroup) findViewById(R.id.smokingGroup);
        int radioButtonSmokingID = radioGroup2.getCheckedRadioButtonId();
        RadioButton radioButtonSmoking = (RadioButton)radioGroup2.findViewById(radioButtonSmokingID);
        if(radioButtonGender ==  null) {
            return "You have to choose gender!";
        }

        else if(radioButtonSmoking == null)
        {
            return "You have to choose smoking option!";
        }
        else if(minAgeNumber > maxAgeNumber)
        {
            return "You have to choose max age that bigger than min age!";
        }
        else
        {
            m_gender = radioButtonGender.getText().toString();
            if((radioButtonSmoking.getText().toString()).equals("I don't care"))
            {
                m_isSmoking=true;
            }
            else
            {
                m_isSmoking=false;
            }
            m_minAge = minAgeNumber;
            m_maxAge = maxAgeNumber;


        }
        return null;
    }

    public void onSaveAndFindButtonClicked(View v)
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
            //save at database
            Partner settingOfPartner = initPartner();
            DatabaseReference mRef = database.getReference();

            if (m_mode_edit!= null && m_mode_edit.equals("edit") && m_tripToEditPosition != -1)
            {
                m_user.getAllTrips().updatePartnerInSpecificTrip(m_tripToEditPosition, settingOfPartner);
                keyOfCountriesFireBase = m_trip.getKeyOfCountriesFireBase();

            }
            else {

                keyOfCountriesFireBase  = mRef.child("Countries").child(m_tripCountryKey).child(m_tripCityKey).push().getKey();
                m_trip.setKeyOfCountriesFireBase(keyOfCountriesFireBase);
                m_user.getAllTrips().updateTripList(m_trip);
            }

            m_trip.setPartner(settingOfPartner);
            mRef.child("usersProfile").child(m_uid).setValue(m_user);
            mRef.child("Countries").child(m_tripCountryKey).child(m_tripCityKey).child(keyOfCountriesFireBase).setValue(m_trip);

            Intent intent = new Intent(this, AllTripsActivity.class);
            intent.putExtra("user", m_user);
            startActivity(intent);
            this.finish();
        }
    }
}


