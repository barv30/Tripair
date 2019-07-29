package com.example.tripair;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class SettingProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinnerDay;
    private Spinner spinnerMonth;
    private Spinner spinnerYear;
    private Spinner languagesSpinner1;
    private Spinner languagesSpinner2;
    String selectedLanguage1;
    String selectedLanguage2;
    private String m_userFirstName;
    private String m_userLastName;
    private Date m_userBirthDate;
    private boolean m_isUserSmoking;
    private String m_userGender;
    private String m_aboutUser;
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
                selectedLanguage1 = adapterView.getSelectedItem().toString();
                Log.i("Info", selectedLanguage1);
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
                break;
            }
            case R.id.monthSpinner:
            {
                break;
            }
            case R.id.yearSpinner:
            {
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
        if(isValid != "")
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
        String userFirstNameText = userFirstName.getText().toString();
        EditText userLastName = (EditText) findViewById(R.id.LastName);
        String userLastNameText = userLastName.getText().toString();

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.groupGender);
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton)radioGroup.findViewById(radioButtonID);
        if(radioButton ==  null) {
            msgIsValid= "You need to choose gender!";
        }
        else if (userFirstNameText == null || isContainsNumbers(userFirstNameText))
        {
          msgIsValid = "first name invalid! Only letters allowed";
        }
        else if(userLastNameText == null || isContainsNumbers(userLastNameText))
        {
            msgIsValid = "last name invalid! Only letters allowed";
        }
        else if(selectedLanguage1 == null || selectedLanguage2 == null)
        {
            msgIsValid = "you need to choose languages!";
        }


        // get values from spinners and check if ok
        if(msgIsValid.equals(""))
        {
            m_userFirstName = userFirstNameText;
            m_userLastName = userLastNameText;
            m_userGender = radioButton.getText().toString();

        }
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
        return languages;
    }

}


