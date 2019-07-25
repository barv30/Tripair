package com.example.tripair;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private String userEmail;
    private String userPassword;
    private FirebaseAuth m_Auth;

    public void getEmailAndPassword ()
    {
        EditText userEmailObj = (EditText) findViewById(R.id.userEmail);
        userEmail = userEmailObj.getText().toString();
        EditText userPasswordObj= (EditText) findViewById(R.id.userPassword);
        userPassword = userPasswordObj.getText().toString();
        Log.i("Info","EMAIL:" + userEmail);
        Log.i("Info","PASSWORD:" + userPassword);
    }

    public void onSignInClicked(View v){

        getEmailAndPassword();
        m_Auth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("Info", "signInWithEmail:success");
                            FirebaseUser user = m_Auth.getCurrentUser();
                         //   updateUI(user); move to home page
                        } else {
                            FirebaseAuthException e = (FirebaseAuthException )task.getException();
                            Log.e("LoginActivity", "Failed signing in", e);
                            Context context = getApplicationContext();
                            CharSequence text = e.getMessage();
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            // If sign in fails, display a message to the user.
                            Log.i("Info", "signInWithEmail:failure", task.getException());

                           // updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void onSignUpClicked(View v)
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
                           // updateUI(user); move to edit profile
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

                        // ...
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_Auth = FirebaseAuth.getInstance();

    }
}

