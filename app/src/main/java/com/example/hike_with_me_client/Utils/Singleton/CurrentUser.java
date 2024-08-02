package com.example.hike_with_me_client.Utils.Singleton;

import com.example.hike_with_me_client.Models.Objects.Location;
import com.example.hike_with_me_client.Models.Objects.UserWithDistance;
import com.example.hike_with_me_client.Models.User.User;

import java.util.ArrayList;
import java.util.Objects;

public class CurrentUser {

    private static CurrentUser instance = null;
    private User user;
    private ArrayList<UserWithDistance> usersWithDistance;
    private Location initiateLocation;

    private CurrentUser() {
    }

    public static void initCurrentUser() {
        if (instance == null) {
            instance = new CurrentUser();
        }
    }

    public static CurrentUser getInstance() {
        return instance;
    }

    public User getUser() {
        return user;
    }

    public CurrentUser setUser(User user) {
        this.user = user;
        return this;
    }

    public void removeUser() {
        this.user = null;
    }

    public ArrayList<UserWithDistance> getUsersWithDistance() {
        return usersWithDistance;
    }

    public void setUsersWithDistance(ArrayList<UserWithDistance> usersWithDistance) {
        this.usersWithDistance = usersWithDistance;
    }

    public Location getInitiateLocation() {
        return initiateLocation;
    }

    public void setInitiateLocation(Location initiateLocation) {
        this.initiateLocation = initiateLocation;
    }

    public boolean isInitiateLocationEqualToUserLocation() {
        if (this.user == null || this.initiateLocation == null) {
            return false;
        }

        Location userLocation = this.user.getLocation();
        if (userLocation == null) {
            return false;
        }

        return Objects.equals(this.initiateLocation, userLocation);
    }
}
