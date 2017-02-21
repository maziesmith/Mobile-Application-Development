package com.example.inclass03;
/*
Assignment No : InClass Assignment 3
File Name : Student.java
Full Name : Sonal Kaulkar
            Siddhant Jain
Group No 34
 */
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by skaul on 9/6/2016.
 */
public class Student implements Parcelable {

    String name,emailid, department,    mood;

    public Student(String name, String emailid, String department, String mood) {
        this.name = name;
        this.emailid = emailid;
        this.department = department;
        this.mood = mood;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(emailid);
        dest.writeString(department);
        dest.writeString(mood);

    }
    public static final Parcelable.Creator<Student> CREATOR
            = new Parcelable.Creator<Student>() {
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    private Student(Parcel in) { //order matters of unmarshalling
        this.name = in.readString();
        this.emailid = in.readString();
        this.department = in.readString();
        this.mood = in.readString();
    }


}
