package com.example.fragmentsdemo;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AFragment extends Fragment {


    public AFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_a, container, false);
        view.findViewById(R.id.buttonClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Button click", Toast.LENGTH_LONG).show();

            }
        });

        return view;
    }
    public void changeColor(int color){
        getView().setBackgroundColor(color);
        //getActivity().findViewById(R.id.fragment).setBackgroundColor(color);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    OnFragmentTextChange mListner;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{

            mListner = (OnFragmentTextChange) activity;
        }catch (ClassCastException e){

            throw new ClassCastException(activity.toString() + " should implement OnFragmentTextChange");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /* one way to show button click
        getActivity().findViewById(R.id.buttonClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Button click", Toast.LENGTH_LONG).show();
            }
        });
        */
        getActivity().findViewById(R.id.editTextInFragment);
        //getActivity().findViewById(R.id.buttonInFragment).setOnClickListener(new View.OnClickListener() { just for 1 fragment
        getView().findViewById(R.id.buttonInFragment).setOnClickListener(new View.OnClickListener() {//for multiple fragments
            @Override
            public void onClick(View v) {
                EditText et = (EditText) getView().findViewById(R.id.editTextInFragment);
                mListner.onTextChanged(et.getText().toString());

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public interface OnFragmentTextChange{
        void onTextChanged(String text);
    }
}
