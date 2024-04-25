package com.example.hike_with_me_client.Trip;

import com.example.hike_with_me_client.MasterClass;

public class TripMasterClass extends MasterClass {

    public TripApiInterface tripApiInterface = MasterClass.retrofit.create(TripApiInterface.class);
}
