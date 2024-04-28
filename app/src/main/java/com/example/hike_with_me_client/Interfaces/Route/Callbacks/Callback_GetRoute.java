package com.example.hike_with_me_client.Interfaces.Route.Callbacks;

import com.example.hike_with_me_client.Models.Route.Route;

public interface Callback_GetRoute {
    void success(Route route);
    void error(String error);
}
