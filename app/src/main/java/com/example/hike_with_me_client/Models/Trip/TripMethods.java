package com.example.hike_with_me_client.Models.Trip;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;

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
            public void success(List<Trip> trips) {
                if (trips.isEmpty()) {
                    Log.d("Trip", "No trips found");
                } else {
                    Log.d("Trip", "Trips: " + trips);
                }
                ListOfTrips.getInstance().setTrips((ArrayList<Trip>) trips);
            }

            @Override
            public void error(String message) {
                Log.d("Trip", "Error: " + message);
                ErrorMessageFromServer.getInstance().setErrorMessageFromServer(message);
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

//    public static void uploadImages(List<Uri> /*images*/imageUris, String userName, String tripName, Context context) {
//        // TODO - upload images
//
//        List<MultipartBody.Part> imageParts = new ArrayList<>();
//
//        for (Uri uri : imageUris) {
//            File file = new File(getRealPathFromURI(context, uri));
//            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
//            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("images", file.getName(), requestFile);
//            imageParts.add(imagePart);
//        }
//
//        RequestBody userNamePart = RequestBody.create(MediaType.parse("text/plain"), userName);
//        RequestBody tripNamePart = RequestBody.create(MediaType.parse("text/plain"), tripName);
//
//
//        Callback_UploadImages callback_uploadImages = new Callback_UploadImages() {
//            @Override
//            public void success(String message) {
//                Log.d("Trip", "Message: " + message);
//            }
//
//            @Override
//            public void error(String error) {
//                Log.d("Trip", "Error: " + error);
//            }
//        };
//        new UploadImages(callback_uploadImages).uploadImages(/*images*/imageUris, /*userName*/userNamePart, /*tripName*/tripNamePart);
//    }

//    public static void uploadImages(List<Uri> imageUris, String userName, String tripName, Context context) {
//        List<MultipartBody.Part> imageParts = new ArrayList<>();
//
//        for (Uri uri : imageUris) {
//            File file = new File(getRealPathFromURI(context, uri));
//            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
//            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("images", file.getName(), requestFile);
//            imageParts.add(imagePart);
//        }
//
//        RequestBody userNamePart = RequestBody.create(MediaType.parse("text/plain"), userName);
//        RequestBody tripNamePart = RequestBody.create(MediaType.parse("text/plain"), tripName);
//
//        Callback_UploadImages callback_uploadImages = new Callback_UploadImages() {
//            @Override
//            public void success(String message) {
//                Log.d("Trip", "Message: " + message);
//            }
//
//            @Override
//            public void error(String error) {
//                Log.d("Trip", "Error: " + error);
//            }
//        };
//        new UploadImages(callback_uploadImages).uploadImages(imageParts, userNamePart, tripNamePart);
//    }
//
//    private static String getRealPathFromURI(Context context, Uri contentUri) {
//        String[] proj = {MediaStore.Images.Media.DATA};
//        CursorLoader loader = new CursorLoader(context);
//        Cursor cursor = loader.loadInBackground();
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        String result = cursor.getString(column_index);
//        cursor.close();
//        return result;
//    }

//    public static void uploadImages(List<Uri> imageUris, String userName, String tripName, Context context) {
//        List<MultipartBody.Part> imageParts = new ArrayList<>();
//
//        for (Uri uri : imageUris) {
//            if (uri != null) {
//                try {
//                    Log.d("TripMethods", "Processing URI: " + uri);
//                    File file = new File(getRealPathFromURI(context, uri));
//                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
//                    MultipartBody.Part imagePart = MultipartBody.Part.createFormData("images", file.getName(), requestFile);
//                    imageParts.add(imagePart);
//                } catch (Exception e) {
//                    Log.e("TripMethods", "Error processing URI: " + uri, e);
//                }
//            }
//        }
//
//        if (imageParts.isEmpty()) {
//            Log.e("TripMethods", "No valid images to upload");
//            return;
//        }
//
//        Log.d("TripMethods", "Images to upload: " + imageParts.size() + " images");
//
//        RequestBody userNamePart = RequestBody.create(MediaType.parse("text/plain"), userName);
//        RequestBody tripNamePart = RequestBody.create(MediaType.parse("text/plain"), tripName);
//
//        Callback_UploadImages callback_uploadImages = new Callback_UploadImages() {
//            @Override
//            public void success(String message) {
//                Log.d("Trip", "Message: " + message);
//            }
//
//            @Override
//            public void error(String error) {
//                Log.d("Trip", "Error: " + error);
//            }
//        };
//        new UploadImages(callback_uploadImages).uploadImages(imageParts, userNamePart, tripNamePart);
//    }
//
//    private static String getRealPathFromURI(Context context, Uri uri) {
//        String result = null;
//        String[] projection = {MediaStore.Images.Media.DATA};
//
//        try {
//            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
//            if (cursor != null) {
//                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                cursor.moveToFirst();
//                result = cursor.getString(column_index);
//                cursor.close();
//            }
//        } catch (Exception e) {
//            Log.e("TripMethods", "Error getting real path from URI", e);
//        }
//
//        if (result == null) {
//            result = uri.getPath(); // fallback to the URI path if we couldn't get the real path
//        }
//
//        return result;
//    }

    public static void uploadImages(List<Uri> imageUris, String userName, String tripName, Context context) {
        List<MultipartBody.Part> imageParts = new ArrayList<>();

        for (Uri uri : imageUris) {
            if (uri != null) {
                try {
                    InputStream inputStream = context.getContentResolver().openInputStream(uri);
                    if (inputStream != null) {
                        byte[] byteArray = getBytes(inputStream);
                        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), byteArray);
                        String fileName = getFileName(context, uri);
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

        new UploadImages(new Callback_UploadImages() {
            @Override
            public void success(String message) {
                Log.d("TripMethods", "Upload success: " + message);
            }

            @Override
            public void error(String error) {
                Log.e("TripMethods", "Upload error: " + error);
            }
        }).uploadImages(imageParts, userNamePart, tripNamePart);
    }

//    public static void uploadImages(List<Uri> imageUris, String userName, String tripName, Context context) {
//        List<MultipartBody.Part> imageParts = new ArrayList<>();
//
//        for (Uri uri : imageUris) {
//            if (uri != null) {
//                try {
//                    InputStream inputStream = context.getContentResolver().openInputStream(uri);
//                    if (inputStream != null) {
//                        byte[] byteArray = getBytes(inputStream);
//                        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), byteArray);
//                        String fileName = getFileName(context, uri);
//                        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("images", fileName, requestFile);
//                        imageParts.add(imagePart);
//                    }
//                } catch (Exception e) {
//                    Log.e("TripMethods", "Error processing URI: " + uri, e);
//                }
//            }
//        }
//
//        if (imageParts.isEmpty()) {
//            Log.e("TripMethods", "No valid images to upload");
//            return;
//        }
//
//        RequestBody userNamePart = RequestBody.create(MediaType.parse("text/plain"), userName);
//        RequestBody tripNamePart = RequestBody.create(MediaType.parse("text/plain"), tripName);
//
//        Callback_UploadImages callback_uploadImages = new Callback_UploadImages() {
//            @Override
//            public void success(String message) {
//                Log.d("Trip", "Upload success: " + message);
//            }
//
//            @Override
//            public void error(String error) {
//                Log.d("Trip", "Upload error: " + error);
//            }
//        };
//        new UploadImages(callback_uploadImages).uploadImages(imageParts, userNamePart, tripNamePart);
//    }

    private static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    @SuppressLint("Range")
    private static String getFileName(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
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
