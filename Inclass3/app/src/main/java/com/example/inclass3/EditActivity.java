package com.example.inclass3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        String sname = getIntent().getExtras().getString(DisplayActivity.SNAME_KEY);
        String smail = getIntent().getExtras().getString(DisplayActivity.SMAIL_KEY);
        String sdep = getIntent().getExtras().getString(DisplayActivity.SDEP_KEY);
        String smood = getIntent().getExtras().getString(DisplayActivity.SMOOD_KEY);
        Log.d("demo"," sname is "+sname);
        Log.d("demo"," smail is"+smail);
        Log.d("demo"," sdep is"+sdep);
        Log.d("demo"," smood is"+smood);
        if(sname != null && !sname.isEmpty()){
            Log.d("demo","enter name change");
            EditText stname = (EditText) findViewById(R.id.editTextNameEdit);

            stname.setVisibility(View.VISIBLE);
            stname.setText(sname);
        Button save = (Button) findViewById(R.id.SubmitEdit);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Log.d("demo","hi");
                EditText stname = (EditText) findViewById(R.id.editTextNameEdit);

                String newName = stname.getText().toString();
                if(newName.equals("")||newName.length()==0){
                    setResult(RESULT_CANCELED);
                }else{
                    Intent intentEdit = new Intent();
                    intentEdit.putExtra(DisplayActivity.NewName_key,newName);
                    setResult(RESULT_OK,intentEdit);
                }
                finish();
                }
            });
        }
        else if(smail != null && !smail.isEmpty()){
            Log.d("demo","enter mail change");
            EditText stmail= (EditText) findViewById(R.id.editTextMailEdit);
            stmail.setVisibility(View.VISIBLE);
            stmail.setText(smail);
            Button save = (Button) findViewById(R.id.SubmitEdit);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("demo", "mail");
                    EditText stmail = (EditText) findViewById(R.id.editTextMailEdit);

                    String newMail = stmail.getText().toString();
                    if (isValidEmailAddress(newMail)) {
                        if (newMail.equals("") || newMail.length() == 0) {
                            setResult(RESULT_CANCELED);
                        } else {
                            Intent intentEdit = new Intent();
                            intentEdit.putExtra(DisplayActivity.NewMail_key, newMail);
                            setResult(RESULT_OK, intentEdit);
                        }
                        finish();
                    }else{
                        Toast.makeText(EditActivity.this, "Please use valid format of mail", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else if(sdep != null && !sdep.isEmpty()){
            Log.d("demo","enter department change");
            RadioGroup srg= (RadioGroup) findViewById(R.id.radioGroupEdit);
            srg.setVisibility(View.VISIBLE);
            if(sdep.equals("SIS")){
                RadioButton sis = (RadioButton) findViewById(R.id.radioButtonSISEdit);
                sis.setChecked(true);
            }else if(sdep.equals("CS")){
                RadioButton cs = (RadioButton) findViewById(R.id.radioButtonCSEdit);
                cs.setChecked(true);
            }else if(sdep.equals("BIO")){
                RadioButton bio = (RadioButton) findViewById(R.id.radioButtonBIOEdit);
                bio.setChecked(true);
            }else {
                RadioButton others = (RadioButton) findViewById(R.id.radioButtonOthersEdit);
                others.setChecked(true);
            }
            Button save = (Button) findViewById(R.id.SubmitEdit);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("demo","department");
                    RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroupEdit);
                    int id = rg.getCheckedRadioButtonId();
                    String department = "";
                    if(id==R.id.radioButtonSISEdit){
                        department = "SIS";
                    }else if(id==R.id.radioButtonCSEdit){
                        department = "CS";
                    }else if(id==R.id.radioButtonBIOEdit){
                        department = "BIO";
                    }else{
                        department = "Others";
                    }


                    if(department.equals("")||department.length()==0){
                        setResult(RESULT_CANCELED);
                    }else{
                        Intent intentEdit = new Intent();
                        intentEdit.putExtra(DisplayActivity.NewDep_key,department);
                        setResult(RESULT_OK,intentEdit);
                    }
                    finish();
                }
            });
        }else if(smood != null && !smood.isEmpty()){
            Log.d("demo","enter mood change");
            TextView moodpertext= (TextView) findViewById(R.id.textViewMoodEdit);
            moodpertext.setVisibility(View.VISIBLE);
            moodpertext.setText(smood);
            String moodint = smood.replace("%","");
            int prgs = Integer.parseInt(moodint);
            Log.d("demo"," "+prgs);
            SeekBar seekbaredit = (SeekBar) findViewById(R.id.seekBarEdit);
            seekbaredit.setVisibility(View.VISIBLE);
            seekbaredit.setProgress(prgs);
            seekbaredit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    TextView per = (TextView) findViewById(R.id.textViewMoodEdit);
                    per.setText(progress+"%");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            Button save = (Button) findViewById(R.id.SubmitEdit);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("demo","mood");
                    TextView stmood = (TextView) findViewById(R.id.textViewMoodEdit);

                    String newMood = stmood.getText().toString();
                    Log.d("demo","new mood value is"+newMood);
                    if(newMood.equals("")||newMood.length()==0){
                        setResult(RESULT_CANCELED);
                    }else{
                        Intent intentEdit = new Intent();
                        intentEdit.putExtra(DisplayActivity.NewMood_key,newMood);
                        setResult(RESULT_OK,intentEdit);
                    }
                    finish();
                }
            });
        }
    }
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
