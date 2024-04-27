package com.example.hike_with_me_client.Route.Actions;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Route.Callbacks.Callback_AddRoute;
import com.example.hike_with_me_client.Route.Route;
import com.example.hike_with_me_client.Route.RouteMasterClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRoute extends RouteMasterClass {
    private final Callback_AddRoute callback_addRoute;

    public AddRoute(Callback_AddRoute callback_addRoute) {
        this.callback_addRoute = callback_addRoute;
    }

    public void addRoute(Route route) {
        Call<Route> call = routeApiInterface.addRoute(route);

        call.enqueue(new Callback<Route>() {
            @Override
            public void onResponse(@NonNull Call<Route> call, @NonNull Response<Route> response) {
                if(response.isSuccessful()) {
                    Route route = response.body();
                    callback_addRoute.success(route);
                } else {
                    callback_addRoute.error(String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Route> call, @NonNull Throwable t) {
                callback_addRoute.error(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
