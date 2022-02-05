package com.example.healthytamagochi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {

    EditText username,password;
    boolean firstSignIn;
    String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

    }

    public void signInClick(View v)
    {

        firstSignIn=true;   // valahogy az adatbázisból kellene kinyerni hogy első bejelentkezés-e
        sex="girl";   // szintén  az adatbázisból kellene a nemet betölteni

        if(username.getText().toString().equals("") || username.getText().toString().equals(""))
        {
            Toast.makeText(this, "Üres mező(k)!", Toast.LENGTH_LONG).show();
        }
        else if(firstSignIn==true)
        {
            Intent i = new Intent();
            i.setClass(this,SetCharacter.class);
            i.putExtra("sex",sex);
            startActivity(i);
        }

    }


}