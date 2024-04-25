package com.example.hike_with_me_client.Route.Callbacks;

import com.example.hike_with_me_client.Route.Route;

import java.util.List;

public interface Callback_GetAllRoutes {
    void success(List<Route> routes);
    void error(String error);
}
