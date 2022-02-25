package com.example.healthytamagochi;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

     EditText emailParent,username,password,parentEducation;
     CheckBox acceptRules;
     Spinner place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        emailParent=findViewById(R.id.emailParent);
        username=findViewById(R.id.username);
        acceptRules=findViewById(R.id.acceptRules);
        password=findViewById(R.id.password);
        parentEducation=findViewById(R.id.parentEducation);
        place=findViewById(R.id.residences);

        ArrayAdapter<CharSequence> adapter_residence = ArrayAdapter.createFromResource(this,R.array.residences, R.layout.spinner_item);
        adapter_residence.setDropDownViewResource(R.layout.spinner_dropdown_item);
        place.setAdapter(adapter_residence);

    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    public void registrationClick(View v)
    {
        String selected = place.getSelectedItem().toString();
        if(isValidEmail(emailParent.getText()))
        {
               if(username.getText().toString().equals("")
                         ||  password.getText().toString().equals("") || parentEducation.getText().toString().equals("") ) {
                   Toast.makeText(this, "Üres mező(k)!", Toast.LENGTH_LONG).show();
               } else {
                   if(acceptRules.isChecked()) {
                       DatabaseReference myDB = FirebaseDatabase.getInstance().getReference();
                       myDB.child("users").child("username").setValue(username.getText().toString());
                       myDB.child("users").child("password").setValue(password.getText().toString());
                       myDB.child("users").child("edu").setValue(parentEducation.getText().toString());
                       myDB.child("users").child("email").setValue(emailParent.getText().toString());
                       myDB.child("users").child("place").setValue(selected);

                       Intent i = new Intent();
                       i.setClass(this,MainActivity.class);
                       startActivity(i);
                   } else {
                       Toast.makeText(this, "Nem fogadtad el az Adatvédelmi Tájékoztatót!", Toast.LENGTH_LONG).show();
                   } } } else{
            Toast.makeText(this, "Hibás e-mail cím formátum!", Toast.LENGTH_LONG).show();
        }
    }
}