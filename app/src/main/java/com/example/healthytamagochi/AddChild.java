package com.example.healthytamagochi;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddChild extends Activity {

    EditText weight, height, name;
    TextView birthdate;
    ImageView pic1, pic2, pic3;
    String selectedPic = "";
    String sex;
    int resID;
    LinearLayout option1, option2, option3;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String parentID = FirebaseAuth.getInstance().getCurrentUser().getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        name = findViewById(R.id.name);
        birthdate = findViewById(R.id.birthdate);
        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);
        pic1 = findViewById(R.id.pic1);
        pic2 = findViewById(R.id.pic2);
        pic3 = findViewById(R.id.pic3);
        option1 = findViewById(R.id.option1_layout);
        option2 = findViewById(R.id.option2_layout);
        option3 = findViewById(R.id.option3_layout);


        List<String> girl_avatars_List = Arrays.asList(getResources().getStringArray(R.array.girl_avatars));
        List<String> boy_avatars_List = Arrays.asList(getResources().getStringArray(R.array.boy_avatars));

        Spinner spinner_sex = findViewById(R.id.sex);
        ArrayAdapter<CharSequence> adapter_sex = ArrayAdapter.createFromResource(this,
                R.array.sex, R.layout.spinner_text);
        adapter_sex.setDropDownViewResource(R.layout.simple_spinner_text);
        spinner_sex.setAdapter(adapter_sex);


        spinner_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if (spinner_sex.getSelectedItem().toString().equals("Lány")) {
                    resID = getResources().getIdentifier(girl_avatars_List.get(0), "drawable", getPackageName());
                    pic1.setImageResource(resID);
                    resID = getResources().getIdentifier(girl_avatars_List.get(1), "drawable", getPackageName());
                    pic2.setImageResource(resID);
                    resID = getResources().getIdentifier(girl_avatars_List.get(2), "drawable", getPackageName());
                    pic3.setImageResource(resID);

                } else {
                    resID = getResources().getIdentifier(boy_avatars_List.get(0), "drawable", getPackageName());
                    pic1.setImageResource(resID);
                    resID = getResources().getIdentifier(boy_avatars_List.get(1), "drawable", getPackageName());
                    pic2.setImageResource(resID);
                    resID = getResources().getIdentifier(boy_avatars_List.get(2), "drawable", getPackageName());
                    pic3.setImageResource(resID);

                }
                sex = spinner_sex.getSelectedItem().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        option1.setOnClickListener(view -> {
            option1.setBackgroundResource(R.drawable.edittext_bg);
            option2.setBackgroundResource(R.drawable.transparent_bground);
            option3.setBackgroundResource(R.drawable.transparent_bground);
            if (sex.equals("Lány")) {
                selectedPic = girl_avatars_List.get(0);
            } else
                selectedPic = boy_avatars_List.get(0);
        });
        option2.setOnClickListener(view -> {
            option2.setBackgroundResource(R.drawable.edittext_bg);
            option1.setBackgroundResource(R.drawable.transparent_bground);
            option3.setBackgroundResource(R.drawable.transparent_bground);
            if (sex.equals("Lány")) {
                selectedPic = girl_avatars_List.get(1);
            } else
                selectedPic = boy_avatars_List.get(1);
        });
        option3.setOnClickListener(view -> {
            option3.setBackgroundResource(R.drawable.edittext_bg);
            option1.setBackgroundResource(R.drawable.transparent_bground);
            option2.setBackgroundResource(R.drawable.transparent_bground);
            if (sex.equals("Lány")) {
                selectedPic = girl_avatars_List.get(2);
            } else
                selectedPic = boy_avatars_List.get(2);
        });
    }

    public void dateSelecterOpen(View v) {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        DatePickerDialog dpd = new DatePickerDialog(
                this,
                (datePicker, i, i1, i2) -> {
                    String date = i + "." + (i1 + 1) + "." + i2;
                    birthdate.setText(date);
                },
                year, month, day
        );
        dpd.show();
    }

    public void Add(View v) {


        if (name.getText().toString().equals("") || weight.getText().toString().equals("")
                || height.getText().toString().equals("")
                || birthdate.getText().toString().equals("")
                || selectedPic.equals("")) {
            Toast.makeText(this, "Üres mező(k)!", Toast.LENGTH_LONG).show();
        } else {
            addKidToDatabase();
            Intent i = new Intent();
            i.setClass(this, Homepage.class);
            startActivity(i);
        }
    }

    public void addKidToDatabase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<String> kids = new ArrayList<>();
        String parentID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("kids")
                .whereEqualTo("parentID", parentID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                kids.add(document.getString("name").toLowerCase().trim());
                            }
                            if (kids.contains(name.getText().toString().toLowerCase().trim())) {
                                Toast.makeText(getApplicationContext(), "Ilyen nevű gyerek már van.", Toast.LENGTH_LONG).show();
                                return;
                            }
                            if (kids.isEmpty() || !kids.contains(name.getText().toString().toLowerCase().trim())) {
                                add();
                            }
                        }
                    }
                });
    }

    public void add() {
        Map<String, Object> kid = new HashMap<>();
        kid.put("parentID", parentID);
        kid.put("name", name.getText().toString());
        kid.put("sex", sex);
        kid.put("kg", weight.getText().toString());
        kid.put("cm", height.getText().toString());
        kid.put("birth", birthdate.getText().toString());
        kid.put("avatar", selectedPic);
        kid.put("registration", Timestamp.now());

        db.collection("kids")
                .add(kid)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getApplicationContext(), "Sikeres hozzáadás.", Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Hiba!", Toast.LENGTH_LONG).show();
                    }
                });

        Map<String, Object> data = new HashMap<>();
        data.put("registered", Timestamp.now());
        db.collection("results")
                .document(parentID)
                .collection("kids")
                .document(name.getText().toString())
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
}



