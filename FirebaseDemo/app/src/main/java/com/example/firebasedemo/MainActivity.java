package com.example.firebasedemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    GoogleApiClient mGoogleApiClient;
    private ImageView imageContainer;
    private TextView overlayText;
    private ProgressBar progressBar;
    private Button uploadButton;
    private TextView downloadUrl;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        tv = (TextView) findViewById(R.id.textView1);
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        Button signOutButton = (Button) findViewById(R.id.sign_out_button);
        signInButton.setOnClickListener(this);
        signOutButton.setOnClickListener(this);

        imageContainer = (ImageView) findViewById(R.id.imageviewContainer);
        overlayText = (TextView) findViewById(R.id.overlaytext);
        overlayText.setText("");
        overlayText.setVisibility(View.INVISIBLE);
        EditText textinput = (EditText) findViewById(R.id.textinput);
        textinput.addTextChangedListener(new InputTextWatcher());
        uploadButton = (Button) findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(new UploadOnClickListner());
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        downloadUrl = (TextView) findViewById(R.id.downloadUrl);
    }

    private class InputTextWatcher implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            overlayText.setVisibility(s.length()>0 ? View.VISIBLE : View.INVISIBLE);
            overlayText.setText(s.toString());
        }
    }
    private class UploadOnClickListner implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            imageContainer.setDrawingCacheEnabled(true);
            imageContainer.buildDrawingCache();
            Bitmap bitmap = imageContainer.getDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            imageContainer.setDrawingCacheEnabled(false);
            byte[] data = baos.toByteArray();

            String path = "firememes/" + UUID.randomUUID() + ".png";
            StorageReference firememeRef = storage.getReference(path);

            StorageMetadata metadata = new StorageMetadata.Builder()
                    .setCustomMetadata("text", overlayText.getText().toString())
                    .build();

            progressBar.setVisibility(View.VISIBLE);
            uploadButton.setEnabled(false);

            UploadTask uploadTask = firememeRef.putBytes(data, metadata);
            uploadTask.addOnSuccessListener(MainActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.GONE);
                    uploadButton.setEnabled(true);

                    Uri uri = taskSnapshot.getDownloadUrl();
                    downloadUrl.setText(uri.toString());
                    downloadUrl.setVisibility(View.VISIBLE);
                }
            });

        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.sign_out_button:
                signOut();
                break;
        }
    }

    private void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result){
        Log.d(TAG, "handleSignInResult: " + result.isSuccess());
        if(result.isSuccess()){

            GoogleSignInAccount acct = result.getSignInAccount();
            tv.setText("Hello, "+ acct.getDisplayName());
        }
        else{

        }
    }
    private void signOut(){
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                tv.setText("Signed out");
            }
        });
    }
}
