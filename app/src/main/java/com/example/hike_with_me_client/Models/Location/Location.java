package com.example.hike_with_me_client.Models.Location;

import androidx.annotation.NonNull;

import java.util.Date;

public class Location {
    private double latitude;
    private double longitude;
    private Date date;

    public Location() {
    }

    public Location(double latitude, double longitude, Date date) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
    }

    public double getLatitude() {
        return latitude;
    }

    public Location setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public Location setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Location setDate(Date date) {
        this.date = date;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "Location{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", date=" + date +
                '}';
    }
}
