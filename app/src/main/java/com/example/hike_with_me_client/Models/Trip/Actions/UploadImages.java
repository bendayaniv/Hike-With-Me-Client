package com.example.hike_with_me_client.Models.Trip.Actions;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Interfaces.Trip.Callbacks.Callback_UploadImages;
import com.example.hike_with_me_client.Models.Trip.TripMasterClass;

import java.io.IOException;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//public class UploadImages extends TripMasterClass {
//
//    private final Callback_UploadImages callback_uploadImages;
//
//    public UploadImages(Callback_UploadImages callback_uploadImages) {
//        this.callback_uploadImages = callback_uploadImages;
//    }
//
//    public void uploadImages(List<MultipartBody.Part> images, String userName, String tripName) {
//        Call<String> call = tripApiInterface.uploadImages(images, userName, tripName);
//
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
//                if(response.isSuccessful()) {
//                    String message = String.valueOf(response.body());
//                    callback_uploadImages.success(message);
//                } else {
//                    callback_uploadImages.error(String.valueOf(response.errorBody()));
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
//                callback_uploadImages.error(t.getMessage());
//                t.printStackTrace();
//            }
//        });
//    }
//}

//public class UploadImages extends TripMasterClass {
//
//    private final Callback_UploadImages callback_uploadImages;
//
//    public UploadImages(Callback_UploadImages callback_uploadImages) {
//        this.callback_uploadImages = callback_uploadImages;
//    }
//
//    public void uploadImages(List<Uri> images, RequestBody userName, RequestBody tripName) {
//        Call<String> call = tripApiInterface.uploadImages(images, userName, tripName);
//
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
//                if(response.isSuccessful()) {
//                    String message = response.body();
//                    callback_uploadImages.success(message);
//                } else {
//                    callback_uploadImages.error(response.errorBody().toString());
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
//                callback_uploadImages.error(t.getMessage());
//                t.printStackTrace();
//            }
//        });
//    }
//}


//public class UploadImages extends TripMasterClass {
//
//    private final Callback_UploadImages callback_uploadImages;
//
//    public UploadImages(Callback_UploadImages callback_uploadImages) {
//        this.callback_uploadImages = callback_uploadImages;
//    }
//
//    public void uploadImages(List<MultipartBody.Part> images, RequestBody userName, RequestBody tripName) {
//        Call<String> call = tripApiInterface.uploadImages(images, userName, tripName);
//
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
//                if(response.isSuccessful()) {
//                    String message = response.body();
//                    callback_uploadImages.success(message);
//                } else {
//                    callback_uploadImages.error(response.errorBody().toString());
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
//                callback_uploadImages.error(t.getMessage());
//                t.printStackTrace();
//            }
//        });
//    }
//}


public class UploadImages extends TripMasterClass {

    private final Callback_UploadImages callback_uploadImages;

    public UploadImages(Callback_UploadImages callback_uploadImages) {
        this.callback_uploadImages = callback_uploadImages;
    }

    public void uploadImages(List<MultipartBody.Part> images, RequestBody userName, RequestBody tripName) {
        Call<String> call = tripApiInterface.uploadImages(images, userName, tripName);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.isSuccessful()) {
                    String message = response.body();
                    if (message != null) {
                        callback_uploadImages.success(message);
                    } else {
                        callback_uploadImages.error("Response body is null");
                    }
                } else {
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
                        callback_uploadImages.error("Error: " + response.code() + " - " + errorBody);
                    } catch (IOException e) {
                        callback_uploadImages.error("Error reading error body: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                callback_uploadImages.error("Network error: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }
}