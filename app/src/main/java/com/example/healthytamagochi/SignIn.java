package com.example.healthytamagochi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends Activity {

    EditText email, password;
    private FirebaseAuth mAuth;
    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.emailParent);
        password = findViewById(R.id.password);
        forgotPassword = findViewById(R.id.forgotPassword);
        forgotPassword.setPaintFlags(forgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        forgotPassword.setOnClickListener(view -> {
            final AlertDialog.Builder alert = new AlertDialog.Builder(SignIn.this);
            final EditText input = new EditText(SignIn.this);
            input.setSingleLine();
            alert.setTitle("Adja meg a regisztrációhoz használt e-mail címét!");
            alert.setView(input);
            alert.setPositiveButton("Ok", (dialog, whichButton) -> {
                String email = input.getText().toString().trim();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignIn.this,
                                        "A jelszó visszaállításhoz szükséges e-mailt elküldtük."
                                        , Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(SignIn.this,
                                        "Ezzel az e-mail címmel nem találtunk regisztrációt."
                                        , Toast.LENGTH_LONG).show();
                            }
                        });
            }).setNegativeButton("Mégse", (dialog, whichButton) -> dialog.cancel());
            alert.show();
        });

    }

    public void signInClick(View v) {
        try {
            InputMethodManager imp = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imp.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            return;
        }
        if (email.getText().toString().equals("") || password.getText().toString().equals("")) {
            Toast.makeText(this, "Üres mező(k)!", Toast.LENGTH_LONG).show();
        }
        if(email.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
            Intent admin = new Intent();
            admin.setClass(this, Admin.class);
            startActivity(admin);
        }else{
            String mail = email.getText().toString().trim();
            String pw = password.getText().toString().trim();
            mAuth.signInWithEmailAndPassword(mail, pw)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user.isEmailVerified())
                                go();
                            else Toast.makeText(SignIn.this,"Igazolja vissza e-mail címét.",Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SignIn.this, "Hibás e-mail vagy jelszó.",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        }

    }


    public void go() {
        Intent i = new Intent();
        i.setClass(this, Homepage.class);
        startActivity(i);
    }
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
            Intent i = new Intent();
            i.setClass(this,MainActivity.class);
            startActivity(i);

    }

}


