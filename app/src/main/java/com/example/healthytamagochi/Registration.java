package com.example.healthytamagochi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Registration extends AppCompatActivity {

     EditText emailParent,username,city,weight,height,password,parentEducation;
     TextView birthdate;
     RadioGroup sex;
     RadioButton girl,boy;
     CheckBox acceptRules;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        emailParent=findViewById(R.id.emailParent);
        username=findViewById(R.id.username);
        city=findViewById(R.id.city);
        birthdate=findViewById(R.id.birthdate);
        weight=findViewById(R.id.weight);
        height=findViewById(R.id.height);
        sex=findViewById(R.id.sex);
        girl=findViewById(R.id.girl);
        boy=findViewById(R.id.boy);
        acceptRules=findViewById(R.id.acceptRules);
        password=findViewById(R.id.password);
        parentEducation=findViewById(R.id.parentEducation);


    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
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

    public void registrationClick(View v)
    {
        if(isValidEmail(emailParent.getText()))
        {
           if(boy.isChecked() || girl.isChecked()) {
               if(username.getText().toString().equals("") || city.getText().toString().equals("")
                       || weight.getText().toString().equals("") || height.getText().toString().equals("") || birthdate.getText().toString().equals("")
                         ||  password.getText().toString().equals("") || parentEducation.getText().toString().equals("") )
               {
                   Toast.makeText(this, "Üres mező(k)!", Toast.LENGTH_LONG).show();
               }
               else {
                   if(acceptRules.isChecked())
                   {
                       Intent i = new Intent();
                       i.setClass(this,MainActivity.class);
                       startActivity(i);
                   }
                   else
                   {
                       Toast.makeText(this, "Nem fogadtad el az Adatvédelmi Tájékoztatót!", Toast.LENGTH_LONG).show();
                   }

               }

            }
           else
           {
               Toast.makeText(this, "Nem választott nemet!!", Toast.LENGTH_LONG).show();
           }

        }
        else{
            Toast.makeText(this, "Hibás e-mail cím formátum!", Toast.LENGTH_LONG).show();
        }

    }
}