package com.example.hike_with_me_client.Route;

import com.example.hike_with_me_client.Utils.MasterClass;

public class RouteMasterClass extends MasterClass {

    public RouteApiInterface routeApiInterface = MasterClass.retrofit.create(RouteApiInterface.class);
}
