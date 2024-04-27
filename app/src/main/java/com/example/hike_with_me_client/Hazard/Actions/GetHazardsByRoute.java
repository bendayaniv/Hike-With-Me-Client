package com.example.hike_with_me_client.Hazard.Actions;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Hazard.Callbacks.Callback_GetHazardsByRoute;
import com.example.hike_with_me_client.Hazard.Hazard;
import com.example.hike_with_me_client.Hazard.HazardMasterClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetHazardsByRoute extends HazardMasterClass {
    private final Callback_GetHazardsByRoute callback_getHazardsByRoute;

    public GetHazardsByRoute(Callback_GetHazardsByRoute callbackGetHazardsByRoute) {
        callback_getHazardsByRoute = callbackGetHazardsByRoute;
    }

    public void getHazardsByRoute(String routeId) {
        Call<List<Hazard>> call = hazardApiInterface.getHazardsByRoute(routeId);

        call.enqueue(new Callback<List<Hazard>>() {
            @Override
            public void onResponse(@NonNull Call<List<Hazard>> call, @NonNull Response<List<Hazard>> response) {
                if(response.isSuccessful()) {
                    List<Hazard> hazards = response.body();
                    callback_getHazardsByRoute.success(hazards);
                } else {
                    callback_getHazardsByRoute.error("" + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Hazard>> call, @NonNull Throwable t) {
                callback_getHazardsByRoute.error(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
