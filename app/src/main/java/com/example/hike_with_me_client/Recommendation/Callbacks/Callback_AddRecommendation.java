package com.example.hike_with_me_client.Recommendation.Callbacks;

import com.example.hike_with_me_client.Recommendation.Recommendation;

public interface Callback_AddRecommendation {
    void success(Recommendation recommendation);
    void error(String error);
}
