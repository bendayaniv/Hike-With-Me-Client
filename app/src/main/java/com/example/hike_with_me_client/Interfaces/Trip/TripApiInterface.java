package com.example.hike_with_me_client.Interfaces.Trip;

import com.example.hike_with_me_client.Models.Trip.Trip;

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
    Call<List<Trip>> getTrips(
            @Path("user_id") String user_id
    );

    @POST("hike-with-me/trips/createTrip")
    Call<Trip> createTrip(
            @Body Trip trip
    );

    @PUT("hike-with-me/trips")
    Call<Trip> updateTrip(
            @Body Trip trip
    );

    @DELETE("hike-with-me/trips/{user_id}/{trip_id}")
    Call<String> deleteTrip(
            @Path("user_id") String user_id,
            @Path("trip_id") String trip_id
    );

//    @POST("hike-with-me/trips/uploadImages")
//    Call<String> uploadImages(
//            @Part List<Uri> files,
//            /*@Body*/@Part("userName") /*String*/RequestBody userName,
//            /*@Body*/@Part("tripName") /*String*/RequestBody tripName
//    );

    @Multipart
    @POST("hike-with-me/trips/uploadImages")
    Call<String> uploadImages(
            @Part List<MultipartBody.Part> images,
            @Part("userName") RequestBody userName,
            @Part("tripName") RequestBody tripName
    );

    @DELETE("hike-with-me/trips/{userName}/{tripName}/{imageName}")
    Call<String> deleteImage(
            @Path("userName") String userName,
            @Path("tripName") String tripName,
            @Path("imageName") String imageName
    );
}
