package com.example.hike_with_me_client.Models.Objects;

import com.example.hike_with_me_client.Models.Trip.Trip;
import com.example.hike_with_me_client.Models.User.User;
import com.example.hike_with_me_client.Utils.File;

import java.util.ArrayList;
import java.util.List;

public class CurrentUser {

    private static CurrentUser instance = null;
    private User user;
    private ArrayList<UserWithDistance> usersWithDistance;
    private ArrayList<Trip> trips;
    private ArrayList<File> urlsImages;
    private String errorMessageFromServer;

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

    public ArrayList<Trip> getTrips() {
        return trips;
    }

    public void setTrips(ArrayList<Trip> trips) {
        this.trips = trips;
    }

    public ArrayList<File> getUrlsImages() {
        return urlsImages;
    }

    public void setUrlsImages(List<File> urlsImages) {
        this.urlsImages = (ArrayList<File>) urlsImages;
    }

    public String getErrorMessageFromServer() {
        return errorMessageFromServer;
    }

    public void setErrorMessageFromServer(String errorMessageFromServer) {
        this.errorMessageFromServer = errorMessageFromServer;
    }
}
