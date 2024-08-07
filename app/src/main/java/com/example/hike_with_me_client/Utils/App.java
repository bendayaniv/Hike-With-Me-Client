package com.example.hike_with_me_client.Utils;

import android.app.Application;

import com.example.hike_with_me_client.Utils.GlobalUtilInstances.CurrentUser;
import com.example.hike_with_me_client.Utils.GlobalUtilInstances.ErrorMessageFromServer;
import com.example.hike_with_me_client.Utils.GlobalUtilInstances.ListOfHazards;
import com.example.hike_with_me_client.Utils.GlobalUtilInstances.ListOfRoutes;
import com.example.hike_with_me_client.Utils.GlobalUtilInstances.ListOfTrips;
import com.example.hike_with_me_client.Utils.GlobalUtilInstances.UserLocation;

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
