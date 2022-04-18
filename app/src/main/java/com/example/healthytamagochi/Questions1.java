package com.example.healthytamagochi;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Questions1 extends Activity {

    ImageView avatar;
    String selectedPic, selectedKid, response;
    TextView time, question, a1, a2, a3, a4, name;
    int hour = 0;
    int min = 0;
    int sec = 0;
    int prevActivityID;
    boolean firstGame;
    LinearLayout option1, option2, option3, option4;
    String A1, A2, A3, A4;
    int points = 3;
    int rndQuestion;
    Boolean next = false;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions1);

        avatar = findViewById(R.id.avatar);
        name = findViewById(R.id.textViewName);
        time = findViewById(R.id.time_tv);
        option1 = findViewById(R.id.layout_1);
        option2 = findViewById(R.id.layout_2);
        option3 = findViewById(R.id.layout_3);
        option4 = findViewById(R.id.layout_4);

        question = findViewById(R.id.testViewQ);
        a1 = findViewById(R.id.a1);
        a2 = findViewById(R.id.a2);
        a3 = findViewById(R.id.a3);
        a4 = findViewById(R.id.a4);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            selectedPic = b.getString("selectedPic");
            selectedKid = b.getString("selectedKid");
            name.setText(selectedKid);
            prevActivityID = b.getInt("prevActivityID");
            firstGame = b.getBoolean("firstGame");
        }
        if (!firstGame) {
            hour = b.getInt("hour");
            min = b.getInt("min");
            sec = b.getInt("sec");
        } else {
            firstGame = false;
        }

        String uri = "@drawable/" + selectedPic;
        int imageRes = getResources().getIdentifier(uri, null, getPackageName());
        Drawable res = getResources().getDrawable(imageRes);
        avatar.setImageDrawable(res);

        Timer T = new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {

                    if (sec % 60 == 0 && sec != 0) {
                        min++;
                        sec = 0;
                    }
                    if (min % 60 == 0 && min != 0) {
                        hour++;
                        min = 0;
                    }
                    if (hour % 24 == 0) {
                        hour = 0;
                    }
                    @SuppressLint("DefaultLocale") String curTime = String.format("%02d : %02d : %02d", hour, min, sec);
                    time.setText(curTime); //change clock to your textview
                    if (min >= 15 || hour > 0) {
                        time.setTextColor(Color.parseColor("#FF1111"));
                    }
                    sec++;

                });
            }
        }, 1000, 1000);

        option1.setOnClickListener(view -> {
            if (points > 0) {
                if (A1.contains("0")) {
                    option1.setBackgroundResource((R.drawable.red_round_bground));
                    points--;
                }
                if (A1.contains("3")) {
                    option1.setBackgroundResource(R.drawable.green_round_bground);
                    option2.setBackgroundResource(R.drawable.red_round_bground);
                    option3.setBackgroundResource(R.drawable.red_round_bground);
                    option4.setBackgroundResource(R.drawable.red_round_bground);
                    option1.setClickable(false);
                    option2.setClickable(false);
                    option3.setClickable(false);
                    option4.setClickable(false);
                    savePoints();
                    next = true;
                }
            } else {
                // 0 pont
                Toast.makeText(getApplicationContext(), "Sajnos ez most nem sikerült.",
                        Toast.LENGTH_LONG).show();
                savePoints();
                next = true;

            }
        });
        option2.setOnClickListener(view -> {
            if (points > 0) {
                if (A2.contains("0")) {
                    option2.setBackgroundResource((R.drawable.red_round_bground));
                    points--;
                }
                if (A2.contains("3")) {
                    option2.setBackgroundResource(R.drawable.green_round_bground);
                    option1.setBackgroundResource(R.drawable.red_round_bground);
                    option3.setBackgroundResource(R.drawable.red_round_bground);
                    option4.setBackgroundResource(R.drawable.red_round_bground);
                    option1.setClickable(false);
                    option2.setClickable(false);
                    option3.setClickable(false);
                    option4.setClickable(false);
                    savePoints();
                    next = true;

                }
            } else {
                // 0 pont
                Toast.makeText(getApplicationContext(), "Sajnos ez most nem sikerült.",
                        Toast.LENGTH_LONG).show();
                savePoints();
                next = true;

            }
        });
        option3.setOnClickListener(view -> {
            if (points > 0) {
                if (A3.contains("0")) {
                    option3.setBackgroundResource((R.drawable.red_round_bground));
                    points--;
                }
                if (A3.contains("3")) {
                    option3.setBackgroundResource(R.drawable.green_round_bground);
                    option1.setBackgroundResource(R.drawable.red_round_bground);
                    option2.setBackgroundResource(R.drawable.red_round_bground);
                    option4.setBackgroundResource(R.drawable.red_round_bground);
                    option1.setClickable(false);
                    option2.setClickable(false);
                    option3.setClickable(false);
                    option4.setClickable(false);
                    savePoints();
                    next = true;
                }
            } else {
                // 0 pont
                Toast.makeText(getApplicationContext(), "Sajnos ez most nem sikerült.",
                        Toast.LENGTH_LONG).show();
                savePoints();
                next = true;
            }
        });
        option4.setOnClickListener(view -> {
            if (points > 0) {
                if (A4.contains("0")) {
                    option4.setBackgroundResource((R.drawable.red_round_bground));
                    points--;
                }
                if (A4.contains("3")) {
                    option4.setBackgroundResource(R.drawable.green_round_bground);
                    option1.setBackgroundResource(R.drawable.red_round_bground);
                    option2.setBackgroundResource(R.drawable.red_round_bground);
                    option3.setBackgroundResource(R.drawable.red_round_bground);
                    option1.setClickable(false);
                    option2.setClickable(false);
                    option3.setClickable(false);
                    option4.setClickable(false);
                    savePoints();
                    next = true;
                }
            } else {
                Toast.makeText(getApplicationContext(), "Sajnos ez most nem sikerült.",
                        Toast.LENGTH_LONG).show();
                savePoints();
                next = true;
            }
        });

        //DATABASE
        //shuffle good and bad answers
        List<String> answerIDs = Arrays.asList("0", "00", "000", "3");
        Collections.shuffle(answerIDs);

        int numberOfQuestions = b.getInt("NoOfQ");

        //fill list with question IDs and then shuffle
        List<Integer> randomQuestionIDs = new ArrayList<>();
        if (numberOfQuestions > 0) {
            for (int i = 1; i <= numberOfQuestions; i++) {
                randomQuestionIDs.add(i);
            }
        } else {
            Toast.makeText(getApplicationContext(), "Error: number of questions is zero.",
                    Toast.LENGTH_LONG).show();
        }
        Collections.shuffle(randomQuestionIDs);

        rndQuestion = randomQuestionIDs.get(1);

        DocumentReference docRefQuestions = db.collection("questions")
                .document(String.valueOf(rndQuestion));
        docRefQuestions.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                question.setText(document.getString("question"));
                a1.setText(document.getString(answerIDs.get(0)));
                A1 = (answerIDs.get(0));
                a2.setText(document.getString(answerIDs.get(1)));
                A2 = answerIDs.get(1);
                a3.setText(document.getString(answerIDs.get(2)));
                A3 = answerIDs.get(2);
                a4.setText(document.getString(answerIDs.get(3)));
                A4 = answerIDs.get(3);
                response = document.getString("response");
            } else {
                Toast.makeText(getApplicationContext(), "Error while loading in question.",
                        Toast.LENGTH_LONG).show();
            }
        });
        //END

    }

    public void question_DoneClick(View v) {
        if (next) {
            Intent i = new Intent();
            i.setClass(this, Evaluate.class);
            i.putExtra("selectedPic", selectedPic);
            i.putExtra("selectedKid", selectedKid);
            i.putExtra("hour", hour);
            i.putExtra("min", min);
            i.putExtra("sec", sec);
            i.putExtra("pont", points);//IDE BERAKTAM A PONTOKAT
            i.putExtra("prevActivityID", prevActivityID);
            i.putExtra("firstGame", firstGame);
            i.putExtra("response", response);
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(), "Még próbálkozz!",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed() {
        Intent i = new Intent();
        i.setClass(this, Homepage.class);
        startActivity(i);
    }

    public void savePoints() {
        int point = points;
        int QuestionID = rndQuestion;
        String parentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference ref = db.collection("results")
                .document(parentID)
                .collection("kids").document(selectedKid);
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String timestamp = formatter.format(date);
        ref.update(timestamp + "," +"qID:"+QuestionID, point);
//        db.collection("results")
//                .whereEqualTo("parentID", parentID)
//                .whereEqualTo("kidName", selectedKid)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                resultDocID = document.getId();
//                                Log.d("------------", resultDocID);
//                            }
//                            DocumentReference resultsRef = db.collection("results").document(resultDocID);
//                            resultsRef.update("Qid+hanyadik", point)
//                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//
//                                        @Override
//                                        public void onSuccess(Void unused) {
//
//                                        }
//                                    })
//                                    .addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//
//                                        }
//                                    });
//                        }
//                    }
//                });
    }
}