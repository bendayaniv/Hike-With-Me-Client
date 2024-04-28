package com.example.hike_with_me_client.Models.Hazard;

import com.example.hike_with_me_client.Interfaces.Hazard.HazardApiInterface;
import com.example.hike_with_me_client.Utils.MasterClass;

public class HazardMasterClass extends MasterClass {

    public HazardApiInterface hazardApiInterface = MasterClass.retrofit.create(HazardApiInterface.class);
}
