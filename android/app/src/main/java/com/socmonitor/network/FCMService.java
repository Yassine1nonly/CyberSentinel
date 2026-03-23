package com.socmonitor.network;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.socmonitor.R;
import com.socmonitor.ui.activities.MainActivity;

public class FCMService extends FirebaseMessagingService {

    private static final String CHANNEL_CRITICAL = "SOC_CRITICAL";
    private static final String CHANNEL_HIGH     = "SOC_HIGH";
    private static final String CHANNEL_GENERAL  = "SOC_GENERAL";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            String severity = remoteMessage.getData().get("severity");
            String title    = remoteMessage.getData().get("title");
            String body     = remoteMessage.getData().get("description");
            sendNotification(title, body, severity);
        }
        if (remoteMessage.getNotification() != null) {
            sendNotification(
                    remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody(),
                    "MEDIUM"
            );
        }
    }

    @Override
    public void onNewToken(String token) {
        // Send this token to your backend server to register device
        // ApiClient.getInstance().registerDevice(token);
    }

    private void sendNotification(String title, String body, String severity) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE
        );

        String channelId = getChannelId(severity);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder nb = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_shield)
                .setContentTitle("🚨 " + (title != null ? title : "SOC Alert"))
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setAutoCancel(true)
                .setSound(sound)
                .setPriority(getPriority(severity))
                .setContentIntent(pendingIntent);

        if ("CRITICAL".equalsIgnoreCase(severity)) {
            nb.setColor(0xFFB71C1C);
        } else if ("HIGH".equalsIgnoreCase(severity)) {
            nb.setColor(0xFFE65100);
        }

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createChannels(nm);
        nm.notify((int) System.currentTimeMillis(), nb.build());
    }

    private void createChannels(NotificationManager nm) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nm.createNotificationChannel(new NotificationChannel(
                    CHANNEL_CRITICAL, "Critical Alerts",
                    NotificationManager.IMPORTANCE_HIGH));
            nm.createNotificationChannel(new NotificationChannel(
                    CHANNEL_HIGH, "High Alerts",
                    NotificationManager.IMPORTANCE_HIGH));
            nm.createNotificationChannel(new NotificationChannel(
                    CHANNEL_GENERAL, "General Alerts",
                    NotificationManager.IMPORTANCE_DEFAULT));
        }
    }

    private String getChannelId(String severity) {
        if ("CRITICAL".equalsIgnoreCase(severity)) return CHANNEL_CRITICAL;
        if ("HIGH".equalsIgnoreCase(severity))     return CHANNEL_HIGH;
        return CHANNEL_GENERAL;
    }

    private int getPriority(String severity) {
        if ("CRITICAL".equalsIgnoreCase(severity) || "HIGH".equalsIgnoreCase(severity))
            return NotificationCompat.PRIORITY_HIGH;
        return NotificationCompat.PRIORITY_DEFAULT;
    }
}
