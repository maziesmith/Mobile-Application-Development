package com.example.hw2;

import java.io.Serializable;
import java.net.URI;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by sid on 09-09-2016.
 */
public class Expense  implements Serializable{
    String expenseName;
    String[] category;
    double amount;
    Date date;
    URI receipt;

    public Expense(String expenseName, String[] category, double amount, URI receipt, Date date) {
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

    public String[] getCategory() {
        return category;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public URI getReceipt() {
        return receipt;
    }

    public void setReceipt(URI receipt) {
        this.receipt = receipt;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseName='" + expenseName + '\'' +
                ", category=" + Arrays.toString(category) +
                ", amount=" + amount +
                ", date=" + date +
                ", receipt=" + receipt +
                '}';
    }
}
