package com.example.hike_with_me_client.Models.Trip.Actions;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Interfaces.Trip.Callbacks.Callback_DeleteImage;
import com.example.hike_with_me_client.Models.Trip.TripMasterClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;

public class DeleteImage extends TripMasterClass {

    private final Callback_DeleteImage callback_deleteImage;

    public DeleteImage(Callback_DeleteImage callback_deleteImage) {
        this.callback_deleteImage = callback_deleteImage;
    }

    public void deleteImage(String userName, String tripName, String imageName) {
        Call<String> call = tripApiInterface.deleteImage(userName, tripName, imageName);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.isSuccessful()) {
                    String message = String.valueOf(response.body());
                    callback_deleteImage.success(message);
                } else {
                    callback_deleteImage.error(String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                callback_deleteImage.error(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
