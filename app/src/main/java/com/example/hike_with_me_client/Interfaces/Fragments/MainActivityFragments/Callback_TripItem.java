package com.example.hike_with_me_client.Interfaces.Fragments.MainActivityFragments;

import com.example.hike_with_me_client.Models.Trip.Trip;

public interface Callback_TripItem {
    void itemClicked(Trip trip, int position);
}
