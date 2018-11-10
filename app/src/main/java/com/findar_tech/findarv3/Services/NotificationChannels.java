package com.findar_tech.findarv3.Services;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import java.util.Objects;

public class NotificationChannels extends Application {
    public static final String CHANNEL_ID = "exampleServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        //if version higher than oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID, "Playing Music",
                    NotificationManager.IMPORTANCE_NONE
            );

            //NotificationManager creates notification channels
            NotificationManager manager = getSystemService(NotificationManager.class);
            //notification channel will be created as soon as app starts
            Objects.requireNonNull(manager).createNotificationChannel(serviceChannel);
        }
    }
}