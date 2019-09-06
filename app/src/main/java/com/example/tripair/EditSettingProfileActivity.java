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
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class EditSettingProfileActivity extends AppCompatActivity {

    private String m_userFirstName;
    private String m_userLastName;
    private boolean m_isUserSmoking = false;
    private String m_uid_user;
    private String m_image;
    FirebaseDatabase database =  FirebaseDatabase.getInstance();
    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private Button btnChoose;
    private ImageView imageView;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    String imageUrl = "https://firebasestorage.googleapis.com/v0/b/tripair-be218.appspot.com/o/profilePic%2FProfilePicture.png?alt=media&token=eb726118-9758-445b-9c77-c017a19ab66d";
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private User m_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_setting_profile);
        Intent intent=getIntent();
        m_user = (User)intent.getSerializableExtra("user");
        m_uid_user = m_user.getId();
        EditText firstNameText = findViewById(R.id.firstName);
        firstNameText.setHint(m_user.getFirstName());
        EditText lastNameText = findViewById(R.id.LastName);
        lastNameText.setHint(m_user.getLastName());
        CheckBox smokingBox = findViewById(R.id.smokingBox);
        if (m_user.isSmoking())
        {
            smokingBox.setChecked(true);
        }
        else
        {
            smokingBox.setChecked(false);
        }

        initializedPicture();

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





    private String checkIfInputFromUserIsValid() {
        EditText userFirstName = (EditText) findViewById(R.id.firstName);
        String userFirstNameText = userFirstName.getText().toString();
        EditText userLastName = (EditText) findViewById(R.id.LastName);
        String userLastNameText = userLastName.getText().toString();
        CheckBox checkBoxSmoking = (CheckBox) findViewById(R.id.smokingBox);

        if (userFirstNameText.equals("") || isContainsNumbers(userFirstNameText))
        {
           return "First name invalid! Only letters allowed";
        }
        else if(userLastNameText.equals("") || isContainsNumbers(userLastNameText))
        {
             return "Last name invalid! Only letters allowed";
        }
        else
            {
            boolean checked = checkBoxSmoking.isChecked();
            if(checked)
            {
                m_isUserSmoking = true;
            }

            m_userFirstName = userFirstNameText;
            m_userLastName = userLastNameText;

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


    public void saveButtonClicked(View v)
    {
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
            m_user.setFirstName(m_userFirstName);
            m_user.setLastName(m_userLastName);
            m_user.setSmoking(m_isUserSmoking);
            //save at database
            DatabaseReference mRef = database.getReference();
            if(filePath != null)
            {
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                StorageReference ref = storageReference.child("images/"+ mAuth.getCurrentUser().getUid());
                ref.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                Toast.makeText(EditSettingProfileActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(EditSettingProfileActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                Task<Uri> urlTask = ref.putFile(filePath).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return ref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            String downloadUri = task.getResult().toString();
                            // need to change
                            m_user.setImgURL(downloadUri);
                            imageUrl = downloadUri;
                            mRef.child("usersProfile").child(m_uid_user).setValue(m_user);
                            moveToAllTrips();
                        }
                    }
                });
            }
            else
            {
                mRef.child("usersProfile").child(m_uid_user).setValue(m_user);
                moveToAllTrips();
            }

        }
    }


        private void moveToAllTrips(){
                // if everything ok - move to home page
                Intent intent = new Intent(this, AllTripsActivity.class);
                intent.putExtra("user", m_user);
                startActivity(intent);
                this.finish();
            }
    }


