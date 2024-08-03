package com.example.hike_with_me_client.Models.Trip.Actions;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Models.Trip.trip;
import com.example.hike_with_me_client.Models.Trip.TripMasterClass;
import com.example.hike_with_me_client.Interfaces.Trip.Callbacks.Callback_UpdateTrip;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateTrip extends TripMasterClass {
    private final Callback_UpdateTrip callback_updateTrip;

    public UpdateTrip(Callback_UpdateTrip callback_updateTrip) {
        this.callback_updateTrip = callback_updateTrip;
    }

    public void updateTrip(trip trip) {
        Call<com.example.hike_with_me_client.Models.Trip.trip> call = tripApiInterface.updateTrip(trip);

        call.enqueue(new Callback<com.example.hike_with_me_client.Models.Trip.trip>() {
            @Override
            public void onResponse(@NonNull Call<com.example.hike_with_me_client.Models.Trip.trip> call, @NonNull Response<com.example.hike_with_me_client.Models.Trip.trip> response) {
                if(response.isSuccessful()) {
                    com.example.hike_with_me_client.Models.Trip.trip trip = response.body();
                    callback_updateTrip.success(trip);
                } else {
                    callback_updateTrip.error(String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<com.example.hike_with_me_client.Models.Trip.trip> call, @NonNull Throwable t) {
                callback_updateTrip.error(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
