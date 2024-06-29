package com.example.hike_with_me_client.Utils;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Models.Route.Route;
import com.example.hike_with_me_client.Models.Route.RouteMethods;

import java.util.ArrayList;

public class ListOfRoutes {

    private static ListOfRoutes instance = null;
    ArrayList<Route> routes;
    boolean firstTime;

    public ListOfRoutes() {
        RouteMethods.getAllRoutes();
    }

    public static void initListOfRoutes() {
        if (instance == null) {
            instance = new ListOfRoutes();
            instance.firstTime = true;
        }
    }

    public static ListOfRoutes getInstance() {
        return instance;
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    @NonNull
    @Override
    public String toString() {
        return "ListOfRoutes{" +
                "routes=" + routes +
                '}';
    }
}
