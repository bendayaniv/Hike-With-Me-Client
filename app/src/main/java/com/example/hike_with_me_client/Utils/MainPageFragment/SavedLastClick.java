package com.example.hike_with_me_client.Utils.MainPageFragment;

import com.example.hike_with_me_client.Models.Route.Route;

public class SavedLastClick {
    private static SavedLastClick instance = null;
    private int position = -1;
    private Route lastClickedRoute = null;
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

    public void setPosition(int position) {
        this.position = position;
    }

    public Route getLastClickedRoute() {
        return lastClickedRoute;
    }

    public void setLastClickedRoute(Route lastClickedRoute) {
        this.lastClickedRoute = lastClickedRoute;
    }

}
