package com.example.hike_with_me_client.Services;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.util.Log;

import com.example.hike_with_me_client.Controller.Activities.MainActivity;
import com.example.hike_with_me_client.Models.Objects.Location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.hike_with_me_client.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * LocationService is a foreground service that handles location tracking and notifications.
 * It provides continuous location updates, manages various types of notifications,
 * and handles service lifecycle events.
 */
public class LocationService extends Service {

    // Constants
    /**
     * Action to start the foreground service.
     */
    public static final String START_FOREGROUND_SERVICE = "START_FOREGROUND_SERVICE";

    /**
     * Action to stop the foreground service.
     */
    public static final String STOP_FOREGROUND_SERVICE = "STOP_FOREGROUND_SERVICE";

    /**
     * Broadcast action for location updates.
     */
    public static final String BROADCAST_LOCATION = "BROADCAST_LOCATION";

    /**
     * Key for notification data in broadcast intent.
     */
    public static final String BROADCAST_NOTIFICATION_KEY = "BROADCAST_NOTIFICATION_KEY";

    /**
     * Key for location data in broadcast intent.
     */
    public static final String BROADCAST_LOCATION_KEY = "BROADCAST_LOCATION_KEY";

    /**
     * Key for counter data in broadcast intent.
     */
    public static final String BROADCAST_COUNTER_KEY = "BROADCAST_COUNTER_KEY";

    /**
     * ID for the main notification.
     */
    public static int NOTIFICATION_ID = 168;

    /**
     * Channel ID for the main notification.
     */
    public static String CHANNEL_ID = "com.example.servicestest.CHANNEL_ID_FOREGROUND";

    /**
     * Main action for the notification intent.
     */
    public static String MAIN_ACTION = "com.example.servicestest.locationservice.action.main";

    /**
     * Channel ID for silent notifications.
     */
    public static String SILENT_CHANNEL_ID = "com.example.servicestest.SILENT_CHANNEL_ID";

    // Time intervals
    private static final int LOCATION_UPDATE_INTERVAL_MS = 1000;
    private static final float LOCATION_UPDATE_DISTANCE_METERS = 0.0f;
    private static final int PERIODIC_TASK_INTERVAL_MS = 2000;
    private static final int CLEAR_NOTIFICATIONS_INTERVAL = 30; // Run every 60 seconds (30 * 2000ms)
    private static final int POPUP_NOTIFICATION_INTERVAL = 5; // Run every 10 seconds (5 * 2000ms)

    // Notification related
    private static final int INITIAL_POPUP_NOTIFICATION_ID = 1000;
    private static final int MAX_ACTIVE_NOTIFICATIONS = 5;
    private static final long NOTIFICATION_EXPIRY_TIME_MS = 60000; // 1 minute

    // Member variables
    private boolean isServiceRunningRightNow = false;
    private boolean isShowingNotification = false;
    private NotificationCompat.Builder notificationBuilder;
    private final Handler handler = new Handler();
    private Runnable runnable;
    private final int interval = 2000; // 2 seconds interval for periodic tasks
    private int counter = 0;
    private int anotherCounter = 0;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private PowerManager.WakeLock wakeLock;
    private PowerManager powerManager;
    private Intent intent;
    private int popupNotificationId = INITIAL_POPUP_NOTIFICATION_ID;

    /**
     * Maximum number of notifications to show at once.
     */
    private static final int MAX_NOTIFICATIONS = MAX_ACTIVE_NOTIFICATIONS;

    /**
     * Time after which a notification expires (in milliseconds).
     */
    private static final long NOTIFICATION_EXPIRY_TIME = NOTIFICATION_EXPIRY_TIME_MS; // 1 minute

    private LinkedList<NotificationInfo> activeNotifications = new LinkedList<>();

    /**
     * Represents information about an active notification.
     */
    private static class NotificationInfo {
        int id;
        long timestamp;

        NotificationInfo(int id, long timestamp) {
            this.id = id;
            this.timestamp = timestamp;
        }
    }

    // Wake lock tag
    private static final String WAKE_LOCK_TAG = "LocationService:WakeLock";

    private static final String LOG_TAG = "LocationService";

    private void logMessage(String message) {
        Log.d(LOG_TAG, message);
    }

    private void logError(String message, Throwable e) {
        Log.e(LOG_TAG, message, e);
    }

    // 1. Service Lifecycle Methods

    /**
     * Called when the service is created. Initializes notification channels.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        createPopupNotificationChannel();
        createSilentNotificationChannel();
    }

    /**
     * Called when the service is started. Handles different start actions.
     *
     * @param intent  The Intent supplied to startService(Intent), as given.
     * @param flags   Additional data about this start request.
     * @param startId A unique integer representing this specific request to start.
     * @return The return value indicates what semantics the system should use for the service's current started state.
     */
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (intent == null || intent.getAction() == null) {
//            return START_NOT_STICKY;
//        }
//        String action = intent.getAction();
//
//        this.intent = new Intent(BROADCAST_LOCATION);
//
//        if (action.equals(START_FOREGROUND_SERVICE)) {
//            if (isServiceRunningRightNow) {
//                return START_STICKY;
//            }
//
//            // Start service with a blank notification
//            startServiceWithoutNotification();
//            // Start GPS updates
//            startGPSUpdates();
//            // Start periodic tasks
//            startRecording();
//        } else if (action.equals(STOP_FOREGROUND_SERVICE)) {
//            stopRecording();
//        }
//
//        return START_STICKY;
//    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null || intent.getAction() == null) {
            return START_NOT_STICKY;
        }

        String action = intent.getAction();
        this.intent = new Intent(BROADCAST_LOCATION);

        if (action.equals(START_FOREGROUND_SERVICE)) {
            handleStartForegroundService();
        } else if (action.equals(STOP_FOREGROUND_SERVICE)) {
            stopRecording();
        }

        return START_STICKY;
    }

    private void handleStartForegroundService() {
        if (isServiceRunningRightNow) {
            return;
        }
        startServiceWithoutNotification();
        startGPSUpdates();
        startRecording();
    }

    /**
     * Called when the service is no longer used and is being destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRecording();
    }

    /**
     * Called when a client binds to the service with bindService().
     *
     * @param intent The Intent that was used to bind to this service.
     * @return Return an IBinder through which clients can call on to the service.
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // 2. Location Handling

    /**
     * Starts GPS updates using FusedLocationProviderClient.
     */
    private void startGPSUpdates() {
        try {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                LocationRequest locationRequest = new LocationRequest.Builder(LOCATION_UPDATE_INTERVAL_MS)
                        .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                        .setMinUpdateDistanceMeters(0.0f)
                        .setMinUpdateIntervalMillis(LOCATION_UPDATE_INTERVAL_MS)
                        .build();

                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
                logMessage("GPS updates started successfully");
            } else {
                logMessage("Location permissions not granted");
            }
        } catch (Exception e) {
            logError("Error starting GPS updates", e);
        }
    }

    /**
     * Callback for location updates.
     */
    private LocationListener locationCallback = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull android.location.Location location) {
            try {
                double lat = location.getLatitude();
                double lon = location.getLongitude();

                logMessage("Location update received: " + lon + ", " + lat);

                Location myLoc = new Location()
                        .setLatitude(lat)
                        .setLongitude(lon)
                        .setDate(null);

                String json = new Gson().toJson(myLoc);
                intent.putExtra(BROADCAST_LOCATION_KEY, json);
                LocalBroadcastManager.getInstance(LocationService.this).sendBroadcast(intent);

                updateNotificationContent("lat: " + lat + ", lon: " + lon);
            } catch (Exception e) {
                logError("Error processing location update", e);
            }
        }
    };

    // 3. Notification Management

    /**
     * Creates the main notification channel.
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    /**
     * Creates the channel for pop-up notifications.
     */
    private void createPopupNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "popup_channel_id";
            CharSequence channelName = "Pop-up Notifications";
            String channelDescription = "Channel for pop-up notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Creates the channel for silent notifications.
     */
    private void createSilentNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    SILENT_CHANNEL_ID,
                    "Silent Notifications",
                    NotificationManager.IMPORTANCE_MIN);
            channel.setSound(null, null);
            channel.enableLights(false);
            channel.enableVibration(false);
            channel.setShowBadge(false);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Creates and shows a notification for the foreground service
     */
//    private void notifyToUserForForegroundService() {
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        notificationIntent.setAction(MAIN_ACTION);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
//
//        notificationBuilder = getNotificationBuilder(this,
//                CHANNEL_ID,
//                NotificationManagerCompat.IMPORTANCE_LOW);
//
//
//        notificationBuilder
//                .setContentIntent(pendingIntent)
//                .setOngoing(true) // true - sticky notification, false - swipeable notification
//                .setSmallIcon(R.drawable.man_walking)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round))
//                .setContentTitle("App in progress")
//                .setContentText(counter + "");
//
//        Notification notification = notificationBuilder.build();
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            startForeground(NOTIFICATION_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION);
//        } else {
//            startForeground(NOTIFICATION_ID, notification);
//        }
//
//        isShowingNotification = true;
//    }
    private void notifyToUserForForegroundService() {
        PendingIntent pendingIntent = createNotificationPendingIntent();
        notificationBuilder = createNotificationBuilder();
        Notification notification = notificationBuilder.build();
        startForegroundWithNotification(notification);
    }

    private PendingIntent createNotificationPendingIntent() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction(MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(this, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }

    private NotificationCompat.Builder createNotificationBuilder() {
        return getNotificationBuilder(this, CHANNEL_ID, NotificationManagerCompat.IMPORTANCE_LOW)
                .setContentIntent(createNotificationPendingIntent())
                .setOngoing(true)
                .setSmallIcon(R.drawable.man_walking)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round))
                .setContentTitle("App in progress")
                .setContentText(counter + "");
    }

    private void startForegroundWithNotification(Notification notification) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(NOTIFICATION_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION);
        } else {
            startForeground(NOTIFICATION_ID, notification);
        }
        isShowingNotification = true;
    }

    /**
     * Updates the content of the main notification.
     *
     * @param content The new content to display in the notification.
     */
    private void updateNotificationContent(String content) {
        try {
            // Check if the notificationBuilder has been initialized
            if (notificationBuilder != null) {
                // Set the notification content text to the current counter value
                notificationBuilder.setContentText("Counter: " + counter + "\nAnother Counter: " + anotherCounter
                        + "\n" + content);

                intent.putExtra(BROADCAST_COUNTER_KEY, counter);

                anotherCounter++;

                // Get the NotificationManager system service
                final NotificationManager notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);

                // Update the existing notification with the new content
                // NOTIFICATION_ID ensures we're updating the correct notification
                notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());

                // Log a debug message to indicate the notification has been updated
                logMessage("Notification content updated");
            }
        } catch (Exception e) {
            logError("Error updating notification content", e);
        }
    }

    /**
     * Shows a new pop-up notification.
     */
    private void showPopUpNotification() {
        try {
            clearExpiredNotifications();

            if (activeNotifications.size() >= MAX_NOTIFICATIONS) {
                // Remove the oldest notification
                NotificationInfo oldest = activeNotifications.removeFirst();
                NotificationManagerCompat.from(this).cancel(oldest.id);
            }

            String popupChannelId = "popup_channel_id";
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, popupChannelId)
                    .setSmallIcon(R.drawable.man_walking)
                    .setContentTitle("Counter Update")
                    .setContentText("Current count: " + counter)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                int notificationId = popupNotificationId++;
                notificationManager.notify(notificationId, builder.build());
                activeNotifications.addLast(new NotificationInfo(notificationId, System.currentTimeMillis()));
                logMessage("Pop-up notification shown");
            } else {
                logMessage("Notification permission not granted");
            }
        } catch (Exception e) {
            logError("Error showing pop-up notification", e);
        }
    }

    // 4. Periodic Task Management

//    /**
//     * Starts periodic tasks for the service
//     */
//    private void startRecording() {
//        isServiceRunningRightNow = true;
//        runnable = new Runnable() {
//            public void run() {
//                // Log for debugging
//                Log.d("LocationService", "Periodic task executed");
//
//                // Check and update notification permission
//                checkAndUpdateNotificationPermission();
//
//                if (counter % 30 == 0) { // Since the interval is 2 seconds, we check every 60 seconds
//                    clearExpiredNotifications();
//                }
//
//                // Show pop-up notification every 10 seconds
//                if (counter % 5 == 0) { // Since the interval is 2 seconds, we check for multiples of 5
//                    showPopUpNotification();
//                }
//
//                // Increment the counter for the next update
//                counter++;
//
//                // Schedule the next run
//                handler.postDelayed(this, interval);
//            }
//        };
//
//        handler.post(runnable);
//
//
//        // Keep CPU working
//        powerManager = (PowerManager) getSystemService(POWER_SERVICE);
//        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "PassiveApp:tag");
//        wakeLock.acquire();
//    }

    /**
     * Starts periodic tasks for the service
     */
    private void startRecording() {
        try {
            isServiceRunningRightNow = true;
            startPeriodicTasks();
            acquireWakeLock();
            logMessage("Recording started successfully");
        } catch (Exception e) {
            logError("Error starting recording", e);
        }
    }

    /**
     * Initializes and starts the periodic tasks
     */
    private void startPeriodicTasks() {
        runnable = new Runnable() {
            public void run() {
                performPeriodicTasks();
                scheduleNextRun();
            }
        };
//        handler.post(runnable);
        handler.postDelayed(runnable, PERIODIC_TASK_INTERVAL_MS);
    }

    /**
     * Performs the periodic tasks
     */
    private void performPeriodicTasks() {
        logMessage("Periodic task executed");
        checkAndUpdateNotificationPermission();

        if (counter % CLEAR_NOTIFICATIONS_INTERVAL == 0) {
            clearExpiredNotifications();
        }

        if (counter % POPUP_NOTIFICATION_INTERVAL == 0) {
            showPopUpNotification();
        }

        counter++;
    }

    /**
     * Schedules the next run of periodic tasks
     */
    private void scheduleNextRun() {
        handler.postDelayed(runnable, interval);
    }

    /**
     * Acquires a wake lock to keep the CPU running
     */
    private void acquireWakeLock() {
        powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, WAKE_LOCK_TAG);
        wakeLock.acquire();
    }

//    /**
//     * Stops the service and removes notifications
//     */
//    private void stopRecording() {
//        handler.removeCallbacks(runnable);
//        Log.d("LocationService", "Service stopped");
//
//        wakeLock.release();
//
//        // Stop GPS
//        if (fusedLocationProviderClient != null) {
//            Task<Void> task = fusedLocationProviderClient.removeLocationUpdates(locationCallback);
//            task.addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if (task.isSuccessful()) {
//                        Log.d("pttt", "stop Location Callback removed.");
//                    } else {
//                        Log.d("pttt", "stop Failed to remove Location Callback.");
//                    }
//                    stopSelf();
//                }
//            });
//        }
//
//        // Stop the foreground service
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            stopForeground(STOP_FOREGROUND_REMOVE);
//        } else {
//            stopForeground(true);
//        }
//
//        isServiceRunningRightNow = false;
//    }

    /**
     * Stops the service and removes notifications
     */
    private void stopRecording() {
        try {
            stopPeriodicTasks();
            releaseWakeLock();
            stopGPSUpdates();
            stopForegroundService();
            logMessage("Recording stopped successfully");
        } catch (Exception e) {
            logError("Error stopping recording", e);
        }
    }

    private void stopPeriodicTasks() {
        handler.removeCallbacks(runnable);
        logMessage("Periodic tasks stopped");
    }

    private void releaseWakeLock() {
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
        }
    }

    private void stopGPSUpdates() {
        if (fusedLocationProviderClient != null) {
            Task<Void> task = fusedLocationProviderClient.removeLocationUpdates(locationCallback);
            task.addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        logMessage("Location updates stopped successfully");
                    } else {
                        logError("Failed to remove location callback", null);
                    }
                    stopSelf();
                }
            });
        }
    }

    private void stopForegroundService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_REMOVE);
        } else {
            stopForeground(true);
        }
        isServiceRunningRightNow = false;
    }

    // 5. Utility Methods

    /**
     * Creates a notification builder based on the Android version
     */
    public static NotificationCompat.Builder getNotificationBuilder(Context context, String channelId, int importance) {
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            prepareChannel(context, channelId, importance);
            builder = new NotificationCompat.Builder(context, channelId);
        } else {
            builder = new NotificationCompat.Builder(context);
        }
        return builder;
    }

    /**
     * Prepares the notification channel for Android O and above
     */
    private static void prepareChannel(Context context, String id, int importance) {
        final String appName = context.getString(R.string.app_name);
        String notifications_channel_description = "HIKE-WITH-ME app location channel";
        final NotificationManager nm = (NotificationManager) context.getSystemService(Service.NOTIFICATION_SERVICE);

        if (nm != null) {
            NotificationChannel nChannel = nm.getNotificationChannel(id);

            if (nChannel == null) {
                nChannel = new NotificationChannel(id, appName, importance);
                nChannel.setDescription(notifications_channel_description);
                nChannel.enableLights(true);
                nChannel.setLightColor(Color.BLUE);
                nm.createNotificationChannel(nChannel);
            }
        }
    }

    /**
     * Clears expired notifications.
     */
    private void clearExpiredNotifications() {
        long currentTime = System.currentTimeMillis();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        Iterator<NotificationInfo> iterator = activeNotifications.iterator();
        while (iterator.hasNext()) {
            NotificationInfo info = iterator.next();
            if (currentTime - info.timestamp > NOTIFICATION_EXPIRY_TIME) {
                notificationManager.cancel(info.id);
                iterator.remove();
            } else {
                // Notifications are ordered by time, so we can stop checking once we find a non-expired one
                break;
            }
        }
    }

    /**
     * Clears all active notifications.
     */
    private void clearAllNotifications() {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        for (NotificationInfo info : activeNotifications) {
            notificationManager.cancel(info.id);
        }
        activeNotifications.clear();
    }

    /**
     * Starts the service with a blank notification
     */
    private void startServiceWithoutNotification() {
        isServiceRunningRightNow = true;
        isShowingNotification = false;
        Notification notification = createInitialNotification();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(NOTIFICATION_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION);
        } else {
            startForeground(NOTIFICATION_ID, notification);
        }
    }

    /**
     * Creates an initial blank notification.
     */
    private Notification createInitialNotification() {
        return new NotificationCompat.Builder(this, SILENT_CHANNEL_ID)
                .setContentTitle("")
                .setContentText("")
                .setSmallIcon(R.drawable.man_walking)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setVisibility(NotificationCompat.VISIBILITY_SECRET)
                .build();
    }

    /**
     * Checks for notification permission and updates the service accordingly
     */
    private void checkAndUpdateNotificationPermission() {
        logMessage("Checking notification permission");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            logMessage("Notification permission check (TIRAMISU)");
            boolean hasPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    == PackageManager.PERMISSION_GRANTED;

            if (hasPermission) {
                if (!isShowingNotification) {
                    logMessage("Notification permission granted - showing notification");
                    notifyToUserForForegroundService();
                    isShowingNotification = true;
                }
                if (isServiceRunningRightNow) {
                    logMessage("Notification permission granted - updating notification content");
                }
            } else {
                if (isShowingNotification) {
                    logMessage("Notification permission not granted - removing notification");
                    NotificationManagerCompat.from(this).cancel(NOTIFICATION_ID);
                    startForeground(NOTIFICATION_ID, createBlankNotification());
                    isShowingNotification = false;
                } else {
                    logError("Notification permission not granted", null);
                }
            }
        }
    }

    /**
     * Creates a blank notification for when permissions are not granted
     */
    private Notification createBlankNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("")
                    .build();
        } else {
            return new Notification();
        }
    }
}
