package com.example.hike_with_me_client.Trip;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Route.Route;
import com.example.hike_with_me_client.User.User;

public class Trip {

    private String id;
    private String name;
    private String startDate;
    private String endDate;
    private String location;
    private String description;
//    private Route route;
    private String routeName;
//    private User user;
    private String userId;

    public Trip() {}

    public Trip(String id, String name, String startDate, String endDate, String location, String description, /*Route route*/String routeName, /*User user*/String userId) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.description = description;
//        this.route = route;
        this.routeName = routeName;
//        this.user = user;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public Trip setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Trip setName(String name) {
        this.name = name;
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    public Trip setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public Trip setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Trip setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Trip setDescription(String description) {
        this.description = description;
        return this;
    }

//    public Route getRoute() {
//        return route;
//    }

//    public Trip setRoute(Route route) {
//        this.route = route;
//        return this;
//    }

    public String getRouteName() {
        return routeName;
    }

    public Trip setRouteName(String routeName) {
        this.routeName = routeName;
        return this;
    }

//    public User getUser() {
//        return user;
//    }

//    public Trip setUser(User user) {
//        this.user = user;
//        return this;
//    }

    public String getUserName() {
        return userId;
    }

    public Trip setUserName(String userName) {
        this.userId = userName;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "Trip{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", routeName='" + routeName + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
