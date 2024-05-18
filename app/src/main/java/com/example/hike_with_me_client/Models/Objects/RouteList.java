package com.example.hike_with_me_client.Models.Objects;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Models.Route.Route;

import java.util.ArrayList;

public class RouteList {

    private ArrayList<Route> routes = new ArrayList<>();

    public RouteList() {
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public RouteList setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "RouteList{" +
                "routes=" + routes +
                '}';
    }
}
