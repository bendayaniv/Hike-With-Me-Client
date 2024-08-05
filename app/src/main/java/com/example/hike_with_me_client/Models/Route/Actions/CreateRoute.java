package com.example.hike_with_me_client.Models.Route.Actions;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Interfaces.Route.Callbacks.Callback_CreateRoute;
import com.example.hike_with_me_client.Models.Route.Route;
import com.example.hike_with_me_client.Models.Route.RouteMasterClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRoute extends RouteMasterClass {
    private final Callback_CreateRoute callback_createRoute;

    public CreateRoute(Callback_CreateRoute callbackCreateRoute) {
        callback_createRoute = callbackCreateRoute;
    }

    public void createRoute(Route route) {
        Log.d("route is valid", "Route3" + route);
        Call<Route> call = routeApiInterface.createRoute(route);

        call.enqueue(new Callback<Route>() {
            @Override
            public void onResponse(@NonNull Call<Route> call, @NonNull Response<Route> response) {
                if (response.isSuccessful()) {
                    Route createdRoute = response.body();
                    callback_createRoute.success(createdRoute);
                } else {
                    callback_createRoute.error(String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Route> call, @NonNull Throwable t) {
                callback_createRoute.error(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
