package com.findar_tech.findarv3.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.findar_tech.findarv3.R;
import com.findar_tech.findarv3.Services.NewBackgroundMusicService;

import static com.findar_tech.findarv3.Services.NewBackgroundMusicService.NOTIFY_TOAST;
import static com.findar_tech.findarv3.Services.NewBackgroundMusicService.player;


public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String toastMessage = intent.getStringExtra(NOTIFY_TOAST);
        RemoteViews expandedView = new RemoteViews(context.getPackageName(), R.layout.music_notification);


        if (intent.getAction().equals(NewBackgroundMusicService.NOTIFY_PLAY)){
            if (!player.isPlaying()) {
                Toast.makeText(context, "NOTIFY_PLAY", Toast.LENGTH_LONG).show();
                Intent serviceIntent = new Intent(context, NewBackgroundMusicService.class);
                serviceIntent.putExtra("PLAY_STATE", false);
                expandedView.setImageViewResource(R.id.button_play, R.drawable.ic_play);
                context.startService(serviceIntent);
            } else {
                Toast.makeText(context, "NOTIFY_PAUSE", Toast.LENGTH_LONG).show();
                Intent serviceIntent = new Intent(context, NewBackgroundMusicService.class);
                serviceIntent.putExtra("PLAY_STATE", true);
                expandedView.setImageViewResource(R.id.button_play, R.drawable.ic_pause);
                context.startService(serviceIntent);
            }
        } else if (intent.getAction().equals(NewBackgroundMusicService.NOTIFY_PAUSE)){
            Toast.makeText(context, "NOTIFY_PAUSE", Toast.LENGTH_LONG).show();
            Intent serviceIntent = new Intent(context, NewBackgroundMusicService.class);
            serviceIntent.putExtra("PLAY_STATE",true);
            expandedView.setImageViewResource(R.id.button_play,R.drawable.ic_pause);
            context.startService(serviceIntent);
        }else if (intent.getAction().equals(NewBackgroundMusicService.NOTIFY_NEXT)){
            Toast.makeText(context, "NOTIFY_NEXT", Toast.LENGTH_LONG).show();
        }else if (intent.getAction().equals(NewBackgroundMusicService.NOTIFY_PREVIOUS)){
            Toast.makeText(context, "NOTIFY_PREVIOUS", Toast.LENGTH_LONG).show();
        }else if(intent.getAction().equals(NewBackgroundMusicService.NOTIFY_TOAST)){
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
        }
    }
}