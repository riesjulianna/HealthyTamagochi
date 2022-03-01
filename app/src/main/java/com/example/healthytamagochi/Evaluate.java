package com.example.healthytamagochi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Evaluate extends AppCompatActivity {

    ImageButton avatar;
    String selectedPic;
    TextView time;
    int hour=0;
    int min=0;
    int sec=0;
    int pont=0;
    FrameLayout color ;
    TextView rating;
    Random rnd;
    int nextActivityID;
    int prevActivityID;
    boolean firstGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);

        avatar = findViewById(R.id.avatar);
        time=findViewById(R.id.time);
        color=findViewById(R.id.color);
        rating=findViewById(R.id.rating_tv);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            selectedPic = b.getString("selectedPic");
            hour=b.getInt("hour");
            min=b.getInt("min");
            sec=b.getInt("sec");
            pont=b.getInt("pont");
            prevActivityID=b.getInt("prevActivityID");
            firstGame=b.getBoolean("firstGame");
        }

        if (selectedPic.equals("boy1"))
        {
            avatar.setImageResource(R.drawable.boy1);
        }
        else if (selectedPic.equals("girl1"))
        {
            avatar.setImageResource(R.drawable.girl1);
        }
        else if (selectedPic.equals("boy2"))
        {
            avatar.setImageResource(R.drawable.boy2);
        }
        else if (selectedPic.equals("girl2"))
        {
            avatar.setImageResource(R.drawable.girl2);
        }

        if(pont<10)
        {
          color.setBackgroundColor(Color.parseColor("#ff3838"));
          rating.setText("Nembaj lesz majd jobb is");
        }
        else
        {
            color.setBackgroundColor(Color.parseColor("#02de0a"));
            rating.setText("Ügyes vagy");
        }

        Timer T=new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {

                        if(sec%60==0 && sec!=0)
                        {
                            min++;
                            sec=0;
                        }
                        if(min%60==0 && min!=0)
                        {
                            hour++;
                            min=0;
                        }
                        if(hour%24==0)
                        {
                            hour=0;
                        }
                        String curTime = String.format("%02d : %02d : %02d", hour, min, sec);
                        time.setText(curTime); //change clock to your textview
                        if (min>=15 || hour>0)
                        {
                            time.setTextColor(Color.parseColor("#FF1111"));
                        }
                        sec++;

                    }
                });
            }
        }, 1000, 1000);

        rnd = new Random();
        nextActivityID = rnd.nextInt(4 - 1) + 1;   //1,2,3 lehet

        while (nextActivityID==prevActivityID)
        {
            nextActivityID = rnd.nextInt(4 - 1) + 1;   //1,2,3 lehet
        }
        prevActivityID=nextActivityID;

    }

    public void DoneClick(View v)
    {

        Intent i = new Intent();
        i.putExtra("selectedPic",selectedPic);
        i.putExtra("hour",hour);
        i.putExtra("min",min);
        i.putExtra("sec",sec);
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
}