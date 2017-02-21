package com.example.inclass8siddhant;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowExpense.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ShowExpense extends Fragment {

    private OnFragmentInteractionListener mListener;

    public ShowExpense() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_expense, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Expense e = mListener.getShowExpense();
        TextView name = (TextView) getActivity().findViewById(R.id.textViewShowName);
        name.setText(e.expenseName);
        TextView cat = (TextView) getActivity().findViewById(R.id.textViewShowCategory);
        cat.setText(e.category);
        TextView amount = (TextView) getActivity().findViewById(R.id.textViewShowAmount);
        double d = e.amount;
        int i = (int)d;
        String amnt = "$ "+i;
        amount.setText(amnt);
        TextView date = (TextView) getActivity().findViewById(R.id.textViewShowDate);
        String df = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
        date.setText(df);
        Button close = (Button) getActivity().findViewById(R.id.buttonClose);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToClose();
            }
        });
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
        public Expense getShowExpense();
        void goToClose();
    }
}
