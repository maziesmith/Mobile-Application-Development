package com.example.intents;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sid on 06-09-2016.
 */
public class Person implements Parcelable {
    String name, address;
    double age;

    public Person(String name, String address, double age) {
        this.name = name;
        this.address = address;
        this.age = age;
    }

    protected Person(Parcel in) {
        name = in.readString();
        address = in.readString();
        age = in.readDouble();
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
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeDouble(age);
    }
}
