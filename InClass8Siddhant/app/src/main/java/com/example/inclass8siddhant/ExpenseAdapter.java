package com.example.inclass8siddhant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sid on 17-10-2016.
 */

public class ExpenseAdapter extends ArrayAdapter<Expense>{

    List<Expense> mData;
    Context mContext;
    int mResource;

    public ExpenseAdapter(Context context, int resource, List<Expense> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mData = objects;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // return super.getView(position, convertView, parent);
        if(convertView==null){

          LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
        }
        Expense expense = mData.get(position);
        TextView expenseNameTextView = (TextView) convertView.findViewById(R.id.textViewExpenseName);
        expenseNameTextView.setText(expense.expenseName);
        TextView expenseAmountTextView = (TextView) convertView.findViewById(R.id.textViewExpenseAmount);
        String amountString = ""+expense.amount;
        expenseAmountTextView.setText(amountString);

        return convertView;
    }
}
