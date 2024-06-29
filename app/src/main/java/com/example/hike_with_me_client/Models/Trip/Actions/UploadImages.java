package com.example.hike_with_me_client.Models.Trip.Actions;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Interfaces.Trip.Callbacks.Callback_UploadImages;
import com.example.hike_with_me_client.Models.Trip.TripMasterClass;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadImages extends TripMasterClass {

    private final Callback_UploadImages callback_uploadImages;

    public UploadImages(Callback_UploadImages callback_uploadImages) {
        this.callback_uploadImages = callback_uploadImages;
    }

    public void uploadImages(List<MultipartBody.Part> images, String userName, String tripName) {
        Call<String> call = tripApiInterface.uploadImages(images, userName, tripName);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.isSuccessful()) {
                    String message = String.valueOf(response.body());
                    callback_uploadImages.success(message);
                } else {
                    callback_uploadImages.error(String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                callback_uploadImages.error(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
