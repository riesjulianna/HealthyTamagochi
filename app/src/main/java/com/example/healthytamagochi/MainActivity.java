package com.example.healthytamagochi;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            skip();
        }
    }

    public void onBackPressed(){
        this.finishAffinity();
    }

    public void signInClick(View v) {
        Intent i = new Intent();
        i.setClass(this, SignIn.class);
        startActivity(i);
    }

    public void signUpClick(View v) {
        Intent i = new Intent();
        i.setClass(this, Registration.class);
        startActivity(i);
    }

    public void skip() {
        Intent i = new Intent().setClass(this, Homepage.class);
        startActivity(i);
    }

}