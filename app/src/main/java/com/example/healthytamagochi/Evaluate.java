package com.example.healthytamagochi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Evaluate extends AppCompatActivity {

    ImageView avatar;
    TextView time,name;
    String selectedPic,selectedKid, response;
    int hour=0;
    int min=0;
    int sec=0;
    int pont=0;
    LinearLayout color ;
    TextView rating;
    Random rnd;
    int nextActivityID;
    int prevActivityID;
    boolean firstGame;
    int numberOfQuestions;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);

        avatar = findViewById(R.id.avatar);
        name = findViewById(R.id.textViewName);
        time = findViewById(R.id.time_tv);
        color = findViewById(R.id.color);
        rating = findViewById(R.id.rating_tv);

        numberOfQuestions = getNumOfQuestions();

        Bundle b = getIntent().getExtras();
        if (b != null) {
            selectedPic = b.getString("selectedPic");
            selectedKid = b.getString("selectedKid");
            hour = b.getInt("hour");
            min = b.getInt("min");
            sec = b.getInt("sec");
            pont = b.getInt("pont");
            prevActivityID = b.getInt("prevActivityID");
            firstGame = b.getBoolean("firstGame");
            response = b.getString("response");

            name.setText(selectedKid);

            String uri = "@drawable/" + selectedPic;
            int imageRes = getResources().getIdentifier(uri, null, getPackageName());
            @SuppressLint("UseCompatLoadingForDrawables") Drawable res = getResources().getDrawable(imageRes);
            avatar.setImageDrawable(res);

            if(prevActivityID==1 && pont>1)
            {
                rating.setText("3/" + pont + " pont\n\n" + response);
                color.setBackgroundResource(R.drawable.green_round_bground);
            }
            else if(prevActivityID==1 && pont<=1)
            {
                rating.setText("3/" + pont + " pont\n\n" + response);
                color.setBackgroundResource(R.drawable.red_round_bground);
            }
            else if(prevActivityID==2 || prevActivityID==3)
            {
                if(pont>1)
                {
                    rating.setText("3/" + pont + " pont\n\n" + "Ügyes vagy!");
                    color.setBackgroundResource(R.drawable.green_round_bground);
                }
                else
                {
                    rating.setText("3/" + pont + " pont\n\n" + "Legközelebb ügyesebb leszel!");
                    color.setBackgroundResource(R.drawable.red_round_bground);
                }
            }


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

            rnd = new Random();
            nextActivityID = rnd.nextInt(4 - 1) + 1;   //1,2,3 lehet

            while (nextActivityID == prevActivityID) {
                nextActivityID = rnd.nextInt(4 - 1) + 1;   //1,2,3 lehet
            }
            prevActivityID = nextActivityID;

        }
    }
    public int getNumOfQuestions(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
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


    public void evaluate_DoneClick(View v)
    {

        Intent i = new Intent();
        i.putExtra("selectedPic",selectedPic);
        i.putExtra("hour",hour);
        i.putExtra("min",min);
        i.putExtra("sec",sec);
        i.putExtra("prevActivityID",nextActivityID);
        i.putExtra("firstGame",firstGame);
        i.putExtra("selectedKid",selectedKid);
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


    public void onBackPressed(){
        Intent i = new Intent();
        i.setClass(this,Homepage.class);
        startActivity(i);
    }
}