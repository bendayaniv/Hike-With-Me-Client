package com.example.hike_with_me_client.Interfaces.Recommendation.Callbacks;

import com.example.hike_with_me_client.Models.Recommendation.Recommendation;

public interface Callback_AddRecommendation {
    void success(Recommendation recommendation);

    void error(String error);
}
