package com.example.hike_with_me_client.Recommendation.Callbacks;

import com.example.hike_with_me_client.Recommendation.Recommendation;
import com.example.hike_with_me_client.User.User;

public interface Callback_AddRecommendation {
    void success(Recommendation recommendation);
    void error(String error);
}
