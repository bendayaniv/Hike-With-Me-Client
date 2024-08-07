package com.example.hike_with_me_client.Models.Recommendation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hike_with_me_client.Models.Recommendation.Actions.AddRecommendation;
import com.example.hike_with_me_client.Models.Recommendation.Actions.GetRecommendationsByRoute;
import com.example.hike_with_me_client.Interfaces.Recommendation.Callbacks.Callback_AddRecommendation;
import com.example.hike_with_me_client.Interfaces.Recommendation.Callbacks.Callback_GetRecommendationsByRoute;

import java.util.List;

public class RecommendationMethods {
    @SuppressLint("SetTextI18n")
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
                textView.setText("Error: " + message + "\nNo recommendations found");
            }
        };
        new GetRecommendationsByRoute(callback_getAllRecommendationsByRoute).getRecommendationsByRoute(routeName);
    }

    @SuppressLint("SetTextI18n")
    public static void addRecommendation(Recommendation recommendation, Context context){
        Callback_AddRecommendation callback_addRecommendation = new Callback_AddRecommendation() {
            @Override
            public void success(Recommendation recommendation) {
                Log.d("Recommendation added: ", recommendation.toString());
                Toast.makeText(context, "Recommendation saved successfully", Toast.LENGTH_SHORT).show();
                //textView.setText("Recommendation added: " + recommendation);
            }

            @Override
            public void error(String message) {
                Log.d("Recommendation", "Error: " + message);
                //textView.setText("Error: " + message);
            }
        };
        new AddRecommendation(callback_addRecommendation).addRecommendation(recommendation);
    }
}
