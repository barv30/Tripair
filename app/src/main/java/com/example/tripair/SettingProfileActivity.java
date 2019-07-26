package com.example.tripair;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class SettingProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinnerDay;
    private Spinner spinnerMonth;
    private Spinner spinnerYear;
    FirebaseDatabase database =  FirebaseDatabase.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile);
        InitializeDays();
        InitializeMonths();
        InitializeYears();

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }



        public void InitializeDays()
        {
            ArrayList<String> days = new ArrayList<>();
            for(Integer i=1;i<=31;i++)
            {
                days.add(i.toString());
            }

            spinnerDay = (Spinner)findViewById(R.id.daySpinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(SettingProfileActivity.this,
                    android.R.layout.simple_spinner_item,days);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDay.setAdapter(adapter);
            spinnerDay.setOnItemSelectedListener(this);
        }

    public void InitializeMonths()
    {
        ArrayList<String> months = new ArrayList<>();
        for(Integer i=1;i<=12;i++)
        {
            months.add(i.toString());
        }

        spinnerMonth = (Spinner)findViewById(R.id.monthSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SettingProfileActivity.this,
                android.R.layout.simple_spinner_item,months);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapter);
        spinnerMonth.setOnItemSelectedListener(this);
    }

    public void InitializeYears()
    {
        ArrayList<String> years = new ArrayList<>();
        for(Integer i=1960;i<=2019;i++)
        {
            years.add(i.toString());
        }

        spinnerYear = (Spinner)findViewById(R.id.yearSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SettingProfileActivity.this,
                android.R.layout.simple_spinner_item,years);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(adapter);
        spinnerYear.setOnItemSelectedListener(this);
    }

    public void saveButtonClicked(View v)
    {
        boolean isValidInput= true;
        String isValid = checkIfInputFromUserIsValid();
        if(isValid != null)
        {
            isValidInput = false;
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            CharSequence text = isValid;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }
        else { // all the input from user is valid
            //save at database
            DatabaseReference mRef = database.getReference();

            // if everything ok - move to next page
            Intent intent = new Intent(this, TripSettingsActivity.class);
            startActivity(intent);
        }
    }

    private String checkIfInputFromUserIsValid(){
        String msgIsValid = "";
        EditText userFirstName = (EditText) findViewById(R.id.firstName);
        EditText userLastName = (EditText) findViewById(R.id.LastName);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.groupGender);
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton)radioGroup.findViewById(radioButtonID);
        if(radioButton ==  null) {
            msgIsValid= "You need to choose gender!";
        }
        else if (userFirstName.toString() == null || isContainsNumbers(userFirstName.toString()))
        {
          msgIsValid = "first name invalid!";
        }
        else if(userLastName.toString() == null || isContainsNumbers(userLastName.toString()))
        {
            msgIsValid = "last name invalidS!";
        }


        // get values from spinners and check if ok
        return msgIsValid;
    }

    private boolean isContainsNumbers(String str)
    {
        char[] chars = str.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }
}


