package com.example.hike_with_me_client.Models.Hazard.Actions;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Interfaces.Hazard.Callbacks.Callback_GetNearHazards;
import com.example.hike_with_me_client.Models.Hazard.Hazard;
import com.example.hike_with_me_client.Models.Hazard.HazardMasterClass;
import com.example.hike_with_me_client.Utils.GlobalUtilInstances.CurrentUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetNearHazards extends HazardMasterClass {
    private final Callback_GetNearHazards callback_getNearHazards;

    public GetNearHazards(Callback_GetNearHazards callback_getNearHazards) {
        this.callback_getNearHazards = callback_getNearHazards;
    }

    public void getNearHazards() {
        Call<List<Hazard>> call = hazardApiInterface.getNearHazards(CurrentUser.getInstance().getUser().getId());

        call.enqueue(new Callback<List<Hazard>>() {
            @Override
            public void onResponse(@NonNull Call<List<Hazard>> call, @NonNull Response<List<Hazard>> response) {
                Log.d("Hazard", "response.body: " + response.body());
                if(response.isSuccessful()) {
                    List<Hazard> hazards = response.body();
                    callback_getNearHazards.success(hazards);
                } else {
                    callback_getNearHazards.error(String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Hazard>> call, @NonNull Throwable t) {
                callback_getNearHazards.error(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
