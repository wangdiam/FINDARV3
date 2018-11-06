package com.findar_tech.findarv3.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.findar_tech.findarv3.Adapters.SelectMusicAdapter;
import com.findar_tech.findarv3.Music;
import com.findar_tech.findarv3.R;

import java.util.ArrayList;

public class SelectMusicActivity extends AppCompatActivity {

    private SelectMusicAdapter mAdapter;
    public static ArrayList<Music> musicArrayList = new ArrayList<>();
    private RecyclerView mMusicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_music);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (musicArrayList.isEmpty()) fillMusic();
        mMusicList = findViewById(R.id.rv_music);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        mMusicList.setLayoutManager(llm);
        mMusicList.setHasFixedSize(true);

        mAdapter = new SelectMusicAdapter(getApplicationContext(), musicArrayList);
        mMusicList.setAdapter(mAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void fillMusic() {
        musicArrayList.clear();

        Music m = new Music();
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