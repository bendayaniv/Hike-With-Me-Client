package com.example.hike_with_me_client.Utils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.CameraPosition;

public class MapViewModel extends ViewModel {
    private MutableLiveData<CameraPosition> cameraPosition = new MutableLiveData<>();

    public LiveData<CameraPosition> getCameraPosition() {
        return cameraPosition;
    }

    public void setCameraPosition(CameraPosition position) {
        cameraPosition.setValue(position);
    }

    public void firstTime(boolean condition) {
        if (condition) {
            cameraPosition.setValue(null);
        }
    }
}
