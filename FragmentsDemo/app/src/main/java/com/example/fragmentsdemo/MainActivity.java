package com.example.fragmentsdemo;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity implements AFragment.OnFragmentTextChange{
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction()
                .add(R.id.container, new AFragment(), "tag_afragment")
                .commit();
        getFragmentManager().beginTransaction()
                .add(R.id.container, new AFragment(), "tag_afragment1")
                .commit();

        RadioGroup rg = (RadioGroup) findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               // AFragment f = (AFragment) getFragmentManager().findFragmentById(R.id.fragment); static fragment method
                AFragment f = (AFragment) getFragmentManager().findFragmentByTag("tag_afragment");
                AFragment f1 = (AFragment) getFragmentManager().findFragmentByTag("tag_afragment1");//dynamic fragment method
                if(checkedId==R.id.radioButtonRed){
                    f.changeColor(Color.RED);
                    f1.changeColor(Color.RED);

                }else if(checkedId==R.id.radioButtonGreen){
                    f.changeColor(Color.GREEN);
                    f1.changeColor(Color.GREEN);


                }else if(checkedId==R.id.radioButtonBlue){
                    f.changeColor(Color.BLUE);
                    f1.changeColor(Color.BLUE);


                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onTextChanged(String text) {
        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(text);
    }
}
