package com.example.hike_with_me_client.Interfaces.Recommendation;

import com.example.hike_with_me_client.Models.Recommendation.Recommendation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RecommendationApiInterface {
    @GET("hike-with-me/recommendations/{route_name}")
    Call<List<Recommendation>> getRecommendationsByRoute(
            @Path("route_name") String route
    );

    @POST("hike-with-me/recommendations/addRecommendation")
    Call<Recommendation> addRecommendation(
            @Body Recommendation recommendation
    );
}
