package com.example.hike_with_me_client.Services;

import android.Manifest;
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
import com.example.hike_with_me_client.Models.Hazard.HazardMethods;
import com.example.hike_with_me_client.Models.Objects.Location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.hike_with_me_client.Models.User.UserMethods;
import com.example.hike_with_me_client.R;
import com.example.hike_with_me_client.Utils.Singleton.CurrentUser;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.util.LinkedList;

/**
 * LocationService is a foreground service that handles location tracking and notifications.
 * It provides continuous location updates, manages various types of notifications,
 * and handles service lifecycle events.
 */
public class LocationService extends Service {

    // 1. Constants
    public static final String START_FOREGROUND_SERVICE = "START_FOREGROUND_SERVICE";
    public static final String STOP_FOREGROUND_SERVICE = "STOP_FOREGROUND_SERVICE";
    public static final String BROADCAST_LOCATION = "BROADCAST_LOCATION";
    public static final String BROADCAST_LOCATION_KEY = "BROADCAST_LOCATION_KEY";
    public static final String BROADCAST_COUNTER_KEY = "BROADCAST_COUNTER_KEY";
    public static final int NOTIFICATION_ID = 168;
    public static final String CHANNEL_ID = "com.example.servicestest.CHANNEL_ID_FOREGROUND";
    public static final String MAIN_ACTION = "com.example.servicestest.locationservice.action.main";
    private static final String WAKE_LOCK_TAG = "LocationService:WakeLock";
    private static final String LOG_TAG = "LocationService";
    public static final String ACTION_ENABLE_STICKY_NOTIFICATION = "ACTION_ENABLE_STICKY_NOTIFICATION";
    public static final String ACTION_DISABLE_STICKY_NOTIFICATION = "ACTION_DISABLE_STICKY_NOTIFICATION";
    public static final String ACTION_ENABLE_POPUP_NOTIFICATIONS = "ACTION_ENABLE_POPUP_NOTIFICATIONS";
    public static final String ACTION_DISABLE_POPUP_NOTIFICATIONS = "ACTION_DISABLE_POPUP_NOTIFICATIONS";

    // Time intervals
    private static final int LOCATION_UPDATE_INTERVAL_MS = 1000;
    private static final float LOCATION_UPDATE_DISTANCE_METERS = 0.0f;
    private static final int PERIODIC_TASK_INTERVAL_MS = 10000;
    private static final int POPUP_NOTIFICATION_INTERVAL = 5; // Run every 10 seconds (5 * 2000ms)

    // 2. Member variables
    private boolean isServiceRunningRightNow = false;
    private boolean isShowingNotification = false;
    private final Handler handler = new Handler();
    private Runnable runnable;
    private int counter = 0;
    private int anotherCounter = 0;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private PowerManager.WakeLock wakeLock;
    private PowerManager powerManager;
    private Intent intent;
    private com.example.hike_with_me_client.Utils.NotificationManager notificationManager;
    private boolean enableStickyNotification = false;
    private boolean enablePopUpNotifications = true;

    // 3. Service Lifecycle Methods

    /**
     * Called when the service is created. Initializes notification channels.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = new com.example.hike_with_me_client.Utils.NotificationManager(this);
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
//
//        String action = intent.getAction();
//        this.intent = new Intent(BROADCAST_LOCATION);
//
//        if (action.equals(START_FOREGROUND_SERVICE)) {
//            handleStartForegroundService();
//        } else if (action.equals(STOP_FOREGROUND_SERVICE)) {
//            stopRecording();
//        }
//
//        return START_STICKY;
//    }
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (intent == null || intent.getAction() == null) {
//            return START_NOT_STICKY;
//        }
//
//        String action = intent.getAction();
//        this.intent = new Intent(BROADCAST_LOCATION);
//
//        switch (action) {
//            case START_FOREGROUND_SERVICE:
//                handleStartForegroundService();
//                break;
//            case STOP_FOREGROUND_SERVICE:
//                stopRecording();
//                break;
//            case ACTION_ENABLE_STICKY_NOTIFICATION:
//                enableStickyNotification = true;
//                updateNotificationVisibility();
//                break;
//            case ACTION_ENABLE_POPUP_NOTIFICATIONS:
//                enablePopUpNotifications = true;
//                break;
//            case ACTION_DISABLE_STICKY_NOTIFICATION:
//                enableStickyNotification = false;
//                updateNotificationVisibility();
//                break;
//            case ACTION_DISABLE_POPUP_NOTIFICATIONS:
//                enablePopUpNotifications = false;
//                break;
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
//        this.intent = new Intent(BROADCAST_LOCATION);

        switch (action) {
            case START_FOREGROUND_SERVICE:
                handleStartForegroundService();
                break;
            case STOP_FOREGROUND_SERVICE:
                stopRecording();
                break;
            case ACTION_ENABLE_STICKY_NOTIFICATION:
                enableStickyNotification = true;
                updateNotificationVisibility();
                break;
            case ACTION_DISABLE_STICKY_NOTIFICATION:
                enableStickyNotification = false;
                updateNotificationVisibility();
                break;
            case ACTION_ENABLE_POPUP_NOTIFICATIONS:
                enablePopUpNotifications = true;
                break;
            case ACTION_DISABLE_POPUP_NOTIFICATIONS:
                enablePopUpNotifications = false;
                break;
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

    // 4. Location Handling

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
                        .setMinUpdateDistanceMeters(LOCATION_UPDATE_DISTANCE_METERS)
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

                logMessage("Location update received: " + lat + ", " + lon);

                Location myLoc = new Location()
                        .setLatitude(lat)
                        .setLongitude(lon)
                        .setDate(null);

//                String json = new Gson().toJson(myLoc);
//                intent.putExtra(BROADCAST_LOCATION_KEY, json);
//                LocalBroadcastManager.getInstance(LocationService.this).sendBroadcast(intent);

                CurrentUser.getInstance().getUser().setLocation(myLoc);
                UserMethods.updateUser(CurrentUser.getInstance().getUser());

                if (enableStickyNotification)
                    updateNotificationContent("lat: " + lat + ", lon: " + lon);
            } catch (Exception e) {
                logError("Error processing location update", e);
            }
        }
    };

    // 5. Notification Management

    /**
     * Updates the content of the main notification.
     *
     * @param content The new content to display in the notification.
     */
    private void updateNotificationContent(String content) {
        notificationManager.updateNotificationContent(content, counter, anotherCounter);
        anotherCounter++;
    }

    /**
     * Shows a new pop-up notification.
     */
//    private void showPopUpNotification() {
//        notificationManager.showPopUpNotification(counter);
//    }
    private void showPopUpNotification() {
        if (enablePopUpNotifications) {
            notificationManager.showPopUpNotification(counter);
        }
    }

    /**
     * Creates and shows a notification for the foreground service
     */
    private PendingIntent createNotificationPendingIntent() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction(MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(this, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }

//    private NotificationCompat.Builder createNotificationBuilder() {
//        return getNotificationBuilder(this, CHANNEL_ID, NotificationManagerCompat.IMPORTANCE_LOW)
//                .setContentIntent(createNotificationPendingIntent())
//                .setOngoing(true)
//                .setSmallIcon(R.drawable.man_walking)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round))
//                .setContentTitle("App in progress")
//                .setContentText(counter + "");
//    }

    private void startForegroundWithNotification(Notification notification) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(NOTIFICATION_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION);
        } else {
            startForeground(NOTIFICATION_ID, notification);
        }
        isShowingNotification = true;
    }

    private void updateNotificationVisibility() {
        if (enableStickyNotification) {
            Notification notification = notificationManager.createForegroundNotification(counter);
            startForegroundWithNotification(notification);
        } else {
            startForegroundWithSilentNotification();
        }
    }

    private void startForegroundWithSilentNotification() {
        Notification silentNotification = notificationManager.createSilentNotification();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(NOTIFICATION_ID, silentNotification, ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION);
        } else {
            startForeground(NOTIFICATION_ID, silentNotification);
        }
        isShowingNotification = false;
    }

//    /**
//     * Creates a notification builder based on the Android version
//     */
//    public static NotificationCompat.Builder getNotificationBuilder(Context context, String channelId, int importance) {
//        NotificationCompat.Builder builder;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            prepareChannel(context, channelId, importance);
//            builder = new NotificationCompat.Builder(context, channelId);
//        } else {
//            builder = new NotificationCompat.Builder(context);
//        }
//        return builder;
//    }

//    /**
//     * Prepares the notification channel for Android O and above
//     */
//    private static void prepareChannel(Context context, String id, int importance) {
//        final String appName = context.getString(R.string.app_name);
//        String notifications_channel_description = "HIKE-WITH-ME app location channel";
//        final NotificationManager nm = (NotificationManager) context.getSystemService(Service.NOTIFICATION_SERVICE);
//
//        if (nm != null) {
//            NotificationChannel nChannel = nm.getNotificationChannel(id);
//
//            if (nChannel == null) {
//                nChannel = new NotificationChannel(id, appName, importance);
//                nChannel.setDescription(notifications_channel_description);
//                nChannel.enableLights(true);
//                nChannel.setLightColor(Color.BLUE);
//                nm.createNotificationChannel(nChannel);
//            }
//        }
//    }

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

    // 6. Periodic Task Management

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
                HazardMethods.getNearHazards();
            }
        };
        handler.postDelayed(runnable, PERIODIC_TASK_INTERVAL_MS);
    }

    /**
     * Performs the periodic tasks
     */
    private void performPeriodicTasks() {
        logMessage("Periodic task executed");
        checkAndUpdateNotificationPermission();

//        if (counter % POPUP_NOTIFICATION_INTERVAL == 0) {
        showPopUpNotification();
//        }

        counter++;
    }

    /**
     * Schedules the next run of periodic tasks
     */
    private void scheduleNextRun() {
        handler.postDelayed(runnable, PERIODIC_TASK_INTERVAL_MS);
    }

    /**
     * Acquires a wake lock to keep the CPU running
     */
    private void acquireWakeLock() {
        powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, WAKE_LOCK_TAG);
        wakeLock.acquire();
    }

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

    // 7. Utility Methods

    /**
     * Starts the service with a blank notification
     */
//    private void startServiceWithoutNotification() {
//        isServiceRunningRightNow = true;
//        isShowingNotification = false;
//        Notification notification = notificationManager.createForegroundNotification(counter);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            startForeground(NOTIFICATION_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION);
//        } else {
//            startForeground(NOTIFICATION_ID, notification);
//        }
//    }
    private void startServiceWithoutNotification() {
        isServiceRunningRightNow = true;
        updateNotificationVisibility();
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
                if (!isShowingNotification && enableStickyNotification /*&& false*/) {
                    logMessage("Notification permission granted - showing notification");
                    Notification notification = notificationManager.createForegroundNotification(counter);
                    startForegroundWithNotification(notification);
                    isShowingNotification = true;
                } else {
                    logMessage("Notification permission granted - not showing notification");
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

    private void logMessage(String message) {
        Log.d(LOG_TAG, message);
    }

    private void logError(String message, Throwable e) {
        Log.e(LOG_TAG, message, e);
    }
}
