package com.example.healthytamagochi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class TeethBrushing extends Activity {

    ImageView avatar;
    String selectedPic,selectedKid;
    TextView time;
    int hour=0;
    int min=0;
    int sec=0;
    float xDown=0,yDown=0;
    Button done;
    ImageView kosz1,kosz2,kosz3,kefe,szaj;
    TextView koord,name;
    int db1=0,db2=0,db3=0;
    int prevActivityID;
    boolean firstGame;
    float actualX,actualY;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teeth_brushing);

        avatar = findViewById(R.id.avatar);
        time=findViewById(R.id.time_tv);
        kosz1=findViewById(R.id.kosz1);
        kosz2=findViewById(R.id.kosz2);
        kosz3=findViewById(R.id.kosz3);
        kefe=findViewById(R.id.fogkefe);
        koord=findViewById(R.id.leiras_tv);
        done=findViewById(R.id.Done_btn);
        name=findViewById(R.id.textViewName);
        szaj=findViewById(R.id.szaj);

        kefe.setBackgroundResource(R.drawable.fogkefe);
        szaj.setBackgroundResource(R.drawable.szaj);
        kosz1.setBackgroundResource(R.drawable.kosz);
        kosz2.setBackgroundResource(R.drawable.kosz);
        kosz3.setBackgroundResource(R.drawable.kosz);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        int orientation = getResources().getConfiguration().orientation;

        Bundle b = getIntent().getExtras();
        if (b != null) {
            selectedPic = b.getString("selectedPic");
            selectedKid = b.getString("selectedKid");
            firstGame=b.getBoolean("firstGame");
            prevActivityID=b.getInt("prevActivityID");
        }

        String uri = "@drawable/" + selectedPic;
        int imageRes = getResources().getIdentifier(uri, null, getPackageName());
        @SuppressLint("UseCompatLoadingForDrawables") Drawable res = getResources().getDrawable(imageRes);
        avatar.setImageDrawable(res);
        name.setText(selectedKid);
        if(!firstGame)
        {
            if (b != null) {
                hour=b.getInt("hour");
                min=b.getInt("min");
                sec=b.getInt("sec");
            }

        }
        else {
            firstGame=false;
        }

        Timer T=new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {

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
                    @SuppressLint("DefaultLocale") String curTime = String.format("%02d : %02d : %02d", hour, min, sec);
                    time.setText(curTime); //change clock to your textview
                    if (min>=15 || hour>0)
                    {
                        time.setTextColor(Color.parseColor("#FF1111"));
                    }
                    sec++;

                });
            }
        }, 1000, 1000);


        kefe.setOnTouchListener((view, event) -> {
            koord.setText(width+"  height   "+height);
            actualX=kefe.getX();
            actualY=kefe.getY();
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                if(actualX<(width*4.5/10.0) &&
                        actualX>(width*2.6/10.0)  &&
                        actualY<(height*5.0/10.0) &&
                        actualY>(height*2.5/10.0))
                {
                    db1++;
                }
                if(actualX<(width-(width*4.1/10.0)) &&
                        actualX>(width-(width*4.7/10.0))  &&
                        actualY<(height-(height*5.8/10.0)) &&
                        actualY>(height-(height*6.1/10.0)))
                {
                    db2++;
                }
                if(actualX<(width-(width*5.5/10.0)) &&
                        actualX>(width-(width*6.1/10.0))  &&
                        actualY<(height-(height*4.7/10.0)) &&
                        actualY>(height-(height*5.0/10.0)))
                {
                    db3++;
                }


                if(event.getAction() == MotionEvent.ACTION_UP)  //amikor elengeded
                {
                    kefe.setX((float)(width*3.1/10.0));
                    kefe.setY((float)(height*1.8/10.0));
                }
            } else {
                // In Landscape

                if(db1>20 && db2>20 && db3>20)
                {
                    done.setVisibility(View.VISIBLE);
                }
                if(actualX<(width-(width*5.8/10.0)) &&
                        actualX>(width-(width*6.1/10.0))  &&
                        actualY<(height-(height*6.1/10.0)) &&
                        actualY>(height-(height*6.5/10.0)))
                {
                    db1++;
                }
                if(actualX<(width-(width*4.3/10.0)) &&
                        actualX>(width-(width*4.9/10.0))  &&
                        actualY<(height-(height*6.1/10.0)) &&
                        actualY>(height-(height*6.5/10.0)))
                {
                    db2++;
                }
                if(actualX<(width-(width*5.5/10.0)) &&
                        actualX>(width-(width*6.1/10.0))  &&
                        actualY<(height-(height*5.1/10.0)) &&
                        actualY>(height-(height*5.6/10.0)))
                {
                    db3++;
                }


                if(event.getAction() == MotionEvent.ACTION_UP)  //amikor elengeded
                {
                    kefe.setX((float)(width-(width*5.8/10.0)));   //750
                    kefe.setY((float)(height-(height*8.11/10.0)));
                }
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
            if(db1>8)
            {
                kosz1.setVisibility(View.INVISIBLE);
            }
            if(db2>8)
            {
                kosz2.setVisibility(View.INVISIBLE);
            }
            if(db3>8)
            {
                kosz3.setVisibility(View.INVISIBLE);
            }
            if(db1>8 && db2>8 && db3>8)
            {
                done.setVisibility(View.VISIBLE);
            }


            return true;
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