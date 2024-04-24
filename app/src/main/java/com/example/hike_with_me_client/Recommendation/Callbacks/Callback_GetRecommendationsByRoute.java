package com.example.hike_with_me_client.Recommendation.Callbacks;


import com.example.hike_with_me_client.Recommendation.Recommendation;

import java.util.List;

public interface Callback_GetRecommendationsByRoute {
    void success(List<Recommendation> recommendations);
    void error(String error);
}
