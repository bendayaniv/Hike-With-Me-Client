package com.example.hike_with_me_client.Recommendation;

import com.example.hike_with_me_client.MasterClass;

public class RecommendationMasterClass extends MasterClass {

    public RecommendationApiInterface recommendationApiInterface = MasterClass.retrofit.create(RecommendationApiInterface.class);
}
