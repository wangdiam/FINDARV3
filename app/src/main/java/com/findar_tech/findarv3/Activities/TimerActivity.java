package com.findar_tech.findarv3.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.findar_tech.findarv3.R;
import com.findar_tech.findarv3.Services.NewBackgroundMusicService;

import java.util.ArrayList;

import static com.findar_tech.findarv3.Fragments.MusicFragment.playBtn;
import static com.findar_tech.findarv3.Fragments.MusicFragment.returnedSongName;
import static com.findar_tech.findarv3.Fragments.MusicFragment.selectedSongID;
import static com.findar_tech.findarv3.Services.NewBackgroundMusicService.player;

public class TimerActivity extends AppCompatActivity {

    Button fifteenBtn, thirtyBtn, fortyfiveBtn, sixtyBtn, ninetyBtn, hundredtwentyBtn, setTimerBtn;
    TextView countdownClockTV;
    int currentPressedBtn = 0;
    Integer currentTime = 0;
    ArrayList<Button> listOfButtons;
    private long timeLeftInMilliseconds;
    private CountDownTimer countDownTimer;
    private boolean timerRunning = false;

    private void startTimer() {
        System.out.println("TIMEUPDATED");
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds -= 1000;
                Log.v("TIMERUPDATED", String.valueOf(timeLeftInMilliseconds));
                updateTime();
            }

            @Override
            public void onFinish() {
                try {
                    playBtn.toggle();
                    player.pause();
                }catch (Exception e) {
                }
            }
        }.start();
        timerRunning = true;
    }

    private void updateTime() {
        System.out.println("test");
        Integer hours = (int) (timeLeftInMilliseconds/3600000);
        Integer minutes = (int) (timeLeftInMilliseconds/60000);
        Integer seconds = (int) (timeLeftInMilliseconds%60000/1000);
        String timeLeftText="";
        if (hours < 10) {
            timeLeftText="0";
        }
        timeLeftText+= Integer.toString(hours);
        timeLeftText+=":";
        if (minutes < 10) {
            timeLeftText+="0";
        }
        timeLeftText += Integer.toString(minutes);
        timeLeftText+=":";
        if (seconds < 10) {
            timeLeftText += "0";
        }
        timeLeftText += seconds;
        countdownClockTV.setText(timeLeftText);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fifteenBtn = findViewById(R.id.fifteen_btn);
        thirtyBtn = findViewById(R.id.thirty_btn);
        fortyfiveBtn = findViewById(R.id.fortyfive_btn);
        sixtyBtn = findViewById(R.id.sixty_btn);
        ninetyBtn = findViewById(R.id.ninety_btn);
        hundredtwentyBtn = findViewById(R.id.hundredtwenty_btn);
        setTimerBtn = findViewById(R.id.set_timer_btn);
        countdownClockTV = findViewById(R.id.countdown_tv);
        listOfButtons = new ArrayList<>();
        listOfButtons.add(fifteenBtn);
        listOfButtons.add(thirtyBtn);
        listOfButtons.add(fortyfiveBtn);
        listOfButtons.add(sixtyBtn);
        listOfButtons.add(ninetyBtn);
        listOfButtons.add(hundredtwentyBtn);


        fifteenBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!fifteenBtn.isPressed()) {
                    fifteenBtn.setPressed(true);
                    currentPressedBtn = R.id.fifteen_btn;
                    uncheckButtonsExceptOne(fifteenBtn);
                    currentTime = 15;
                }
                return true;
            }
        });

        thirtyBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!thirtyBtn.isPressed()) {
                    thirtyBtn.setPressed(true);
                    currentPressedBtn = R.id.thirty_btn;
                    uncheckButtonsExceptOne(thirtyBtn);
                    currentTime = 30;
                }
                return true;
            }
        });

        fortyfiveBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!fortyfiveBtn.isPressed()) {
                    fortyfiveBtn.setPressed(true);
                    currentPressedBtn = R.id.fortyfive_btn;
                    uncheckButtonsExceptOne(fortyfiveBtn);
                    currentTime = 45;
                }
                return true;
            }
        });

        sixtyBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!sixtyBtn.isPressed()) {
                    sixtyBtn.setPressed(true);
                    currentPressedBtn = R.id.sixty_btn;
                    uncheckButtonsExceptOne(sixtyBtn);
                    currentTime = 60;
                }
                return true;
            }
        });

        ninetyBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!ninetyBtn.isPressed()) {
                    ninetyBtn.setPressed(true);
                    currentPressedBtn = R.id.ninety_btn;
                    uncheckButtonsExceptOne(ninetyBtn);
                    currentTime = 90;
                }
                return true;
            }
        });

        hundredtwentyBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!hundredtwentyBtn.isPressed()) {
                    hundredtwentyBtn.setPressed(true);
                    currentPressedBtn = R.id.hundredtwenty_btn;
                    uncheckButtonsExceptOne(hundredtwentyBtn);
                    currentTime = 120;
                }
                return true;
            }
        });

        setTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentTime == 0) {
                    Toast.makeText(TimerActivity.this, "Please select a duration", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TimerActivity.this,"You have selected " + currentTime.toString() + " minutes",Toast.LENGTH_SHORT).show();
                    startTimer();
                    playBtn.toggle();
                    MainActivity.isServiceOn = true;
                    Intent i = new Intent(view.getContext(), NewBackgroundMusicService.class);
                    i.putExtra("SONGID",selectedSongID);
                    i.putExtra("SONGPROGRESS",MainActivity.songProgress);
                    i.putExtra("SONGNAME",returnedSongName);
                    i.putExtra("TIMESELECTED",currentTime.longValue());
                    startForegroundService(i);
                }
                return;
            }
        });


    }

    private void uncheckButtonsExceptOne(Button button) {
        for (int i=0;i<listOfButtons.size();i++){
            if (listOfButtons.get(i).getId() != button.getId()) {
                listOfButtons.get(i).setPressed(false);
            }
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
