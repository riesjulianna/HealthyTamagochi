package com.example.healthytamagochi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class SetCharacter extends AppCompatActivity {

    String sex;
    ImageView pic1,pic2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_character);

        pic1=findViewById(R.id.pic1);
        pic2=findViewById(R.id.pic2);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            sex = b.getString("sex");
        }

        if(sex.equals("girl"))
        {
           pic1.setImageResource(R.drawable.cat_one);
           pic2.setImageResource(R.drawable.cat_two);


        }
        else if(sex.equals("boy"))
        {
            pic1.setImageResource(R.drawable.dog_one);
            pic2.setImageResource(R.drawable.dog_two);
        }
    }
}