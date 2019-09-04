package com.example.tripair;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dataUser.TripManager;
import com.example.dataUser.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;


public class SingUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
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
    private String m_uid_user;
    private String m_image;
    FirebaseDatabase database =  FirebaseDatabase.getInstance();
    FirebaseAuth m_Auth=FirebaseAuth.getInstance();
    User m_userInput;
    private Button btnChoose;
    private ImageView imageView;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage;
    StorageReference storageReference;
    private String userEmail;
    private String userPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        InitializeDays();
        InitializeMonths();
        InitializeYears();
        InitializeLanguages();
        initializedPicture();

    }


    public void saveButtonClicked(View v)
    {
        onSignUpClicked();
        String isValid = checkIfInputFromUserIsValid();

        if(isValid != null)
        {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            CharSequence text = isValid;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }
        else {
            TripManager alltrips = new TripManager();
            ArrayList<String> languagesArr = new ArrayList<>();
            languagesArr.add(selectedLanguageNative);
            if(!selectedLanguage2.equals("None"))
            {
                languagesArr.add(selectedLanguage2);
            }
            m_userInput = new User(m_userFirstName,m_userLastName,m_dayBirth,m_monthBirth,m_yearBirth,m_userGender,languagesArr,m_isUserSmoking,m_aboutUser, alltrips,"");

            //save at database
            DatabaseReference mRef = database.getReference();
            mRef.child("usersProfile").child(m_uid_user).setValue(m_userInput);


            if(filePath != null)
            {
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                StorageReference ref = storageReference.child("images/"+ m_Auth.getCurrentUser().getUid());
                ref.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                Toast.makeText(SingUpActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(SingUpActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded "+(int)progress+"%");
                            }
                        });
            }

            // if everything ok - move to home page
            Intent intent = new Intent(this, AllTripsActivity.class);
            intent.putExtra("userUid", m_uid_user);
            intent.putExtra("user", m_userInput);
            startActivity(intent);
        }
    }

    public void getEmailAndPassword ()
    {
        EditText userEmailObj = (EditText) findViewById(R.id.userEmail);
        userEmail = userEmailObj.getText().toString();
        EditText userPasswordObj= (EditText) findViewById(R.id.userPassword);
        userPassword = userPasswordObj.getText().toString();
        Log.i("Info","EMAIL:" + userEmail);
        Log.i("Info","PASSWORD:" + userPassword);
    }

    public void onSignUpClicked()
    {
        getEmailAndPassword();
        m_Auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("Info", "createUserWithEmail:success");
                            FirebaseUser user = m_Auth.getCurrentUser();
                            m_uid_user = user.getUid();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("Info", "createUserWithEmail:failure", task.getException());
                            FirebaseAuthException e = (FirebaseAuthException )task.getException();
                            Log.e("LoginActivity", "Failed signing up", e);
                            Context context = getApplicationContext();
                            CharSequence text = e.getMessage();
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }
                });
    }

    private void initializedPicture()
    {
        btnChoose = (Button) findViewById(R.id.btnChoose);
        imageView = (ImageView) findViewById(R.id.imgView);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
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
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(SingUpActivity.this,
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SingUpActivity.this,
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SingUpActivity.this,
                android.R.layout.simple_spinner_item,years);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(adapter);
        spinnerYear.setOnItemSelectedListener(this);
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SingUpActivity.this,
                android.R.layout.simple_spinner_item,languages);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languagesSpinner1.setAdapter(adapter);
        languagesSpinner1.setOnItemSelectedListener(this);

        languagesSpinner2 = (Spinner)findViewById(R.id.languageSpinner2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(SingUpActivity.this,
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


