package com.example.hike_with_me_client.Trip;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TripApiInterface {

    @GET("trips/{user_id}")
    Call<List<Trip>> getTrips(
            @Path("user_id") String user_id
    );

    @POST("trips/createTrip")
    Call<Trip> createTrip(
            @Body Trip trip
    );

    @PUT("trips")
    Call<Trip> updateTrip(
            @Body Trip trip
    );

    @DELETE("trips/{user_id}/{trip_id}")
    Call<String> deleteTrip(
            @Path("user_id") String user_id,
            @Path("trip_id") String trip_id
    );
}
