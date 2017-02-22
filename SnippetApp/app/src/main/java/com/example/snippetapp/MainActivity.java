package com.example.snippetapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {


    CheckBox cb1;
    RadioGroup rg1;
    RadioButton rb1;
    RadioButton rb2;
    TextView tvseekbar;
    SeekBar sb1;
    int sbvalue, sbvalue2;
    Button submit;
    String gender;
    EditText nameED;
    String s1;
    String s2;
    String s;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cb1 = (CheckBox) findViewById(R.id.checkBox);
        nameED = (EditText) findViewById(R.id.editText1Main);
        rg1 = (RadioGroup) findViewById(R.id.rg1);
        rb1 = (RadioButton) findViewById(R.id.radioButton1);
        rb2 = (RadioButton) findViewById(R.id.radioButton2);
        tvseekbar = (TextView) findViewById(R.id.TVSeekbarValue);
        sb1 = (SeekBar) findViewById(R.id.seekBarMain);
        submit = (Button) findViewById(R.id.buttonMain);
        sb1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sbvalue = progress * 10;
                sbvalue2 = sbvalue + 10;
                s1 = Integer.toString(sbvalue);
                s2 = Integer.toString(sbvalue2);
                s = s1 + " - " + s2;
                tvseekbar.setText(s);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb1.isChecked()) {
                    if (rb1.isChecked()) {
                        gender = "male";
                    } else {
                        gender = "female";
                    }
                    name = nameED.getText().toString();
                    User user = new User(name, s, gender);
                    Person person = new Person(name, s , gender);
                    Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
                    intent.putExtra("NAME", name);
                    intent.putExtra("AGE", s);
                    intent.putExtra("GENDER", gender);
                    intent.putExtra("USER",user);
                    intent.putExtra("PERSON", person);
                    startActivity(intent);
                }
            }
        });


    }
}
