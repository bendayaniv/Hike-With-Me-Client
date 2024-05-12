package com.example.hike_with_me_client.Models.Objects;

import android.content.Context;

import com.example.hike_with_me_client.Models.User.User;

public class CurrentUser {

    private static CurrentUser instance = null;
    private User user;

    private CurrentUser() {
    }

    public static void initCurrentUser(Context context) {
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
}
