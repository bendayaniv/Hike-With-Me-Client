package com.example.hike_with_me_client.Models.Trip.Actions;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Interfaces.Trip.Callbacks.Callback_GetTripsByUser;
import com.example.hike_with_me_client.Utils.GlobalUtilInstances.CurrentUser;
import com.example.hike_with_me_client.Models.Trip.trip;
import com.example.hike_with_me_client.Models.Trip.TripMasterClass;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetTripsByUser extends TripMasterClass {
    private final Callback_GetTripsByUser callback_getTripsByUser;

    public GetTripsByUser(Callback_GetTripsByUser callbackGetTripsByUser) {
        callback_getTripsByUser = callbackGetTripsByUser;
    }

    public void getTripsByUser() {
         Call<List<trip>> call = tripApiInterface.getTrips(CurrentUser.getInstance().getUser().getId());
         call.enqueue(new Callback<List<trip>>() {
             @Override
             public void onResponse(@NonNull Call<List<trip>> call, @NonNull Response<List<trip>> response) {
                 if(response.isSuccessful()) {
                     List<trip> trips = response.body();
                     callback_getTripsByUser.success(trips);
                 } else {
                     ResponseBody errorBody = response.errorBody();
                     try {
                         assert errorBody != null;
                         String errorMessage = errorBody.string();
                         callback_getTripsByUser.error(errorMessage);
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 }
             }

             @Override
             public void onFailure(@NonNull Call<List<trip>> call, @NonNull Throwable t) {
                 callback_getTripsByUser.error(t.getMessage());
                 t.printStackTrace();
             }
         });
    }
}
