package com.example.intents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        findViewById(R.id.GoButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getExtras() != null){
                    String name = getIntent().getExtras().getString(MainActivity.NAME_KEY);
                    User user = (User) getIntent().getExtras().getSerializable(MainActivity.USER_KEY);
                    Person person = (Person) getIntent().getExtras().getParcelable(MainActivity.Parcel_key);
                    Toast.makeText(getApplicationContext(), person.toString(), Toast.LENGTH_LONG).show();
                }
                else{

                }
                finish();
            }
        });
        final EditText editText;
        editText = (EditText) findViewById(R.id.editTextResult);
        findViewById(R.id.SendMainButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = editText.getText().toString();
                if(value.equals("")||value.length()==0){
                    setResult(RESULT_CANCELED);
                }else{
                    Intent intent = new Intent();
                    intent.putExtra(MainActivity.Value_key,value);
                    setResult(RESULT_OK,intent);
                }
                finish();
            }
        });
    }
}
