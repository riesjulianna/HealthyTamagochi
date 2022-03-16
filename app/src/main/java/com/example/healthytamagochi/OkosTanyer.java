package com.example.healthytamagochi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

    ImageView avatar;
    String selectedPic,selectedKid;
    TextView time,type_tv,name;
    int hour=0;
    int min=0;
    int sec=0;
    ImageView option1,option2,option4,option3,tanyer;
    float xDown=0,yDown=0;
    Button done;
    int prevActivityID;
    boolean firstGame;
    Random rnd;
    int starter, rnd_fruit1,rnd_fruit2,rnd_veg1,rnd_veg2, resID_f1,resID_f2,resID_v1,resID_v2,score=3,rnd_type;
    float actualX,actualY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okos_tanyer);

        avatar = findViewById(R.id.avatar);
        time=findViewById(R.id.time_tv);
        option1=findViewById(R.id.apples);
        option2=findViewById(R.id.csoki);
        option3=findViewById(R.id.teszta);
        option4=findViewById(R.id.sali);
        tanyer=findViewById(R.id.tanyer);
        done=findViewById(R.id.Done_btn);
        type_tv=findViewById(R.id.type_tv);
        name=findViewById(R.id.textViewName);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

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
            option1.setImageResource(resID_f2);
            option2.setImageResource(resID_v1);
            option3.setImageResource(resID_f1);
            option4.setImageResource(resID_v2);
        }
        else if(starter==2)
        {
            option1.setImageResource(resID_f2);
            option2.setImageResource(resID_f1);
            option3.setImageResource(resID_v1);
            option4.setImageResource(resID_v2);
        }
        else if(starter==3)
        {
            option1.setImageResource(resID_v1);
            option2.setImageResource(resID_f2);
            option3.setImageResource(resID_f1);
            option4.setImageResource(resID_v2);
        }
        else if(starter==4)
        {
            option1.setImageResource(resID_v2);
            option2.setImageResource(resID_v1);
            option3.setImageResource(resID_f1);
            option4.setImageResource(resID_f2);
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

        name.setText(selectedKid);

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



        option1.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    actualX=option1.getX();
                    actualY=option1.getY();
                    type_tv.setText(rnd_type+"  rnd ---- starter  "+starter);
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
                            option1.setX(option1.getX()+distanceX);
                            option1.setY(option1.getY()+distanceY);
                        }
                        if(event.getAction() == MotionEvent.ACTION_UP)  //amikor elengeded
                        {
                            if(actualX>(width-(width*7.3/10.0)) &&
                                    actualX<(width-(width*4.3/10.0)) &&
                                    actualY>(height-(height*6.9/10.0)) &&
                                    actualY<(height-(height*5.4/10.0)))
                            {
                                option1.setVisibility(View.GONE);
                                if(starter==1  && rnd_type==0)
                                {
                                    done.setVisibility(View.VISIBLE);
                                }
                                if(starter==2 && rnd_type==0)
                                {
                                    done.setVisibility(View.VISIBLE);
                                }

                                 if(starter==3 && rnd_type==1)
                                {
                                    done.setVisibility(View.VISIBLE);
                                }
                                if(starter==4 && rnd_type==1)
                                {
                                    done.setVisibility(View.VISIBLE);
                                }
                               if(starter==1 && rnd_type==1)
                                {
                                    if( score>0)
                                    {
                                        score--;
                                    }
                                }
                                if(starter==2 && rnd_type==1)
                                {
                                    if( score>0)
                                    {
                                        score--;
                                    }
                                }
                                if(starter==3 && rnd_type==0)
                                {
                                    if( score>0)
                                    {
                                        score--;
                                    }
                                }
                                if(starter==4 && rnd_type==0)
                                {
                                    if( score>0)
                                    {
                                        score--;
                                    }
                                }
                            }
                            else  {
                                // ha nem a tányérra húzta
                                option1.setX((float)(width-(width*(9.6/10.0))));
                                option1.setY((float)(height-(height*(8.14/10.0))));
                            }
                        }



                    return true;
                }
            });


        option4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                actualX=option4.getX();
                actualY=option4.getY();
                type_tv.setText(rnd_type+"  rnd ---- starter  "+starter);
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
                            option4.setX(option4.getX()+distanceX);
                            option4.setY(option4.getY()+distanceY);

                        }
                        if(event.getAction() == MotionEvent.ACTION_UP)
                        {
                            if(actualX>(width-(width*7.3/10.0)) &&
                                    actualX<(width-(width*4.3/10.0)) &&
                                    actualY>(height-(height*6.9/10.0)) &&
                                    actualY<(height-(height*5.4/10.0)))
                            {
                                option4.setVisibility(View.GONE);
                                if(starter==4 && rnd_type==0)
                                {
                                    done.setVisibility(View.VISIBLE);
                                }

                                if(starter==1 && rnd_type==1)
                                {
                                    done.setVisibility(View.VISIBLE);
                                }
                                if(starter==2  && rnd_type==1)
                                {
                                    done.setVisibility(View.VISIBLE);
                                }
                                if( starter==3 && rnd_type==1)
                                {
                                    done.setVisibility(View.VISIBLE);
                                }
                                if(starter==4 && rnd_type==1)
                                {
                                    if( score>0)
                                    {
                                        score--;
                                    }
                                }
                                if(starter==1 && rnd_type==0)
                                {
                                    if( score>0)
                                    {
                                        score--;
                                    }
                                }
                                if(starter==2 && rnd_type==0)
                                {
                                    if( score>0)
                                    {
                                        score--;
                                    }
                                }
                                if(starter==3 && rnd_type==0)
                                {
                                    if( score>0)
                                    {
                                        score--;
                                    }
                                }
                            }
                           else   {
                                // ha nem a tányérra húzta
                                option4.setX((float)(width-(width*(2.0/10.0))));
                                option4.setY((float)(height-(height*(3.8/10.0))));
                            }
                        }



                return true;
            }
        });


        option2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                actualX=option2.getX();
                actualY=option2.getY();
                type_tv.setText(rnd_type+"  rnd ---- starter  "+starter);
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
                    option2.setX(option2.getX()+distanceX);
                    option2.setY(option2.getY()+distanceY);
                }
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    if(actualX>(width-(width*7.3/10.0)) &&
                            actualX<(width-(width*4.3/10.0)) &&
                            actualY>(height-(height*6.9/10.0)) &&
                            actualY<(height-(height*5.4/10.0)))
                    {
                        option2.setVisibility(View.GONE);
                        if(starter==2 && rnd_type==0)
                        {
                            done.setVisibility(View.VISIBLE);
                        }
                        if( starter==3 && rnd_type==0)
                        {
                            done.setVisibility(View.VISIBLE);
                        }
                        if(starter==1  && rnd_type==1)
                        {
                            done.setVisibility(View.VISIBLE);
                        }
                        if( starter==4 && rnd_type==1)
                        {
                            done.setVisibility(View.VISIBLE);
                        }
                        if(starter==2 && rnd_type==1)
                        {
                            if( score>0)
                            {
                                score--;
                            }
                        }
                        if(starter==3 && rnd_type==1)
                        {
                            if( score>0)
                            {
                                score--;
                            }
                        }
                        if(starter==1 && rnd_type==0)
                        {
                            if( score>0)
                            {
                                score--;
                            }
                        }
                        if(starter==4 && rnd_type==0)
                        {
                            if( score>0)
                            {
                                score--;
                            }
                        }


                    }
                    else{
                        option2.setX((float)(width-(width*(2.0/10.0))));
                        option2.setY((float)(height-(height*(8.14/10.0))));
                    }



                }


                return true;
            }
        });

        option3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                actualX=option3.getX();
                actualY=option3.getY();
                type_tv.setText(rnd_type+"  rnd ---- starter  "+starter);
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
                            option3.setX(option3.getX()+distanceX);
                            option3.setY(option3.getY()+distanceY);
                    }
                        if(event.getAction() == MotionEvent.ACTION_UP)
                        {
                            if(actualX>(width-(width*7.3/10.0)) &&
                                    actualX<(width-(width*4.3/10.0)) &&
                                    actualY>(height-(height*6.9/10.0)) &&
                                    actualY<(height-(height*5.4/10.0)))
                            {
                                option3.setVisibility(View.GONE);
                                if(starter==1 && rnd_type==0)
                                {
                                    done.setVisibility(View.VISIBLE);
                                }
                                if(starter==3  && rnd_type==0)
                                {
                                    done.setVisibility(View.VISIBLE);
                                }
                                if(starter==4 && rnd_type==0)
                                {
                                    done.setVisibility(View.VISIBLE);
                                }
                                if(starter==2 && rnd_type==1)
                                {
                                    done.setVisibility(View.VISIBLE);
                                }
                                if(starter==1 && rnd_type==1)
                                {
                                    if( score>0)
                                    {
                                        score--;
                                    }
                                }
                                if(starter==3 && rnd_type==1)
                                {
                                    if( score>0)
                                    {
                                        score--;
                                    }
                                }
                                if(starter==4 && rnd_type==1)
                                {
                                    if( score>0)
                                    {
                                        score--;
                                    }
                                }
                                if(starter==2 && rnd_type==0)
                                {
                                    if( score>0)
                                    {
                                        score--;
                                    }
                                }
                            }
                            else {
                                option3.setX((float)(width-(width*(9.6/10.0))));
                                option3.setY((float)(height-(height*(3.8/10.0))));
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

    public static Point getLocationOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return new Point(location[0], location[1]);
    }



}