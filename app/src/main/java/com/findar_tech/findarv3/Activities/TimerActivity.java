package com.findar_tech.findarv3.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntegerRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.dd.morphingbutton.MorphingButton;
import com.findar_tech.findarv3.R;
import com.findar_tech.findarv3.Services.NewBackgroundMusicService;

import java.util.Objects;

import me.angrybyte.circularslider.CircularSlider;

import static com.findar_tech.findarv3.Fragments.MusicFragment.playBtn;
import static com.findar_tech.findarv3.Fragments.MusicFragment.returnedSongName;
import static com.findar_tech.findarv3.Fragments.MusicFragment.selectedSongID;

public class TimerActivity extends AppCompatActivity {

    private TextView minutesTV;
    private MorphingButton setTimerBtn;
    private CircularSlider cs;
    private Integer currentTime = 0;
    private int buttonState;

    int dimen(@DimenRes int resId) {
        return (int) getResources().getDimension(resId);
    }

    int color(@ColorRes int resId) {
        return getResources().getColor(resId);
    }

    int integer(@IntegerRes int resId) {
        return getResources().getInteger(resId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTimerBtn = findViewById(R.id.set_timer_btn);
        morphToSquare(setTimerBtn,0);
        buttonState = 0;
        cs = findViewById(R.id.circular);
        minutesTV = findViewById(R.id.current_time_selected_tv);
        minutesTV.setText("0 minutes");
        cs.setAngle(3.14);
        cs.setOnSliderMovedListener(new CircularSlider.OnSliderMovedListener() {
            @Override
            public void onSliderMoved(double pos) {
                Double position = pos;
                position = (position * -1 + 0.25) * 181;
                Integer positionWithoutDecimal = position.intValue();
                minutesTV.setText(String.format("%s minutes", positionWithoutDecimal.toString()));
                currentTime = positionWithoutDecimal;
                if (buttonState == 1) {
                    morphToSquare(setTimerBtn,500);
                    buttonState = 0;
                }
            }
        });


        setTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (buttonState == 1) {
                    morphToSquare(setTimerBtn,500);
                    buttonState = 0;
                } else if (currentTime == 0) {
                    Snackbar.make(view,"Please select a duration!",Snackbar.LENGTH_SHORT).show();
                    if (buttonState != 1) {
                        morphToFailure(setTimerBtn);
                        buttonState = 1;
                    }
                } else if (buttonState == 2) {
                        finish();
                } else {
                    Snackbar.make(view,"You have selected " + currentTime.toString() + " minutes",Snackbar.LENGTH_SHORT).show();
                    try {
                        stopService(new Intent(TimerActivity.this, NewBackgroundMusicService.class));
                    } catch (Exception e) {
                    }
                    playBtn.change(false,true);
                    Intent i = new Intent(view.getContext(), NewBackgroundMusicService.class);
                    i.putExtra("SONGID",selectedSongID);
                    i.putExtra("SONGPROGRESS",MainActivity.songProgress);
                    i.putExtra("SONGNAME",returnedSongName);
                    i.putExtra("TIMESELECTED",currentTime.longValue());
                    i.putExtra("ISTIMERSERVICE",true);
                    morphToSuccess(setTimerBtn);
                    buttonState = 2;
                    int sdk = Build.VERSION.SDK_INT;
                    if (sdk < Build.VERSION_CODES.O) startService(i);
                    else startForegroundService(i);
                }
            }
        });


    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void morphToSuccess(final MorphingButton btnMorph) {
        MorphingButton.Params circle = MorphingButton.Params.create()
                .duration(integer(R.integer.mb_animation))
                .cornerRadius(dimen(R.dimen.mb_height_56))
                .width(dimen(R.dimen.mb_height_56))
                .height(dimen(R.dimen.mb_height_56))
                .color(color(R.color.mb_green))
                .colorPressed(color(R.color.mb_green_dark))
                .icon(R.drawable.ic_done_black_24dp);
        btnMorph.morph(circle);
    }

    private void morphToFailure(final MorphingButton btnMorph) {
        MorphingButton.Params circle = MorphingButton.Params.create()
                .duration(500)
                .cornerRadius(dimen(R.dimen.mb_height_56))
                .width(dimen(R.dimen.mb_height_56))
                .height(dimen(R.dimen.mb_height_56))
                .color(color(R.color.mb_red))
                .colorPressed(color(R.color.mb_red_dark))
                .icon(R.drawable.ic_clear_black_24dp);
        btnMorph.morph(circle);
    }

    private void morphToSquare(final MorphingButton btnMorph, int duration) {
        MorphingButton.Params square = MorphingButton.Params.create()
                .duration(duration)
                .cornerRadius(dimen(R.dimen.mb_corner_radius_2))
                .width(dimen(R.dimen.mb_width_200))
                .height(dimen(R.dimen.mb_height_56))
                .color(color(R.color.mb_blue))
                .colorPressed(color(R.color.mb_blue_dark))
                .text(getString(R.string.mb_button));
        btnMorph.morph(square);
    }

}
