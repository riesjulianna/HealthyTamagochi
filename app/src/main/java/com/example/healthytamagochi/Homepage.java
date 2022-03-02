package com.example.healthytamagochi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

public class Homepage extends AppCompatActivity {

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        img = findViewById(R.id.avatar_img);

        Spinner spinner_selectedKid = findViewById(R.id.selectedKid);
        ArrayAdapter<CharSequence> adapter_selectedKid = ArrayAdapter.createFromResource(this,
                R.array.kids, R.layout.spinner_item);
        adapter_selectedKid.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner_selectedKid.setAdapter(adapter_selectedKid);
    }

    public void addKid_onClick(View v) {
        Intent i = new Intent();
        i.setClass(this, AddChild.class);
        startActivity(i);

    }

    public void play_onClick(View v) {
        Intent i = new Intent();
        i.putExtra("selectedPic", "boy1");
        i.setClass(this, Questions1.class);
        startActivity(i);

    }

    public void signOutClick(View v) {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent();
        i.setClass(this, MainActivity.class);
        startActivity(i);
    }
}