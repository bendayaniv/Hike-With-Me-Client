package com.example.hike_with_me_client.Interfaces.Trip.Callbacks;

import com.example.hike_with_me_client.Models.Trip.trip;

public interface Callback_UpdateTrip {
    void success(trip trip);
    void error(String error);
}
