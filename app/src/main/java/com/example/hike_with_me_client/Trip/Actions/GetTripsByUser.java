package com.example.hike_with_me_client.Trip.Actions;

import com.example.hike_with_me_client.Trip.Callbacks.Callback_GetTripsByUser;
import com.example.hike_with_me_client.Trip.Trip;
import com.example.hike_with_me_client.Trip.TripMasterClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetTripsByUser extends TripMasterClass {
    private final Callback_GetTripsByUser callback_getTripsByUser;

    public GetTripsByUser(Callback_GetTripsByUser callbackGetTripsByUser) {
        callback_getTripsByUser = callbackGetTripsByUser;
    }

    public void getTripsByUser(String userId) {
         Call<List<Trip>> call = tripApiInterface.getTrips(userId);
         call.enqueue(new Callback<List<Trip>>() {
             @Override
             public void onResponse(Call<List<Trip>> call, Response<List<Trip>> response) {
                 if(response.isSuccessful()) {
                     List<Trip> trips = response.body();
                     callback_getTripsByUser.success(trips);
                 } else {
                     callback_getTripsByUser.error("" + response.errorBody());
                 }
             }

             @Override
             public void onFailure(Call<List<Trip>> call, Throwable t) {
                 callback_getTripsByUser.error(t.getMessage());
                 t.printStackTrace();
             }
         });
    }
}
