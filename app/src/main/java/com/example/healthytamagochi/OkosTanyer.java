package com.example.healthytamagochi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class OkosTanyer extends AppCompatActivity {

    ImageButton avatar;
    String selectedPic="girl1";
    TextView time;
    int hour=0;
    int min=0;
    int sec=0;
    int pont=0;
    ImageView apples,csoki,sali,teszta,tanyer;
    boolean jo1=false,jo2=false,jo3=false;
    float xDown=0,yDown=0;
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okos_tanyer);

        avatar = findViewById(R.id.avatar);
        time=findViewById(R.id.time);
        apples=findViewById(R.id.apples);
        csoki=findViewById(R.id.csoki);
        teszta=findViewById(R.id.teszta);
        sali=findViewById(R.id.sali);
        tanyer=findViewById(R.id.tanyer);
        done=findViewById(R.id.Done_btn);

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



        apples.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if(apples.getX()>146 && apples.getX()<540 && apples.getY()>360 && apples.getY()<850)
                    {
                        apples.setBackgroundColor(Color.parseColor("#0e9905"));
                        apples.setX(apples.getX());
                        apples.setY(apples.getY());
                        jo1=true;
                        if(jo1 && jo2 && jo3)
                        {
                            done.setVisibility(View.VISIBLE);
                        }
                    }
                    else
                    {
                        // rá teszi az ujját a képre
                        if(event.getAction() == MotionEvent.ACTION_DOWN)
                        {
                            xDown=event.getX();
                            yDown=event.getY();

                        }
                        //mozgatja az ujját
                        if(event.getAction() == MotionEvent.ACTION_MOVE)
                        {
                            float movedX,movedY;
                            movedX=event.getX();
                            movedY=event.getY();

                            //kiszámolni mennyit mozdított az ujjával
                            float distanceX=movedX-xDown;
                            float distanceY=movedY-yDown;

                            //oda tesszük a képet
                            apples.setX(apples.getX()+distanceX);
                            apples.setY(apples.getY()+distanceY);
                        }
                        if(event.getAction() == MotionEvent.ACTION_UP)  //amikor elengeded
                        {
                            if(!(apples.getX()>146 && apples.getX()<540 && apples.getY()>360 && apples.getY()<850)) {
                                apples.setX(0);
                                apples.setY(1200);
                            }
                        }
                    }


                    return true;
                }
            });


        sali.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                // rá teszi az ujját a képre

                    if(sali.getX()>146 && sali.getX()<540 && sali.getY()>360 && sali.getY()<850)
                    {
                        sali.setBackgroundColor(Color.parseColor("#0e9905"));
                        sali.setX(sali.getX());
                        sali.setY(sali.getY());
                        jo2=true;
                        if(jo1 && jo2 && jo3)
                        {
                            done.setVisibility(View.VISIBLE);
                        }
                    }
                    else
                    {
                        sali.setBackgroundColor(Color.parseColor("#f7f7f7"));
                        if(event.getAction() == MotionEvent.ACTION_DOWN)
                    {
                        xDown=event.getX();
                        yDown=event.getY();
                    }
                        //mozgatja az ujját
                        if(event.getAction() == MotionEvent.ACTION_MOVE)
                        {
                            float movedX,movedY;
                            movedX=event.getX();
                            movedY=event.getY();

                            //kiszámolni mennyit mozdított az ujjával
                            float distanceX=movedX-xDown;
                            float distanceY=movedY-yDown;

                            //oda tesszük a képet
                            sali.setX(sali.getX()+distanceX);
                            sali.setY(sali.getY()+distanceY);

                        }
                        if(event.getAction() == MotionEvent.ACTION_UP)
                        {
                            if(!(sali.getX()>146 && sali.getX()<540 && sali.getY()>360 && sali.getY()<850)) {
                                sali.setX(700);
                                sali.setY(1200);
                            }
                        }

                }

                return true;
            }
        });


        csoki.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                // rá teszi az ujját a képre
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    xDown=event.getX();
                    yDown=event.getY();
                }
                //mozgatja az ujját
                if(event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    float movedX,movedY;
                    movedX=event.getX();
                    movedY=event.getY();

                    //kiszámolni mennyit mozdított az ujjával
                    float distanceX=movedX-xDown;
                    float distanceY=movedY-yDown;

                    //oda tesszük a képet
                    csoki.setX(csoki.getX()+distanceX);
                    csoki.setY(csoki.getY()+distanceY);
                }
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    csoki.setX(700);
                    csoki.setY(0);

                }
                if(csoki.getX()>146 && csoki.getX()<540 && csoki.getY()>360 && csoki.getY()<850)
                {
                    csoki.setBackgroundColor(Color.parseColor("#960e0e"));
                }
                else
                {
                    csoki.setBackgroundColor(Color.parseColor("#f7f7f7"));

                }


                return true;
            }
        });

        teszta.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                    if(teszta.getX()>146 && teszta.getX()<540 && teszta.getY()>360 && teszta.getY()<850)
                    {
                        teszta.setBackgroundColor(Color.parseColor("#0e9905"));
                        teszta.setX(teszta.getX());
                        teszta.setY(teszta.getY());
                        jo3=true;
                        if(jo1 && jo2 && jo3)
                        {
                            done.setVisibility(View.VISIBLE);
                        }
                    }
                    else
                    {
                        teszta.setBackgroundColor(Color.parseColor("#f7f7f7"));
                        // rá teszi az ujját a képre
                        if(event.getAction() == MotionEvent.ACTION_DOWN)
                        {
                            xDown=event.getX();
                            yDown=event.getY();
                        }
                        //mozgatja az ujját
                        if(event.getAction() == MotionEvent.ACTION_MOVE)
                        {
                            float movedX,movedY;
                            movedX=event.getX();
                            movedY=event.getY();

                            //kiszámolni mennyit mozdított az ujjával
                            float distanceX=movedX-xDown;
                            float distanceY=movedY-yDown;

                            //oda tesszük a képet
                            teszta.setX(teszta.getX()+distanceX);
                            teszta.setY(teszta.getY()+distanceY);
                    }
                        if(event.getAction() == MotionEvent.ACTION_UP)
                        {
                            if(!(teszta.getX()>146 && teszta.getX()<540 && teszta.getY()>360 && teszta.getY()<850)) {
                                teszta.setX(0);
                                teszta.setY(0);
                            }
                        }

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
            i.putExtra("hour",hour);
            i.putExtra("min",min);
            i.putExtra("sec",sec);
            i.putExtra("pont",5);
            i.putExtra("prev","OkosTanyer");
            startActivity(i);
    }



}