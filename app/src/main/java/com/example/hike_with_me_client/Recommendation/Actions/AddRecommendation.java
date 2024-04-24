package com.example.hike_with_me_client.Recommendation.Actions;

import android.util.Log;

import com.example.hike_with_me_client.Recommendation.Callbacks.Callback_AddRecommendation;
import com.example.hike_with_me_client.Recommendation.Recommendation;
import com.example.hike_with_me_client.Recommendation.RecommendationMasterClass;

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
            public void onResponse(Call<Recommendation> call, Response<Recommendation> response) {
                Log.d("pttt", "onResponse: ");
                if(response.isSuccessful()) {
                    Log.d("pttt", "onResponse: " + response.body());
                    Recommendation recommendation = response.body();
                    Log.d("pttt", "onResponse: " + recommendation);
                    callback_addRecommendation.success(recommendation);
                } else {
                    callback_addRecommendation.error("" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Recommendation> call, Throwable t) {
                callback_addRecommendation.error(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
