package com.example.hw7part1;

import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class SendReplyActivity extends AppCompatActivity {

    private static final java.lang.String EXTRA_USER_ID = "USER_ID";
    private static final java.lang.String EXTRA_FROM_USER_ID = "FROM_USER_ID";
    private static final int RESULT_LOAD_IMAGE = 111;
    TextView SenderNameTV, MessageTV;
    ImageView attchImageTV, backButton;
    EditText replET;
    Button replyButton;
    ImageView imageDialog;
    Dialog dialog;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_reply);
         dialog= new Dialog(SendReplyActivity.this);
        if (getIntent().getExtras() != null) {
            SenderNameTV = (TextView) findViewById(R.id.textViewSenderNameChatActivity);
            replET = (EditText) findViewById(R.id.editTextReplyChatActivity);
            attchImageTV = (ImageView) findViewById(R.id.imageViewChatActivity);
            replyButton = (Button) findViewById(R.id.buttonSendChatActivity);
            MessageTV = (TextView) findViewById(R.id.textViewMessageChatActivity);
            backButton = (ImageView) findViewById(R.id.imageViewBackChatActivity);
            final String user_id = getIntent().getExtras().getString(EXTRA_USER_ID);
            final String fromuser_id = getIntent().getExtras().getString(EXTRA_FROM_USER_ID);
            DatabaseReference Userref = FirebaseDatabase.getInstance().getReference().getRoot().child("users");

            Userref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("kkk", "in here ");
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        if (postSnapshot.getValue(User.class).getUser_id().equals(fromuser_id)) {
                            User touser = postSnapshot.getValue(User.class);
                            String fname = touser.getUserfirstname();
                            String lname = touser.getUserlastname();

                            Log.d("insideif", fname + lname);
                            SenderNameTV.setText(fname + " " + lname);
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("chat11", "failed");
                }
            });

            DatabaseReference Mesgref = FirebaseDatabase.getInstance().getReference().getRoot().child("inbox").child(user_id);

            Mesgref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("kkk", "in here ");
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        if (postSnapshot.getValue(Message.class).getFromuser_id().equals(fromuser_id)) {
                            Message msg = postSnapshot.getValue(Message.class);
                            MessageTV.setText(msg.getText());
                            if (!msg.getMimageid().equals("attachimage")) {
                                StorageReference storageReference = storage.getReferenceFromUrl("gs://fir-test-471fa.appspot.com");
                                StorageReference eachImage = storageReference.child(msg.getMimage_url());
                                eachImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Picasso.with(SendReplyActivity.this).load(uri).into(attchImageTV);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("replyactivity", e.toString());
                                    }
                                });
                            }
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("chat11", "failed");
                }
            });

            replyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.setContentView(R.layout.dialog_view);
                    dialog.setTitle("Attach an image ");
                    imageDialog = (ImageView) dialog.findViewById(R.id.imageViewAttach);
                    Button dialogButton = (Button) dialog.findViewById(R.id.buttonSend);
                    dialog.show();

                    imageDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(
                                    Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                            startActivityForResult(i, RESULT_LOAD_IMAGE);
                        }
                    });
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                            String key = mDatabase.child("inbox").child(fromuser_id).push().getKey();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                            Date date = new Date();
                            final String image_id = String.valueOf(UUID.randomUUID());
                            Message m = new Message();
                            String path = null;
                            if (imageDialog.getTag().equals("attach")) {
                                m.setMimageid("attachimage");
                            } else {
                                m.setMimageid(image_id);
                                path = "inboxmessage_images/" + fromuser_id + user_id + ".jpg";
                                StorageReference imageRef = storage.getReference(path);
                                Log.d("writeprofile", imageDialog.getTag().toString());
                                imageDialog.setDrawingCacheEnabled(true);
                                imageDialog.buildDrawingCache();
                                Bitmap bitmap = imageDialog.getDrawingCache();
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
                            m.setFromuser_id(user_id);
                            m.setDateSent(dateFormat.format(date));
                            m.setMimage_url(path);
                            m.setText(replET.getText().toString());
                            m.setKey(key);
                            m.setRead_flag("N");
                            m.setKey(key);
                            Map<String, Object> postValues = m.toMap();

                            Map<String, Object> childUpdates = new HashMap<>();
                            childUpdates.put("/inbox/" + fromuser_id + "/" + key, postValues);

                            // childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
                            Log.d("mystery", "here");
                            Log.d("sonalhere",user_id);
                            Log.d("fromsonalhere",fromuser_id);
                            mDatabase.updateChildren(childUpdates);
                            dialog.dismiss();

                            finish();
                            Intent i = new Intent(SendReplyActivity.this, DetailsActivity.class);

                            i.putExtra(EXTRA_USER_ID, user_id);
                            startActivity(i);

                        }
                    });


                }
            });

            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    Intent i = new Intent(SendReplyActivity.this, DetailsActivity.class);
                    i.putExtra(EXTRA_USER_ID, user_id);
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
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            //  ImageButton imageReceipt = (ImageButton) findViewById(R.id.imageButtonRecipt);
            imageDialog = (ImageView) dialog.findViewById(R.id.imageViewAttach);
            imageDialog.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            imageDialog.setTag("imageReceived");
        }

    }

}
