package com.example.mytravel.service;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String ARG_NOTIFICATION = "ARG_NOTIFICATION";

    private String tokenFireBase;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        updateToken(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (MainApp.getInstance().getAppDataManager().isCheckLogin() && MainApp.getInstance().getAppDataManager().isNotification())
            sendNotification(remoteMessage);
    }

    private void sendNotification(RemoteMessage remoteMessage) {

        Intent intent = new Intent(this, ((MainApp) getApplication()).getCurrentActivity().getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 8000, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Uri soundUri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.sound_notify);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.app_name))
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(Objects.requireNonNull(remoteMessage.getNotification()).getBody()))
                        .setContentText(Objects.requireNonNull(Objects.requireNonNull(remoteMessage).getNotification()).getBody())
                        .setAutoCancel(true)
                        .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))
                        .setSound(soundUri)
                        .setVibrate(new long[]{1000, 1000})
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    getString(R.string.default_notification_channel_id),
                    NotificationManager.IMPORTANCE_DEFAULT);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
        }
        try {
            Random rand = new Random();
            Objects.requireNonNull(notificationManager).notify(rand.nextInt(), notificationBuilder.build());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


    }

    @SuppressLint("CheckResult")
    public void updateToken(String token) {
        if (!MainApp.getInstance().getPresenter().isCheckLogin()) return;
    }
}
