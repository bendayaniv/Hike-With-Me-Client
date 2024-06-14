package com.example.hike_with_me_client.Models.Objects;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Models.User.User;

public class UserWithDistance {

    private User user;
    private double distance;

    public UserWithDistance() {
    }

    public UserWithDistance(User user, double distance) {
        this.user = user;
        this.distance = distance;
    }

    public User getUser() {
        return user;
    }

    public UserWithDistance setUser(User user) {
        this.user = user;
        return this;
    }

    public double getDistance() {
        return distance;
    }

    public UserWithDistance setDistance(double distance) {
        this.distance = distance;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "UsersWithDistance{" +
                "user=" + user +
                ", distance=" + distance +
                '}';
    }
}
