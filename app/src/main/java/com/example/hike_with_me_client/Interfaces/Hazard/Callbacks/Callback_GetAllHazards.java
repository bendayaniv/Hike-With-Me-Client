package com.example.hike_with_me_client.Interfaces.Hazard.Callbacks;

import com.example.hike_with_me_client.Models.Hazard.Hazard;

import java.util.List;

public interface Callback_GetAllHazards {
    void success(List<Hazard> hazards);

    void error(String error);
}
