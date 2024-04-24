package com.example.hike_with_me_client.Recommendation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RecommendationApiInterface {

    @GET("recommendations/{route}")
    Call<List<Recommendation>> getRecommendationsByRoute(
            @Path("route") String route
    );

    @POST("recommendations/addRecommendation")
    Call<Recommendation> addRecommendation(
            @Body Recommendation recommendation
    );
}
