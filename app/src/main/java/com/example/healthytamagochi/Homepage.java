package com.example.healthytamagochi;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Homepage extends AppCompatActivity {

    ImageView img;
    Random rnd;
    int nextActivityID;
    boolean firstGame = true;
    Spinner selectedKid;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<CharSequence> kidNamesList = new ArrayList<CharSequence>();
    String parentID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_homepage);
        img = findViewById(R.id.avatar_img);
        Spinner spinner = (Spinner) findViewById(R.id.selectedKid);

        //GET KIDS*******
        db.collection("kids")
                .whereEqualTo("parentID", parentID)
                .addSnapshotListener((documentSnapshots, error) -> {
                    for (DocumentSnapshot snapshot : documentSnapshots) {
                        kidNamesList.add(snapshot.getString("name"));
                    }
                });
        //END************

        // EZ SZARUL MŰKÖDIK...
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                this, android.R.layout.simple_spinner_item, kidNamesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();

//        EZ A RÉGI XML-BŐL JÓL MŰKÖDÖTT
//        Spinner spinner_selectedKid = findViewById(R.id.selectedKid);
//        ArrayAdapter<CharSequence> adapter_selectedKid =
//                ArrayAdapter.createFromResource(this, R.array.kids, R.layout.spinner_item);
//        adapter_selectedKid.setDropDownViewResource(R.layout.spinner_dropdown_item);
//        spinner_selectedKid.setAdapter(adapter_selectedKid);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //selected gyerek
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onBackPressed() {
        //kilép az alkalmazásból, bejelentkezve marad
        this.finishAffinity();
    }

    public void addKid_onClick(View v) {
        Intent i = new Intent();
        i.setClass(this, AddChild.class);
        startActivity(i);

    }


    public void play_onClick(View v) {
        rnd = new Random();
        nextActivityID = rnd.nextInt(4 - 1) + 1;   //1,2,3 lehet

        Intent i = new Intent();
        i.putExtra("selectedPic","boy1");
        i.putExtra("selectedKid",selectedKid.getSelectedItem().toString().trim());
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