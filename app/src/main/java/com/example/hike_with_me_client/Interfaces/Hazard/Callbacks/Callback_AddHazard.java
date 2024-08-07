package com.example.hike_with_me_client.Interfaces.Hazard.Callbacks;

import com.example.hike_with_me_client.Models.Hazard.Hazard;

public interface Callback_AddHazard {
    void success(Hazard hazard);

    void error(String error);
}
