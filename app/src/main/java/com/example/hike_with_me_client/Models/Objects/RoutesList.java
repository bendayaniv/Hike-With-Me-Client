package com.example.hike_with_me_client.Models.Objects;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Models.Route.Route;

import java.util.ArrayList;

public class RoutesList {

    private static RoutesList instance = null;
    private ArrayList<Route> routes = new ArrayList<>();

    public RoutesList() {
    }

    public static void initCurrentInstant(Context context) {
        if (instance == null) {
            instance = new RoutesList();
        }
    }

    public static RoutesList getInstance() {
        return instance;
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public RoutesList setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
        return this;
    }

    public void sortList() {
        routes.sort((o1, o2) -> {
            if (o1.getName().compareTo(o2.getName()) > 0) {
                return 1;
            } else if (o1.getName().compareTo(o2.getName()) < 0) {
                return -1;
            } else {
                return 0;
            }
        });
    }

    @NonNull
    @Override
    public String toString() {
        return "RoutesList{" +
                "routes=" + routes +
                '}';
    }
}
