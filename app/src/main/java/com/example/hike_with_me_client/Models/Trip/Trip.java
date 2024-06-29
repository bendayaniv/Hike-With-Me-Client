package com.example.hike_with_me_client.Models.Trip;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Models.Objects.Location;
import com.example.hike_with_me_client.Utils.File;

import java.util.ArrayList;
import java.util.Arrays;

public class Trip {

    private String id;
    private String name;
    private String startDate;
    private String endDate;
    private Location[] locations;
    private String description;
    private String[] routesNames;
    private String userId;
    private ArrayList<File> images;

    public Trip() {}

    public Trip(String id, String name, String startDate, String endDate, Location[] locations, String description, String[] routesNames, String userId) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.locations = locations;
        this.description = description;
        this.routesNames = routesNames;
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

    public Location[] getLocations() {
        return locations;
    }

    public Trip setLocations(Location[] locations) {
        this.locations = locations;
        return this;
    }

    public void addLocation(Location location) {
        if (locations == null) {
            locations = new Location[1];
            locations[0] = location;
        } else {
            Location[] newLocations = new Location[locations.length + 1];
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

    public String[] getRoutesNames() {
        return routesNames;
    }

    public Trip setRoutesNames(String[] routesNames) {
        this.routesNames = routesNames;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public Trip setUserId(String userName) {
        this.userId = userName;
        return this;
    }

    public ArrayList<File> getImages() {
        return images;
    }

    public void setImages(ArrayList<File> images) {
        this.images = images;
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
                ", routeName='" + Arrays.toString(routesNames) + '\'' +
                ", userId='" + userId + '\'' +
                ", images='" + images + '\'' +
                '}';
    }
}
