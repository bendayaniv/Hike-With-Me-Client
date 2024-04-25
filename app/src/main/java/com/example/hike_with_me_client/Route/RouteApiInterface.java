package com.example.hike_with_me_client.Route;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RouteApiInterface {

    @GET("routes")
    Call<List<Route>> getAllRoutes();

    @GET("routes/{route_name}")
    Call<Route> getRoute(
            @Path("route_name") String route_name
    );

    @POST("routes/addRoute")
    Call<Route> addRoute(
            @Body Route route
    );
}
