package com.example.hike_with_me_client.Utils;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CurrentUser.initCurrentUser();
        UserLocation.initUserLocation();
        ListOfRoutes.initListOfRoutes();
        ListOfHazards.initListOfHazards();
        ListOfTrips.initListOfTrips();
        ErrorMessageFromServer.initErrorMessageFromServer();
    }
}
