package com.example.hw7part1;

import android.app.Activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class DetailsActivity extends Activity implements UserFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener , InboxFragment.OnFragmentInteractionListener {
    private static final String EXTRA_FROM_USER_ID = "FROM_USER_ID";
    private Button profileButton, usersButton, inboxButton;
    private Button signoutButton;
    private String user_id;
    private static final String EXTRA_USER_ID = "USER_ID";
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if(getIntent().getExtras()!=null) {
            user_id = getIntent().getExtras().getString(EXTRA_USER_ID);
            Log.d("sonal", "user id in details activity is "+user_id);
            if (getFragmentManager().getBackStackEntryCount() > 0)
            {
                Log.d("sonal", "inside if");
                getFragmentManager().popBackStack();
            }
            Log.d("sonal", "out of  if");
               getFragmentManager().beginTransaction().add(R.id.container, new InboxFragment(), "inboxfrag").addToBackStack(null).commit();
            profileButton = (Button) findViewById(R.id.profileButton);
            usersButton = (Button) findViewById(R.id.usersButton);
            inboxButton = (Button) findViewById(R.id.inboxButton);
            signoutButton = (Button) findViewById(R.id.buttonSignOut);
            usersButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(getFragmentManager().getBackStackEntryCount() > 0)
                    {
                        getFragmentManager().popBackStack();
                    }

                    getFragmentManager().beginTransaction().add(R.id.container, new UserFragment(), "userfrag").addToBackStack(null).commit();
                }
            });

            profileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(getFragmentManager().getBackStackEntryCount() > 0)
                    {
                        getFragmentManager().popBackStack();
                    }


                    getFragmentManager().beginTransaction().add(R.id.container, new ProfileFragment(), "profilefrag").addToBackStack(null).commit();
                }
            });

            inboxButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getFragmentManager().getBackStackEntryCount() > 0)
                    {
                        Log.d("sonal", "inside inbox fragment if");
                        getFragmentManager().popBackStack();
                    }
                    Log.d("sonal", "outside inbox fragment if");

                    getFragmentManager().beginTransaction().add(R.id.container, new InboxFragment(), "inboxfrag").addToBackStack(null).commit();
                }
            });

            signoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signOut();
                    finish();
                    Intent i = new Intent(DetailsActivity.this, MainActivity.class);
                    startActivity(i);
                }
            });


        }
    }

    @Override
    public String getUserid() {
     return user_id;
    }

    @Override
    public void gotoDetailsActivity() {


        finish();
        startActivity(getIntent());
    }

    @Override
    public void gotoSendMsgActivity(String user_id, String fromuserid) {
        Intent i = new Intent(DetailsActivity.this, SendMessageActivity.class);
        i.putExtra(EXTRA_USER_ID,user_id);
        i.putExtra(EXTRA_FROM_USER_ID,fromuserid);
        startActivity(i);
    }

    @Override
    public String getUser_id() {
        return user_id;
    }

    @Override
    public void gotoSendReplyActivity(String user_id, String fromuser_id) {
        Intent i = new Intent(DetailsActivity.this, SendReplyActivity.class);
        i.putExtra(EXTRA_USER_ID,user_id);
        i.putExtra(EXTRA_FROM_USER_ID,fromuser_id);
        startActivity(i);
    }
}
