package com.example.hike_with_me_client.Hazard;

import com.example.hike_with_me_client.MasterClass;

public class HazardMasterClass extends MasterClass {

    public HazardApiInterface hazardApiInterface = MasterClass.retrofit.create(HazardApiInterface.class);
}
