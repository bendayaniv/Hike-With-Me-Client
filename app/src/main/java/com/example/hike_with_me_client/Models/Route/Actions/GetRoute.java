package com.example.hike_with_me_client.Models.Route.Actions;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Models.Route.Route;
import com.example.hike_with_me_client.Models.Route.RouteMasterClass;
import com.example.hike_with_me_client.Interfaces.Route.Callbacks.Callback_GetRoutesNames;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetRoute extends RouteMasterClass {
    private final Callback_GetRoutesNames callback_getRoute;

    public GetRoute(Callback_GetRoutesNames callback_getRoute) {
        this.callback_getRoute = callback_getRoute;
    }

    public void getRoutesNames() {
        Call<List<String>> call = routeApiInterface.getRoutesNames();

        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> routesNames = response.body();
                    callback_getRoute.success(routesNames);
                } else {
                    callback_getRoute.error(String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
                callback_getRoute.error(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
