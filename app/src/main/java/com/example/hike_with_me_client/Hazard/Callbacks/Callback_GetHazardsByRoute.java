package com.example.hike_with_me_client.Hazard.Callbacks;

import com.example.hike_with_me_client.Hazard.Hazard;

import java.util.List;

public interface Callback_GetHazardsByRoute {
    void success(List<Hazard> hazards);
    void error(String error);
}
