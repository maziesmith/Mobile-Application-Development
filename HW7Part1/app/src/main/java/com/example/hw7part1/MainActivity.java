package com.example.hw7part1;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hw7part1.R;
import com.example.hw7part1.SignUpActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements  GoogleApiClient.OnConnectionFailedListener {
    GoogleApiClient mGoogleApiClient;

    CallbackManager callbackManager;

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 999;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String EXTRA_USER_ID = "USER_ID";
    private SignInButton google_signin;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private GoogleSignInAccount account;
    private String image_id = String.valueOf(UUID.randomUUID());
    private FirebaseUser user ;
    private  String login = "app";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();
        final LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        Log.d("demo","before button");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("demo", "fb login successful");
                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                Log.d("demo","error login");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("demo","error fb login");
            }



        });



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("608312571712-9ptoaqjd2vlojjh2s7p1cbh5l1bkk9r2.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        google_signin = (SignInButton) findViewById(R.id.googlesign_in_button);

        google_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });

        findViewById(R.id.buttonSignupLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });


        findViewById(R.id.buttonLoginLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mEmailField = (EditText) findViewById(R.id.editTextUserNameLogin);
                EditText mPasswordField = (EditText) findViewById(R.id.editTextPasswordLogin);
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


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("main", "onAuthStateChanged:signed_in:" + user.getUid());
                    Log.d("main", "coming from signup " + user.toString());
                    Log.d("fb",login);
                    if(login.equals("google"))
                    {
                        DatabaseReference Userref = FirebaseDatabase.getInstance().getReference().getRoot().child("users");
                        Userref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Log.d("kkk", "in here ");
                                int k = 0;
                                long n = dataSnapshot.getChildrenCount();
                                Log.d("n is ", "value : "+n);
                                User user1 = dataSnapshot.getValue(User.class);
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    if (postSnapshot.getValue(User.class).getUser_id().equals(user.getUid())) {


                                        break;
                                    }
                                    Log.d("new","k is :"+k);
                                    k++;
                                }
                                Log.d("new","k is :"+k);
                                if(k == n)
                                {
                                    new LoadProfileImage().execute(account.getPhotoUrl().toString());

                                    final String path = "images/" + user.getUid() + image_id;


                                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                                    User usertobe = new User(account.getDisplayName(), account.getFamilyName(), account.getEmail(), "", image_id, path, user.getUid());

                                    mDatabase.child("users").child(user.getUid()).setValue(usertobe);



                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }


                        });

                    }


                    if(login.equals("fb"))
                    {

                        DatabaseReference Userref = FirebaseDatabase.getInstance().getReference().getRoot().child("users");
                        Userref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Log.d("kkk", "in here ");
                                int k = 0;
                                long n = dataSnapshot.getChildrenCount();
                                // User user1 = dataSnapshot.getValue(User.class);
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    if (postSnapshot.getValue(User.class).getUser_id().equals(user.getUid())) {
                                        Log.d("fb", "k is here");

                                        break;
                                    }
                                    k++;
                                }
                                Log.d("newfb", "n is "+n);
                                Log.d("newfb", "k is "+k);
                                if(k == n)
                                {
                                    final String path = "images/"+user.getUid()+image_id;
                                    new LoadProfileFBImage().execute(user.getPhotoUrl().toString());
                                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                    User user11 = new User(user.getDisplayName(), user.getDisplayName(), user.getEmail(),"",image_id,path,user.getUid());

                                    mDatabase.child("users").child(user.getUid()).setValue(user11);



                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }


                        });
                    }




                    // writeUser(user.getUid(),mEmailField.getText().toString(),  mNameTV.getText().toString());
                    Intent i = new Intent(MainActivity.this, DetailsActivity.class);
                    //i.putExtra(EXTRA_USER_ID, user1.getUser_id());
                    i.putExtra(EXTRA_USER_ID, user.getUid());
                    startActivity(i);

                } //else {
                //mAuth.signOut();

                // User is signed out
                //    Log.d("main", "onAuthStateChanged:signed_out");
                //}
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
                        login = "app";
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("main", "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, R.string.auth_f,
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]

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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.d("main", "result" + result.isSuccess());

            if (result.isSuccess()) {
                account = result.getSignInAccount();
                login = "google";
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Log.w(TAG, "signInWithCredential", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                                // ...
                            }
                        });
            }
        }
        else
        {

            callbackManager.onActivityResult(requestCode, resultCode, data);


        }

    }


    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            //camera=mIcon11;
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            //    bmImage.setImageBitmap(result);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            result.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            StorageReference imageRef = storage.getReference("images/" + user.getUid() + image_id);
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
        }
    }


    private class LoadProfileFBImage extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            //camera=mIcon11;
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            result.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            StorageReference imageRef = storage.getReference("images/" + user.getUid() + image_id);
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
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("demo", "handleFacebookAccessToken:" + token);
        login = "fb";
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("signin", "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.d("signin", "signInWithCredential", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }



                        // ...
                    }
                });
    }


}
