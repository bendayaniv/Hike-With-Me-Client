package com.example.hike_with_me_client.Utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.hike_with_me_client.Controller.Activities.MainActivity;
import com.example.hike_with_me_client.R;

import java.util.LinkedList;

import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.content.ContextCompat;

/**
 * NotificationManager is a utility class that handles the creation and management of notifications
 * for the application. It provides methods to create and update foreground notifications,
 * show pop-up notifications, and manage notification channels.
 */
public class NotificationManager {

    // Constants
    private static final String CHANNEL_ID = "com.example.servicestest.CHANNEL_ID_FOREGROUND";
    private static final String SILENT_CHANNEL_ID = "com.example.servicestest.SILENT_CHANNEL_ID";
    private static final String POPUP_CHANNEL_ID = "popup_channel_id";
    private static final int NOTIFICATION_ID = 168;
    private static final int INITIAL_POPUP_NOTIFICATION_ID = 1000;
    private static final int MAX_ACTIVE_NOTIFICATIONS = 5;
    private static final long NOTIFICATION_EXPIRY_TIME_MS = 60000; // 1 minute

    // Member variables
    private final Context context;
    private NotificationCompat.Builder notificationBuilder;
    private int popupNotificationId = INITIAL_POPUP_NOTIFICATION_ID;
    private LinkedList<NotificationInfo> activeNotifications = new LinkedList<>();

    /**
     * Constructor for NotificationManager.
     *
     * @param context The application context.
     */
    public NotificationManager(Context context) {
        this.context = context;
        createNotificationChannels();
    }

    // Notification Channel Creation Methods

    /**
     * Creates all necessary notification channels for the app.
     */
    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createMainChannel();
            createSilentChannel();
            createPopupChannel();
        }
    }

    /**
     * Creates the main notification channel for foreground service notifications.
     */
    private void createMainChannel() {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                android.app.NotificationManager.IMPORTANCE_DEFAULT
        );
        getSystemNotificationManager().createNotificationChannel(channel);
    }

    /**
     * Creates a silent notification channel for non-intrusive notifications.
     */
    private void createSilentChannel() {
        NotificationChannel channel = new NotificationChannel(
                SILENT_CHANNEL_ID,
                "Silent Notifications",
                android.app.NotificationManager.IMPORTANCE_MIN);
        channel.setSound(null, null);
        channel.enableLights(false);
        channel.enableVibration(false);
        channel.setShowBadge(false);
        getSystemNotificationManager().createNotificationChannel(channel);
    }

    /**
     * Creates a notification channel for pop-up notifications.
     */
    private void createPopupChannel() {
        NotificationChannel channel = new NotificationChannel(
                POPUP_CHANNEL_ID,
                "Pop-up Notifications",
                android.app.NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);
        getSystemNotificationManager().createNotificationChannel(channel);
    }

    // Notification Creation and Update Methods

    /**
     * Creates a foreground notification for the service.
     *
     * @param counter The current counter value to display in the notification.
     * @return A Notification object for the foreground service.
     */
    public Notification createForegroundNotification(int counter) {
        PendingIntent pendingIntent = createNotificationPendingIntent();
        notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .setSmallIcon(R.drawable.man_walking)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_round))
                .setContentTitle("App in progress")
                .setContentText(String.valueOf(counter));
        return notificationBuilder.build();
    }

    /**
     * Creates a PendingIntent for the notification.
     *
     * @return A PendingIntent that opens the MainActivity when the notification is tapped.
     */
    private PendingIntent createNotificationPendingIntent() {
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(context, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }

    /**
     * Updates the content of the foreground notification.
     *
     * @param content        Additional content to display in the notification.
     * @param counter        The current counter value.
     * @param anotherCounter Another counter value to display.
     */
    public void updateNotificationContent(String content, int counter, int anotherCounter) {
        if (notificationBuilder != null) {
            notificationBuilder.setContentText("Counter: " + counter + "\nAnother Counter: " + anotherCounter + "\n" + content);
            getSystemNotificationManager().notify(NOTIFICATION_ID, notificationBuilder.build());
        }
    }

    /**
     * Shows a pop-up notification with the current counter value.
     *
     * @param counter The current counter value to display in the pop-up notification.
     */
    public void showPopUpNotification(int counter) {
        clearExpiredNotifications();

        if (activeNotifications.size() >= MAX_ACTIVE_NOTIFICATIONS) {
            NotificationInfo oldest = activeNotifications.removeFirst();
            NotificationManagerCompat.from(context).cancel(oldest.id);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, POPUP_CHANNEL_ID)
                .setSmallIcon(R.drawable.man_walking)
                .setContentTitle("Counter Update")
                .setContentText("Current count: " + counter)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        int notificationId = popupNotificationId++;
        // Check for notification permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                NotificationManagerCompat.from(context).notify(notificationId, builder.build());
                activeNotifications.addLast(new NotificationInfo(notificationId, System.currentTimeMillis()));
            } else {
                // Handle the case where permission is not granted
                Log.e("NotificationManager", "Notification permission not granted");
            }
        } else {
            // For versions below Android 13, no runtime permission is needed
            NotificationManagerCompat.from(context).notify(notificationId, builder.build());
            activeNotifications.addLast(new NotificationInfo(notificationId, System.currentTimeMillis()));
        }
    }

    // Utility Methods

    /**
     * Clears expired notifications from the active notifications list.
     */
    private void clearExpiredNotifications() {
        long currentTime = System.currentTimeMillis();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        activeNotifications.removeIf(info -> {
            if (currentTime - info.timestamp > NOTIFICATION_EXPIRY_TIME_MS) {
                notificationManager.cancel(info.id);
                return true;
            }
            return false;
        });
    }

    /**
     * Gets the system NotificationManager service.
     *
     * @return The system NotificationManager.
     */
    private android.app.NotificationManager getSystemNotificationManager() {
        return (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * Inner class to represent information about an active notification.
     */
    private static class NotificationInfo {
        int id;
        long timestamp;

        NotificationInfo(int id, long timestamp) {
            this.id = id;
            this.timestamp = timestamp;
        }
    }
}