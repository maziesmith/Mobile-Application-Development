/*
Assignment No : Homework 2
File Name : Expense.java
Full Name : Sonal Kaulkar
            Siddhant Jain
Group No 34
 */

package com.example.inclass_10;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sid on 09-09-2016.
 */
@IgnoreExtraProperties
public class Expense implements  Comparable<Expense>{
    String name;
    String category;
    double amount;
    String date;
    @Exclude
    String key;

    @Override
    public String toString() {
        return "Expense{" +
                "expenseName='" + name + '\'' +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                ", date='" + date + '\'' +
                ", key='" + key + '\'' +
                '}';
    }

    public Expense(String name, String category, double amount, String date, String key) {
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.key = key;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("category", category);
        result.put("amount", amount);
        result.put("date", date);
        result.put("key", key);

        return result;
    }

    public Expense() {
    }

    @Override
    public int compareTo(Expense another) {
        return 0;
    }


    /*
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(expenseName);
        dest.writeString(category);
        dest.writeDouble(amount);
        dest.writeString(receipt.toString());
        dest.writeString(date);
    }

    public static final Parcelable.Creator<Expense> CREATOR
            = new Parcelable.Creator<Expense>() {
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

    private Expense(Parcel in) { //order matters of unmarshalling
        this.expenseName = in.readString();
        this.category = in.readString();
        this.amount = in.readDouble();
        this.receipt = Uri.parse(in.readString());
        this.date = in.readString();
    }



    @Override
    public int compareTo(Expense another) {

        if(this.getExpenseName().compareTo(another.getExpenseName())  < 0)
        {
            return -1 ;
        }
        else if (this.getExpenseName().compareTo(another.getExpenseName())  >  0)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }*/

}
