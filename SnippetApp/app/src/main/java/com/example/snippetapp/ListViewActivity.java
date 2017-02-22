package com.example.snippetapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ListViewActivity extends AppCompatActivity {

    String name;
    String age;
    String gender;
    User user;
    Person person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        if(getIntent().getExtras()!=null){

            name = getIntent().getExtras().getString("NAME");
            age = getIntent().getExtras().getString("AGE");
            gender = getIntent().getExtras().getString("GENDER");
            user = (User) getIntent().getExtras().getSerializable("USER");
            person = (Person) getIntent().getExtras().getParcelable("PERSON");
            //Toast.makeText(this, name+"  "+age+"  "+gender,Toast.LENGTH_LONG).show();
            //Toast.makeText(this, user.toString(), Toast.LENGTH_LONG).show();
            Toast.makeText(this, person.toString(), Toast.LENGTH_LONG).show();

        }

    }
}
