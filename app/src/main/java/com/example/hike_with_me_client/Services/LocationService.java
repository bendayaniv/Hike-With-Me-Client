package com.example.hike_with_me_client.Services;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.util.Log;

import com.example.hike_with_me_client.BroadcastReceivers.DailyTaskReceiver;
import com.example.hike_with_me_client.Models.Hazard.HazardMethods;
import com.example.hike_with_me_client.Models.Objects.Location;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.hike_with_me_client.Models.Trip.TripMethods;
import com.example.hike_with_me_client.Models.User.UserMethods;
import com.example.hike_with_me_client.Utils.GlobalUtilInstances.CurrentUser;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.Task;

import java.util.Calendar;
import java.util.Date;

/**
 * LocationService is a foreground service that handles location tracking and notifications.
 * It provides continuous location updates, manages various types of notifications,
 * and handles service lifecycle events.
 */
public class LocationService extends Service {

    // 1. Constants
    public static final String START_FOREGROUND_SERVICE = "START_FOREGROUND_SERVICE";
    public static final String STOP_FOREGROUND_SERVICE = "STOP_FOREGROUND_SERVICE";
    public static final int NOTIFICATION_ID = 168;
    private static final String WAKE_LOCK_TAG = "LocationService:WakeLock";
    private static final String LOG_TAG = "LocationService";

    // Time intervals
    private static final int LOCATION_UPDATE_INTERVAL_MS = 120000; // Every 120 seconds
    private static final float LOCATION_UPDATE_DISTANCE_METERS = 200.0f;
    private static final int PERIODIC_TASK_INTERVAL_MS = 60000; // Every 60 seconds

    // 2. Member variables
    private boolean isServiceRunningRightNow = false;
    private final Handler handler = new Handler();
    private Runnable runnable;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private PowerManager.WakeLock wakeLock;
    private PowerManager powerManager;
    private com.example.hike_with_me_client.Utils.NotificationManager notificationManager;

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
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null || intent.getAction() == null) {
            return START_NOT_STICKY;
        }

        String action = intent.getAction();

        switch (action) {
            case START_FOREGROUND_SERVICE:
                handleStartForegroundService();
                break;
            case STOP_FOREGROUND_SERVICE:
                stopRecording();
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
        setupDailyAlarm();
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
    private LocationListener locationCallback = location -> {
        try {
            double lat = location.getLatitude();
            double lon = location.getLongitude();

            logMessage("Location update received: " + lat + ", " + lon);

            // get me the current time with Date object
            Date currentDate = new Date();

            Log.d("LocationService", "Location update received: " + lat + ", " + lon + " at " + currentDate.toString());

            Location myLoc = new Location()
                    .setLatitude(lat)
                    .setLongitude(lon)
                    .setDate(currentDate);

            CurrentUser.getInstance().getUser().setLocation(myLoc);
            CurrentUser.getInstance().setInitiateLocation(myLoc);
            UserMethods.updateUser(CurrentUser.getInstance().getUser());

            if (CurrentUser.getInstance().getActiveTrips() != null && !CurrentUser.getInstance().getActiveTrips().isEmpty()) {
                // For every active trip add the location to the list of the locations
                // and save the list of the locations to the trip
                Log.d("LocationService", "Active trips");
                for (com.example.hike_with_me_client.Models.Trip.trip trip : CurrentUser.getInstance().getActiveTrips()) {
                    trip.addLocation(myLoc);

                    TripMethods.updateTrip(trip);
                }
            }
        } catch (Exception e) {
            logError("Error processing location update", e);
        }
    };

    // 5. Notification Management

    private void startForegroundWithSilentNotification() {
        Notification silentNotification = notificationManager.createSilentNotification();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(NOTIFICATION_ID, silentNotification, ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION);
        } else {
            startForeground(NOTIFICATION_ID, silentNotification);
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
        runnable = () -> {
            performPeriodicTasks();
            scheduleNextRun();
            HazardMethods.getNearHazards();
        };
        handler.postDelayed(runnable, PERIODIC_TASK_INTERVAL_MS);
    }

    /**
     * Performs the periodic tasks
     */
    private void performPeriodicTasks() {
        logMessage("Periodic task executed");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkAndUpdateNotificationPermission();
        }
        notificationManager.showPopUpNotification();
    }

    private void setupDailyAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, DailyTaskReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Set the alarm to start at midnight
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        // If it's already past midnight, set it for the next day
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        // Schedule the alarm to repeat every day
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

        Log.d(LOG_TAG, "Daily alarm set for midnight");
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
        wakeLock.acquire(10 * 60 * 1000L /*10 minutes*/);
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
            cancelDailyAlarm();
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
            task.addOnCompleteListener(task1 -> {
                if (task1.isSuccessful()) {
                    logMessage("Location updates stopped successfully");
                } else {
                    logError("Failed to remove location callback", null);
                }
                stopSelf();
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

    private void cancelDailyAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, DailyTaskReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        alarmManager.cancel(pendingIntent);
        Log.d(LOG_TAG, "Daily alarm cancelled");
    }

    // 7. Utility Methods

    /**
     * Starts the service with a blank notification
     */
    private void startServiceWithoutNotification() {
        isServiceRunningRightNow = true;
        startForegroundWithSilentNotification();
    }

    /**
     * Checks for notification permission and updates the service accordingly
     */
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void checkAndUpdateNotificationPermission() {
        logMessage("Checking notification permission");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            logMessage("Notification permission check (TIRAMISU)");
            boolean hasPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    == PackageManager.PERMISSION_GRANTED;

            if (hasPermission) {
                if (isServiceRunningRightNow) {
                    logMessage("Notification permission granted - updating notification content");
                }
            } else {
                logError("Notification permission not granted", null);
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
