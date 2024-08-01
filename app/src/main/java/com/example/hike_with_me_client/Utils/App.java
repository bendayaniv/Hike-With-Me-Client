package com.example.hike_with_me_client.Utils;

import android.app.Application;

import com.example.hike_with_me_client.Utils.Singleton.CurrentUser;
import com.example.hike_with_me_client.Utils.Singleton.ErrorMessageFromServer;
import com.example.hike_with_me_client.Utils.Singleton.ListOfHazards;
import com.example.hike_with_me_client.Utils.Singleton.ListOfRoutes;
import com.example.hike_with_me_client.Utils.Singleton.ListOfTrips;
import com.example.hike_with_me_client.Utils.Singleton.UserLocation;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CurrentUser.initCurrentUser();
        UserLocation.initUserLocation();
//        UserLocation.getInstance().setContext(this);
        ListOfRoutes.initListOfRoutes();
        ListOfHazards.initListOfHazards();
        ListOfTrips.initListOfTrips();
        ErrorMessageFromServer.initErrorMessageFromServer();
    }
}
