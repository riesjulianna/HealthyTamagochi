package com.example.healthytamagochi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    EditText email, password;
    private FirebaseAuth mAuth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    //boolean emailVerified = user.isEmailVerified();
    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.emailParent);
        password = findViewById(R.id.password);
        forgotPassword=findViewById(R.id.forgotPassword);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            //reload();
        }
    }

    public void signInClick(View v) {
        if (email.getText().toString().equals("") || password.getText().toString().equals("")) {
            Toast.makeText(this, "Üres mező(k)!", Toast.LENGTH_LONG).show();
        }
//        if(!emailVerified){
//            Toast.makeText(this, "Igazolja vissza e-mail címét.", Toast.LENGTH_LONG).show();
//        }
        else {
            String mail = email.getText().toString().trim();
            String pw = password.getText().toString().trim();
            mAuth.signInWithEmailAndPassword(mail, pw)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                go();
                                FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);
                            } else {
                                Toast.makeText(SignIn.this, "Hibás e-mail vagy jelszó.",
                                        Toast.LENGTH_LONG).show();
                                forgotPassword.setVisibility(View.VISIBLE);
                                forgotPassword.setPaintFlags(forgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                                //updateUI(null);
                            }
                        }
                    });
        }

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //jöhet ide a kód
                Toast.makeText(SignIn.this, "Megnyomtad a szöveget!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
    public void go() {
        Intent i = new Intent();
        i.setClass(this, Homepage.class);
        startActivity(i);
    }
}


