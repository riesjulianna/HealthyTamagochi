package com.example.healthytamagochi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Homepage extends Activity {

    ImageView avatar_img,downArrow,loading;
    Random rnd;
    Spinner selectedKid;
    TextView proba,signOut;
    int nextActivityID;
    boolean firstGame = true;
    int numberOfQuestions;
    String avatar;
    Button addKid,play;
    RelativeLayout relativeLayout;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<String> kidNamesList = new ArrayList<>();
    String parentID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        proba = findViewById(R.id.proba_tv);
        signOut=findViewById(R.id.signOut_btn);
        addKid=findViewById(R.id.addKid_btn);
        relativeLayout=findViewById(R.id.relativeLayout3);
        selectedKid=findViewById(R.id.selectedKid);
        downArrow=findViewById(R.id.drop_img);
        avatar_img = findViewById(R.id.avatar_img);
        play=findViewById(R.id.play_btn);
        loading=findViewById(R.id.loadingScreen);

        loading.setBackgroundResource(R.drawable.loading_screen);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.INVISIBLE);
                signOut.setVisibility(View.VISIBLE);
                addKid.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.VISIBLE);
                selectedKid.setVisibility(View.VISIBLE);
                downArrow.setVisibility(View.VISIBLE);
                avatar_img.setVisibility(View.VISIBLE);
                play.setVisibility(View.VISIBLE);
                proba.setVisibility(View.VISIBLE);
            }
        },2000);

        //GET KIDS FROM DB
        db.collection("kids")
                .whereEqualTo("parentID", parentID)
                .addSnapshotListener((documentSnapshots, error) -> {
                    for (DocumentSnapshot snapshot : documentSnapshots) {
                        kidNamesList.add(snapshot.getString("name"));
                    }
                });
        //END************

        kidNamesList.add("Ki játszik?");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.spinner_text, kidNamesList);
        adapter.setDropDownViewResource(R.layout.simple_spinner_text);
        selectedKid.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        selectedKid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!selectedKid.getSelectedItem().toString().contains("?")) {
                    db.collection("kids")
                            .whereEqualTo("name", selectedKid.getSelectedItem().toString())
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        avatar = document.getString("avatar");
                                        String uri = "@drawable/" + avatar;
                                        int imageRes = getResources().getIdentifier(uri, null, getPackageName());
                                        Drawable res = getResources().getDrawable(imageRes);
                                        avatar_img.setImageDrawable(res);
                                        avatar_img.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    //error handling?
                                }
                            });
                }else{
                    avatar_img.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //set img null
        avatar_img.setVisibility(View.INVISIBLE);

        //load number of questions in database
        numberOfQuestions = getNumOfQuestions();
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
        if (selectedKid.getSelectedItem().toString().contains("?")) {
            Toast.makeText(getApplicationContext(), "Válasszon gyereket!", Toast.LENGTH_LONG).show();
        } else {
            rnd = new Random();
            nextActivityID = rnd.nextInt(4 - 1) + 1;   //1,2,3 lehet

            Intent i = new Intent();
            i.putExtra("selectedPic", avatar);
            i.putExtra("selectedKid", selectedKid.getSelectedItem().toString().trim());
            i.putExtra("prevActivityID", nextActivityID);
            i.putExtra("firstGame", firstGame);
            i.putExtra("NoOfQ", numberOfQuestions);
            if (nextActivityID == 1) {
                i.setClass(this, Questions1.class);
            } else if (nextActivityID == 2) {
                i.setClass(this, OkosTanyer.class);
            } else if (nextActivityID == 3) {
                i.setClass(this, TeethBrushing.class);
            }
            startActivity(i);
        }
    }

    public void signOutClick(View v) {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent();
        i.setClass(this, MainActivity.class);
        startActivity(i);
    }

    public int getNumOfQuestions() {
        db.collection("questions").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        numberOfQuestions = task.getResult().size();
                    } else {
                        Toast.makeText(getApplicationContext(), "Could not get number of questions.", Toast.LENGTH_LONG).show();
                    }
                });
        return numberOfQuestions;
    }


}