package com.example.healthytamagochi;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class TeethBrushing extends AppCompatActivity {

    ImageButton avatar;
    String selectedPic,selectedKid;
    TextView time;
    int hour=0;
    int min=0;
    int sec=0;
    float xDown=0,yDown=0;
    Button done;
    ImageView kosz1,kosz2,kosz3,kefe;
    TextView koord;
    int db1=0,db2=0,db3=0;
    int prevActivityID;
    boolean firstGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teeth_brushing);

        avatar = findViewById(R.id.avatar);
        time=findViewById(R.id.time);
        kosz1=findViewById(R.id.kosz1);
        kosz2=findViewById(R.id.kosz2);
        kosz3=findViewById(R.id.kosz3);
        kefe=findViewById(R.id.fogkefe);
        koord=findViewById(R.id.leiras_tv);
        done=findViewById(R.id.Done_btn);


        kosz1.setX(300);
        kosz1.setY(460);

        kosz2.setX(600);
        kosz2.setY(460);

        kosz3.setX(400);
        kosz3.setY(650);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            selectedPic = b.getString("selectedPic");
            selectedKid = b.getString("selectedKid");
            firstGame=b.getBoolean("firstGame");
            prevActivityID=b.getInt("prevActivityID");
        }

        String uri = "@drawable/" + selectedPic;
        int imageRes = getResources().getIdentifier(uri, null, getPackageName());
        Drawable res = getResources().getDrawable(imageRes);
        avatar.setImageDrawable(res);

        if(!firstGame)
        {
            hour=b.getInt("hour");
            min=b.getInt("min");
            sec=b.getInt("sec");
        }
        else {
            firstGame=false;
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


        kefe.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(db1>20 && db2>20 && db3>20)
                {
                    done.setVisibility(View.VISIBLE);
                }
                if(kefe.getX()>=100 && kefe.getX()<=300 && kefe.getY()>=400 && kefe.getY()<=500)
                {
                    db1++;
                }
                if(kefe.getX()>=300 && kefe.getX()<=600 && kefe.getY()>=400 && kefe.getY()<=500)
                {
                    db2++;
                }
                if(kefe.getX()>=200 && kefe.getX()<=400 && kefe.getY()>=600 && kefe.getY()<=700)
                {
                    db3++;
                }

                if(db1>20)
                {
                    kosz1.setImageResource(R.drawable.csillog);
                }
                if(db2>20)
                {
                    kosz2.setImageResource(R.drawable.csillog);
                }
                if(db3>20)
                {
                    kosz3.setImageResource(R.drawable.csillog);
                }

                    // rá teszi az ujját a képre
                    if(event.getAction() == MotionEvent.ACTION_DOWN)
                    {
                        xDown=event.getX();
                        yDown=event.getY();

                    }
                    //mozgatja az ujját
                    if(event.getAction() == MotionEvent.ACTION_MOVE) {
                        float movedX, movedY;
                        movedX = event.getX();
                        movedY = event.getY();

                        //kiszámolni mennyit mozdított az ujjával
                        float distanceX = movedX - xDown;
                        float distanceY = movedY - yDown;

                        //oda tesszük a képet
                        kefe.setX(kefe.getX() + distanceX);
                        kefe.setY(kefe.getY() + distanceY);
                    }
                    if(event.getAction() == MotionEvent.ACTION_UP)  //amikor elengeded
                    {
                            kefe.setX(300);
                            kefe.setY(0);
                    }



                return true;
            }
        });

    }

    public void DoneClick(View v)
    {
        Intent i = new Intent();
        i.setClass(this,Evaluate.class);
        i.putExtra("selectedPic",selectedPic);
        i.putExtra("selectedKid",selectedKid);
        i.putExtra("hour",hour);
        i.putExtra("min",min);
        i.putExtra("sec",sec);
        i.putExtra("pont",20);
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