package com.example.hike_with_me_client.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.hike_with_me_client.Models.Trip.TripMethods;

public class DailyTaskReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Perform your daily task here
        performDailyTask();
    }

    private void performDailyTask() {
        Log.d("DailyTaskReceiver", "Performing daily task at midnight");

        TripMethods.getTripsByUser();
    }
}