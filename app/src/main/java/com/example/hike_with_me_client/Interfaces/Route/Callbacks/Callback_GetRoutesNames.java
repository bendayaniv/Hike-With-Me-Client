package com.example.hike_with_me_client.Interfaces.Route.Callbacks;

import com.example.hike_with_me_client.Models.Route.Route;

import java.util.List;

public interface Callback_GetRoutesNames {
    void success(List<String> routesNames);
    void error(String error);
}