package com.example.healthytamagochi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SetCharacter extends AppCompatActivity {

    String sex;
    ImageView pic1,pic2;
    RadioButton rb1,rb2;
    RadioGroup radioGroup;
    String selectedPic;  //így küldöm amíg nincs benne az adatbázisba, majd később onnan kéne lekérni

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_character);

        pic1=findViewById(R.id.pic1);
        pic2=findViewById(R.id.pic2);
        rb1=findViewById(R.id.rb1);
        rb2=findViewById(R.id.rb2);


        Bundle b = getIntent().getExtras();
        if (b != null) {
            sex = b.getString("sex");
        }

        if(sex.equals("girl"))
        {
            pic1.setImageResource(R.drawable.girl1);
            pic2.setImageResource(R.drawable.girl2);


        }
        else if(sex.equals("boy"))
        {
            pic1.setImageResource(R.drawable.boy1);
            pic2.setImageResource(R.drawable.boy2);
        }
    }

    public void StartClick(View v)
    {
        if(rb1.isChecked() || rb2.isChecked())
        {
            if(sex.equals("girl"))
            {
                if(rb1.isChecked())
                {
                    selectedPic="girl1";
                }
                else
                    selectedPic="girl2";
            }
            else if(sex.equals("boy"))
            {
                if(rb1.isChecked())
                {
                    selectedPic="boy1";
                }
                else
                    selectedPic="boy2";
            }
            Intent i = new Intent();
            i.setClass(this,Questions1.class);
            i.putExtra("selectedPic",selectedPic);
            startActivity(i);
        }
        else
        {
            Toast.makeText(this, "Válassz karaktert!", Toast.LENGTH_LONG).show();
        }


    }
}