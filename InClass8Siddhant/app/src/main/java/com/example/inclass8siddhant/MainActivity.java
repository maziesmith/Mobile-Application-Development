package com.example.inclass8siddhant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ExpenseAppFragment.OnFragmentInteractionListener, AddFragment.OnFragmentInteractionListener, ShowExpense.OnFragmentInteractionListener{
    ArrayList<Expense> expenses = new ArrayList<>();
    Expense e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction()
                .add(R.id.activity_main, new ExpenseAppFragment(), "first")
                .commit();

    }

    @Override
    public void gotoNextFragment() {
        getFragmentManager().beginTransaction().replace(R.id.activity_main, new AddFragment(), "second")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoLastFragment() {
        getFragmentManager().beginTransaction().replace(R.id.activity_main, new ShowExpense(), "third")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void getOneExpense(Expense exp) {
        e = exp;

    }


    @Override
    public ArrayList<Expense> getExpense() {
        return expenses;
    }

    /*@Override
    public void comeBackHere() {
        getFragmentManager().beginTransaction()
                .add(R.id.activity_main, new ExpenseAppFragment(), "first")
                .commit();
    }*/

    @Override
    public void deleteThisExpense(Expense exp) {
        expenses.remove(e);

    }

    @Override
    public void onAddExpense(Expense e) {
        expenses.add(e);
    }

    @Override
    public void gotoFirstFragment() {
        getFragmentManager().beginTransaction().replace(R.id.activity_main, new ExpenseAppFragment(), "first")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public Expense getShowExpense() {
        return e;
    }

    @Override
    public void goToClose() {
        getFragmentManager().beginTransaction().replace(R.id.activity_main, new ExpenseAppFragment(), "first")
                .addToBackStack(null)
                .commit();
    }
}
