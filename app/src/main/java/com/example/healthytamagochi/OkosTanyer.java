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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class OkosTanyer extends AppCompatActivity {

    ImageButton avatar;
    String selectedPic,selectedKid;
    TextView time,type_tv;
    int hour=0;
    int min=0;
    int sec=0;
    ImageView apples,csoki,sali,teszta,tanyer;
    float xDown=0,yDown=0;
    Button done;
    int prevActivityID;
    boolean firstGame;
    Random rnd;
    int starter, rnd_fruit1,rnd_fruit2,rnd_veg1,rnd_veg2, resID_f1,resID_f2,resID_v1,resID_v2,score=3,rnd_type;

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
        type_tv=findViewById(R.id.type_tv);

        List<String> fruit_List = Arrays.asList(getResources().getStringArray(R.array.fruits));
        List<String> vegetables_List = Arrays.asList(getResources().getStringArray(R.array.vegetables));
        List<String> type_List = Arrays.asList(getResources().getStringArray(R.array.okostanyer_type));

        rnd = new Random();
        starter = rnd.nextInt(5 - 1) + 1;   //1,2,3,4 lehet

        rnd_type = rnd.nextInt(2) ;   //0,1 lehet
        type_tv.setText(type_List.get(rnd_type));

        rnd_fruit1=rnd.nextInt(4) ;
        rnd_fruit2=rnd.nextInt(4) ;
        while (rnd_fruit1==rnd_fruit2)
        {
            rnd_fruit2 = rnd.nextInt(4 ) ;
        }

        rnd_veg1=rnd.nextInt(4) ;
        rnd_veg2=rnd.nextInt(4) ;
        while (rnd_veg1==rnd_veg2)
        {
            rnd_veg2 = rnd.nextInt(4 ) ;
        }

        String fruit1 = fruit_List.get(rnd_fruit1);
        String fruit2 = fruit_List.get(rnd_fruit2);
        String veg1 = vegetables_List.get(rnd_veg1);
        String veg2 = vegetables_List.get(rnd_veg2);
        resID_f1 = getResources().getIdentifier(fruit1 , "drawable", getPackageName());
        resID_f2 = getResources().getIdentifier(fruit2 , "drawable", getPackageName());
        resID_v1 = getResources().getIdentifier(veg1 , "drawable", getPackageName());
        resID_v2 = getResources().getIdentifier(veg2 , "drawable", getPackageName());

        if(starter==1)
        {

            teszta.setImageResource(resID_f1);
            csoki.setImageResource(resID_v1);
            apples.setImageResource(resID_f2);
            sali.setImageResource(resID_v2);
        }
        else if(starter==2)
        {
            teszta.setImageResource(resID_v1);
            csoki.setImageResource(resID_f1);
            apples.setImageResource(resID_f2);
            sali.setImageResource(resID_v2);
        }
        else if(starter==3)
        {
            teszta.setImageResource(resID_f1);
            csoki.setImageResource(resID_f2);
            apples.setImageResource(resID_v1);
            sali.setImageResource(resID_v2);
        }
        else if(starter==4)
        {
            teszta.setImageResource(resID_f1);
            csoki.setImageResource(resID_v1);
            apples.setImageResource(resID_v2);
            sali.setImageResource(resID_f2);
        }


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



        apples.setOnTouchListener(new View.OnTouchListener() {
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
                            apples.setX(apples.getX()+distanceX);
                            apples.setY(apples.getY()+distanceY);
                        }
                        if(event.getAction() == MotionEvent.ACTION_UP)  //amikor elengeded
                        {
                            if(apples.getX()>146 && apples.getX()<540 && apples.getY()>360 && apples.getY()<850)
                            {
                                apples.setVisibility(View.GONE);
                                if(starter==1 || starter==2 && (rnd_type==0))
                                {
                                    done.setVisibility(View.VISIBLE);
                                }

                                else if(starter==3 || starter==4 && (rnd_type==1))
                                {
                                    done.setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                    if( score>0)
                                    {
                                        score--;
                                    }
                                }
                            }
                            else if(!(apples.getX()>146 && apples.getX()<540 && apples.getY()>360 && apples.getY()<850)) {
                                apples.setX(0);
                                apples.setY(1200);
                            }
                        }



                    return true;
                }
            });


        sali.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                // rá teszi az ujját a képre

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
                            if(sali.getX()>146 && sali.getX()<540 && sali.getY()>360 && sali.getY()<850)
                            {
                                sali.setVisibility(View.GONE);
                                if(starter==4 && (rnd_type==0))
                                {
                                    done.setVisibility(View.VISIBLE);
                                }

                                else  if(starter==1 || starter==2 || starter==3 && (rnd_type==1))
                                {
                                    done.setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                    if( score>0)
                                    {
                                        score--;
                                    }
                                }
                            }
                           else  if(!(sali.getX()>146 && sali.getX()<540 && sali.getY()>360 && sali.getY()<850)) {
                                sali.setX(700);
                                sali.setY(1200);
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
                    if(csoki.getX()>146 && csoki.getX()<540 && csoki.getY()>360 && csoki.getY()<850)
                    {
                        csoki.setVisibility(View.GONE);
                        if(starter==2 || starter==3 && (rnd_type==0))
                        {
                            done.setVisibility(View.VISIBLE);
                        }
                        else if(starter==1 || starter==4 && (rnd_type==1))
                        {
                            done.setVisibility(View.VISIBLE);
                        }
                        else
                        {

                            if( score>0)
                            {
                                score--;
                            }
                        }


                    }
                    else{
                        csoki.setX(700);
                        csoki.setY(0);
                    }



                }


                return true;
            }
        });

        teszta.setOnTouchListener(new View.OnTouchListener() {
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
                            teszta.setX(teszta.getX()+distanceX);
                            teszta.setY(teszta.getY()+distanceY);
                    }
                        if(event.getAction() == MotionEvent.ACTION_UP)
                        {
                            if(teszta.getX()>146 && teszta.getX()<540 && teszta.getY()>360 && teszta.getY()<850)
                            {
                                teszta.setVisibility(View.GONE);
                                if(starter==1 || starter==3 || starter==4 && (rnd_type==0))
                                {
                                    done.setVisibility(View.VISIBLE);
                                }
                                else if(starter==2 && (rnd_type==1))
                                {
                                    done.setVisibility(View.VISIBLE);
                                }
                                else
                                {

                                    if( score>0)
                                    {
                                        score--;
                                    }
                                }
                            }
                            else if(!(teszta.getX()>146 && teszta.getX()<540 && teszta.getY()>360 && teszta.getY()<850)) {
                                teszta.setX(0);
                                teszta.setY(0);
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
            i.putExtra("pont",score);
            i.putExtra("prevActivityID",prevActivityID);
            i.putExtra("firstGame",firstGame);
         i.putExtra("selectedKid",selectedKid);
            startActivity(i);
    }

    public void onBackPressed(){
        Intent i = new Intent();
        i.setClass(this,Homepage.class);
        startActivity(i);
    }



}