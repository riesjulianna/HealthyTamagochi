package com.example.healthytamagochi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Homepage extends Activity {

    ImageView avatar_img, downArrow, loading, exit_kep, pearplus, info;
    Random rnd;
    Spinner selectedKid;
    TextView point, eddigiPont, exit_tv;
    int nextActivityID, points;
    boolean firstGame = true;
    int numberOfQuestions;
    String avatar, all;
    Button addKid, play;
    RelativeLayout relativeLayout;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<String> kidNamesList = new ArrayList<>();
    String parentID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        point = findViewById(R.id.proba_tv);
        exit_kep = findViewById(R.id.exit_kep);
        exit_tv = findViewById(R.id.exit_tv);
        addKid = findViewById(R.id.addKid_btn);
        relativeLayout = findViewById(R.id.relativeLayout3);
        selectedKid = findViewById(R.id.selectedKid);
        downArrow = findViewById(R.id.drop_img);
        avatar_img = findViewById(R.id.avatar_img);
        play = findViewById(R.id.play_btn);
        loading = findViewById(R.id.loadingImg);
        eddigiPont = findViewById(R.id.pontok);
        pearplus = findViewById(R.id.pearplus_kep);
        info = findViewById(R.id.information_kep);

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            loading.setBackgroundResource(R.drawable.pearplus_felirat);
        }


        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                loading.setVisibility(View.INVISIBLE);
                point.setVisibility(View.INVISIBLE);
                eddigiPont.setVisibility(View.INVISIBLE);
                exit_kep.setVisibility(View.VISIBLE);
                exit_tv.setVisibility(View.VISIBLE);
                addKid.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.VISIBLE);
                selectedKid.setVisibility(View.VISIBLE);
                downArrow.setVisibility(View.VISIBLE);
                avatar_img.setVisibility(View.VISIBLE);
                play.setVisibility(View.VISIBLE);
                pearplus.setVisibility(View.VISIBLE);
                info.setVisibility(View.VISIBLE);


            }

        }, 2000);

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
                if (!selectedKid.getSelectedItem().toString().contains("?")) {
                    showPoints(selectedKid.getSelectedItem().toString());
                    db.collection("kids")
                            .whereEqualTo("name", selectedKid.getSelectedItem().toString())
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        avatar = document.getString("avatar");
                                        String uri = "@drawable/" + avatar;
                                        int imageRes = getResources().getIdentifier(uri, null, getPackageName());
                                        Drawable res = ContextCompat.getDrawable(getApplicationContext(), imageRes);
                                        avatar_img.setImageDrawable(res);
                                        avatar_img.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Error showing avatar.",
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                } else {
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

    @SuppressLint("SetTextI18n")
    public void showPoints(String kid) {
        List<String> pointsList = new ArrayList<>();
        db.collection("results").document(parentID)
                .collection("kids").document(kid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Map<String, Object> allPoints = document.getData();
                            Log.d("db", String.valueOf(allPoints.size() - 1));
                            all = String.valueOf((allPoints.size() - 1) * 3);

                            Map<String, Object> reachedPoints = document.getData();
                            if (reachedPoints != null) {
                                for (Map.Entry<String, Object> entry : reachedPoints.entrySet()) {
                                    pointsList.add(entry.getValue().toString());
                                }
                            }
                            pointsList.removeIf(s -> s.length() > 1);
                            for (String s : pointsList) {
                                Log.d("elért pont:", s);
                                points += Integer.parseInt(s);
                                Log.d("pontok osszeadva", String.valueOf(points));
                            }
                        }
                        point.setText(points + " / " + all);
                    }
                });
        eddigiPont.setVisibility(View.VISIBLE);
        point.setVisibility(View.VISIBLE);
        pointsList.clear();
        all = "";
        points = 0;
    }


    public void popupOnClick(View view) {

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}


