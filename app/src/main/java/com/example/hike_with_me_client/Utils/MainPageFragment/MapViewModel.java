package com.example.hike_with_me_client.Utils.MainPageFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.CameraPosition;

public class MapViewModel extends ViewModel {
    private final MutableLiveData<CameraPosition> cameraPosition = new MutableLiveData<>();

    public LiveData<CameraPosition> getCameraPosition() {
        return cameraPosition;
    }

    public void setCameraPosition(CameraPosition position) {
        cameraPosition.setValue(position);
    }

}
