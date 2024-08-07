package com.example.hike_with_me_client.Interfaces.Route.Callbacks;

import com.example.hike_with_me_client.Models.Route.Route;

import java.util.List;

public interface Callback_GetAllRoutes {
    void success(List<Route> routes);

    void error(String error);
}
