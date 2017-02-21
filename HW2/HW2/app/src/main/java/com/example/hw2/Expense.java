/*
Assignment No : Homework 2
File Name : Expense.java
Full Name : Sonal Kaulkar
            Siddhant Jain
Group No 34
 */

package com.example.hw2;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.net.URI;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by sid on 09-09-2016.
 */
public class Expense  implements Parcelable , Comparable<Expense>{
    String expenseName;
    String category;
    double amount;
    String date;
    Uri receipt;

    public Expense(String expenseName, String category, double amount, Uri receipt, String date) {
        this.expenseName = expenseName;
        this.category = category;
        this.amount = amount;
        this.receipt = receipt;
        this.date = date;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
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

    public Uri getReceipt() {
        return receipt;
    }

    public void setReceipt(Uri receipt) {
        this.receipt = receipt;
    }

    public Expense() {
    }


    @Override
    public String toString() {
        return "Expense{" +
                "expenseName='" + expenseName + '\'' +
                ", category=" + category +
                ", amount=" + amount +
                ", date=" + date +
                ", receipt=" + receipt +
                '}';
    }


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
    }

}
