package com.example.hike_with_me_client.Interfaces.Trip.Callbacks;

import com.example.hike_with_me_client.Models.Trip.Trip;

import java.util.List;

public interface Callback_GetTripsByUser {
    void success(List<Trip> trips);
    void error(String error);
}
