package com.findar_tech.findarv3.Activities;

import android.content.Intent;
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
import com.findar_tech.findarv3.Fragments.MusicFragment;
import com.findar_tech.findarv3.R;
import com.findar_tech.findarv3.Services.NewBackgroundMusicService;

import java.util.Objects;

public class TimerStartedActivity extends AppCompatActivity {
    public static TextView timeLeftTV;
    MorphingButton morphingButton;
    int buttonState = 0;

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
        setContentView(R.layout.activity_timer_started);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        timeLeftTV = findViewById(R.id.time_left_tv);
        morphingButton = findViewById(R.id.cancel_timer_btn);
        morphToSquare(morphingButton,0);
        NewBackgroundMusicService.updateTime();

        morphingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    stopService(new Intent(TimerStartedActivity.this, NewBackgroundMusicService.class));
                } catch (Exception e) {
                }
                if (buttonState == 0) {
                    MusicFragment.playBtn.change(true, true);
                    morphToFailure(morphingButton);
                    buttonState = 1;
                    Snackbar.make(view, "Timer has been stopped", Snackbar.LENGTH_SHORT).show();
                } else {
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void morphToSquare(final MorphingButton btnMorph, int duration) {
        MorphingButton.Params square = MorphingButton.Params.create()
                .duration(duration)
                .cornerRadius(dimen(R.dimen.mb_corner_radius_2))
                .width(dimen(R.dimen.mb_width_200))
                .height(dimen(R.dimen.mb_height_56))
                .color(color(R.color.mb_red))
                .colorPressed(color(R.color.mb_red_dark))
                .text(getString(R.string.mb_button_cancel));
        btnMorph.morph(square);
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
}
