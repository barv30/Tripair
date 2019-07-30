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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dataUser.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;


public class SettingProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinnerDay;
    private Spinner spinnerMonth;
    private Spinner spinnerYear;
    private Spinner languagesSpinner1;
    private Spinner languagesSpinner2;
    String selectedLanguageNative;
    String selectedLanguage2;
    private String m_userFirstName;
    private String m_userLastName;
    private boolean m_isUserSmoking = false;
    private String m_userGender;
    private String m_aboutUser;
    private int m_dayBirth;
    private int m_monthBirth;
    private int m_yearBirth;

    FirebaseDatabase database =  FirebaseDatabase.getInstance();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile);
        InitializeDays();
        InitializeMonths();
        InitializeYears();
        InitializeLanguages();

    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View v, int position, long id) {

        switch(adapterView.getId())
        {
            case R.id.languagesSpinner1:
            {
                Log.i("Info", "spinnerLanguages");
                selectedLanguageNative = adapterView.getSelectedItem().toString();
                Log.i("Info", selectedLanguageNative);
                break;
            }
            case R.id.languageSpinner2:
            {
                Log.i("Info", "spinnerLanguages");
                selectedLanguage2 = adapterView.getSelectedItem().toString();
                Log.i("Info", selectedLanguage2);
                break;
            }
            case R.id.daySpinner:
            {
                Log.i("Info", "spinnerLanguages");
                m_dayBirth =Integer.parseInt( adapterView.getSelectedItem().toString());
                Log.i("Info", String.valueOf(m_dayBirth));
                break;
            }
            case R.id.monthSpinner:
            {
                Log.i("Info", "spinnerLanguages");
                m_monthBirth =Integer.parseInt( adapterView.getSelectedItem().toString());
                Log.i("Info", String.valueOf(m_monthBirth));
                break;
            }
            case R.id.yearSpinner:
            {
                Log.i("Info", "spinnerLanguages");
                m_yearBirth =Integer.parseInt( adapterView.getSelectedItem().toString());
                Log.i("Info", String.valueOf(m_yearBirth));
                break;
            }

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }



    private void InitializeDays()
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

    private void InitializeMonths()
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

    private void InitializeYears()
    {
        ArrayList<String> years = new ArrayList<>();
        for(Integer i=1950;i<=2019;i++)
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

    public void saveButtonClicked(View v) throws ParseException {
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
        else {
            ArrayList<String> languagesArr = new ArrayList<>();
            languagesArr.add(selectedLanguageNative);
            if(!selectedLanguage2.equals("None"))
            {
                languagesArr.add(selectedLanguage2);
            }
            User userInput = new User(m_userFirstName,m_userFirstName,m_dayBirth,m_monthBirth,m_yearBirth,m_userGender,languagesArr,m_isUserSmoking,m_aboutUser);
            //save at database
            DatabaseReference mRef = database.getReference();
            mRef.child("userProfile").push().setValue(userInput);

            // if everything ok - move to next page
            Intent intent = new Intent(this, TripSettingsActivity.class);
            startActivity(intent);
       }
    }

    private String checkIfInputFromUserIsValid() {
        EditText userFirstName = (EditText) findViewById(R.id.firstName);
        String userFirstNameText = userFirstName.getText().toString();
        EditText userLastName = (EditText) findViewById(R.id.LastName);
        String userLastNameText = userLastName.getText().toString();
        EditText userAboutMe = (EditText)findViewById(R.id.aboutMeInput);
        String userAboutMeText = userAboutMe.getText().toString();
        CheckBox checkBoxSmoking = (CheckBox) findViewById(R.id.smokingBox);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.groupGender);
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton)radioGroup.findViewById(radioButtonID);
        if(radioButton ==  null) {
            return "You have to choose gender!";
        }
        else if (userFirstNameText == null || isContainsNumbers(userFirstNameText))
        {
           return "First name invalid! Only letters allowed";
        }
        else if(userLastNameText == null || isContainsNumbers(userLastNameText))
        {
             return "Last name invalid! Only letters allowed";
        }
        else if(selectedLanguageNative.equals("None"))
        {
            return "You have to choose a native language!";
        }

        else
            {
            boolean checked = checkBoxSmoking.isChecked();
            if(checked)
            {
                m_isUserSmoking = true;
            }
            if(userAboutMeText != "")
            {
                m_aboutUser = userAboutMeText;
            }
            m_userFirstName = userFirstNameText;
            m_userLastName = userLastNameText;
            m_userGender = radioButton.getText().toString();


        }
        return null;
    }

    private boolean isContainsNumbers(String str)
    {
        char[] chars = str.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return true;
            }
        }
        return false;
    }

    private void InitializeLanguages()
    {
        ArrayList<String> languages = getLanguagesArr();
        languagesSpinner1 = (Spinner)findViewById(R.id.languagesSpinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SettingProfileActivity.this,
                android.R.layout.simple_spinner_item,languages);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languagesSpinner1.setAdapter(adapter);
        languagesSpinner1.setOnItemSelectedListener(this);

        languagesSpinner2 = (Spinner)findViewById(R.id.languageSpinner2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(SettingProfileActivity.this,
                android.R.layout.simple_spinner_item,languages);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languagesSpinner2.setAdapter(adapter2);
        languagesSpinner2.setOnItemSelectedListener(this);
    }

    private ArrayList<String> getLanguagesArr()
    {
        ArrayList<String> languages = new ArrayList<String>();
        languages.add("None");
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
        return languages;
    }

}


