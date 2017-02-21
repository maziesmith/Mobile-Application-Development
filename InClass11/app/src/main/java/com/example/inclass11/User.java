package com.example.inclass11;

/**
 * Created by sid on 14-11-2016.
 */

public class User {
    public String userfirstname;
    public String userlastname;
    public String email;
    public String user_id;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUserfirstname() {
        return userfirstname;
    }

    public void setUserfirstname(String userfirstname) {
        this.userfirstname = userfirstname;
    }

    public String getUserlastname() {
        return userlastname;
    }

    public void setUserlastname(String userlastname) {
        this.userlastname = userlastname;
    }

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public User(String userfirstname, String userlastname, String email, String user_id) {
        this.userfirstname = userfirstname;
        this.userlastname = userlastname;
        this.email = email;
        this.user_id = user_id;
    }
    @Override
    public String toString() {
        return email;
    }
}