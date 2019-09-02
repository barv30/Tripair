package com.example.tripair;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dataUser.User;
import com.google.android.gms.tasks.OnCompleteListener;
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

public class MainActivity extends AppCompatActivity {
    private String userEmail;
    private String userPassword;
    private FirebaseAuth m_Auth;
    FirebaseDatabase database =  FirebaseDatabase.getInstance();



    public boolean getEmailAndPassword ()
    {
        EditText userEmailObj = (EditText) findViewById(R.id.userEmail);
        userEmail = userEmailObj.getText().toString();
        EditText userPasswordObj= (EditText) findViewById(R.id.userPassword);
        userPassword = userPasswordObj.getText().toString();
        Log.i("Info","EMAIL:" + userEmail);
        Log.i("Info","PASSWORD:" + userPassword);
        if(userEmail.equals("") || userPassword.equals(""))
        {
            return false;
        }
        return true;
    }

    public void onSignInClicked(View v){

        DatabaseReference mRef =  database.getReference();
        if (getEmailAndPassword()) {
            m_Auth.signInWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.i("Info", "signInWithEmail:success");
                                FirebaseUser user = m_Auth.getCurrentUser();
                                String uid = user.getUid();
                                ValueEventListener UserListener = new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        User user;
                                        // Get Post object and use the values to update the UI
                                        if (dataSnapshot.child("usersProfile").child(uid).exists()) {
                                            user = dataSnapshot.child("usersProfile").child(uid).getValue(User.class);
                                            openAllTripsActivity(uid, user);
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                };
                                mRef.addValueEventListener(UserListener);

                                //  move to home page

                            } else {
                                FirebaseAuthException e = (FirebaseAuthException) task.getException();
                                Log.e("LoginActivity", "Failed signing in", e);
                                Context context = getApplicationContext();
                                CharSequence text = e.getMessage();
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                // If sign in fails, display a message to the user.
                                Log.i("Info", "signInWithEmail:failure", task.getException());

                            }

                        }
                    });
        }
        else
        {
            Context context = getApplicationContext();
            CharSequence text = "You have to enter email and password";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    public void onSignUpClicked(View v)
    {
        if(getEmailAndPassword()) {
            m_Auth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.i("Info", "createUserWithEmail:success");
                                FirebaseUser user = m_Auth.getCurrentUser();
                                String uid = user.getUid();
                                openSettingsProfileActivity(uid);// move to edit profile
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.i("Info", "createUserWithEmail:failure", task.getException());
                                FirebaseAuthException e = (FirebaseAuthException) task.getException();
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
        else {
            Context context = getApplicationContext();
            CharSequence text = "You have to enter email and password";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    private void openSettingsProfileActivity(String uid) {
        Intent intent = new Intent(this,SettingProfileActivity.class);
        intent.putExtra("userUid" , uid);
        intent.putExtra("userEmail" , userEmail);
        startActivity(intent);
    }

    private void openAllTripsActivity(String uid, User user)
    {
        Intent intent = new Intent(this, AllTripsActivity.class);
        intent.putExtra("userUid" , uid);
        intent.putExtra("user" , user);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_Auth = FirebaseAuth.getInstance();
    }
}

