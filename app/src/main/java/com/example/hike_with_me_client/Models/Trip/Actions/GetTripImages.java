package com.example.hike_with_me_client.Models.Trip.Actions;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Interfaces.Trip.Callbacks.Callback_GetTripImages;
import com.example.hike_with_me_client.Models.Trip.TripMasterClass;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetTripImages extends TripMasterClass {

    private final Callback_GetTripImages callback_getTripImages;

    public GetTripImages(Callback_GetTripImages callback_getTripImages) {
        this.callback_getTripImages = callback_getTripImages;
    }

    public void getTripImages(String userName, String tripName) {
         Call<ArrayList<File>> call = tripApiInterface.getTripImages(userName, tripName);

         call.enqueue(new Callback<ArrayList<File>>() {
             @Override
             public void onResponse(@NonNull Call<ArrayList<File>> call, @NonNull Response<ArrayList<File>> response) {
                 if(response.isSuccessful()) {
                     ArrayList<File> images = response.body();
                     callback_getTripImages.success(images);
                 } else {
                     ResponseBody errorBody = response.errorBody();
                     try {
                         assert errorBody != null;
                         String errorMessage = errorBody.string();
                         callback_getTripImages.error(errorMessage);
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 }
             }

             @Override
             public void onFailure(@NonNull Call<ArrayList<File>> call, @NonNull Throwable t) {
                 callback_getTripImages.error(t.getMessage());
                 t.printStackTrace();
             }
         });
    }
}
