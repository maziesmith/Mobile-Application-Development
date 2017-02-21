package com.example.hw7part1;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SendMessageActivity extends AppCompatActivity {
    private static final String EXTRA_USER_ID = "USER_ID";
    private static final int RESULT_LOAD_IMAGE = 112;
    private static final java.lang.String EXTRA_FROM_USER_ID = "FROM_USER_ID";
    private TextView ReceiverTV;
    private EditText MessageTV;
    private ImageView messsageImage;
    private Button sendButton , selectButton;
    private  User touser;
    private String email;
    private Uri selectedImage;
    private FirebaseStorage storage = FirebaseStorage.getInstance();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        if(getIntent().getExtras()!=null)
        {   ReceiverTV = (TextView) findViewById(R.id.textViewReceiver);
            MessageTV = (EditText) findViewById(R.id.editTextMessageSendActivity);
            messsageImage = (ImageView) findViewById(R.id.imageViewPicSendActivity);
            sendButton = (Button) findViewById(R.id.buttonSendSendActivity);
            selectButton = (Button) findViewById(R.id.buttonSelectImageSendActivity);
            final String user_id = getIntent().getExtras().getString(EXTRA_USER_ID);
            final String fromuser_id = getIntent().getExtras().getString(EXTRA_FROM_USER_ID);
            DatabaseReference Userref = FirebaseDatabase.getInstance().getReference().getRoot().child("users");

            Userref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("kkk", "in here ");
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        if(postSnapshot.getValue(User.class).getUser_id().equals(user_id))
                        {
                            touser = postSnapshot.getValue(User.class);
                            email = touser.getEmail();
                            Log.d("insideif",email);
                            TextView mailTv = (TextView) findViewById(R.id.textViewReceiver);
                            mailTv.setText(email);
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("chat11", "failed");
                }
            });


            selectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }
            });

            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText messageText = (EditText) findViewById(R.id.editTextMessageSendActivity);
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    String key = mDatabase.child("inbox").child(user_id).push().getKey();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                    Date date = new Date(); final String image_id = String.valueOf(UUID.randomUUID());
                    Message m = new Message();
                    String path = null;
                    if(messsageImage.getTag().equals("attachimage"))
                    {
                        m.setMimageid("attachimage");
                    }

                    else {
                        m.setMimageid(image_id);
                         path = "inboxmessage_images/" + fromuser_id + user_id + ".jpg";
                        StorageReference imageRef = storage.getReference(path);
                        Log.d("writeprofile", messsageImage.getTag().toString());
                        messsageImage.setDrawingCacheEnabled(true);
                        messsageImage.buildDrawingCache();
                        Bitmap bitmap = messsageImage.getDrawingCache();
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
                    }
                    System.out.println(dateFormat.format(date));//2014/08/06 15:59:48
                    m.setFromuser_id(fromuser_id);
                    m.setDateSent(dateFormat.format(date));
                    m.setMimage_url(path);
                    m.setText(messageText.getText().toString());
                    m.setKey(key);
                    m.setRead_flag("N");
                    Map<String, Object> postValues = m.toMap();

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/inbox/" +user_id+"/"+key, postValues);
                    m.setKey(key);
                    // childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
                    Log.d("mystery","here");
                    mDatabase.updateChildren(childUpdates);
                    finish();
                    Intent i = new Intent(SendMessageActivity.this, DetailsActivity.class);
                    i.putExtra(EXTRA_USER_ID,fromuser_id);
                    startActivity(i);
                }
            });

        }
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
            messsageImage = (ImageView) findViewById(R.id.imageViewPicSendActivity);
            messsageImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            messsageImage.setTag("imageReceived");
        }

    }
}
