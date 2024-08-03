package com.example.hike_with_me_client.Utils.Singleton;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Models.Hazard.HazardMethods;
import com.example.hike_with_me_client.Models.Trip.trip;

import java.util.ArrayList;

public class ListOfTrips {
    private static ListOfTrips instance = null;
    ArrayList<trip> trips = new ArrayList<>();

    public ListOfTrips() {
        HazardMethods.getAllHazards();
    }

    public static void initListOfTrips() {
        if (instance == null) {
            instance = new ListOfTrips();
        }
    }

    public static ListOfTrips getInstance() {
        return instance;
    }

    public ArrayList<trip> getTrips() {
        return trips;
    }

    public void setTrips(ArrayList<trip> hazards) {
        this.trips = hazards;
    }

    @NonNull
    @Override
    public String toString() {
        return "ListOfHazards{" +
                "trips=" + trips +
                '}';
    }
}
