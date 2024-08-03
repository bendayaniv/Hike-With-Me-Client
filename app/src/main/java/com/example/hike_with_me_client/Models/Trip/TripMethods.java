package com.example.hike_with_me_client.Models.Trip;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.hike_with_me_client.Interfaces.Trip.Callbacks.Callback_CreateTrip;
import com.example.hike_with_me_client.Interfaces.Trip.Callbacks.Callback_DeleteImage;
import com.example.hike_with_me_client.Interfaces.Trip.Callbacks.Callback_DeleteTrip;
import com.example.hike_with_me_client.Interfaces.Trip.Callbacks.Callback_GetTripsByUser;
import com.example.hike_with_me_client.Interfaces.Trip.Callbacks.Callback_UploadImages;
import com.example.hike_with_me_client.Models.Trip.Actions.CreateTrip;
import com.example.hike_with_me_client.Models.Trip.Actions.DeleteImage;
import com.example.hike_with_me_client.Models.Trip.Actions.DeleteTrip;
import com.example.hike_with_me_client.Models.Trip.Actions.GetTripsByUser;
import com.example.hike_with_me_client.Models.Trip.Actions.UpdateTrip;
import com.example.hike_with_me_client.Interfaces.Trip.Callbacks.Callback_UpdateTrip;
import com.example.hike_with_me_client.Models.Trip.Actions.UploadImages;
import com.example.hike_with_me_client.Utils.Singleton.ErrorMessageFromServer;
import com.example.hike_with_me_client.Utils.Singleton.ListOfTrips;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;

@SuppressLint("SetTextI18n")
public class TripMethods {

    public static void getTripsByUser() {
        Callback_GetTripsByUser callback_getTripsByUser = new Callback_GetTripsByUser() {
            @Override
            public void success(List<trip> trips) {
                if(trips.isEmpty()) {
                    Log.d("trip", "No trips found");
                } else {
                    Log.d("trip", "Trips: " + trips);
                }
                ListOfTrips.getInstance().setTrips((ArrayList<trip>) trips);
            }

            @Override
            public void error(String message) {
                Log.d("trip", "Error: " + message);
                ErrorMessageFromServer.getInstance().setErrorMessageFromServer(message);
            }
        };
        new GetTripsByUser(callback_getTripsByUser).getTripsByUser();
    }

    public static void createTrip(trip trip) {
        Callback_CreateTrip callback_createTrip = new Callback_CreateTrip() {
            @Override
            public void success(com.example.hike_with_me_client.Models.Trip.trip trip) {
                Log.d("trip", "trip created: " + trip);
            }

            @Override
            public void error(String message) {
                Log.d("trip", "Error: " + message);
            }
        };
        Log.d("trip is valid", "Trip2" + trip);
        new CreateTrip(callback_createTrip).createTrip(trip);
    }

    public static void updateTrip(trip trip) {
        Callback_UpdateTrip callback_updateTrip = new Callback_UpdateTrip() {
            @Override
            public void success(com.example.hike_with_me_client.Models.Trip.trip trip) {
                Log.d("trip", "trip updated: " + trip);
            }

            @Override
            public void error(String message) {
                Log.d("trip", "Error: " + message);
            }
        };
        new UpdateTrip(callback_updateTrip).updateTrip(trip);
    }

    public static void deleteTrip(String userId, String tripId) {
        Callback_DeleteTrip callback_deleteTrip = new Callback_DeleteTrip() {
            @Override
            public void success(String message) {
                Log.d("trip", "Message: " + message);
            }

            @Override
            public void error(String message) {
                Log.d("trip", "Error: " + message);
            }
        };
        new DeleteTrip(callback_deleteTrip).deleteTrip(userId, tripId);
    }

    public static void uploadImages(List<MultipartBody.Part> images, String userName, String tripName) {
        // TODO - upload images
        Callback_UploadImages callback_uploadImages = new Callback_UploadImages() {
            @Override
            public void success(String message) {
                Log.d("trip", "Message: " + message);
            }

            @Override
            public void error(String error) {
                Log.d("trip", "Error: " + error);
            }
        };
        new UploadImages(callback_uploadImages).uploadImages(images, userName, tripName);
    }

    public static void deleteImage(String userName, String tripName, String imageName) {
        // TODO - delete image
        Callback_DeleteImage callback_deleteImage = new Callback_DeleteImage() {
            @Override
            public void success(String message) {
                Log.d("trip", "Message: " + message);
            }

            @Override
            public void error(String error) {
                Log.d("trip", "Error: " + error);
            }
        };
        new DeleteImage(callback_deleteImage).deleteImage(userName, tripName, imageName);
    }
}
