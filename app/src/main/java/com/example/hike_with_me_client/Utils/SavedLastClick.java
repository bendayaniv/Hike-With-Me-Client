package com.example.hike_with_me_client.Utils;

import com.example.hike_with_me_client.Models.Route.Route;

public class SavedLastClick {
    private static SavedLastClick instance = null;
    private int position = -1;
    private Route lastClickedRoute;
    private SavedLastClick() {
    }

    public static void initSavedLastClick() {
        if (instance == null) {
            instance = new SavedLastClick();
        }
    }

    public static SavedLastClick getInstance() {
        return instance;
    }

    public int getPosition() {
        return position;
    }

    public SavedLastClick setPosition(int position) {
        this.position = position;
        return this;
    }

    public Route getLastClickedRoute() {
        return lastClickedRoute;
    }

    public SavedLastClick setLastClickedRoute(Route lastClickedRoute) {
        this.lastClickedRoute = lastClickedRoute;
        return this;
    }

}
