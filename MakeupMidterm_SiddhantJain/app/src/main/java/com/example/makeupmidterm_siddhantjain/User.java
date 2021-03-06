package com.example.makeupmidterm_siddhantjain;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by sid on 11/7/2016.
 */



@IgnoreExtraProperties
public class User {

    public String username;
    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    @Override
    public String toString() {
        return email;
    }
}


