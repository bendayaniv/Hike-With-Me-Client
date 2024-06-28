package com.example.hike_with_me_client.Models.Objects;

import com.example.hike_with_me_client.Models.User.User;

import java.util.ArrayList;

public class CurrentUser {

    private static CurrentUser instance = null;
    private User user;
    private ArrayList<UserWithDistance> usersWithDistance;
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

    public String getErrorMessageFromServer() {
        return errorMessageFromServer;
    }

    public void setErrorMessageFromServer(String errorMessageFromServer) {
        this.errorMessageFromServer = errorMessageFromServer;
    }
}
