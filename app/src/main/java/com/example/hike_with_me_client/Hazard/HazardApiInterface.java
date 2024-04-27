package com.example.hike_with_me_client.Hazard;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HazardApiInterface {
    @GET("hazards/{route_name}")
    Call<List<Hazard>> getHazardsByRoute(
            @Path("route_name") String route
    );

    @POST("hazards/addHazard")
    Call<Hazard> addHazard(
            @Body Hazard hazard
    );
}
