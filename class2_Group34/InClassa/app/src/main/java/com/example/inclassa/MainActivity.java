package com.example.inclassa;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private double Area;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button triangle = (Button) findViewById(R.id.buttontriangle);
        triangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText Length1view = (EditText) findViewById(R.id.Length1Input);
                EditText Length2view = (EditText) findViewById(R.id.Length2Input);
                if(Length1view.getText().toString().equals("") || Length2view.getText().toString().equals(""))
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Length1 and Length2 are mandatory ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else {
                    double length1 = Double.parseDouble(Length1view.getText().toString());
                    double length2 = Double.parseDouble(Length2view.getText().toString());
                    Area = 0.5 * length1 * length2;
                    TextView Areaview = (TextView) findViewById(R.id.AreaOutput);
                    Areaview.setText("" + Area);
                }
            }
        });

        Button square = (Button) findViewById(R.id.buttonSquare);
        square.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText Length1view = (EditText) findViewById(R.id.Length1Input);
                EditText Length2view = (EditText) findViewById(R.id.Length2Input);
                if(Length1view.getText().toString().equals(""))
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Length1 is  mandatory ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else {
                    double side = Double.parseDouble(Length1view.getText().toString());
                    // double length2 = Double.parseDouble(Length2view.getText().toString());
                    Area = side * side;
                    TextView Areaview = (TextView) findViewById(R.id.AreaOutput);
                    Areaview.setText("" + Area);
                    Length2view.setText("");
                }

            }
        });


        Button circle = (Button) findViewById(R.id.buttonCircle);
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText Length1view = (EditText) findViewById(R.id.Length1Input);
                EditText Length2view = (EditText) findViewById(R.id.Length2Input);
                if(Length1view.getText().toString().equals("") )
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Length1 is  mandatory ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else {
                    double radius = Double.parseDouble(Length1view.getText().toString());
                    // double length2 = Double.parseDouble(Length2view.getText().toString());
                    Area = Math.PI * radius * radius;
                    TextView Areaview = (TextView) findViewById(R.id.AreaOutput);
                    Areaview.setText("" + Area);
                    Length2view.setText("");
                }

            }
        });


        Button rec = (Button) findViewById(R.id.buttonRectangle);
        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText Length1view = (EditText) findViewById(R.id.Length1Input);
                EditText Length2view = (EditText) findViewById(R.id.Length2Input);
                if(Length1view.getText().toString().equals("") || Length2view.getText().toString().equals(""))
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Length1 and Length2 are mandatory ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else {
                    double length1 = Double.parseDouble(Length1view.getText().toString());
                    double length2 = Double.parseDouble(Length2view.getText().toString());
                    Area = length1 * length2;
                    TextView Areaview = (TextView) findViewById(R.id.AreaOutput);
                    Areaview.setText("" + Area);
                }
            }
        });



        Button clear = (Button) findViewById(R.id.buttonClear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText Length1view = (EditText) findViewById(R.id.Length1Input);
                EditText Length2view = (EditText) findViewById(R.id.Length2Input);
                TextView Areaview = (TextView) findViewById(R.id.AreaOutput);
                Areaview.setText("");
                Length2view.setText("");
                Length1view.setText("");


            }
        });
    }
}
