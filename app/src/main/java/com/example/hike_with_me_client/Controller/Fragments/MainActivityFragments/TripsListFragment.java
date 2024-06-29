package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hike_with_me_client.Adapters.TripItemAdapter;
import com.example.hike_with_me_client.Interfaces.Fragments.MainActivityFragments.Callback_TripItem;
import com.example.hike_with_me_client.Models.Objects.CurrentUser;
import com.example.hike_with_me_client.Models.Trip.Trip;
import com.example.hike_with_me_client.Models.Trip.TripMethods;
import com.example.hike_with_me_client.R;
import com.example.hike_with_me_client.Utils.Constants;
import com.example.hike_with_me_client.Utils.File;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class TripsListFragment extends Fragment {

    ArrayList<Trip> trips;
    private RecyclerView fragmentTripsRV;
    private MaterialTextView emptyTripsListTV;
    private TripItemAdapter tripItemAdapter;
    private ProgressBar progressBarTripsList;
    private FloatingActionButton addTripFab;
    private TripFragment tripFragment;
    private FragmentManager fragmentManager;

    private Handler handler;
    private Runnable retryRunnable;

    Callback_TripItem callback_tripItem = (trip, position) -> {
        CurrentUser.getInstance().setUrlsImages(null);
        TripMethods.getTripImages(CurrentUser.getInstance().getUser().getName(), trip.getName());
        loadTripsImagesFromServer(trip);
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trips_list, container, false);

        findViews(view);

        initializing();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initializing() {
        tripFragment = new TripFragment();

        TripMethods.getTripsByUser();

        handler = new Handler(Looper.getMainLooper());

        init();
    }

    private void init() {
        fragmentTripsRV.setVisibility(View.GONE);
        emptyTripsListTV.setVisibility(View.GONE);
        progressBarTripsList.setVisibility(View.VISIBLE);
        addTripFab.setOnClickListener(v -> {
            // TODO - move to create trip fragment
            Toast.makeText(getContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
        });
        addTripFab.setVisibility(View.GONE);

        loadTripsFromServer();
    }

    private void loadTripsFromServer() {
        retryRunnable = new Runnable() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void run() {
                ArrayList<Trip> loadedTrips = CurrentUser.getInstance().getTrips();

                if (loadedTrips != null && !loadedTrips.isEmpty()) {
                    trips.clear();
                    trips.addAll(loadedTrips);

                    emptyTripsListTV.setVisibility(View.GONE);
                    fragmentTripsRV.setVisibility(View.VISIBLE);
                    progressBarTripsList.setVisibility(View.GONE);
                    addTripFab.setVisibility(View.VISIBLE);
                    tripItemAdapter.notifyDataSetChanged();
                } else if (CurrentUser.getInstance().getErrorMessageFromServer() != null &&
                        !CurrentUser.getInstance().getErrorMessageFromServer().isEmpty()) {
                    emptyTripsListTV.setVisibility(View.VISIBLE);
                    emptyTripsListTV.setText(CurrentUser.getInstance().getErrorMessageFromServer());
                    fragmentTripsRV.setVisibility(View.GONE);
                    progressBarTripsList.setVisibility(View.GONE);
                    addTripFab.setVisibility(View.VISIBLE);
                } else {
                    handler.postDelayed(retryRunnable, Constants.RETRY_INTERVAL);
                }
            }
        };

        handler.post(retryRunnable);
    }

    private void loadTripsImagesFromServer(Trip trip) {
        retryRunnable = new Runnable() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void run() {
                ArrayList<File> urlsImages = CurrentUser.getInstance().getUrlsImages();

                if (urlsImages != null && !urlsImages.isEmpty()) {
                    trip.setImages(urlsImages);
                    tripItemAdapter.notifyDataSetChanged();

                    tripFragment.setTrip(trip);
                    fragmentManager.beginTransaction().replace(R.id.main_fragment_container, tripFragment).commit();

                } else if (CurrentUser.getInstance().getErrorMessageFromServer() != null &&
                        CurrentUser.getInstance().getErrorMessageFromServer().contains("image")) {
                    tripFragment.setTrip(trip);
                    fragmentManager.beginTransaction().replace(R.id.main_fragment_container, tripFragment).commit();
                } else {
                    handler.postDelayed(retryRunnable, Constants.RETRY_INTERVAL);
                }
            }
        };

        handler.post(retryRunnable);
    }

    private void findViews(View view) {
        tripFragment = new TripFragment();

        trips = new ArrayList<>();

        tripItemAdapter = new TripItemAdapter(trips);
        tripItemAdapter.setCallbackTripItem(callback_tripItem);

        fragmentTripsRV = view.findViewById(R.id.fragmentTripsRV);
        fragmentTripsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentTripsRV.setAdapter(tripItemAdapter);

        emptyTripsListTV = view.findViewById(R.id.emptyTripsListTV);
        progressBarTripsList = view.findViewById(R.id.progressBarTripsList);
        addTripFab = view.findViewById(R.id.addTripFab);
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        CurrentUser.getInstance().setTrips(null);
        CurrentUser.getInstance().setErrorMessageFromServer(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        CurrentUser.getInstance().setTrips(null);
        CurrentUser.getInstance().setErrorMessageFromServer(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CurrentUser.getInstance().setTrips(null);
        CurrentUser.getInstance().setErrorMessageFromServer(null);
    }

    @Override
    public void onStop() {
        super.onStop();
        CurrentUser.getInstance().setTrips(null);
        CurrentUser.getInstance().setErrorMessageFromServer(null);
    }
}