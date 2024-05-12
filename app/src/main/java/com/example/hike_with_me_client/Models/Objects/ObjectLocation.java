package com.example.hike_with_me_client.Models.Objects;

import androidx.annotation.NonNull;

import java.util.Date;

public class ObjectLocation {
    private double latitude;
    private double longitude;
    private Date date;

    public ObjectLocation() {
    }

    public ObjectLocation(double latitude, double longitude, Date date) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
    }

    public double getLatitude() {
        return latitude;
    }

    public ObjectLocation setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public ObjectLocation setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public ObjectLocation setDate(Date date) {
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
