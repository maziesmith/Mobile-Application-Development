package com.example.inclass_10;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String EXTRA_USER_ID = "USER_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_createNewAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mEmailField = (EditText) findViewById(R.id.editText_email);
                EditText mPasswordField = (EditText) findViewById(R.id.editText_password);
                if (mEmailField.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "email field is empty", Toast.LENGTH_LONG).show();
                } else if (!isValidEmailAddress(mEmailField.getText().toString())) {
                    Toast.makeText(MainActivity.this, "email is not in valid format", Toast.LENGTH_LONG).show();
                } else if (mPasswordField.getText().toString().length() < 6) {
                    Toast.makeText(MainActivity.this, "Password is too short", Toast.LENGTH_LONG).show();
                } else if (mPasswordField.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Password field is empty", Toast.LENGTH_LONG).show();
                } else {
                    signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());

                }
            }
        });
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("main", "onAuthStateChanged:signed_in:" + user.getUid());
                    Log.d("main", "coming from signup "+user.toString());
                   // writeUser(user.getUid(),mEmailField.getText().toString(),  mNameTV.getText().toString());
                    Intent i = new Intent(MainActivity.this, ListActivity.class);
                    i.putExtra(EXTRA_USER_ID, user.getUid());
                    startActivity(i);


                } else {
                    mAuth.signOut();

                    // User is signed out
                    Log.d("main", "onAuthStateChanged:signed_out");
                }
            }
        };
    }



    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.signOut();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAuth.signOut();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void signIn(String email, String password) {
        Log.d("main", "signIn:" + email);
  //      if (!validateForm()) {
    //        return;
      //  }

        //showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("main", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("main", "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                           // mStatusTextView.setText(R.string.auth_failed);
                        }
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

/*

    private boolean validateForm() {
        boolean valid = true;


        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }
*/

}
