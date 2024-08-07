package com.example.hike_with_me_client.Models.Hazard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.hike_with_me_client.Interfaces.Hazard.Callbacks.Callback_GetAllHazards;
import com.example.hike_with_me_client.Interfaces.Hazard.Callbacks.Callback_GetNearHazards;
import com.example.hike_with_me_client.Models.Hazard.Actions.AddHazard;
import com.example.hike_with_me_client.Models.Hazard.Actions.GetAllHazards;
import com.example.hike_with_me_client.Models.Hazard.Actions.GetHazardsByRoute;
import com.example.hike_with_me_client.Interfaces.Hazard.Callbacks.Callback_AddHazard;
import com.example.hike_with_me_client.Interfaces.Hazard.Callbacks.Callback_GetHazardsByRoute;
import com.example.hike_with_me_client.Models.Hazard.Actions.GetNearHazards;
import com.example.hike_with_me_client.Utils.GlobalUtilInstances.ListOfHazards;

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

    public static void getNearHazards() {
        Callback_GetNearHazards callback_getNearHazards = new Callback_GetNearHazards() {
            @Override
            public void success(List<Hazard> hazards) {
                ListOfHazards.getInstance().setHazards((ArrayList<Hazard>) hazards);
                Log.d("Hazard", "Hazards found: " + hazards.size());
            }

            @Override
            public void error(String message) {
                Log.d("Hazard", "Error: " + message + "\nNo hazards found");
            }
        };
        new GetNearHazards(callback_getNearHazards).getNearHazards();
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
    public static void addHazard(Hazard hazard, Context context) {
        Callback_AddHazard callback_addHazard = new Callback_AddHazard() {
            @Override
            public void success(Hazard hazard) {
                Log.d("Hazard", "Hazard added successfully");
                Toast.makeText(context, "Hazard saved successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void error(String message) {
                Log.d("Hazard", "Error: " + message);
            }
        };
        new AddHazard(callback_addHazard).addHazard(hazard);
    }
}
