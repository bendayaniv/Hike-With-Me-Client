package com.example.hike_with_me_client.Models.Objects;

import androidx.annotation.NonNull;


public class point {
    private Location _location;
    private String _type;

    public point() {
    }

    public point(Location location, String type) {
        this._location = location;
        this._type = type;
    }

    public point getPoint(){
        return this;
    }
    public Location getLocation() {
        return _location;
    }

    public point setLocation(Location location) {
        this._location = location;
        return this;
    }

    public String getType() {
        return _type;
    }

    public point setType(String type) {
        this._type = type;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "point{" +
                "_location=" + _location +
                ", _type='" + _type + '\'' +
                '}';
    }
}
