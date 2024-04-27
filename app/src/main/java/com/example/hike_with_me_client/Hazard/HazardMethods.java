package com.example.hike_with_me_client.Hazard;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.TextView;

import com.example.hike_with_me_client.Hazard.Actions.AddHazard;
import com.example.hike_with_me_client.Hazard.Actions.GetHazardsByRoute;
import com.example.hike_with_me_client.Hazard.Callbacks.Callback_AddHazard;
import com.example.hike_with_me_client.Hazard.Callbacks.Callback_GetHazardsByRoute;

import java.util.List;

public class HazardMethods {
    @SuppressLint("SetTextI18n")
    public static void getHazardsByRoute(String routeName, TextView textView) {
        Callback_GetHazardsByRoute callback_getHazardsByRoute = new Callback_GetHazardsByRoute() {
            @Override
            public void success(List<Hazard> hazards) {
                if(hazards.size() == 0) {
                    textView.setText("No hazards found");
                } else {
                    textView.setText("Hazards found: " + hazards);
                }
            }

            @Override
            public void error(String message) {
                textView.setText("Error: " + message + "\nNo hazards found");
            }
        };
        new GetHazardsByRoute(callback_getHazardsByRoute).getHazardsByRoute(routeName);
    }
    @SuppressLint("SetTextI18n")
    public static void addHazard(Hazard hazard, TextView textView){
        Callback_AddHazard callback_addHazard = new Callback_AddHazard() {
            @Override
            public void success(Hazard hazard) {
                textView.setText("Hazard added: " + hazard);
            }

            @Override
            public void error(String message) {
                Log.d("pttt", "Error: " + message);
                textView.setText("Error: " + message);
            }
        };
        new AddHazard(callback_addHazard).addHazard(hazard);
    }
}
