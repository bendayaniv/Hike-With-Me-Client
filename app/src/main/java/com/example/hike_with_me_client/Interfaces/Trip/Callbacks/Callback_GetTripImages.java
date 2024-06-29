package com.example.hike_with_me_client.Interfaces.Trip.Callbacks;

import com.example.hike_with_me_client.Models.Trip.Trip;

import java.io.File;
import java.util.ArrayList;

public interface Callback_GetTripImages {
    void success(ArrayList<File> images);
    void error(String error);
}
