package com.example.healthytamagochi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class Questions1 extends AppCompatActivity {

    ImageButton avatar;
    String selectedPic;
    TextView time;
    int hour=0;
    int min=0;
    int sec=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions1);

        avatar = findViewById(R.id.avatar);
        time=findViewById(R.id.time);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            selectedPic = b.getString("selectedPic");
        }

        if (selectedPic.equals("cat_one"))
        {
            avatar.setImageResource(R.drawable.cat_one);
        }
        else if (selectedPic.equals("cat_two"))
        {
            avatar.setImageResource(R.drawable.cat_two);
        }
        else if (selectedPic.equals("dog_one"))
        {
            avatar.setImageResource(R.drawable.dog_one);
        }
        else if (selectedPic.equals("dog_two"))
        {
            avatar.setImageResource(R.drawable.dog_two);
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
                        sec++;

                    }
                });
            }
        }, 1000, 1000);




    }




}