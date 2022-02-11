package com.example.healthytamagochi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    RadioGroup q1,q2,q3,q4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions1);

        avatar = findViewById(R.id.avatar);
        time=findViewById(R.id.time);
        q1=findViewById(R.id.question1);
        q2=findViewById(R.id.question2);
        q3=findViewById(R.id.question3);
        q4=findViewById(R.id.question4);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            selectedPic = b.getString("selectedPic");
        }
       // String pic="R.drawable"+selectedPic;
        //avatar.setImageResource(Integer.parseInt(pic));

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

    public void DoneClick(View v)
    {
        if(q1.getCheckedRadioButtonId()==-1 || q2.getCheckedRadioButtonId()==-1 || q3.getCheckedRadioButtonId()==-1 || q4.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(this, "Üres mező(k)!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Intent i = new Intent();
            i.setClass(this,Evaluate.class);
            i.putExtra("selectedPic",selectedPic);
            i.putExtra("hour",hour);
            i.putExtra("min",min);
            i.putExtra("sec",sec);
            i.putExtra("pont",10);
            startActivity(i);
        }


    }




}