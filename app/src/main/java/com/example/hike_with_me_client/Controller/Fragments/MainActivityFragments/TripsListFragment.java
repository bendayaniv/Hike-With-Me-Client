package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hike_with_me_client.Models.Objects.CurrentUser;
import com.example.hike_with_me_client.Models.Trip.Trip;
import com.example.hike_with_me_client.Models.Trip.TripMethods;
import com.example.hike_with_me_client.R;

import java.util.ArrayList;

public class TripsListFragment extends Fragment {

    ArrayList<Trip> trips;
    private TripFragment tripFragment;
    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trips_list, container, false);

        findViews(view);

        initializing();

        return view;
    }

    private void initializing() {
        tripFragment = new TripFragment();
        // TODO - get all the trips of the user
        TripMethods.getTripsByUser(trips, CurrentUser.getInstance().getUser().getId());
        if(trips != null && !trips.isEmpty()) {
            initTripRV();
        } else {
            // TODO - show message that there are no trips
        }
    }

    private void initTripRV() {
        initAndScroll();

        setCallbackTripItemForAdapter();
    }

    private void setCallbackTripItemForAdapter() {

    }

    private void initAndScroll() {

    }

    private void findViews(View view) {
        tripFragment = new TripFragment();
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
}