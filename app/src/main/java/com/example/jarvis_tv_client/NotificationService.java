package com.example.jarvis_tv_client;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQException;

import java.util.logging.Logger;

public class NotificationService extends Service {
    public static Logger logger = Logger.getLogger(NotificationService.class.getName());
    private static final Handler handler = new Handler();
    public NotificationService() {
    }

    @Override
    public void onCreate() {
        logger.info("Service Create");
        super.onCreate();
    }

    private static class LoopRunnable implements Runnable {
        ZContext context;
        ZMQ.Socket socket;
        public LoopRunnable() {
            context = new ZContext();
            socket = context.createSocket(SocketType.REQ);
            socket.connect("tcp://13.37.69.107:43599");
        }
        @Override
        public void run() {
            socket.send("test");
            logger.info("Sending message");
            handler.postDelayed(new LoopRunnable(), 1000);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        logger.info("calling onStartCommand");

        try (ZContext context = new ZContext()) {

            ZMQ.Socket socket = context.createSocket(SocketType.REQ);
            if (!socket.connect("tcp://127.0.0.1:43599")) {
                logger.severe("Unable to bind to socket");
            }
            // create the notification
            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
            Notification notification = new Notification.Builder(this, Config.CHANNEL_ID)
                    .setContentTitle(getText(R.string.notification_title))
                    .setContentText(getText(R.string.notification_message))
                    .setContentIntent(pendingIntent)
                    .setTicker(getText(R.string.ticker_text))
                    .build();
            startForeground(99, notification);
            // AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
            handler.postDelayed(new LoopRunnable(), 1000);
            return super.onStartCommand(intent, flags, startId);
        }
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