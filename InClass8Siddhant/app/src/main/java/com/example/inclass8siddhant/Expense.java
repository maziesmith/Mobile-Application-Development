

package com.example.inclass8siddhant;

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
public class Expense  implements Parcelable{
    String expenseName;
    String category;
    double amount;
    String date;


    public Expense(String expenseName, String category, double amount, String date) {
        this.expenseName = expenseName;
        this.category = category;
        this.amount = amount;
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



    public Expense() {
    }


    @Override
    public String toString() {
        return "Expense{" +
                "expenseName='" + expenseName + '\'' +
                ", category=" + category +
                ", amount=" + amount +
                ", date=" + date +"}";
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
        this.date = in.readString();
    }

}
