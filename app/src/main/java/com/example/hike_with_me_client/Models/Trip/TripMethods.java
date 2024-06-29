package com.example.hike_with_me_client.Models.Trip;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.hike_with_me_client.Interfaces.Trip.Callbacks.Callback_CreateTrip;
import com.example.hike_with_me_client.Interfaces.Trip.Callbacks.Callback_DeleteImage;
import com.example.hike_with_me_client.Interfaces.Trip.Callbacks.Callback_DeleteTrip;
import com.example.hike_with_me_client.Interfaces.Trip.Callbacks.Callback_GetTripImages;
import com.example.hike_with_me_client.Interfaces.Trip.Callbacks.Callback_GetTripsByUser;
import com.example.hike_with_me_client.Interfaces.Trip.Callbacks.Callback_UploadImages;
import com.example.hike_with_me_client.Models.Objects.CurrentUser;
import com.example.hike_with_me_client.Models.Trip.Actions.CreateTrip;
import com.example.hike_with_me_client.Models.Trip.Actions.DeleteImage;
import com.example.hike_with_me_client.Models.Trip.Actions.DeleteTrip;
import com.example.hike_with_me_client.Models.Trip.Actions.GetTripImages;
import com.example.hike_with_me_client.Models.Trip.Actions.GetTripsByUser;
import com.example.hike_with_me_client.Models.Trip.Actions.UpdateTrip;
import com.example.hike_with_me_client.Interfaces.Trip.Callbacks.Callback_UpdateTrip;
import com.example.hike_with_me_client.Models.Trip.Actions.UploadImages;
import com.example.hike_with_me_client.Utils.File;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;

@SuppressLint("SetTextI18n")
public class TripMethods {

    public static void getTripsByUser() {
        Callback_GetTripsByUser callback_getTripsByUser = new Callback_GetTripsByUser() {
            @Override
            public void success(List<Trip> trips) {
                if(trips.isEmpty()) {
                    Log.d("Trip", "No trips found");
                } else {
                    Log.d("Trip", "Trips: " + trips);
                }
                CurrentUser.getInstance().setTrips((ArrayList<Trip>) trips);
            }

            @Override
            public void error(String message) {
                Log.d("Trip", "Error: " + message);
                CurrentUser.getInstance().setErrorMessageFromServer(message);
            }
        };
        new GetTripsByUser(callback_getTripsByUser).getTripsByUser();
    }

    public static void createTrip(Trip trip) {
        Callback_CreateTrip callback_createTrip = new Callback_CreateTrip() {
            @Override
            public void success(Trip trip) {
                Log.d("Trip", "Trip created: " + trip);
            }

            @Override
            public void error(String message) {
                Log.d("Trip", "Error: " + message);
            }
        };
        new CreateTrip(callback_createTrip).createTrip(trip);
    }

    public static void updateTrip(Trip trip) {
        Callback_UpdateTrip callback_updateTrip = new Callback_UpdateTrip() {
            @Override
            public void success(Trip trip) {
                Log.d("Trip", "Trip updated: " + trip);
            }

            @Override
            public void error(String message) {
                Log.d("Trip", "Error: " + message);
            }
        };
        new UpdateTrip(callback_updateTrip).updateTrip(trip);
    }

    public static void deleteTrip(String userId, String tripId) {
        Callback_DeleteTrip callback_deleteTrip = new Callback_DeleteTrip() {
            @Override
            public void success(String message) {
                Log.d("Trip", "Message: " + message);
            }

            @Override
            public void error(String message) {
                Log.d("Trip", "Error: " + message);
            }
        };
        new DeleteTrip(callback_deleteTrip).deleteTrip(userId, tripId);
    }

    public static void uploadImages(List<MultipartBody.Part> images, String userName, String tripName) {
        // TODO - upload images
        Callback_UploadImages callback_uploadImages = new Callback_UploadImages() {
            @Override
            public void success(String message) {
                Log.d("Trip", "Message: " + message);
            }

            @Override
            public void error(String error) {
                Log.d("Trip", "Error: " + error);
            }
        };
        new UploadImages(callback_uploadImages).uploadImages(images, userName, tripName);
    }

    public static void getTripImages(String userName, String tripName) {
        // TODO - get images
        Callback_GetTripImages callback_getTripImages = new Callback_GetTripImages() {
            @Override
            public void success(List<File> images) {
                if(images.isEmpty()) {
                    Log.d("Trip", "No images found");
                } else {
                    Log.d("Trip", "Images: " + images);
                }

                CurrentUser.getInstance().setUrlsImages(images);
            }

            @Override
            public void error(String error) {
                Log.d("Trip", "Error: " + error);
                CurrentUser.getInstance().setErrorMessageFromServer(error);
            }
        };
        new GetTripImages(callback_getTripImages).getTripImages(userName, tripName);
    }

    public static void deleteImage(String userName, String tripName, String imageName) {
        // TODO - delete image
        Callback_DeleteImage callback_deleteImage = new Callback_DeleteImage() {
            @Override
            public void success(String message) {
                Log.d("Trip", "Message: " + message);
            }

            @Override
            public void error(String error) {
                Log.d("Trip", "Error: " + error);
            }
        };
        new DeleteImage(callback_deleteImage).deleteImage(userName, tripName, imageName);
    }
}
