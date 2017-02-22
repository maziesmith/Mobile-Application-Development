package com.example.snippetapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sid on 21-02-2017.
 */

public class Person implements Parcelable{

    String name;
    String age;
    String gender;

    public Person(String name, String age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    private Person(Parcel in) {

        this.name = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", gender=" + '\'' +
                '}';
    }
}
