package com.example.tripair;
import android.content.Context;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class PartnerSettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinnerLanguage;
    private String selectedLanguage;
    private int m_minAge;
    private int m_maxAge;
    private boolean m_isSmoking;
    private String m_language;
    private String m_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_settings);
        InitializeLanguages();
    }


    public void InitializeLanguages()
    {
        ArrayList<String> languages = new ArrayList<String>();
        languages.add("Hebrew");
        languages.add("English");
        languages.add("Arabic");
        languages.add("Russian");
        languages.add("Spanish");
        languages.add("Chinese");
        languages.add("Japanese");
        languages.add("Turkish");
        languages.add("French");
        languages.add("German");
        languages.add("Italian");

        Collections.sort(languages);

        spinnerLanguage = (Spinner)findViewById(R.id.languageSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PartnerSettingsActivity.this,
                android.R.layout.simple_spinner_item,languages);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(adapter);
        spinnerLanguage.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


        if(adapterView.getId() == R.id.languageSpinner) {
            Log.i("Info", "spinnerLanguages");
            selectedLanguage = adapterView.getSelectedItem().toString();
            Log.i("Info", selectedLanguage);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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

        else
        {

        }
    }

    private String checkIfInputFromUserIsValid() {
        EditText minAge = (EditText) findViewById(R.id.minAge);
        if(minAge.getText().toString() == null)
        {
            return "You have to enter minimum age!";
        }
        int minAgeNumber = Integer.parseInt(minAge.getText().toString());
        EditText maxAge = (EditText) findViewById(R.id.maxAge);
        if(maxAge.getText().toString() == null)
        {
            return "You have to enter maximum age!";
        }
        int maxAgeNumber = Integer.parseInt(maxAge.getText().toString());
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
            if(radioButtonSmoking.getText().toString() == "I don't care")
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
}


