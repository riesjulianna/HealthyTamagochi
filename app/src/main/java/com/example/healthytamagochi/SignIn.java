package com.example.healthytamagochi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    EditText username, password;
    String loginData;
    DatabaseReference usersRef;
    boolean exists = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    public void signInClick(View v) {
        if (username.getText().toString().equals("") || username.getText().toString().equals("")) {
            Toast.makeText(this, "Üres mező(k)!", Toast.LENGTH_LONG).show();
        } else {
            checkLogin();
            if (exists) {
                Intent i = new Intent();
                i.setClass(this, Homepage.class);
                startActivity(i);
            }
        }
    }

    private void checkLogin() {
        loginData = username.getText().toString() + password.getText().toString();
        usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        usersRef.orderByChild("loginData").equalTo(loginData)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            exists = true;
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}


