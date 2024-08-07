package com.example.hike_with_me_client.Models.Recommendation.Actions;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Models.Recommendation.RecommendationMasterClass;
import com.example.hike_with_me_client.Interfaces.Recommendation.Callbacks.Callback_AddRecommendation;
import com.example.hike_with_me_client.Models.Recommendation.Recommendation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRecommendation extends RecommendationMasterClass {
    private final Callback_AddRecommendation callback_addRecommendation;

    public AddRecommendation(Callback_AddRecommendation callbackAddRecommendation) {
        this.callback_addRecommendation = callbackAddRecommendation;
    }

    public void addRecommendation(Recommendation recommendation) {
        Call<Recommendation> call = recommendationApiInterface.addRecommendation(recommendation);

        call.enqueue(new Callback<Recommendation>() {
            @Override
            public void onResponse(@NonNull Call<Recommendation> call, @NonNull Response<Recommendation> response) {
                if (response.isSuccessful()) {
                    Recommendation recommendation = response.body();
                    callback_addRecommendation.success(recommendation);
                } else {
                    callback_addRecommendation.error(String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Recommendation> call, @NonNull Throwable t) {
                callback_addRecommendation.error(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
