package com.example.makeupmidterm_siddhantjain;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "EmailPassword";
    //  Firebase ref;
    EditText mEmailField, mPasswordField, mNameTV;
    EditText password;
    Button loginButton;
    Button createAccountButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mEmailField = (EditText) findViewById(R.id.editText_email);
        mPasswordField = (EditText) findViewById(R.id.editText_password);
        mNameTV = (EditText) findViewById(R.id.editText_Name);
        Button buttonSignUp = (Button) findViewById(R.id.button_signu);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("sign1", mEmailField.getText().toString());
                Log.d("sign2", mPasswordField.getText().toString());
                if (mEmailField.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, "email field is empty", Toast.LENGTH_LONG).show();
                } else if (!isValidEmailAddress(mEmailField.getText().toString())) {
                    Toast.makeText(SignUpActivity.this, "email is not in valid format", Toast.LENGTH_LONG).show();
                } else if (mPasswordField.getText().toString().length() < 6) {
                    Toast.makeText(SignUpActivity.this, "Password is too short", Toast.LENGTH_LONG).show();
                } else if (mPasswordField.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, "Password field is empty", Toast.LENGTH_LONG).show();
                } else if (mNameTV.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, "Name field is empty", Toast.LENGTH_LONG).show();
                } else {

                    createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString(), mNameTV.getText().toString());
                }
            }
        });

        findViewById(R.id.button_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    writeUser(user.getUid(), mEmailField.getText().toString(), mNameTV.getText().toString());
                    finish();
                    Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(i);

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

    }

    private void writeUser(String userid, String email, String name) {
        Log.d("write", email);
        Log.d("write", name);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        User user = new User(name, email);

        mDatabase.child("users").child(userid).setValue(user);


    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void createAccount(String email, String password, String name) {
        Log.d(TAG, "createAccount:" + email);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@android.support.annotation.NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Account already exists", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Account successfully created", Toast.LENGTH_LONG).show();


                        }


                    }


                });
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
   /* @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_signu) {
            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString(), mNameTV.getText().toString());
        } /*else if (i == R.id.email_sign_in_button) {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (i == R.id.sign_out_button) {
            signOut();
        }*/
    //}
}
