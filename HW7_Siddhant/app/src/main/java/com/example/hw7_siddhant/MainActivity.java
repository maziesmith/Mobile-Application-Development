//M61nf+aC69kCXmFY1ejcX83rDNc=      <----dev key
//eKJJHinr+D3pnrXYFuIJnPbO7ag=    <--- release key


package com.example.hw7_siddhant;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String KEY_KEY = "KEY";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private AccessTokenTracker mFacebookAccessTokenTracker;
    private Uri Uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseAuth.getInstance().signOut();
/*
        mFacebookAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.i(TAG, "Facebook.AccessTokenTracker.OnCurrentAccessTokenChanged");
                MainActivity.this.onFacebookAccessTokenChange(currentAccessToken);
            }
        };*/
        //AppEventsLogger.activateApp(this);

        callbackManager = CallbackManager.Factory.create();
        //callbackManager.onActivityResult()
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
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
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                  //  Log.d("demo", "onAuthStateChanged:signed_in:" + user.getUid());
                    Log.d("demo1",user.getPhotoUrl().toString());
                    Log.d("demo", "here");
                    Uri file = user.getPhotoUrl();
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    //StorageReference storageRef = storage.getReferenceFromUrl("gs://firecast-app-c24ee.appspot.com");
                    StorageReference imageRef = storage.getReference("images/"+user.getUid()+file.getLastPathSegment());
                    final String path = "images/"+user.getUid()+file.getLastPathSegment();

                    UploadTask uploadTask = imageRef.putFile(file);

// Register observers to listen for when the download is done or if it fails
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
                    User user1 = new User(user.getDisplayName(), user.getDisplayName(), user.getEmail(),"",file.getLastPathSegment(),path,user.getUid());

                    mDatabase.child("users").child(user.getUid()).setValue(user);

                    //Intent i = new Intent(MainActivity.this, DetailsActivity.class);
                    //i.putExtra(EXTRA_USER_ID, account.getId());
                    //startActivity(i);
                    Intent i = new Intent(MainActivity.this, ChatActivity.class);
                    startActivity(i);
                } else {
                    // User is signed out
                    Log.d("demo", "onAuthStateChanged:signed_out");
                }
            }
        };
    }
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("demo", "handleFacebookAccessToken:" + token);

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
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
