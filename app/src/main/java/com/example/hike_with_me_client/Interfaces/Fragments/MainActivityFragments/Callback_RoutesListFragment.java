package com.example.hike_with_me_client.Interfaces.Fragments.MainActivityFragments;

import com.example.hike_with_me_client.Models.Route.Route;

public interface Callback_RoutesListFragment {
    void sendLocation(double latitude, double longitude);
    void goToRoutePage(Route route);
}
