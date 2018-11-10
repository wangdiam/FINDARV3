package com.findar_tech.findarv3.Services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.RemoteViews;

import com.findar_tech.findarv3.Activities.MainActivity;
import com.findar_tech.findarv3.R;
import com.findar_tech.findarv3.Receivers.NotificationReceiver;

import java.util.Objects;

import static com.findar_tech.findarv3.Fragments.MusicFragment.playBtn;
import static com.findar_tech.findarv3.Services.NotificationChannels.CHANNEL_ID;

public class NewBackgroundMusicService extends Service {

    private static String inputMessage;

    public static final String NOTIFY_TOAST = "com.findar_tech.findarv3.musicinbackground.toast";
    public static final String NOTIFY_PAUSE = "com.findar_tech.findarv3.musicinbackground.pause";
    public static final String NOTIFY_PLAY = "com.findar_tech.findarv3.musicinbackground.play";
    public static final String NOTIFY_PREVIOUS = "com.findar_tech.findarv3.musicinbackground.previous";
    public static final String NOTIFY_NEXT = "com.findar_tech.findarv3.musicinbackground.next";
    private static final String PLAY_STATE = "PLAY_STATE";

    //creating a mediaplayer object
    public static MediaPlayer player;
    private Integer songID, songProgress,lastSongID;
    private Boolean changePlayState;
    private String songName;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long timeLeftInMilliseconds;


    @Nullable
    @Override
    //for bound services.It allows components (such as activities) to bind to the service
    // , send requests, receive responses, and perform interprocess communication (IPC)
    public IBinder onBind(Intent intent) {
        return null;
    }

    //called first time service is created
    @Override
    public void onCreate() {
        super.onCreate();
    }

    //called everytime startService gets called
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //shows notification and checks for backwards compatibility
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (intent != null && intent.getExtras() != null) {
            songID = intent.getExtras().getInt("SONGID");
            songProgress = MainActivity.songProgress;
            songName = intent.getExtras().getString("SONGNAME");
            changePlayState = intent.getBooleanExtra(PLAY_STATE,false);
            timeLeftInMilliseconds = intent.getLongExtra("TIMESELECTED",999999999) * 1000;
        } else {
            songID = R.raw.ambiphonic_lounge_easy_listening_music;
            changePlayState = false;
        }
        if (player == null || !songID.equals(lastSongID)) {
            //creating and starting new media player
            player = MediaPlayer.create(this,
                    songID);
            lastSongID = songID;
            player.setLooping(true);
            player.start();
            player.seekTo(songProgress);
            //String extra from Main Activity EditText
            inputMessage = Objects.requireNonNull(intent).getStringExtra("inputExtra");
            startStop();

            //opens Main Activity when user taps on notification, uses pending intent
            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            //music_notification layout xml
            RemoteViews expandedView = new RemoteViews(this.getPackageName(), R.layout.music_notification);
            RemoteViews countdownClockTV = new RemoteViews(this.getPackageName(), R.layout.activity_timer);

            Bitmap artWork = BitmapFactory.decodeResource(getResources(), R.drawable.findar);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle(songName)
                    .setContentText("FINDAR")
                    //largeIcon is optional
                    .setLargeIcon(artWork)
//                .addAction(R.drawable.ic_skip_previous, "previous", null)
//                .addAction(R.drawable.ic_play_arrow, "play", null)
//                .addAction(R.drawable.ic_pause, "pause", null)
//                .addAction(R.drawable.ic_skip_next, "next", null)
//                .addAction(R.drawable.ic_music_note, "ADD ACTION BUTTON LOL", actionIntent)
//                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
//                .setShowActionsInCompactView(0,2,3))
//                .setSubText("Sub Text")
//                .setCustomBigContentView(expandedView)
                    //smallIcon is compulsory
                    .setSmallIcon(R.drawable.findar_hdpi)
                    //pending intent
                    .setContentIntent(pendingIntent)
                    //auto dismiss when notification is tapped
                    .setAutoCancel(true)
                    .setOngoing(true)
                    //for broadcastIntent and action button in respond to notification
                    .build();

            notification.flags = Notification.FLAG_ONGOING_EVENT;
            //music button on click listeners
            setListeners(expandedView, this);

            notificationManager.notify(1, notification);

            //get service to foreground
            startForeground(1, notification);


            //don't care if system kills the service
        } else if (changePlayState) {
            player.pause();
        } else {
            player.start();
        }

        return START_NOT_STICKY;
    }

    private void startStop() {
        if (timerRunning) {
            stopTimer();
        } else {
            startTimer();
        }
    }

    private void stopTimer() {
        countDownTimer.cancel();
        timerRunning = false;
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds -= 1000;
                updateTime();
            }

            @Override
            public void onFinish() {
                try {
                    playBtn.change(true,true);
                    onDestroy();
                } catch (Exception e) {
                }
            }
        }.start();
        timerRunning = true;
    }

    private void updateTime() {
        Integer hours = (int) (timeLeftInMilliseconds/3600000);
        Integer minutes = (int) (timeLeftInMilliseconds/60000);
        Integer seconds = (int) (timeLeftInMilliseconds%60000/1000);
        String timeLeftText="";
        if (hours < 10) {
            timeLeftText="0";
        }
        timeLeftText+= Integer.toString(hours);
        timeLeftText+=":";
        if (minutes < 10) {
            timeLeftText+="0";
        }
        timeLeftText += Integer.toString(minutes);
        timeLeftText+=":";
        if (seconds < 10) {
            timeLeftText += "0";
        }
    }

    private static void setListeners(RemoteViews view, Context context){

        //toast Intent to send a Toast Message String to NotificationReceiver class and setting actions
        Intent toast = new Intent(context, NotificationReceiver.class);
        toast.putExtra(NOTIFY_TOAST, inputMessage);
        toast.setAction(NOTIFY_TOAST);


        //Intent to NotificationReceiver class and setting actions
        Intent previous = new Intent(context, NotificationReceiver.class);
        previous.setAction(NOTIFY_PREVIOUS);

        Intent play = new Intent(context, NotificationReceiver.class);
        play.setAction(NOTIFY_PLAY);

        Intent pause = new Intent(context, NotificationReceiver.class);
        pause.setAction(NOTIFY_PAUSE);

        Intent next = new Intent(context, NotificationReceiver.class);
        next.setAction(NOTIFY_NEXT);

        //Pending Intent and setting on click listeners for music buttons

        PendingIntent toastIntent = PendingIntent.getBroadcast(context, 0, toast, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.image_mario, toastIntent);

        PendingIntent pPlay = PendingIntent.getBroadcast(context, 0, play, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.button_play, pPlay);

/*
        PendingIntent pPause = PendingIntent.getBroadcast(context, 0, pause, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.button_pause, pPause);
*/





    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        //stopping the player when service is destroyed
        player.pause();
        this.stopSelf();
        MainActivity.isServiceOn = false;
    }
}
