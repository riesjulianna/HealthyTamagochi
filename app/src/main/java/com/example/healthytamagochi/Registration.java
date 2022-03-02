package com.example.healthytamagochi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    EditText password, password2, email, parentEducation;
    CheckBox acceptRules;
    TextView validPassword;
    Spinner residence;
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        email = findViewById(R.id.emailParent);
        acceptRules = findViewById(R.id.acceptRules);
        password = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);
        parentEducation = findViewById(R.id.parentEducation);
        residence = findViewById(R.id.residences);
        validPassword = findViewById(R.id.validPassword);

        mAuth = FirebaseAuth.getInstance();

        ArrayAdapter<CharSequence> adapter_residence = ArrayAdapter.createFromResource(this, R.array.residences, R.layout.spinner_item);
        adapter_residence.setDropDownViewResource(R.layout.spinner_dropdown_item);
        residence.setAdapter(adapter_residence);

        password2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pass = password.getText().toString();
                String pass2 = password2.getText().toString();
                if (pass.length() < 6) {
                    validPassword.setText(("Túl rövid jelszó."));
                    validPassword.setTextColor(Color.RED);
                }
                if (!(pass2.equals(pass))) {
                    validPassword.setText("A két jelszó nem egyezik meg!");
                    validPassword.setTextColor(Color.RED);
                }
                if (pass2.equals(pass) && pass.length() >= 6) {
                    validPassword.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void registrationClick(View v) {
        if (isValidEmail(email.getText())) {
            if (password.getText().toString().equals("") ||
                    password.length() < 6 ||
                    parentEducation.getText().toString().equals("") ||
                    password2.getText().toString().equals("")) {
                Toast.makeText(this, "Üres mező(k) vagy túl rövid jelszó (min. 6 karakter.)", Toast.LENGTH_LONG).show();
            } else {
                if (acceptRules.isChecked()) {
                    String mail = email.getText().toString().trim();
                    String pw = password.getText().toString().trim();
                    String edu = parentEducation.getText().toString().trim();
                    String res = residence.getSelectedItem().toString().trim();
                    mAuth.createUserWithEmailAndPassword(mail, pw)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        addUserToDB();
                                        go();
                                        Toast.makeText(getApplicationContext(), "Sikeres regisztráció.", Toast.LENGTH_LONG).show();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        user.sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(getApplicationContext(), "Visszaigazoló e-mail elküldve.", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(Registration.this, "Invalid email vagy már regisztrált.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(this, "Nem fogadtad el az Adatvédelmi Tájékoztatót!", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            Toast.makeText(this, "Hibás e-mail cím formátum!", Toast.LENGTH_LONG).show();
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void go() {
        Intent i = new Intent();
        i.setClass(this, MainActivity.class);
        startActivity(i);
    }

    public void addUserToDB() {
        String mail = email.getText().toString().trim();
        String edu = parentEducation.getText().toString().trim();
        String res = residence.getSelectedItem().toString().trim();
        Map<String, Object> user = new HashMap<>();
        user.put("email", mail);
        user.put("education", edu);
        user.put("residence", res);
        db.collection("users").add(user);
    }
}
//User user = new User(uname, pw, mail, edu, res, ld);
//        Map<String, Object> user = new HashMap<>();
//        user.put("username", username.getText().toString());
//        user.put("password", password.getText().toString());
//        user.put("email", email.getText().toString());
//        db.collection("users")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("TAG", "Error adding document", e);
//                    }
//                });


//        String uname = username.getText().toString();
//        String pw = password.getText().toString();
//        String mail = email.getText().toString();
//        String edu = parentEducation.getText().toString();
//        String res = residence.getSelectedItem().toString();
//        String ld = uname+pw;
//        usersRef.push().setValue(user);


//if task is successfullba
//                            User user = new User(mail,pw);
//                            FirebaseDatabase.getInstance().getReference("users")
//                                    .child((FirebaseAuth.getInstance().getCurrentUser().getUid()))
//                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if(task.isSuccessful()){

//                                    }else{
//                                        Toast.makeText(Registration.this,"fail",Toast.LENGTH_LONG).show();
//                                    }
//                                }
//                            });