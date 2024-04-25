package com.example.hike_with_me_client.Route.Callbacks;

import com.example.hike_with_me_client.Route.Route;

public interface Callback_AddRoute {
    void success(Route route);
    void error(String message);
}
