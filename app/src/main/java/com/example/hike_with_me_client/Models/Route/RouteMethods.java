package com.example.hike_with_me_client.Models.Route;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hike_with_me_client.Interfaces.Route.Callbacks.Callback_CreateRoute;
import com.example.hike_with_me_client.Models.Route.Actions.CreateRoute;
import com.example.hike_with_me_client.Models.Route.Actions.GetAllRoutes;
import com.example.hike_with_me_client.Models.Route.Actions.GetRoutesNames;
import com.example.hike_with_me_client.Interfaces.Route.Callbacks.Callback_GetAllRoutes;
import com.example.hike_with_me_client.Interfaces.Route.Callbacks.Callback_GetRoutesNames;
import com.example.hike_with_me_client.Utils.Singleton.ErrorMessageFromServer;
import com.example.hike_with_me_client.Utils.Singleton.ListOfRoutes;

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

    public static void saveRoute(Route route, Context context) {
        Callback_CreateRoute callback_createRoute = new Callback_CreateRoute() {
            @Override
            public void success(Route route) {
                Log.d("route", "Route created: " + route);
                Toast.makeText(context, "Route saved successfully!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void error(String message) {
                Log.d("route", "Error: " + message);
                Toast.makeText(context, "Error: " + message, Toast.LENGTH_SHORT).show();

            }
        };

        Log.d("route is valid", "Route2" + route);
        new CreateRoute(callback_createRoute).createRoute(route);
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
