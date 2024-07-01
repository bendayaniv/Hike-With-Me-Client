package com.example.hike_with_me_client.Models.Route;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.hike_with_me_client.Models.Route.Actions.GetAllRoutes;
import com.example.hike_with_me_client.Models.Route.Actions.GetRoutesNames;
import com.example.hike_with_me_client.Interfaces.Route.Callbacks.Callback_GetAllRoutes;
import com.example.hike_with_me_client.Interfaces.Route.Callbacks.Callback_GetRoutesNames;
import com.example.hike_with_me_client.Utils.ErrorMessageFromServer;
import com.example.hike_with_me_client.Utils.ListOfRoutes;

import java.util.ArrayList;
import java.util.List;

public class RouteMethods {

    public static void getAllRoutes() {
        Callback_GetAllRoutes callback_getAllRoutes = new Callback_GetAllRoutes() {
            @Override
            public void success(List<Route> routes) {
                ListOfRoutes.getInstance().setRoutes((ArrayList<Route>) routes);
            }

            @Override
            public void error(String message) {
                Log.d("RouteMethods", "Error: " + message);
                ErrorMessageFromServer.getInstance().setErrorMessageFromServer(message);
            }
        };
        new GetAllRoutes(callback_getAllRoutes).getAllRoutes();
    }

    @SuppressLint("SetTextI18n")
    public static void getRoutesNames(List<String> _routesNames) {
        Callback_GetRoutesNames callback_getRoute = new Callback_GetRoutesNames() {
            @Override
            public void success(List<String> routesNames) {
                Log.d("RouteMethods", "Routes Names: " + routesNames);
                _routesNames.clear();
                _routesNames.addAll(routesNames);
            }

            @Override
            public void error(String message) {
                Log.d("RouteMethods", "Error: " + message);
            }
        };
        new GetRoutesNames(callback_getRoute).getRoutesNames();
    }
}
