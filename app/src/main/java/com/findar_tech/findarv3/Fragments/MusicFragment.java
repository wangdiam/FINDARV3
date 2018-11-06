package com.findar_tech.findarv3.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
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

import static com.findar_tech.findarv3.Services.NewBackgroundMusicService.player;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnMusicFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static PlayPauseView playBtn;
    ImageButton listBtn,volumeBtn,timerBtn;
    TextView songTitleTV;
    RelativeLayout backgroundRL;
    final int REQUESTCODE = 100;
    public static String returnedSongName;
    public static Integer selectedSongID;
    static Integer selectedSongImageID;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    backgroundRL.setBackgroundDrawable(ContextCompat.getDrawable(getContext(),selectedSongImageID) );
                } else {
                    backgroundRL.setBackground(ContextCompat.getDrawable(getContext(), selectedSongImageID));
                }
                backgroundRL.getBackground().setAlpha(150);
                getActivity().stopService(new Intent(getContext(),NewBackgroundMusicService.class));
                MainActivity.songProgress = 0;
                if (playBtn.onPause()) playBtn.toggle();
                MainActivity.isServiceOn = false;
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MusicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MusicFragment newInstance(String param1, String param2) {
        MusicFragment fragment = new MusicFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString("SONGCHOSEN");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences sp = getActivity().getSharedPreferences("com.findar_tech.findarv3", Context.MODE_PRIVATE);
        returnedSongName = sp.getString("SELECTEDSONGFORALARMTEXT","Ambiphonic Lounge");
        selectedSongID = sp.getInt("SELECTEDSONGFORALARM",R.raw.ambiphonic_lounge_easy_listening_music);
        selectedSongImageID = sp.getInt("SELECTEDSONGIMAGEFORALARM",R.drawable.brain_development_wallpaper);
        View v = inflater.inflate(R.layout.fragment_music, container, false);
        songTitleTV = v.findViewById(R.id.song_title_tv);
        backgroundRL = v.findViewById(R.id.fragment_background);
        songTitleTV.setText(returnedSongName);
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            backgroundRL.setBackgroundDrawable(ContextCompat.getDrawable(getContext(),selectedSongImageID) );
        } else {
            backgroundRL.setBackground(ContextCompat.getDrawable(getContext(), selectedSongImageID));
        }
        backgroundRL.getBackground().setAlpha(150);
        if (mParam1 != null) {
            songTitleTV.setText(mParam1);
        };
        listBtn = v.findViewById(R.id.list_ib);
        volumeBtn = v.findViewById(R.id.adjust_ib);
        timerBtn = v.findViewById(R.id.timer_ib);
        playBtn = v.findViewById(R.id.play_pause_button);
        Log.v("ISSERVICEON", String.valueOf(MainActivity.isServiceOn));
        Log.v("PLAYBTNSTATE", String.valueOf(playBtn.onPlaying()));
        if (MainActivity.isServiceOn && playBtn.onPlaying()) {
            playBtn.toggle();
        } else if (!MainActivity.isServiceOn && playBtn.onPause()) {
            playBtn.toggle();
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
                playBtn.toggle();
                if (selectedSongID == null) selectedSongID = R.raw.ambiphonic_lounge_easy_listening_music;
                if (returnedSongName == null) returnedSongName = "Ambiphonic Lounge";
                if (!MainActivity.isServiceOn) {
           /*         i.putExtra("SONGID",selectedSongID);
                    i.putExtra("SONGPROGRESS",MainActivity.songProgress);
                    getActivity().startService(i);*/
                    startService(selectedSongID,MainActivity.songProgress,returnedSongName);
                    MainActivity.isServiceOn = true;
                } else {
                    MainActivity.isServiceOn = false;
                    player.pause();
                }
               /* if (mp == null) {
                    isPlaying = true;
                    if (selectedSongID == null) {
                        mp = MediaPlayer.create(getContext(), R.raw.ambiphonic_lounge_easy_listening_music);
                    } else {
                        mp = MediaPlayer.create(getContext(),selectedSongID);
                    }
                    mp.start();
                } else if (isPlaying) {
                    isPlaying = false;
                    mp.pause();
                } else {
                    isPlaying = true;
                    mp.start();
                }*/
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onMusicFragmentInteraction(uri);
        }
    }

    public void startService(int songid, int songprogress, String songname) {
        Intent i = new Intent(this.getActivity(), NewBackgroundMusicService.class);
        i.putExtra("SONGID",selectedSongID);
        i.putExtra("SONGPROGRESS",MainActivity.songProgress);
        i.putExtra("SONGNAME",returnedSongName);
        getActivity().startForegroundService(i);

    }

    public void stopService() {
        Intent serviceIntent = new Intent(getActivity(), NewBackgroundMusicService.class);
        getActivity().stopService(serviceIntent);
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
        // TODO: Update argument type and name
        void onMusicFragmentInteraction(Uri uri);
    }
}
