package com.example.hike_with_me_client.Models.Route.Actions;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Models.Route.Route;
import com.example.hike_with_me_client.Models.Route.RouteMasterClass;
import com.example.hike_with_me_client.Interfaces.Route.Callbacks.Callback_GetAllRoutes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllRoutes extends RouteMasterClass {
    private final Callback_GetAllRoutes callback_getAllRoutes;

    public GetAllRoutes(Callback_GetAllRoutes callback_getAllRoutes) {
        this.callback_getAllRoutes = callback_getAllRoutes;
    }

    public void getAllRoutes() {
        Call<List<Route>> call = routeApiInterface.getAllRoutes();

        call.enqueue(new Callback<List<Route>>() {
            @Override
            public void onResponse(@NonNull Call<List<Route>> call, @NonNull Response<List<Route>> response) {
                if(response.isSuccessful()) {
                    List<Route> routes = response.body();
                    Log.d("tag", routes.toString());
                    callback_getAllRoutes.success(routes);
                } else {
                    callback_getAllRoutes.error(String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Route>> call, @NonNull Throwable t) {
                callback_getAllRoutes.error(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
