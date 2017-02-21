package com.example.inclass11;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;



public class ChatActivity extends AppCompatActivity {
    public static final int RESULT_LOAD_IMAGE = 111;
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    ArrayList<Message> messagesList = new ArrayList<Message>();
    MessageAdapter madapter = null;
   // SharedPreference sp = new SharedPreference();
    ListView listview = null;
    Uri selectedImage;
    User user;
    String fname, lname= null;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String EXTRA_USER_ID = "USER_ID";
    private static final String EXTRA_FIRST_NAME = "FIRST_NAME";
    private static final String EXTRA_LAST_NAME = "LAST_NAME";
    private MessageAdapter mAdapter;
    private RecyclerView mMessageRecycler;
    private DatabaseReference mMessageReference;

    private  String user_id;
    private List<Message> MessageList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);



        if(getIntent().getExtras()!=null) {
            user_id = getIntent().getStringExtra(EXTRA_USER_ID);
            Log.d("chat11", user_id);
            DatabaseReference Userref = FirebaseDatabase.getInstance().getReference().getRoot().child("users");

            Userref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("kkk", "in here ");
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        if(postSnapshot.getValue(User.class).getUser_id().equals(user_id))
                        {
                            user = postSnapshot.getValue(User.class);
                            fname = user.getUserfirstname();
                            lname = user.getUserlastname();
                            Log.d("insideif",fname);
                            Log.d("insideif",lname);
                            TextView nameTv = (TextView) findViewById(R.id.textViewName);
                            nameTv.setText(fname+" "+lname);
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                 Log.d("chat11", "failed");
                }
            });

            mMessageReference = FirebaseDatabase.getInstance().getReference()
                    .child("messages").child(user_id);

            mMessageRecycler = (RecyclerView) findViewById(R.id.message_list_recycler);

            mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));

            mAdapter = new MessageAdapter(this, mMessageReference);
            mMessageRecycler.setAdapter(mAdapter);


        }
            findViewById(R.id.ImageViewLogOut).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signOut();
                    Intent i = new Intent(ChatActivity.this,MainActivity.class);
                    startActivity(i);
                }
            });



            mAdapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {
                    Log.d("item",itemView.getId()+"");
                    if(itemView.getId()== R.id.imageViewDelete)
                    {
                        String selectedKey = mAdapter.mMessages.get(position).getKey();
                        Log.d("list", "Selected Key: " + selectedKey);
                        Log.d("list", "User id: " + user_id);
                        DatabaseReference db_node = FirebaseDatabase.getInstance().getReference().getRoot().child("messages").child(user_id).child(selectedKey);
                        db_node.setValue(null);
                    }
                    if(itemView.getId() == R.id.imageViewComment)
                    {
                        EditText commentEdit = (EditText)findViewById(R.id.editTextCommentText);
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                        String key = mDatabase.child("messages").child(user_id).child("comments").push().getKey();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Date date = new Date();
                        System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48
                        Message m = new Message(fname,lname,dateFormat.format(date),"",commentEdit.getText().toString(), "noimage", null);
                        Map<String, Object> postValues = m.toMap();

                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put("/messages/" +user_id+"/comments/"+key, postValues);
                        m.setKey(key);
                        // childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
                        Log.d("mystery","here");
                        mDatabase.updateChildren(childUpdates);
                    }
                }
            });
        findViewById(R.id.imageViewSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText messageText = (EditText) findViewById(R.id.editTextMsgSend);
                 DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                String key = mDatabase.child("messages").child(user_id).push().getKey();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                Date date = new Date();
                System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48
                Message m = new Message(fname,lname,dateFormat.format(date),"",messageText.getText().toString(), "noimage", null);
                Map<String, Object> postValues = m.toMap();

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/messages/" +user_id+"/"+key, postValues);
                m.setKey(key);
                // childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
                Log.d("mystery","here");
                mDatabase.updateChildren(childUpdates);
            }
        });

        findViewById(R.id.imageViewGallery).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

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
            Uri file = Uri.fromFile(new File(picturePath));
            final String image_id = String.valueOf(UUID.randomUUID());
            final String path = "images/"+user_id+ image_id+file.getLastPathSegment();
            StorageReference imageRef = storage.getReference(path);

            UploadTask uploadTask = imageRef.putFile(file);

// Register observers to listen for when the download is done or if it fails
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    String key = mDatabase.child("messages").child(user_id).push().getKey();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48
                    Message m = new Message(fname,lname,dateFormat.format(date),path,"",image_id, null);
                    Map<String, Object> postValues = m.toMap();

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/messages/" +user_id+"/"+key, postValues);
                    m.setKey(key);
                    // childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
                    Log.d("mystery","here");
                    mDatabase.updateChildren(childUpdates);
                    Intent i= new Intent(ChatActivity.this,ChatActivity.class);
                }
            });




                }
            }

        }







