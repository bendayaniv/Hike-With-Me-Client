package com.example.hike_with_me_client.Models.Trip;

import com.example.hike_with_me_client.Interfaces.Trip.TripApiInterface;
import com.example.hike_with_me_client.Utils.Rertofit.MasterClass;

public class TripMasterClass extends MasterClass {

    public TripApiInterface tripApiInterface = MasterClass.retrofit.create(TripApiInterface.class);
}
