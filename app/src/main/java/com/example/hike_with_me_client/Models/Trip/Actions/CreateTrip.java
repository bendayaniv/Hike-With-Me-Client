package com.example.hike_with_me_client.Models.Trip.Actions;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Interfaces.Trip.Callbacks.Callback_CreateTrip;
import com.example.hike_with_me_client.Models.Trip.trip;
import com.example.hike_with_me_client.Models.Trip.TripMasterClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTrip extends TripMasterClass {
    private final Callback_CreateTrip callback_createTrip;

    public CreateTrip(Callback_CreateTrip callbackCreateTrip) {
        callback_createTrip = callbackCreateTrip;
    }

    public void createTrip(trip trip) {
        Log.d("trip is valid", "Trip3" + trip);
        Call<com.example.hike_with_me_client.Models.Trip.trip> call = tripApiInterface.createTrip(trip);

        call.enqueue(new Callback<com.example.hike_with_me_client.Models.Trip.trip>() {
            @Override
            public void onResponse(@NonNull Call<com.example.hike_with_me_client.Models.Trip.trip> call, @NonNull Response<com.example.hike_with_me_client.Models.Trip.trip> response) {
                if(response.isSuccessful()) {
                    com.example.hike_with_me_client.Models.Trip.trip trip = response.body();
                    callback_createTrip.success(trip);
                } else {
                    callback_createTrip.error(String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<com.example.hike_with_me_client.Models.Trip.trip> call, @NonNull Throwable t) {
                callback_createTrip.error(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
