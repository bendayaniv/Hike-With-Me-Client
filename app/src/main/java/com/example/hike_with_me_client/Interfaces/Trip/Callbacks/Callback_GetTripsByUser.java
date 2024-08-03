package com.example.hike_with_me_client.Interfaces.Trip.Callbacks;

import com.example.hike_with_me_client.Models.Trip.trip;

import java.util.List;

public interface Callback_GetTripsByUser {
    void success(List<trip> trips);
    void error(String error);
}
