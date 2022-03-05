package com.example.healthytamagochi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;


import com.google.firebase.auth.FirebaseAuth;
import java.util.Random;


public class Homepage extends AppCompatActivity {

    ImageView img;
    Random rnd;
    int nextActivityID;
    boolean firstGame=true;

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

    public void onBackPressed(){
        //kilép az alkalmazásból, bejelentkezve marad
        this.finishAffinity();
    }

    public void addKid_onClick(View v) {
        Intent i = new Intent();
        i.setClass(this, AddChild.class);
        startActivity(i);

    }


    public void play_onClick(View v){
        rnd = new Random();
        nextActivityID = rnd.nextInt(4 - 1) + 1;   //1,2,3 lehet

        Intent i = new Intent();
        i.putExtra("selectedPic","boy1");
        i.putExtra("prevActivityID",nextActivityID);
        i.putExtra("firstGame",firstGame);
        if(nextActivityID==1)
        {
            i.setClass(this,Questions1.class);
        }
        else if(nextActivityID==2)
        {
            i.setClass(this,OkosTanyer.class);
        }
        else if(nextActivityID==3)
        {
            i.setClass(this,TeethBrushing.class);
        }
        startActivity(i);

    }

    public void signOutClick(View v) {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent();
        i.setClass(this, MainActivity.class);
        startActivity(i);
    }
}