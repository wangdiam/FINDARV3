package com.findar_tech.findarv3.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.findar_tech.findarv3.Activities.MainActivity;
import com.findar_tech.findarv3.Activities.SelectMusicActivity;
import com.findar_tech.findarv3.Activities.TimerActivity;
import com.findar_tech.findarv3.Activities.VolumeActivity;
import com.findar_tech.findarv3.R;
import com.findar_tech.findarv3.Services.NewBackgroundMusicService;
import com.romancha.playpause.PlayPauseView;

import java.util.Objects;

import static com.findar_tech.findarv3.Activities.MainActivity.isServiceOn;
import static com.findar_tech.findarv3.Services.NewBackgroundMusicService.player;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnMusicFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MusicFragment extends Fragment {
    public static PlayPauseView playBtn;
    private ImageButton listBtn,volumeBtn,timerBtn;
    private TextView songTitleTV;
    private RelativeLayout backgroundRL;
    private final int REQUESTCODE = 100;
    public static String returnedSongName;
    public static Integer selectedSongID;
    private static Integer selectedSongImageID;



    // TODO: Rename and change types of parameters
    private String mParam1;

    private OnMusicFragmentInteractionListener mListener;

    public MusicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if(requestCode == REQUESTCODE && resultCode == Activity.RESULT_OK) {
                returnedSongName = data.getStringExtra("RETURNEDSONG");
                selectedSongID = data.getIntExtra("RETURNEDSONGID",0);
                selectedSongImageID = data.getIntExtra("RETURNEDSONGIMAGEID", 0 );
                Bundle bundle = new Bundle();
                bundle.putString("SONGCHOSEN",returnedSongName);
                songTitleTV.setText(returnedSongName);
                final int sdk = android.os.Build.VERSION.SDK_INT;
                backgroundRL.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), selectedSongImageID));
                backgroundRL.getBackground().setAlpha(150);
                Objects.requireNonNull(getActivity()).stopService(new Intent(getContext(),NewBackgroundMusicService.class));
                MainActivity.songProgress = 0;
                if (playBtn.onPause()) playBtn.toggle();
                isServiceOn = false;
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString("SONGCHOSEN");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (isServiceOn) playBtn.change(true);
        SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("com.findar_tech.findarv3", Context.MODE_PRIVATE);
        returnedSongName = sp.getString("SELECTEDSONGFORALARMTEXT","Ambiphonic Lounge");
        selectedSongID = sp.getInt("SELECTEDSONGFORALARM",R.raw.ambiphonic_lounge_easy_listening_music);
        selectedSongImageID = sp.getInt("SELECTEDSONGIMAGEFORALARM",R.drawable.brain_development_wallpaper);
        View v = inflater.inflate(R.layout.fragment_music, container, false);
        songTitleTV = v.findViewById(R.id.song_title_tv);
        backgroundRL = v.findViewById(R.id.fragment_background);
        songTitleTV.setText(returnedSongName);
        System.out.println(selectedSongImageID);
        backgroundRL.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), selectedSongImageID));
        backgroundRL.getBackground().setAlpha(150);
        if (mParam1 != null) {
            songTitleTV.setText(mParam1);
        }
        listBtn = v.findViewById(R.id.list_ib);
        volumeBtn = v.findViewById(R.id.adjust_ib);
        timerBtn = v.findViewById(R.id.timer_ib);
        playBtn = v.findViewById(R.id.play_pause_button);
        Log.v("ISSERVICEON", String.valueOf(isServiceOn));
        Log.v("PLAYBTNSTATE", String.valueOf(playBtn.onPlaying()));
        if (isServiceOn && playBtn.onPlaying()) {
            playBtn.change(false,true);
        } else if (!isServiceOn && playBtn.onPause()) {
            playBtn.change(true,true);
        }
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SelectMusicActivity.class);
                startActivityForResult(intent,100);
            }
        });
        volumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), VolumeActivity.class);
                startActivity(intent);
            }
        });
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedSongID == null) selectedSongID = R.raw.ambiphonic_lounge_easy_listening_music;
                if (returnedSongName == null) returnedSongName = "Ambiphonic Lounge";
                if (!isServiceOn) {
           /*         i.putExtra("SONGID",selectedSongID);
                    i.putExtra("SONGPROGRESS",MainActivity.songProgress);
                    getActivity().startService(i);*/
                    startService();
                    isServiceOn = true;
                    playBtn.change(false,true);
                } else {
                    System.out.println("oof");
                    isServiceOn = false;
                    player.pause();
                    playBtn.change(true,true);
                }
            }
        });
        timerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TimerActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    private void startService() {
        Intent i = new Intent(this.getActivity(), NewBackgroundMusicService.class);
        i.putExtra("SONGID",selectedSongID);
        i.putExtra("SONGPROGRESS",MainActivity.songProgress);
        i.putExtra("SONGNAME",returnedSongName);
        Objects.requireNonNull(getActivity()).startForegroundService(i);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMusicFragmentInteractionListener) {
            mListener = (OnMusicFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnHelpFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMusicFragmentInteractionListener {
    }
}
