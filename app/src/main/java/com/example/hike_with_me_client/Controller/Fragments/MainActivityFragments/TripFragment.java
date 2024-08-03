package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hike_with_me_client.R;

public class TripFragment extends Fragment {

    private com.example.hike_with_me_client.Models.Trip.trip trip;
    private TextView tripNameText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip, container, false);

        findViews(view);

        initViews(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initViews(View view) {
        if(trip != null) {
            createTripDetails(view);
        }
    }

    private void createTripDetails(View view) {
        tripNameText.setText(trip.getName());
    }

    private void findViews(View view) {
        tripNameText = view.findViewById(R.id.text_name_trip);
        tripNameText.setOnClickListener(v -> {
            Log.d("TripFragment", "tripNameText: " + trip);
        });
    }

    public void setTrip(com.example.hike_with_me_client.Models.Trip.trip trip) {
        this.trip = trip;
    }
}