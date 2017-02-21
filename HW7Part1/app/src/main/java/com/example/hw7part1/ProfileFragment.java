package com.example.hw7part1;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RESULT_LOAD_IMAGE =112 ;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView profilePic;
    private OnFragmentInteractionListener mListener;
    private Uri selectedImage;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {

        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final EditText fnET = (EditText) getActivity().findViewById(R.id.editTextProfileUpdateFistName);
        final EditText lnET = (EditText) getActivity().findViewById(R.id.editTextProfileUpdateLastName);
        profilePic = (ImageView) getActivity().findViewById(R.id.imageViewProfileUpdate);
        String user_id = mListener.getUserid();
        DatabaseReference Userref = FirebaseDatabase.getInstance().getReference().getRoot().child("users").child(user_id);
        final User[] mainuser = {null};
        Userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("kkk", "in here ");

                  User     user = dataSnapshot.getValue(User.class);
                  mainuser[0] = user;
                fnET.setText(user.getUserfirstname());
                lnET.setText(user.getUserlastname());
                String image_id = user.getImage_id();
                String path = user.getImage_url();
                Log.d("path", path);
                StorageReference storageReference = storage.getReferenceFromUrl("gs://fir-test-471fa.appspot.com");
                StorageReference eachImage = storageReference.child(path);
                eachImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(getActivity()).load(uri).into(profilePic);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("adapater", e.toString());
                    }
                });

            }
            
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("chat11", "failed");
            }
        });


        Button updateButton = (Button) getActivity().findViewById(R.id.buttonUpdateProfile);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String fname = fnET.getText().toString();
                String lname = lnET.getText().toString();
                String image_id = mainuser[0].getImage_id();
                final String path = "images/"+mainuser[0].getUser_id()+ image_id+".jpg";
                StorageReference imageRef = storage.getReference(path);
                Log.d("writeprofile", profilePic.getTag().toString());
                profilePic.setDrawingCacheEnabled(true);
                profilePic.buildDrawingCache();
                Bitmap bitmap = profilePic.getDrawingCache();
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
                String key = mainuser[0].getUser_id();
                mainuser[0].setUserfirstname(fnET.getText().toString());
                mainuser[0].setUserlastname(lnET.getText().toString());
                Map<String, Object> postValues = mainuser[0].toMap();
                Log.d("profilefrag",postValues.toString());
                Log.d("profilefrag",key);

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/users/" + key, postValues);
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.updateChildren(childUpdates);
                mListener.gotoDetailsActivity();
            }

        });
        getActivity().findViewById(R.id.buttonCancelProfileUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoDetailsActivity();
            }
        });
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public  String getUserid();

        void gotoDetailsActivity();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            //  ImageButton imageReceipt = (ImageButton) findViewById(R.id.imageButtonRecipt);
            profilePic = (ImageView) getActivity().findViewById(R.id.imageViewProfileUpdate);
            profilePic.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            profilePic.setTag("imageReceived");
        }

    }
}
