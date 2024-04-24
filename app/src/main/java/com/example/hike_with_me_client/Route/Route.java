package com.example.hike_with_me_client.Route;

public class Route {

    private String name;

    public Route() {}

    public Route(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Route setName(String name) {
        this.name = name;
        return this;
    }
}
