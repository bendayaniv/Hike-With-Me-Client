package com.example.hike_with_me_client.Route.Actions;

import com.example.hike_with_me_client.Route.Callbacks.Callback_GetAllRoutes;
import com.example.hike_with_me_client.Route.Route;
import com.example.hike_with_me_client.Route.RouteMasterClass;

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
            public void onResponse(Call<List<Route>> call, Response<List<Route>> response) {
                if(response.isSuccessful()) {
                    List<Route> routes = response.body();
                    callback_getAllRoutes.success(routes);
                } else {
                    callback_getAllRoutes.error("" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Route>> call, Throwable t) {
                callback_getAllRoutes.error(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
