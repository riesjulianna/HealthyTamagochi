package com.example.healthytamagochi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddChild extends AppCompatActivity {

    EditText weight,height;
    TextView birthdate;
    ImageView pic1,pic2;
    String selectedPic;
    RadioButton rb1,rb2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        birthdate=findViewById(R.id.birthdate);
        weight=findViewById(R.id.weight);
        height=findViewById(R.id.height);
        pic1=findViewById(R.id.pic1) ;
        pic2=findViewById(R.id.pic2) ;
        rb1=findViewById(R.id.rb1);
        rb2=findViewById(R.id.rb2);

        Spinner spinner_sex = (Spinner) findViewById(R.id.sex);
        ArrayAdapter<CharSequence> adapter_sex = ArrayAdapter.createFromResource(this,
                R.array.sex, R.layout.spinner_text);
        adapter_sex.setDropDownViewResource(R.layout.simple_spinner_text);
        spinner_sex.setAdapter(adapter_sex);


        spinner_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if(spinner_sex.getSelectedItem().toString().equals("Lány")) {
                    pic1.setImageResource(R.drawable.girl1);
                    pic2.setImageResource(R.drawable.girl2);
                }
                else
                {
                     pic1.setImageResource(R.drawable.boy1);
                    pic2.setImageResource(R.drawable.boy2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


    }

    public void dateSelecterOpen(View v){
        Calendar c = Calendar.getInstance();
        int day=c.get(Calendar.DAY_OF_MONTH);
        int month=c.get(Calendar.MONTH);
        int year=c.get(Calendar.YEAR);

        DatePickerDialog dpd = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker,
                                          int i, int i1, int i2) {
                        String date = i+"."+(i1+1)+"."+i2;
                        birthdate.setText(date);
                    }
                },
                year, month, day
        );
        dpd.show();
    }

    public void Add(View v) {
        if(rb1.isChecked())
        {
            selectedPic="girl1";
        }
        else if(rb2.isChecked())
        {
            selectedPic="girl2";
        }



        if(weight.getText().toString().equals("") || height.getText().toString().equals("") || birthdate.getText().toString().equals("") || !(rb1.isChecked() || rb2.isChecked()) )
        {

            Toast.makeText(this, "Üres mező(k)!", Toast.LENGTH_LONG).show();
        }
        else
        {

            Intent i = new Intent();
            i.setClass(this,Homepage.class);
            startActivity(i);
        }
    }
}