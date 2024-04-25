package com.example.hike_with_me_client.Trip.Callbacks;

import com.example.hike_with_me_client.Trip.Trip;

public interface Callback_CreateTrip {
    void success(Trip trip);
    void error(String error);
}
