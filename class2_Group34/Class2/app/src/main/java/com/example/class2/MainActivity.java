package com.example.class2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RadioGroup rg;
    double Area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rg = (RadioGroup) findViewById(R.id.RadioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton)findViewById(checkedId);
                Log.d("radioDemo","Checked the" + rb.getText().toString());
            }
        });

        findViewById(R.id.Calculate).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int id = rg.getCheckedRadioButtonId();
                if(id== R.id.Triangle){
                    Log.d("radio","Triangle");
                    EditText length1view = (EditText) findViewById(R.id.Length1Value);
                    EditText length2view = (EditText) findViewById(R.id.Length2Value);
                    if(length2view.getText().toString().equals("") || length1view.getText().toString().equals("") ){
                        Context context = getApplicationContext();
                        CharSequence text = "Both length 1 and length2 are required";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    else {
                        double length1 = Double.parseDouble(length1view.getText().toString());
                        double length2 = Double.parseDouble(length2view.getText().toString());
                        Area = 0.5 * length1 * length2;
                        TextView AreaView = (TextView) findViewById(R.id.AreaText);
                        AreaView.setText("" + Area);
                    }
                }
                else if(id== R.id.Rectangle){
                    Log.d("radio","Rectangle");
                    EditText length1view = (EditText) findViewById(R.id.Length1Value);
                    EditText length2view = (EditText) findViewById(R.id.Length2Value);
                    if(length2view.getText().toString().equals("") || length1view.getText().toString().equals("") ){
                        Context context = getApplicationContext();
                        CharSequence text = "Both length 1 and length2 are required";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    else {
                        double length1 = Double.parseDouble(length1view.getText().toString());
                        double length2 = Double.parseDouble(length2view.getText().toString());
                        Area = length1 * length2;
                        TextView AreaView = (TextView) findViewById(R.id.AreaText);
                        AreaView.setText("" + Area);
                    }
                }
                else if(id== R.id.Square){
                    Log.d("radio","Square");
                    EditText length1view = (EditText) findViewById(R.id.Length1Value);
                    EditText length2view = (EditText) findViewById(R.id.Length2Value);
                    if(length1view.getText().toString().equals("")  ){
                        Context context = getApplicationContext();
                        CharSequence text = " length 1 is required";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    else {
                        double length1 = Double.parseDouble(length1view.getText().toString());

                        Area = length1 * length1;
                        TextView AreaView = (TextView) findViewById(R.id.AreaText);
                        AreaView.setText("" + Area);
                        length2view.setText("");
                    }
                }
                else if(id== R.id.Circle){
                    Log.d("radio","Circle");
                    EditText length1view = (EditText) findViewById(R.id.Length1Value);
                    EditText length2view = (EditText) findViewById(R.id.Length2Value);
                    if(length1view.getText().toString().equals("")  ){
                        Context context = getApplicationContext();
                        CharSequence text = "Length 1 is required";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    else {
                        double length1 = Double.parseDouble(length1view.getText().toString());

                        Area = Math.PI * length1 * length1;
                        TextView AreaView = (TextView) findViewById(R.id.AreaText);
                        AreaView.setText("" + Area);
                        length2view.setText("");
                    }
                }
                else if(id== R.id.ClearAll){
                    Log.d("radio","Clear all");
                    EditText length1view = (EditText) findViewById(R.id.Length1Value);
                    EditText length2view = (EditText) findViewById(R.id.Length2Value);
                    length1view.setText("");
                    length2view.setText("");
                    TextView AreaView = (TextView) findViewById(R.id.AreaText) ;
                    AreaView.setText("");

                }
                else if(id== -1){
                    Log.d("radio","nothing is checked");
                }
            }
        });
    }
}
