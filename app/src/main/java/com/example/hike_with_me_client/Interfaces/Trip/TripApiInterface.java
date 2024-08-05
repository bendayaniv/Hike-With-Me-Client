package com.example.hike_with_me_client.Interfaces.Trip;

import com.example.hike_with_me_client.Models.Trip.trip;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface TripApiInterface {

    @GET("hike-with-me/trips/{user_id}")
    Call<List<trip>> getTrips(
            @Path("user_id") String user_id
    );

    @POST("hike-with-me/trips/createTrip")
    Call<trip> createTrip(
            @Body trip trip
    );

    @PUT("hike-with-me/trips")
    Call<trip> updateTrip(
            @Body trip trip
    );

    @DELETE("hike-with-me/trips/{user_id}/{trip_id}")
    Call<String> deleteTrip(
            @Path("user_id") String user_id,
            @Path("trip_id") String trip_id
    );

    @Multipart
    @POST("hike-with-me/trips/uploadImages")
    Call<String> uploadImages(
            @Part List<MultipartBody.Part> images,
            @Part("userId") RequestBody userId,
            @Part("tripName") RequestBody tripName
    );

    @DELETE("hike-with-me/trips/{userName}/{tripName}/{imageName}")
    Call<String> deleteImage(
            @Path("userName") String userName,
            @Path("tripName") String tripName,
            @Path("imageName") String imageName
    );
}
