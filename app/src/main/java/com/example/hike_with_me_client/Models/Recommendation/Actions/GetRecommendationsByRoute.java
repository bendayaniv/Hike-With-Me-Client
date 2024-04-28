package com.example.hike_with_me_client.Models.Recommendation.Actions;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Models.Recommendation.RecommendationMasterClass;
import com.example.hike_with_me_client.Interfaces.Recommendation.Callbacks.Callback_GetRecommendationsByRoute;
import com.example.hike_with_me_client.Models.Recommendation.Recommendation;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetRecommendationsByRoute extends RecommendationMasterClass {
    private final Callback_GetRecommendationsByRoute callback_getRecommendationsByRoute;

    public GetRecommendationsByRoute(Callback_GetRecommendationsByRoute callbackGetRecommendationsByRoute) {
        callback_getRecommendationsByRoute = callbackGetRecommendationsByRoute;
    }

    public void getRecommendationsByRoute(String routeId) {
        Call<List<Recommendation>> call = recommendationApiInterface.getRecommendationsByRoute(routeId);

        call.enqueue(new Callback<List<Recommendation>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recommendation>> call, @NonNull Response<List<Recommendation>> response) {
                if(response.isSuccessful()) {
                    List<Recommendation> recommendations = response.body();
                    callback_getRecommendationsByRoute.success(recommendations);
                } else {
                    callback_getRecommendationsByRoute.error(String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Recommendation>> call, @NonNull Throwable t) {
                callback_getRecommendationsByRoute.error(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
