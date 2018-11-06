package com.findar_tech.findarv3.Activities;

import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.findar_tech.findarv3.R;

public class VolumeActivity extends AppCompatActivity {

    SeekBar volumeSeekbar;
    AudioManager audioManager;
    TextView volumeTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume);
        volumeSeekbar = findViewById(R.id.volume_seek_bar);
        volumeTV = findViewById(R.id.volume_tv);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volumeSeekbar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));
            volumeTV.setText(String.valueOf(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressValue;
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                progressValue = progress;
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        progress, 0);
                volumeTV.setText(String.valueOf(progress));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
