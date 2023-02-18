package com.example.jarvis_tv_client;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;

import java.util.logging.Logger;

public class NotificationService extends Service {
    public static Logger logger = Logger.getLogger(NotificationService.class.getName());

    public NotificationService() {
    }

    @Override
    public void onCreate() {
        logger.info("Service Create");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        logger.info("calling onStartCommand");
        // create the notification
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        Notification notif = new Notification.Builder(this, Config.CHANNEL_ID)
                .setContentTitle(getText(R.string.notification_title))
                .setContentText(getText(R.string.notification_message))
                .setContentIntent(pendingIntent)
                .setTicker(getText(R.string.ticker_text))
                .build();
        startForeground(99, notif);
        AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (audioManager.isMusicActive()) {
                    logger.info("Music is active");
                } else {
                    logger.info("No muisic");
                }
                handler.postDelayed(this, 1000);
            }
        }, 1000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
      return null;
    }
}