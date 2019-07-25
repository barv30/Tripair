package com.example.tripair;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private String userEmail;
    private String userPassword;


    public void onSignInClicked(View v){

       EditText userEmailObj = (EditText) findViewById(R.id.userEmail);
       userEmail = userEmailObj.getText().toString();
       EditText userPasswordObj= (EditText) findViewById(R.id.userPassword);
       userPassword = userPasswordObj.getText().toString();
       Log.i("Info","EMAIL:" + userEmail);
       Log.i("Info","PASSWORD:" + userPassword);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

