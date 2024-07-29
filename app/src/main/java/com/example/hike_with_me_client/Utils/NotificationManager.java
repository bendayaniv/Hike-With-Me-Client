package com.example.hike_with_me_client.Utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.hike_with_me_client.Controller.Activities.MainActivity;
import com.example.hike_with_me_client.Models.Hazard.Hazard;
import com.example.hike_with_me_client.R;
import com.example.hike_with_me_client.Utils.Singleton.ListOfHazards;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
    public static final String OPEN_APP_DISMISS_NOTIFICATIONS = "OPEN_APP_DISMISS_NOTIFICATIONS";
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

    public static final String DB_FILE = "HIKE_WITH_ME_LOCAL_DB";
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    /**
     * Constructor for NotificationManager.
     *
     * @param context The application context.
     */
    public NotificationManager(Context context) {
        this.context = context;
        // clean SharedPreferences
        context.deleteSharedPreferences(DB_FILE);
        preferences = context.getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
        createNotificationChannels();
    }

    public void setList(List<Hazard> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor = preferences.edit();
        editor.putString(DB_FILE, json);
        editor.commit();
    }

    public ArrayList<Hazard> getList() {
        ArrayList<Hazard> list = null;
        String serializeObject = preferences.getString(DB_FILE, null);
        if (serializeObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Hazard>>() {
            }.getType();
            list = gson.fromJson(serializeObject, type);
        }
        return list == null ? new ArrayList<Hazard>() : list;
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

    public Notification createSilentNotification() {
        return new NotificationCompat.Builder(context, SILENT_CHANNEL_ID)
                .setContentTitle("")
                .setContentText("")
                .setSmallIcon(R.drawable.man_walking) // Create a transparent icon
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setVisibility(NotificationCompat.VISIBILITY_SECRET)
                .build();
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

    private PendingIntent createPopupNotificationPendingIntent() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setAction(OPEN_APP_DISMISS_NOTIFICATIONS);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
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

        Log.d("NotificationManager", "ListOfHazards.getInstance().getHazards(): " + ListOfHazards.getInstance().getHazards());

//        context.getSharedPreferences()
        ArrayList<Hazard> alreadyNoticed = getList();
        Log.d("NotificationManager", "alreadyNoticed: " + alreadyNoticed);

//        ArrayList<Hazard> didNotNoticed = new ArrayList<>();
//
//        Log.d("NotificationManager", "didNotNoticed1: " + didNotNoticed);
//
//        // for loop who gets all over ListOfHazards.getInstance().getHazards(), check which is not in list of alreadyNoticed, and put them in didNotNoticed
//        for (Hazard hazard : ListOfHazards.getInstance().getHazards()) {
//            if (!alreadyNoticed.contains(hazard)) {
//                didNotNoticed.add(hazard);
//            }
//        }

        List<Hazard> didNotNoticed = ListOfHazards.getInstance().getHazards().stream()
                .filter(hazard -> alreadyNoticed.stream()
                        .noneMatch(noticed -> noticed.getId().equals(hazard.getId())))
                .collect(Collectors.toList());

        Log.d("NotificationManager", "didNotNoticed1: " + didNotNoticed);

        // updating the SharedPreferences with setList with the ListOfHazards.getInstance().getHazards() if there is a new hazard
        if (!didNotNoticed.isEmpty()) {
            Log.d("NotificationManager", "didNotNoticed2: " + didNotNoticed);
            setList(ListOfHazards.getInstance().getHazards());

            // get over all didNotNoticed and show the pop-up notification
            for (Hazard hazard : didNotNoticed) {
//                Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher); // To set the icon app

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, POPUP_CHANNEL_ID)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.man_walking))  // This sets your app icon
                        .setSmallIcon(R.drawable.hazard_sign)
                        .setContentTitle("Hazard In Your Area!")
                        .setContentText(hazard.getDescription())
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setContentIntent(createPopupNotificationPendingIntent());

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
        }

////        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher); // To set the icon app
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, POPUP_CHANNEL_ID)
//                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.man_walking))  // This sets your app icon
//                .setSmallIcon(R.drawable.hazard_sign)
//                .setContentTitle("Hazard In Your Area!")
//                .setContentText(ListOfHazards.getInstance().getHazards().get(0).getDescription())
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setAutoCancel(true)
//                .setDefaults(NotificationCompat.DEFAULT_ALL);
//
//        int notificationId = popupNotificationId++;
//        // Check for notification permission
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
//                NotificationManagerCompat.from(context).notify(notificationId, builder.build());
//                activeNotifications.addLast(new NotificationInfo(notificationId, System.currentTimeMillis()));
//            } else {
//                // Handle the case where permission is not granted
//                Log.e("NotificationManager", "Notification permission not granted");
//            }
//        } else {
//            // For versions below Android 13, no runtime permission is needed
//            NotificationManagerCompat.from(context).notify(notificationId, builder.build());
//            activeNotifications.addLast(new NotificationInfo(notificationId, System.currentTimeMillis()));
//        }
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