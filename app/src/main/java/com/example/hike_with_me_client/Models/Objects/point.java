package com.example.hike_with_me_client.Models.Objects;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Utils.Constants;

import java.util.Date;

public class point {
    private double _latitude;
    private double _longitude;
    private Date _date;

    private String _type;

    public point() {
    }

    public point(double latitude, double longitude, Date date, String type) {
        this._latitude = latitude;
        this._longitude = longitude;
        this._date = date;
        this._type = type;
    }

    public point getPoint(){
        return this;
    }
    public double getLatitude() {
        return _latitude;
    }

    public point setLatitude(double latitude) {
        this._latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return _longitude;
    }

    public point setLongitude(double longitude) {
        this._longitude = longitude;
        return this;
    }

    public Date getDate() {
        return _date;
    }

    public point setDate(Date date) {
        this._date = date;
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
                "_latitude=" + _latitude +
                ", _longitude=" + _longitude +
                ", _date=" + _date +
                ", _type='" + _type + '\'' +
                '}';
    }
}
