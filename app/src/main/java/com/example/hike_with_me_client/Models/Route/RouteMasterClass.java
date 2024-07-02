package com.example.hike_with_me_client.Models.Route;

import com.example.hike_with_me_client.Interfaces.Route.RouteApiInterface;
import com.example.hike_with_me_client.Utils.Rertofit.MasterClass;

public class RouteMasterClass extends MasterClass {

    public RouteApiInterface routeApiInterface = MasterClass.retrofit.create(RouteApiInterface.class);
}
