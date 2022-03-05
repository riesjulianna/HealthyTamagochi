package com.example.healthytamagochi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Layout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.collection.LLRBNode;

import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Questions1 extends AppCompatActivity {

    ImageButton avatar;
    String selectedPic,selectedKid;
    TextView time;
    int hour=0;
    int min=0;
    int sec=0;
    int prevActivityID;
    boolean firstGame;
    LinearLayout option1,option2,option3,option4 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions1);

        avatar = findViewById(R.id.avatar);
        time=findViewById(R.id.time);
        option1=findViewById(R.id.titas_layout_1);
        option2=findViewById(R.id.titas_layout_2);
        option3=findViewById(R.id.kutyis_layout);
        option4=findViewById(R.id.kutyis_layout_2);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            selectedPic = b.getString("selectedPic");
            selectedKid = b.getString("selectedKid");
            prevActivityID=b.getInt("prevActivityID");
            firstGame=b.getBoolean("firstGame");
        }
        if(firstGame==false)
        {
            hour=b.getInt("hour");
            min=b.getInt("min");
            sec=b.getInt("sec");
        }
        else if(firstGame)
        {
            firstGame=false;
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
                        if (min>=15 || hour>0)
                        {
                            time.setTextColor(Color.parseColor("#FF1111"));
                        }
                        sec++;

                    }
                });
            }
        }, 1000, 1000);

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                option1.setBackgroundResource(R.drawable.green_round_bground);
                option2.setBackgroundResource(R.drawable.red_round_bground);
                option3.setBackgroundResource(R.drawable.red_round_bground);
                option4.setBackgroundResource(R.drawable.red_round_bground);
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                option2.setBackgroundResource(R.drawable.green_round_bground);
                option1.setBackgroundResource(R.drawable.red_round_bground);
                option3.setBackgroundResource(R.drawable.red_round_bground);
                option4.setBackgroundResource(R.drawable.red_round_bground);
            }
        });
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                option3.setBackgroundResource(R.drawable.green_round_bground);
                option2.setBackgroundResource(R.drawable.red_round_bground);
                option1.setBackgroundResource(R.drawable.red_round_bground);
                option4.setBackgroundResource(R.drawable.red_round_bground);
            }
        });
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                option4.setBackgroundResource(R.drawable.green_round_bground);
                option2.setBackgroundResource(R.drawable.red_round_bground);
                option3.setBackgroundResource(R.drawable.red_round_bground);
                option1.setBackgroundResource(R.drawable.red_round_bground);
            }
        });

    }

    public void question_DoneClick(View v)
    {
            Intent i = new Intent();
            i.setClass(this,Evaluate.class);
            i.putExtra("selectedPic",selectedPic);
        i.putExtra("selectedKid",selectedKid);
            i.putExtra("hour",hour);
            i.putExtra("min",min);
            i.putExtra("sec",sec);
            i.putExtra("pont",5);
            i.putExtra("prevActivityID",prevActivityID);
            i.putExtra("firstGame",firstGame);
            startActivity(i);
    }

    public void onBackPressed(){
        Intent i = new Intent();
        i.setClass(this,Homepage.class);
        startActivity(i);
    }




}