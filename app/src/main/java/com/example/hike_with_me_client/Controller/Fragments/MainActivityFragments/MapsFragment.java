package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hike_with_me_client.Models.Objects.CurrentUser;
import com.example.hike_with_me_client.Models.Route.Route;
import com.example.hike_with_me_client.Models.Route.RouteMethods;
import com.example.hike_with_me_client.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    static GoogleMap mMap;
    ArrayList<Route> routes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        routes = new ArrayList<>();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    public static void zoom(double _latitude, double _longitude) {
        LatLng randomPlace = new LatLng(_latitude, _longitude);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(randomPlace)
                .zoom(17)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(randomPlace));
    }

    public void refreshMap() {
        mMap.clear();
        for (Route route : routes) {
            LatLng routeLocation = new LatLng(route.getLocation().getLatitude(), route.getLocation().getLongitude());
            mMap.addMarker(new MarkerOptions().position(routeLocation).title(route.getName()));
        }
        zoom(CurrentUser.getInstance().getLocation().getLatitude(), CurrentUser.getInstance().getLocation().getLongitude());
        mMap.addMarker(new MarkerOptions().position(
                new LatLng(CurrentUser.getInstance().getLocation().getLatitude(), CurrentUser.getInstance().getLocation().getLongitude())
        ).title("My Location"));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        RouteMethods.getAllRoutes(routes);
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        if(routes != null && routes.size() > 0) {
                            refreshMap();
                        }

                        initiateMap();
                    }
                },
                5000);
    }

    private void initiateMap() {
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
    }
}