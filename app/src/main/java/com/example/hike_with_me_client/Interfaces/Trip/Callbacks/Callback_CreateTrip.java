package com.example.hike_with_me_client.Interfaces.Trip.Callbacks;

import com.example.hike_with_me_client.Models.Trip.Trip;

public interface Callback_CreateTrip {
    void success(Trip trip);
    void error(String error);
}
