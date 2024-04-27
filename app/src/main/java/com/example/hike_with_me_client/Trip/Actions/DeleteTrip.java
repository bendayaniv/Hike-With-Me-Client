package com.example.hike_with_me_client.Trip.Actions;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Trip.Callbacks.Callback_DeleteTrip;
import com.example.hike_with_me_client.Trip.Trip;
import com.example.hike_with_me_client.Trip.TripMasterClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteTrip extends TripMasterClass {

    private final Callback_DeleteTrip callback_deleteTrip;

    public DeleteTrip(Callback_DeleteTrip callback_deleteTrip) {
        this.callback_deleteTrip = callback_deleteTrip;
    }

    public void deleteTrip(String userId, String tripId) {
        Call<String> call = tripApiInterface.deleteTrip(userId, tripId);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.isSuccessful()) {
                    String message = String.valueOf(response.body());
                    callback_deleteTrip.success(message);
                } else {
                    callback_deleteTrip.error("" + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                callback_deleteTrip.error(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
