package com.example.hike_with_me_client.Models.Recommendation;

import com.example.hike_with_me_client.Interfaces.Recommendation.RecommendationApiInterface;
import com.example.hike_with_me_client.Utils.MasterClass;

public class RecommendationMasterClass extends MasterClass {

    public RecommendationApiInterface recommendationApiInterface = MasterClass.retrofit.create(RecommendationApiInterface.class);
}
