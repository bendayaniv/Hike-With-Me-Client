package com.example.hike_with_me_client.Models.Hazard;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.hike_with_me_client.Interfaces.Hazard.Callbacks.Callback_GetAllHazards;
import com.example.hike_with_me_client.Models.Hazard.Actions.AddHazard;
import com.example.hike_with_me_client.Models.Hazard.Actions.GetAllHazards;
import com.example.hike_with_me_client.Models.Hazard.Actions.GetHazardsByRoute;
import com.example.hike_with_me_client.Interfaces.Hazard.Callbacks.Callback_AddHazard;
import com.example.hike_with_me_client.Interfaces.Hazard.Callbacks.Callback_GetHazardsByRoute;
import com.example.hike_with_me_client.Utils.ListOfHazards;

import java.util.ArrayList;
import java.util.List;

public class HazardMethods {

    public static void getAllHazards() {
        Callback_GetAllHazards callback_getAllHazards = new Callback_GetAllHazards() {
            @Override
            public void success(List<Hazard> hazards) {
                ListOfHazards.getInstance().setHazards((ArrayList<Hazard>) hazards);
            }

            @Override
            public void error(String message) {
                Log.d("Hazard", "Error: " + message + "\nNo hazards found");
            }
        };
        new GetAllHazards(callback_getAllHazards).getAllHazards();
    }

    @SuppressLint("SetTextI18n")
    public static void getHazardsByRoute(String routeName, List<Hazard> _hazards) {
        Callback_GetHazardsByRoute callback_getHazardsByRoute = new Callback_GetHazardsByRoute() {
            @Override
            public void success(List<Hazard> hazards) {
                _hazards.addAll(hazards);
                _hazards.addAll(hazards);
            }

            @Override
            public void error(String message) {
                Log.d("Hazard", "Error: " + message + "\nNo hazards found");
            }
        };
        new GetHazardsByRoute(callback_getHazardsByRoute).getHazardsByRoute(routeName);
    }

    @SuppressLint("SetTextI18n")
    public static void addHazard(Hazard hazard) {
        Callback_AddHazard callback_addHazard = new Callback_AddHazard() {
            @Override
            public void success(Hazard hazard) {
                Log.d("Hazard", "Hazard added successfully");
            }

            @Override
            public void error(String message) {
                Log.d("Hazard", "Error: " + message);
            }
        };
        new AddHazard(callback_addHazard).addHazard(hazard);
    }
}
