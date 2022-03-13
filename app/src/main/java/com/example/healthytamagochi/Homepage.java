package com.example.healthytamagochi;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Homepage extends AppCompatActivity {

    ImageView img;
    Random rnd;
    Spinner selectedKid;
    TextView proba;
    int nextActivityID;
    boolean firstGame = true;
    int numberOfQuestions;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<String> kidNamesList = new ArrayList<>();
    String parentID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        proba=findViewById(R.id.proba_tv);

        //GET KIDS FROM DB
        db.collection("kids")
                .whereEqualTo("parentID", parentID)
                .addSnapshotListener((documentSnapshots, error) -> {
                    for (DocumentSnapshot snapshot : documentSnapshots) {
                        kidNamesList.add(snapshot.getString("name"));
                    }
                });
        //END************

        img = findViewById(R.id.avatar_img);
        selectedKid = findViewById(R.id.selectedKid);
        kidNamesList.add("Válasszon egy gyermeket!"); //ha hozzáadok egy bármit akkor oké, enélkül nem
        // EZ SZARUL MŰKÖDIK A MANUÁLISAN HOZZÁADOTT STRING NÉLKÜL
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.spinner_text, kidNamesList);
        adapter.setDropDownViewResource(R.layout.simple_spinner_text);
        selectedKid.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//        EZ A RÉGI XML-BŐL JÓL MŰKÖDÖTT
//        Spinner spinner_selectedKid = findViewById(R.id.selectedKid);
//        ArrayAdapter<CharSequence> adapter_selectedKid =
//                ArrayAdapter.createFromResource(this, R.array.kids, R.layout.spinner_item);
//        adapter_selectedKid.setDropDownViewResource(R.layout.spinner_dropdown_item);
//        spinner_selectedKid.setAdapter(adapter_selectedKid);

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
        rnd = new Random();
        nextActivityID = rnd.nextInt(4 - 1) + 1;   //1,2,3 lehet

        Intent i = new Intent();
        i.putExtra("selectedPic","boy1");
        i.putExtra("selectedKid",selectedKid.getSelectedItem().toString().trim());
        i.putExtra("prevActivityID",nextActivityID);
        i.putExtra("firstGame",firstGame);
        i.putExtra("NoOfQ",numberOfQuestions);
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

    public int getNumOfQuestions(){
        db.collection("questions").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        numberOfQuestions = ((Integer) task.getResult().size());
                    } else {
                        Toast.makeText(getApplicationContext(), "Could not get number of questions.", Toast.LENGTH_LONG).show();
                    }
                });
        return numberOfQuestions;
    }


}