package com.findar_tech.findarv3.Adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.findar_tech.findarv3.Activities.MainActivity;
import com.findar_tech.findarv3.Music;
import com.findar_tech.findarv3.R;
import com.findar_tech.findarv3.Services.NewBackgroundMusicService;

import java.util.List;


public class SelectMusicAdapter extends RecyclerView.Adapter<SelectMusicAdapter.NumberViewHolder>{

    private Context context;
    private LinearLayout selectSongLL;
        private final List<Music> musicList;


        public SelectMusicAdapter(Context context, List<Music> musicList) {
            this.context = context;
            this.musicList = musicList;
        }

        @NonNull
        @Override
        public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = parent.getContext();
            int layoutIdForListMusic = R.layout.number_list_music;
            LayoutInflater li = LayoutInflater.from(context);
            View view = li.inflate(layoutIdForListMusic,parent, false);
            return new NumberViewHolder(view,(Activity)context);
        }

        @Override
        public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {
            holder.bind(position);

        }

        @Override
        public int getItemCount() {
            return musicList.size();
        }

        public class NumberViewHolder extends RecyclerView.ViewHolder {
            final TextView listMusicItemView;
            final TextView listMusicDescView;

            NumberViewHolder(View itemView, final Activity activity) {
                super(itemView);
                listMusicItemView = itemView.findViewById(R.id.song_name_tv_rv);
                listMusicDescView = itemView.findViewById(R.id.song_description_tv);
                selectSongLL = itemView.findViewById(R.id.select_song_ll);
                selectSongLL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("RETURNEDSONG", listMusicItemView.getText());
                        resultIntent.putExtra("RETURNEDSONGID",musicList.get(getAdapterPosition()).getSongID());
                        resultIntent.putExtra("RETURNEDSONGIMAGEID",musicList.get(getAdapterPosition()).getImageID());
                        SharedPreferences sp = activity.getSharedPreferences("com.findar_tech.findarv3", Context.MODE_PRIVATE);
                        sp.edit().putString("SELECTEDSONGFORALARMTEXT",musicList.get(getAdapterPosition()).getName()).apply();
                        sp.edit().putInt("SELECTEDSONGFORALARM",musicList.get(getAdapterPosition()).getSongID()).apply();
                        sp.edit().putInt("SELECTEDSONGIMAGEFORALARM",musicList.get(getAdapterPosition()).getImageID()).apply();
                        v.getContext().stopService(new Intent(v.getContext(), NewBackgroundMusicService.class));
                        MainActivity.songProgress = 0;
                        activity.setResult(Activity.RESULT_OK, resultIntent);
                        activity.finish();
                    }
                });

            }

            void bind(int listIndex){
                listMusicItemView.setText(musicList.get(listIndex).getName());
                listMusicDescView.setText(musicList.get(listIndex).getDescription());
            }
        }
    }

