package com.findar_tech.findarv3.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.findar_tech.findarv3.Adapters.SelectMusicAdapter;
import com.findar_tech.findarv3.Music;
import com.findar_tech.findarv3.R;

import java.util.ArrayList;
import java.util.Objects;

public class SelectMusicActivity extends AppCompatActivity {

    private static final ArrayList<Music> musicArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_music);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (musicArrayList.isEmpty()) fillMusic();
        RecyclerView mMusicList = findViewById(R.id.rv_music);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        mMusicList.setLayoutManager(llm);
        mMusicList.setHasFixedSize(true);

        SelectMusicAdapter mAdapter = new SelectMusicAdapter(getApplicationContext(), musicArrayList);
        mMusicList.setAdapter(mAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void fillMusic() {
        musicArrayList.clear();

        Music m = new Music("Beach Wind Gentle","20:00",getResources().getString(R.string.brief_description),R.raw.beach_wind_gentle,R.drawable.beach_wind_gentle);
        musicArrayList.add(m);
        m = new Music("Ocean Waves Close","20:00",getResources().getString(R.string.brief_description),R.raw.ocean_wave_close,R.drawable.ocean_waves_close);
        musicArrayList.add(m);
        m = new Music("Fountain Rocks","20:00",getResources().getString(R.string.brief_description),R.raw.fountain_rocks,R.drawable.fountain_rocks);
        musicArrayList.add(m);


        m = new Music();
        m.setName("Ambiphonic Lounge");
        m.setDuration("05:02");
        m.setDescription(getResources().getString(R.string.brief_description));
        m.setSong(R.raw.ambiphonic_lounge_easy_listening_music);
        m.setImageID(R.drawable.brain_development_wallpaper);
        musicArrayList.add(m);

        m = new Music();
        m.setName("Warm Piano");
        m.setDuration("03:24");
        m.setDescription(getResources().getString(R.string.brief_description));
        m.setImageID(R.drawable.g_string_classical);
        m.setSong(R.raw.warm_piano);
        musicArrayList.add(m);

        m = new Music();
        m.setName("Windswept Synth");
        m.setDuration("03:28");
        m.setDescription(getResources().getString(R.string.brief_description));
        m.setImageID(R.drawable.brain_development_wallpaper);
        m.setSong(R.raw.windswept_synth_guitar_and_soft_string);
        musicArrayList.add(m);

        m = new Music();
        m.setName("Electronic Lounge");
        m.setDuration("05:10");
        m.setImageID(R.drawable.g_string_classical);
        m.setDescription(getResources().getString(R.string.brief_description));
        m.setSong(R.raw.forgiven_electronic_lounge_music);
        musicArrayList.add(m);

    }
}