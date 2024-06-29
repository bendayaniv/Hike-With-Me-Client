package com.example.hike_with_me_client.Interfaces.Trip.Callbacks;

import com.example.hike_with_me_client.Utils.File;

import java.util.List;

public interface Callback_GetTripImages {
    void success(List<File> images);
    void error(String error);
}
