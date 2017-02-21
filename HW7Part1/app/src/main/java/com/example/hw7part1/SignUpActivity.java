package com.example.hw7part1;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String EXTRA_EMAIL_ID = "EMAIL_ID";
    private static final String EXTRA_FNAME = "FNAME";
    private static final String EXTRA_LNAME = "LNAME";
    private static final int RESULT_LOAD_IMAGE = 112;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private static final String TAG = "EmailPassword";
    //  Firebase ref;
    EditText mEmailField, mPasswordField, mFirstNameTV, mLastNameTV, mConfirmPasswordField;
    ImageView profilePicIV;
    EditText password;
    Button loginButton;
    Button createAccountButton;
    private String gender;
    Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Spinner spinner = (Spinner) findViewById(R.id.spinnerCategory);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.gender)){
            @Override
            public int getCount() {
                return super.getCount()-1;
            }

        };

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(dataAdapter.getCount());



        mEmailField = (EditText) findViewById(R.id.editTextSignupEmail);
        mPasswordField = (EditText) findViewById(R.id.editTextSignupPassword);
        mConfirmPasswordField = (EditText) findViewById(R.id.editTextSignupConfirmPassword);
        mFirstNameTV = (EditText) findViewById(R.id.editTextSignupFistName);
        mLastNameTV = (EditText) findViewById(R.id.editTextSignupLastName);
        profilePicIV = (ImageView) findViewById(R.id.imageViewProfile);
        Button buttonSignUp = (Button) findViewById(R.id.buttonSignupSignup);


        profilePicIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });


        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("sign1", mEmailField.getText().toString());
                Log.d("sign2", mPasswordField.getText().toString());
                if (mEmailField.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, "email field is empty", Toast.LENGTH_LONG).show();
                }
                else if(!isValidEmailAddress(mEmailField.getText().toString())){
                    Toast.makeText(SignUpActivity.this, "email is not in valid format", Toast.LENGTH_LONG).show();
                }else if (mPasswordField.getText().toString().length()<6) {
                    Toast.makeText(SignUpActivity.this, "Password is too short", Toast.LENGTH_LONG).show();
                }
                else if (mPasswordField.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, "Password field is empty", Toast.LENGTH_LONG).show();
                } else if (mFirstNameTV.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, "First Name field is empty", Toast.LENGTH_LONG).show();
                }else if (mLastNameTV.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, "Last Name field is empty", Toast.LENGTH_LONG).show();
                }else if (mConfirmPasswordField.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, "Confirm Password field is empty", Toast.LENGTH_LONG).show();}
                    else if (gender.equals("")) {
                        Toast.makeText(SignUpActivity.this, "Choose a gender", Toast.LENGTH_LONG).show();
                }else if ("profile".equals(profilePicIV.getTag())) {
                    Toast.makeText(SignUpActivity.this, "Confirm Password field is empty", Toast.LENGTH_LONG).show();}
                else {

                    createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString(), mFirstNameTV.getText().toString(), mLastNameTV.getText().toString(),gender,profilePicIV);
                }
            }
        });
        findViewById(R.id.buttonCancelSignup).setOnClickListener(new View.OnClickListener() {
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
                    writeUser(user.getUid(),mEmailField.getText().toString(),  mFirstNameTV.getText().toString(), mLastNameTV.getText().toString(), gender, profilePicIV);
                  finish();
                   // Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                    // i.putExtra(EXTRA_EMAIL_ID, mEmailField.getText().toString());
                    // i.putExtra(EXTRA_FNAME, mFirstNameTV.getText().toString());
                    //i.putExtra(EXTRA_LNAME, mLastNameTV.getText().toString());
                    //startActivity(i);

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

    }

    private void writeUser(String userid, String email,  String userfirstname, String userlastname, String gender, ImageView profilepicIV) {
        Log.d("write", email);
        Log.d("write", userfirstname);
        Log.d("write", userlastname);
        final String image_id = String.valueOf(UUID.randomUUID());
        final String path = "images/"+userid+ image_id+".jpg";
        StorageReference imageRef = storage.getReference(path);
        Log.d("writeprofile", profilepicIV.getTag().toString());
        profilePicIV.setDrawingCacheEnabled(true);
        profilePicIV.buildDrawingCache();
        Bitmap bitmap = profilePicIV.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
            }
        });
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        User user = new User(userfirstname, userlastname, email,gender,image_id,path,userid);

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
    private void createAccount(String email, String password, String fname, String lname, String gender , ImageView profilepicIV) {
        Log.d(TAG, "createAccount:" + email);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@android.support.annotation.NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Account already exists", Toast.LENGTH_LONG).show();
                        }else{
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        gender = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            //  ImageButton imageReceipt = (ImageButton) findViewById(R.id.imageButtonRecipt);
            profilePicIV = (ImageView) findViewById(R.id.imageViewProfile);
            profilePicIV.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            profilePicIV.setTag("imageReceived");
        }

    }

}
