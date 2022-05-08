package com.example.healthytamagochi;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {

    List<ResultsData> list = new ArrayList<>();
    ArrayList<String> ids = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ListView listView;
    ArrayAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        load();

        listView = (ListView) findViewById(R.id.listview);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        ResultsData x = new ResultsData();
        x.setNev("SANDOR"); x.setJatek("08-05-2022 17:28:58, okostányér"); x.setPont("3");
        list.add(x);

    }

    public void load() {
        names.add("");
        db.collection("results")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ids.add(document.getId());
                            Log.d("-------", document.getId());
                        }


                        for (String id : ids) {
                            db.collection("results").document(id).collection("kids").get()
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            for (QueryDocumentSnapshot gyerekDocNev : task1.getResult()) {
                                                names.add(gyerekDocNev.getId());
                                                Log.d("--------", String.valueOf(names.size()));
                                                Log.d("---------", gyerekDocNev.getId());


//                                                for (String name : names) {
//                                                    db.collection("results").document(id).collection("kids").document(name)
//                                                            .get().addOnCompleteListener(task2 -> {
//                                                        if (task2.isSuccessful()) {
//                                                            DocumentSnapshot doc = task2.getResult();
//                                                            ResultsData data = doc.toObject(ResultsData.class);
//                                                            list.add(data);
//                                                            adapter.notifyDataSetChanged();
//                                                        }
//
//                                                    });
//                                                }
                                            }



                                        }
                                    });
                        }

                    }
                });
    }
}