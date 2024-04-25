package com.example.hike_with_me_client.Trip.Actions;

import com.example.hike_with_me_client.Trip.Callbacks.Callback_CreateTrip;
import com.example.hike_with_me_client.Trip.Trip;
import com.example.hike_with_me_client.Trip.TripMasterClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTrip extends TripMasterClass {
    private final Callback_CreateTrip callback_createTrip;

    public CreateTrip(Callback_CreateTrip callbackCreateTrip) {
        callback_createTrip = callbackCreateTrip;
    }

    public void createTrip(Trip trip) {
        Call<Trip> call = tripApiInterface.createTrip(trip);

        call.enqueue(new Callback<Trip>() {
            @Override
            public void onResponse(Call<Trip> call, Response<Trip> response) {
                if(response.isSuccessful()) {
                    Trip trip = response.body();
                    callback_createTrip.success(trip);
                } else {
                    callback_createTrip.error("" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Trip> call, Throwable t) {
                callback_createTrip.error(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
