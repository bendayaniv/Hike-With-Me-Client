package com.example.hike_with_me_client.Models.Trip.Actions;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Models.Trip.Trip;
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

    public void updateTrip(Trip trip) {
        Call<Trip> call = tripApiInterface.updateTrip(trip);

        call.enqueue(new Callback<Trip>() {
            @Override
            public void onResponse(@NonNull Call<Trip> call, @NonNull Response<Trip> response) {
                if(response.isSuccessful()) {
                    Trip trip = response.body();
                    callback_updateTrip.success(trip);
                } else {
                    callback_updateTrip.error(String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Trip> call, @NonNull Throwable t) {
                callback_updateTrip.error(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
