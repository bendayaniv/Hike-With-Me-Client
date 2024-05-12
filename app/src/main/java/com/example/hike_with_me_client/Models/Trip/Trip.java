package com.example.hike_with_me_client.Models.Trip;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Models.Objects.ObjectLocation;

import java.util.Arrays;

public class Trip {

    private String id;
    private String name;
    private String startDate;
    private String endDate;
    private ObjectLocation[] locations;
    private String description;
    private String routeName;
    private String userId;

    public Trip() {}

    public Trip(String id, String name, String startDate, String endDate, ObjectLocation[] locations, String description, String routeName, String userId) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.locations = locations;
        this.description = description;
        this.routeName = routeName;
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

    public ObjectLocation[] getLocations() {
        return locations;
    }

    public Trip setLocations(ObjectLocation[] locations) {
        this.locations = locations;
        return this;
    }

    public void addLocation(ObjectLocation location) {
        if (locations == null) {
            locations = new ObjectLocation[1];
            locations[0] = location;
        } else {
            ObjectLocation[] newLocations = new ObjectLocation[locations.length + 1];
            System.arraycopy(locations, 0, newLocations, 0, locations.length);
            newLocations[locations.length] = location;
            locations = newLocations;
        }
    }

    public String getDescription() {
        return description;
    }

    public Trip setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getRouteName() {
        return routeName;
    }

    public Trip setRouteName(String routeName) {
        this.routeName = routeName;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public Trip setUserId(String userName) {
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
                ", location='" + Arrays.toString(locations) + '\'' +
                ", description='" + description + '\'' +
                ", routeName='" + routeName + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
