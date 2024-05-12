package com.example.hike_with_me_client.Models.Route;

import com.example.hike_with_me_client.Models.Objects.Location;
import com.example.hike_with_me_client.Utils.Constants;

public class Route {

    private String id;
    private String name;
    private String description;
    private Constants.Level difficultyLevel;
    private String length;
    private Location location;

    public Route() {}

    public Route(String id, String name, String description, Constants.Level difficultyLevel, String length, Location location) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.difficultyLevel = difficultyLevel;
        this.length = length;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public Route setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Route setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Route setDescription(String description) {
        this.description = description;
        return this;
    }

    public Constants.Level getDifficultyLevel() {
        return difficultyLevel;
    }

    public Route setDifficultyLevel(Constants.Level difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
        return this;
    }

    public String getLength() {
        return length;
    }

    public Route setLength(String length) {
        this.length = length;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public Route setLocation(Location location) {
        this.location = location;
        return this;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", difficultyLevel=" + difficultyLevel +
                ", length='" + length + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
