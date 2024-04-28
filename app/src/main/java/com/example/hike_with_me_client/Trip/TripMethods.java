package com.example.hike_with_me_client.Trip;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.TextView;

import com.example.hike_with_me_client.Trip.Actions.CreateTrip;
import com.example.hike_with_me_client.Trip.Actions.DeleteTrip;
import com.example.hike_with_me_client.Trip.Actions.GetTripsByUser;
import com.example.hike_with_me_client.Trip.Actions.UpdateTrip;
import com.example.hike_with_me_client.Trip.Callbacks.Callback_CreateTrip;
import com.example.hike_with_me_client.Trip.Callbacks.Callback_DeleteTrip;
import com.example.hike_with_me_client.Trip.Callbacks.Callback_GetTripsByUser;
import com.example.hike_with_me_client.Trip.Callbacks.Callback_UpdateTrip;

import java.util.List;

@SuppressLint("SetTextI18n")
public class TripMethods {

    public static void getTripsByUser(String userId, TextView textView) {
        Callback_GetTripsByUser callback_getTripsByUser = new Callback_GetTripsByUser() {
            @Override
            public void success(List<Trip> trips) {
                if(trips.size() == 0) {
                    textView.setText("No trips found");
                } else {
                    textView.setText("Trips found: " + trips);
                }
            }

            @Override
            public void error(String message) {
                Log.d("pttt", "Error: " + message);
                textView.setText("Error: " + message + "\nNo trips found");
            }
        };
        new GetTripsByUser(callback_getTripsByUser).getTripsByUser(userId);
    }

    public static void createTrip(Trip trip) {
        Callback_CreateTrip callback_createTrip = new Callback_CreateTrip() {
            @Override
            public void success(Trip trip) {
                Log.d("pttt", "Trip created: " + trip);
            }

            @Override
            public void error(String message) {
                Log.d("pttt", "Error: " + message);
            }
        };
        new CreateTrip(callback_createTrip).createTrip(trip);
    }

    public static void updateTrip(Trip trip) {
        Callback_UpdateTrip callback_updateTrip = new Callback_UpdateTrip() {
            @Override
            public void success(Trip trip) {
                Log.d("pttt", "Trip updated: " + trip);
            }

            @Override
            public void error(String message) {
                Log.d("pttt", "Error: " + message);
            }
        };
        new UpdateTrip(callback_updateTrip).updateTrip(trip);
    }

    public static void deleteTrip(String userId, String tripId) {
        Callback_DeleteTrip callback_deleteTrip = new Callback_DeleteTrip() {
            @Override
            public void success(String message) {
                Log.d("pttt", "Message: " + message);
            }

            @Override
            public void error(String message) {
                Log.d("pttt", "Error: " + message);
            }
        };
        new DeleteTrip(callback_deleteTrip).deleteTrip(userId, tripId);
    }
}
