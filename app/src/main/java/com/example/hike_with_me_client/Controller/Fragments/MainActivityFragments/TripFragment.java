package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hike_with_me_client.Interfaces.Fragments.MainActivityFragments.Callback_RouteItem;
import com.example.hike_with_me_client.Models.Objects.Location;
import com.example.hike_with_me_client.Models.Route.Route;
import com.example.hike_with_me_client.Models.Trip.trip;
import com.example.hike_with_me_client.R;
import com.example.hike_with_me_client.Utils.Constants;
import com.example.hike_with_me_client.Utils.GlobalUtilInstances.ErrorMessageFromServer;
import com.example.hike_with_me_client.Utils.GlobalUtilInstances.ListOfRoutes;
import com.example.hike_with_me_client.Utils.GlobalUtilInstances.ListOfTrips;
import com.example.hike_with_me_client.Adapters.RouteItemAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.StrokeStyle;
import com.google.android.gms.maps.model.StyleSpan;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

public class TripFragment extends Fragment implements OnMapReadyCallback {

    private TripFragment tripFragment;
    private static final String ARG_TRIP_ID = "trip_id";
    private trip trip;
    private TextView tripNameText;
    private TextView tripDescriptionText;
    private TextView tripStartDateText;
    private TextView tripEndDateText;
    private RecyclerView recyclerViewRoutes;
    private ImageView tripImageView;
    private Handler handler = new Handler();
    private Runnable retryRunnable;

    private RouteDetailsFragment routeDetailsFragment;
    private MapView mapView;
    private GoogleMap googleMap;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static TripFragment newInstance(String tripId) {
        TripFragment fragment = new TripFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TRIP_ID, tripId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip, container, false);
        findViews(view);
        loadTripFromServer();
        // Initialize the MapView
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        return view;
    }

    private void findViews(View view) {
        tripNameText = view.findViewById(R.id.text_name_trip);
        tripDescriptionText = view.findViewById(R.id.text_description_trip);
        tripStartDateText = view.findViewById(R.id.text_start_date_trip);
        tripEndDateText = view.findViewById(R.id.text_end_date_trip);
        recyclerViewRoutes = view.findViewById(R.id.recycler_view_routes);
        tripImageView = view.findViewById(R.id.tripImageView);
        mapView = view.findViewById(R.id.mapView);
    }

    private void loadTripFromServer() {
        retryRunnable = new Runnable() {
            @Override
            public void run() {
                String tripId = getArguments().getString(ARG_TRIP_ID);
                ArrayList<trip> trips = ListOfTrips.getInstance().getTrips();

                if (trips != null) {
                    for (trip t : trips) {
                        if (t.getId().equals(tripId)) {
                            trip = t;
                            break;
                        }
                    }
                }

                if (trip != null) {
                    // Update UI with the loaded trip details
                    tripNameText.setText(trip.getName());
                    tripDescriptionText.setText(trip.getDescription());
                    tripStartDateText.setText(trip.getStartDate());
                    tripEndDateText.setText(trip.getEndDate());
                    if (trip.getImagesUrls() != null && !trip.getImagesUrls().isEmpty()) {
                        // Load the first image into the ImageView
                        Glide.with(getContext()).load(trip.getImagesUrls().get(0)).into(tripImageView);
                        tripImageView.setVisibility(View.VISIBLE);
                    } else {
                        // Hide the ImageView if there are no images
                        tripImageView.setVisibility(View.GONE);
                    }

                    // Fetch routes by names
                    ArrayList<Route> routes = getRoutesByNames(trip.getRoutesNames());
                    if (routes.isEmpty()) {
                        // Handle the case where no routes are found
                        Log.d("TripFragment", "No routes found for trip.");
                    } else {
                        // Set up routes RecyclerView
                        RouteItemAdapter routeAdapter = new RouteItemAdapter(getContext(), routes);
                        routeAdapter.setCallbackRouteItem(new Callback_RouteItem() {
                            @Override
                            public void itemClicked(Route route, int position) {
                                routeDetailsFragment = new RouteDetailsFragment();
                                routeDetailsFragment.setRoute(route);
                                getFragmentManager().beginTransaction()
                                        .replace(R.id.main_fragment_container, routeDetailsFragment) // Replace with your actual container ID
                                        .addToBackStack(null)
                                        .commit();
                            }
                        });
                        recyclerViewRoutes.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerViewRoutes.setAdapter(routeAdapter);
                    }

                    // Load locations on the map
                    if (googleMap != null) {
                        loadLocationsOnMap();
                    }

                } else if (ErrorMessageFromServer.getInstance().getErrorMessageFromServer() != null &&
                        !ErrorMessageFromServer.getInstance().getErrorMessageFromServer().isEmpty()) {
                    // Display error message if available
                    tripNameText.setText(R.string.trip_name);
                    tripDescriptionText.setText(R.string.trip_description);
                    tripStartDateText.setText(R.string.trip_start_date);
                    tripEndDateText.setText(R.string.trip_end_date);
                } else {
                    // Retry fetching trips if no data and no error message
                    handler.postDelayed(retryRunnable, Constants.RETRY_INTERVAL);
                }
            }
        };

        handler.post(retryRunnable);
    }

    private ArrayList<Route> getRoutesByNames(String[] routeNames) {
        ArrayList<Route> routes = new ArrayList<>();
        List<Route> listRoute = ListOfRoutes.getInstance().getRoutes();
        for (int i = 0; i < routeNames.length; i++) {
            Log.d("Route found", routeNames[i]);
            for (Route route : listRoute) {
                Log.d("Route found1", route.getName());
                if (routeNames[i].equals(route.getName())) {
                    Route r = new Route(route.getLocation(), route.getType(), route.getId(), route.getName(), route.getDescription(), route.getDifficultyLevel(), route.getLength(), route.getImageUrl());
                    routes.add(r);
                }
            }
        }
        return routes;
    }

    public void setTrip(com.example.hike_with_me_client.Models.Trip.trip trip) {
        this.trip = trip;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setAllGesturesEnabled(true);
        // Load locations on the map
        loadLocationsOnMap();
    }

    private void loadLocationsOnMap() {
        if (trip == null || googleMap == null) return;
        googleMap.clear(); // Clear existing markers
        final LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        LatLng previousLatLng = null;
        for (Location location : trip.getLocations()) {
            LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(currentLatLng).title(trip.getName()));
            boundsBuilder.include(currentLatLng);

            // Draw a line from the previous location to the current one
            if (previousLatLng != null) {
                drawLine(previousLatLng, currentLatLng);
            }
            previousLatLng = currentLatLng;
        }


        // Use ViewTreeObserver to wait until the map view layout is complete
        mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                LatLngBounds bounds = boundsBuilder.build();
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
            }
        });
    }

    public void drawLine(LatLng start, LatLng end) {
        googleMap.addPolyline(new PolylineOptions()
                .add(start, end)
                .addSpan(new StyleSpan(StrokeStyle.colorBuilder(Color.BLACK).build())));
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


}