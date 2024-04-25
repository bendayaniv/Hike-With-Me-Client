package com.example.hike_with_me_client.Hazard.Actions;

import com.example.hike_with_me_client.Hazard.Hazard;
import com.example.hike_with_me_client.Hazard.HazardMasterClass;
import com.example.hike_with_me_client.Hazard.Callbacks.Callback_AddHazard;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddHazard extends HazardMasterClass {
    private final Callback_AddHazard callBack_addHazard;

    public AddHazard(Callback_AddHazard callbackAddHazard) {
        this.callBack_addHazard = callbackAddHazard;
    }

    public void addHazard(Hazard hazard) {
        Call<Hazard> call = hazardApiInterface.addHazard(hazard);

        call.enqueue(new Callback<Hazard>() {
            @Override
            public void onResponse(Call<Hazard> call, Response<Hazard> response) {
                if(response.isSuccessful()) {
                    Hazard hazard = response.body();
                    callBack_addHazard.success(hazard);
                } else {
                    callBack_addHazard.error("" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Hazard> call, Throwable t) {
                callBack_addHazard.error(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
