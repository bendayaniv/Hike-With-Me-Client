package com.example.hike_with_me_client.Interfaces.Fragments.MainActivityFragments;

import com.example.hike_with_me_client.Models.Trip.Trip;

import java.io.File;
import java.util.ArrayList;

public interface Callback_TripItem {
    void itemClicked(Trip trip, ArrayList<File> images, int position);
}
