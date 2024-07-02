package com.example.hike_with_me_client.Utils.Singleton;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Models.Hazard.Hazard;
import com.example.hike_with_me_client.Models.Hazard.HazardMethods;

import java.util.ArrayList;

public class ListOfHazards {
    private static ListOfHazards instance = null;
    ArrayList<Hazard> hazards = new ArrayList<>();

    public ListOfHazards() {
        HazardMethods.getAllHazards();
    }

    public static void initListOfHazards() {
        if (instance == null) {
            instance = new ListOfHazards();
        }
    }

    public static ListOfHazards getInstance() {
        return instance;
    }

    public ArrayList<Hazard> getHazards() {
        return hazards;
    }

    public void setHazards(ArrayList<Hazard> hazards) {
        this.hazards = hazards;
    }

    @NonNull
    @Override
    public String toString() {
        return "ListOfHazards{" +
                "hazards=" + hazards +
                '}';
    }
}
