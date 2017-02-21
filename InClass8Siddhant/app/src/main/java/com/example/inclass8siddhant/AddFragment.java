package com.example.inclass8siddhant;

import android.app.Activity;
import android.content.Context;

import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class AddFragment extends Fragment {
    String category ;
    String expensename;
    double amount;
    Date date;
    private OnFragmentInteractionListener mListener;

    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_add, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Spinner spinner = (Spinner) getActivity().findViewById(R.id.spinnerCategory);

        // Spinner click listener
//          spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.categories)){
            @Override
            public int getCount() {
                return super.getCount()-1;
            }

        };

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(dataAdapter.getCount());
        AdapterView.OnItemSelectedListener categorySelectedListener = new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> spinner, View container,
                                       int position, long id) {
                category = spinner.getItemAtPosition(position).toString();
                Log.d("tag", "selected");
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        };
        spinner.setOnItemSelectedListener(categorySelectedListener);

        getActivity().findViewById(R.id.buttonSaveExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText expenseName = (EditText) getActivity().findViewById(R.id.editTextExpenseName);
                EditText Amount = (EditText) getActivity().findViewById(R.id.editTextAmount);
                if (expenseName.getText().toString().equals("") || Amount.getText().toString().equals("") || category.toString().equals("Select a category")) {
                    Toast.makeText(getActivity(), "Please Enter all details", Toast.LENGTH_LONG).show();
                } else {
                    amount = Double.parseDouble(Amount.getText().toString());
                    expensename = expenseName.getText().toString();
                    Calendar today = Calendar.getInstance();
                    today.clear(Calendar.HOUR); today.clear(Calendar.MINUTE); today.clear(Calendar.SECOND);
                    Date todayDate = today.getTime();
                    Log.d("demo",todayDate.toString());
                    Expense e = new Expense(expensename, category, amount, todayDate.toString());
                    mListener.onAddExpense(e);
                    mListener.gotoFirstFragment();
                }
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        category = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item

        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
       void onAddExpense(Expense e);
        public void gotoFirstFragment();
    }
}
