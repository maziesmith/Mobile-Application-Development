package com.example.inclass8siddhant;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExpenseAppFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ExpenseAppFragment extends Fragment {

    private OnFragmentInteractionListener mListener;


    public ExpenseAppFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense_app, container, false);

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ArrayList<Expense> expense = mListener.getExpense();
        TextView tex = (TextView) getActivity().findViewById(R.id.textView);
        if(expense.isEmpty()){
            tex.setText("There is no expense to show, Please add your expenses from the menu");

        }else{
            tex.setText(" ");
        }
        ListView listView = (ListView) getActivity().findViewById(R.id.listView);

        Log.d("demo",mListener.getExpense().toString());
        final ExpenseAdapter expenseAdapter = new ExpenseAdapter(getActivity(), R.layout.row_item_layout, expense);
        listView.setAdapter(expenseAdapter);
        expenseAdapter.setNotifyOnChange(true);
        ImageButton btn = (ImageButton) getActivity().findViewById(R.id.imageButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoNextFragment();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Expense e = expense.get(position);
                mListener.getOneExpense(e);
                mListener.gotoLastFragment();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Expense e = expense.get(position);
                mListener.deleteThisExpense(e);
                expenseAdapter.notifyDataSetChanged();
                //Toast.makeText(getActivity(), "Expense deleted", Toast.LENGTH_LONG).show();

              // mListener.comeBackHere();
                return true;
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
        //void onFragmentInteraction(Uri uri);
        public void gotoNextFragment();
        public void gotoLastFragment();
        public void getOneExpense(Expense exp);
        public ArrayList<Expense> getExpense();
        //public void comeBackHere();
        public void deleteThisExpense(Expense exp);
    }
}
