package com.example.hike_with_me_client.Models.Trip;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.loader.content.CursorLoader;

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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@SuppressLint("SetTextI18n")
public class TripMethods {

    public static void getTripsByUser() {
        Callback_GetTripsByUser callback_getTripsByUser = new Callback_GetTripsByUser() {
            @Override
            public void success(List<trip> trips) {
                if (trips.isEmpty()) {
                    Log.d("Trip", "No trips found");
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

    public static void uploadImages(List<Uri> imageUris, String userName, String tripName, Context context, ProgressBar progressBar) {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        List<MultipartBody.Part> imageParts = new ArrayList<>();

        for (Uri uri : imageUris) {
            if (uri != null) {
                try {
                    InputStream inputStream = context.getContentResolver().openInputStream(uri);
                    if (inputStream != null) {
                        byte[] byteArray = TripMethodsUtils.getBytes(inputStream);
                        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), byteArray);
                        String fileName = TripMethodsUtils.getFileName(context, uri);
                        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("images", fileName, requestFile);
                        imageParts.add(imagePart);
                    }
                } catch (Exception e) {
                    Log.e("TripMethods", "Error processing URI: " + uri, e);
                }
            }
        }

        RequestBody userNamePart = RequestBody.create(MediaType.parse("text/plain"), userName);
        RequestBody tripNamePart = RequestBody.create(MediaType.parse("text/plain"), tripName);

        Callback_UploadImages callback_uploadImages = new Callback_UploadImages() {
            @Override
            public void success(String message) {
                progressBar.setVisibility(ProgressBar.GONE);
                Log.d("TripMethods", "Upload success: " + message);
                Toast.makeText(context, "Images uploaded successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void error(String error) {
                progressBar.setVisibility(ProgressBar.GONE);
                Log.e("TripMethods", "Upload error: " + error);
            }
        };
        new UploadImages(callback_uploadImages).uploadImages(imageParts, userNamePart, tripNamePart);
    }

//    private static byte[] getBytes(InputStream inputStream) throws IOException {
//        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
//        int bufferSize = 1024;
//        byte[] buffer = new byte[bufferSize];
//        int len;
//        while ((len = inputStream.read(buffer)) != -1) {
//            byteBuffer.write(buffer, 0, len);
//        }
//        return byteBuffer.toByteArray();
//    }
//
//    @SuppressLint("Range")
//    private static String getFileName(Context context, Uri uri) {
//        String result = null;
//        if (uri.getScheme().equals("content")) {
//            try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
//                if (cursor != null && cursor.moveToFirst()) {
//                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
//                }
//            }
//        }
//        if (result == null) {
//            result = uri.getPath();
//            int cut = result.lastIndexOf('/');
//            if (cut != -1) {
//                result = result.substring(cut + 1);
//            }
//        }
//        return result;
//    }

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
