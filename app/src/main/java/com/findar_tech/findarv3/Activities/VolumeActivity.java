package com.findar_tech.findarv3.Activities;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.findar_tech.findarv3.R;
import com.sdsmdg.harjot.crollerTest.Croller;
import com.sdsmdg.harjot.crollerTest.OnCrollerChangeListener;

public class VolumeActivity extends AppCompatActivity {

    SeekBar volumeSeekbar;
    AudioManager audioManager;
    TextView volumeTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume);
        /*volumeSeekbar = findViewById(R.id.volume_seek_bar);
        volumeTV = findViewById(R.id.volume_tv);*/
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Croller croller = findViewById(R.id.croller);
        volumeTV = findViewById(R.id.volume_tv);
         croller.setOnCrollerChangeListener(new OnCrollerChangeListener() {
             Integer progressValue;
            @Override
            public void onProgressChanged(Croller croller, int progress) {
                // use the progress
                progressValue = progress;
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        progressValue, 0);
                volumeTV.setText(progressValue.toString());
            }

            @Override
            public void onStartTrackingTouch(Croller croller) {
                // tracking started
            }

            @Override
            public void onStopTrackingTouch(Croller croller) {
                // tracking stopped
            }
        });

        try {
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            croller.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            croller.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        /*try {
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
        }*/

        /*volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
        });*/
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
