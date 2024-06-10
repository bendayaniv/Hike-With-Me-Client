package com.example.hike_with_me_client.Models.Hazard.Actions;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Interfaces.Hazard.Callbacks.Callback_GetAllHazards;
import com.example.hike_with_me_client.Models.Hazard.Hazard;
import com.example.hike_with_me_client.Models.Hazard.HazardMasterClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllHazards extends HazardMasterClass {
    private final Callback_GetAllHazards callback_getAllHazards;

    public GetAllHazards(Callback_GetAllHazards callback_getAllHazards) {
        this.callback_getAllHazards = callback_getAllHazards;
    }

    public void getAllHazards() {
        Call<List<Hazard>> call = hazardApiInterface.getAllHazards();

        call.enqueue(new Callback<List<Hazard>>() {
            @Override
            public void onResponse(@NonNull Call<List<Hazard>> call, @NonNull Response<List<Hazard>> response) {
                if(response.isSuccessful()) {
                    List<Hazard> hazards = response.body();
                    callback_getAllHazards.success(hazards);
                } else {
                    callback_getAllHazards.error(String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Hazard>> call, @NonNull Throwable t) {
                callback_getAllHazards.error(t.getMessage());
                t.printStackTrace();
            }
        });
    };
}
