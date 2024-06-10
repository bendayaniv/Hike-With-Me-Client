package com.example.hike_with_me_client.Models.Route;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.TextView;

import com.example.hike_with_me_client.Models.Route.Actions.GetAllRoutes;
import com.example.hike_with_me_client.Models.Route.Actions.GetRoute;
import com.example.hike_with_me_client.Interfaces.Route.Callbacks.Callback_GetAllRoutes;
import com.example.hike_with_me_client.Interfaces.Route.Callbacks.Callback_GetRoutesNames;

import java.util.ArrayList;
import java.util.List;

public class RouteMethods {

    public static void getAllRoutes(ArrayList<Route> _routes) {
        Callback_GetAllRoutes callback_getAllRoutes = new Callback_GetAllRoutes() {
            @Override
            public void success(List<Route> routes) {
                _routes.clear();
                _routes.addAll(routes);
            }

            @Override
            public void error(String message) {
                Log.d("RouteMethods", "Error: " + message);
            }
        };
        new GetAllRoutes(callback_getAllRoutes).getAllRoutes();
    }

    @SuppressLint("SetTextI18n")
    public static void getSpecificRoute(String routeName, TextView textView) {
        Callback_GetRoutesNames callback_getRoute = new Callback_GetRoutesNames() {

            @Override
            public void success(List<String> routesNames) {
                textView.setText("Routes Names: " + routesNames);
            }

            @Override
            public void error(String message) {
                Log.d("RouteMethods", "Error: " + message);
                textView.setText("Error: " + message);
            }
        };
        new GetRoute(callback_getRoute).getRoutesNames();
    }
}
