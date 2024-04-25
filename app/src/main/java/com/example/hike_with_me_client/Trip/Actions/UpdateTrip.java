package com.example.hike_with_me_client.Trip.Actions;

import com.example.hike_with_me_client.Route.Route;
import com.example.hike_with_me_client.Trip.Callbacks.Callback_UpdateTrip;
import com.example.hike_with_me_client.Trip.Trip;
import com.example.hike_with_me_client.Trip.TripMasterClass;
import com.example.hike_with_me_client.User.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateTrip extends TripMasterClass {
    private final Callback_UpdateTrip callback_updateTrip;

    public UpdateTrip(Callback_UpdateTrip callback_updateTrip) {
        this.callback_updateTrip = callback_updateTrip;
    }

    public void updateTrip(String userId, String tripId, String name, String startDate, String endDate, String location, String description, Route route, User user) {
        Trip trip = new Trip(tripId, name, startDate, endDate, location, description, route, user);
        Call<Trip> call = tripApiInterface.updateTrip(userId, tripId, trip);

        call.enqueue(new Callback<Trip>() {
            @Override
            public void onResponse(Call<Trip> call, Response<Trip> response) {
                if(response.isSuccessful()) {
                    Trip trip = response.body();
                    callback_updateTrip.success(trip);
                } else {
                    callback_updateTrip.error("" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Trip> call, Throwable t) {
                callback_updateTrip.error(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
