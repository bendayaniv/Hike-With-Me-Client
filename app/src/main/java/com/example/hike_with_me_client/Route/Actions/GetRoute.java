package com.example.hike_with_me_client.Route.Actions;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Route.Callbacks.Callback_GetRoute;
import com.example.hike_with_me_client.Route.Route;
import com.example.hike_with_me_client.Route.RouteMasterClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetRoute extends RouteMasterClass {
    private final Callback_GetRoute callback_getRoute;

    public GetRoute(Callback_GetRoute callback_getRoute) {
        this.callback_getRoute = callback_getRoute;
    }

    public void getRoute(String routeName) {
         Call<Route> call = routeApiInterface.getRoute(routeName);

         call.enqueue(new Callback<Route>() {
             @Override
             public void onResponse(@NonNull Call<Route> call, @NonNull Response<Route> response) {
                 if(response.isSuccessful()) {
                     Route route = response.body();
                     callback_getRoute.success(route);
                 } else {
                     callback_getRoute.error(String.valueOf(response.errorBody()));
                 }
             }

             @Override
             public void onFailure(@NonNull Call<Route> call, @NonNull Throwable t) {
                 callback_getRoute.error(t.getMessage());
                 t.printStackTrace();
             }
         });
    }
}
