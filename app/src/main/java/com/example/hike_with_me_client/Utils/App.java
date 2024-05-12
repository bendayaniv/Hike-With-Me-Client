package com.example.hike_with_me_client.Utils;

import android.app.Application;

import com.example.hike_with_me_client.Models.Objects.CurrentUser;
import com.example.hike_with_me_client.Models.Objects.RoutesList;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CurrentUser.initCurrentUser(this);
        RoutesList.initCurrentInstant(this);
    }
}
