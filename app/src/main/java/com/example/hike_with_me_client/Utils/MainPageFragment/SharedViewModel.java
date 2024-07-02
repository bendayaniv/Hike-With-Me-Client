package com.example.hike_with_me_client.Utils.MainPageFragment;

import android.os.Parcelable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<Parcelable> recyclerViewState = new MutableLiveData<>();

    public LiveData<Parcelable> getRecyclerViewState() {
        return recyclerViewState;
    }

    public void setRecyclerViewState(Parcelable state) {
        recyclerViewState.setValue(state);
    }
}