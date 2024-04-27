package com.example.hike_with_me_client.Recommendation;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.TextView;

import com.example.hike_with_me_client.Recommendation.Actions.AddRecommendation;
import com.example.hike_with_me_client.Recommendation.Actions.GetRecommendationsByRoute;
import com.example.hike_with_me_client.Recommendation.Callbacks.Callback_AddRecommendation;
import com.example.hike_with_me_client.Recommendation.Callbacks.Callback_GetRecommendationsByRoute;

import org.w3c.dom.Text;

import java.util.List;

public class RecommendationMethods {

    public static void getRecommendationsByRoute(String routeName, TextView textView) {
        Callback_GetRecommendationsByRoute callback_getAllRecommendationsByRoute = new Callback_GetRecommendationsByRoute() {
            @Override
            public void success(List<Recommendation> recommendations) {
                if(recommendations.size() == 0) {
                    textView.setText("No recommendations found");
                } else {
                    textView.setText("Recommendations found: " + recommendations);
                }
            }

            @Override
            public void error(String message) {
                Log.d("pttt", "Error: " + message);
                textView.setText("Error: " + message + "\nNo recommendations found");
            }
        };
        new GetRecommendationsByRoute(callback_getAllRecommendationsByRoute).getRecommendationsByRoute(routeName);
    }

    @SuppressLint("SetTextI18n")
    public static void addRecommendation(Recommendation recommendation, TextView textView){
        Callback_AddRecommendation callback_addRecommendation = new Callback_AddRecommendation() {
            @Override
            public void success(Recommendation recommendation) {
                textView.setText("Recommendation added: " + recommendation);
            }

            @Override
            public void error(String message) {
                Log.d("pttt", "Error: " + message);
                textView.setText("Error: " + message);
            }
        };
        new AddRecommendation(callback_addRecommendation).addRecommendation(recommendation);
    }
}
