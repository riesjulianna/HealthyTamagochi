package com.example.healthytamagochi;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);

        avatar = findViewById(R.id.avatar);
        time=findViewById(R.id.time);
        color=findViewById(R.id.color);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            selectedPic = b.getString("selectedPic");
            hour=b.getInt("hour");
            min=b.getInt("min");
            sec=b.getInt("sec");
            pont=b.getInt("pont");
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
        }
        else
        {
            color.setBackgroundColor(Color.parseColor("#02de0a"));
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