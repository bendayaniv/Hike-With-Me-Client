package com.example.hike_with_me_client.Models.Route;

import android.util.Log;
import android.widget.TextView;

import com.example.hike_with_me_client.Models.Route.Actions.GetAllRoutes;
import com.example.hike_with_me_client.Models.Route.Actions.GetRoute;
import com.example.hike_with_me_client.Interfaces.Route.Callbacks.Callback_GetAllRoutes;
import com.example.hike_with_me_client.Interfaces.Route.Callbacks.Callback_GetRoute;

import java.util.ArrayList;
import java.util.List;

public class RouteMethods {

    public static void getAllRoutes(ArrayList<Route> _routes) {
        Callback_GetAllRoutes callback_getAllRoutes = new Callback_GetAllRoutes() {
            @Override
            public void success(List<Route> routes) {
                _routes.addAll(routes);
            }

            @Override
            public void error(String message) {
                Log.d("routes", "Error: " + message);
            }
        };
        new GetAllRoutes(callback_getAllRoutes).getAllRoutes();
    }

    public static void getSpecificRoute(String routeName, TextView textView) {
        Callback_GetRoute callback_getRoute = new Callback_GetRoute() {
            @Override
            public void success(Route route) {
                textView.setText("Route found: " + route);
            }

            @Override
            public void error(String message) {
                Log.d("pttt", "Error: " + message);
                textView.setText("Error: " + message);
            }
        };
        new GetRoute(callback_getRoute).getRoute(routeName);
    }
}
