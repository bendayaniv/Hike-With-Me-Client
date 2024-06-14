package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments.MainPage;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hike_with_me_client.Models.Hazard.Hazard;
import com.example.hike_with_me_client.Models.Objects.CurrentUser;
import com.example.hike_with_me_client.Models.Objects.Location;
import com.example.hike_with_me_client.Models.Route.Route;
import com.example.hike_with_me_client.R;
import com.example.hike_with_me_client.Utils.ListOfHazards;
import com.example.hike_with_me_client.Utils.ListOfRoutes;
import com.example.hike_with_me_client.Utils.MapViewModel;
import com.example.hike_with_me_client.Utils.SavedLastClick;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.StrokeStyle;
import com.google.android.gms.maps.model.StyleSpan;

import java.util.ArrayList;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mMap;
    private MapViewModel mapViewModel;
    ArrayList<Route> routes;
    Context context;
    ArrayList<Marker> list = new ArrayList<>();
    ArrayList<Hazard> hazardsList;

    public void setContext(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        routes = new ArrayList<Route>();

        hazardsList = new ArrayList<Hazard>();

        mapViewModel = new ViewModelProvider(requireActivity()).get(MapViewModel.class);

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

    public void zoom(double _latitude, double _longitude) {
        LatLng randomPlace = new LatLng(_latitude, _longitude);
        Log.d("MyMapFragment", "zoom: " + randomPlace.toString());
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(randomPlace)
                .zoom(10)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void refreshMap(boolean exist) {
        mMap.clear();
        list.clear();
        for (Route route : routes) {
            LatLng routeLocation = new LatLng(route.getLocation().getLatitude(), route.getLocation().getLongitude());

            MarkerOptions marker = new MarkerOptions()
                    .position(routeLocation)
                    .title(route.getName());
            list.add(mMap.addMarker(marker));
        }

        for (Hazard hazard : hazardsList) {
            if (routes.stream().noneMatch(route -> route.getName().equals(hazard.getRouteName()))) {
                continue;
            }

            LatLng hazardLocation = new LatLng(hazard.getLocation().getLatitude(), hazard.getLocation().getLongitude());
            MarkerOptions marker = new MarkerOptions()
                    .position(hazardLocation)
                    .icon(bitmapDescriptorFromVector(context, R.drawable.hazard_sign));
            list.add(mMap.addMarker(marker));
        }

        mMap.setOnCameraMoveListener(() -> {
            for (Marker m : list) {
                if (m.getTitle() == null) {
                    m.setVisible(mMap.getCameraPosition().zoom > 14);
                }
//                else {
//                    m.setVisible(mMap.getCameraPosition().zoom > 5);
//                }
            }
        });

        mapViewModel.getCameraPosition().observe(getViewLifecycleOwner(), position -> {
            Log.d("MyMapFragment", "onViewCreated");
            if (position != null) {
                Log.d("MyMapFragment", "position is not null");
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
            }
        });

        if (!exist) {
            Location location = getLocation();
            zoom(location.getLatitude(), location.getLongitude());
            ListOfRoutes.getInstance().setFirstTime(false);
        }
    }

    private static Location getLocation() {
        Location location;

        if (SavedLastClick.getInstance().getLastClickedRoute() == null) {
            location = CurrentUser.getInstance().getUser().getLocation();
        } else {
            location = new Location(SavedLastClick.getInstance().getLastClickedRoute().getPoint().getLocation().getLatitude(),
                    SavedLastClick.getInstance().getLastClickedRoute().getPoint().getLocation().getLongitude(), null);
        }
        return location;
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        assert vectorDrawable != null;
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    // The use of this is for draw a line on the user's trip map to see the route he took
    public void drawLine(LatLng start, LatLng end) {
        mMap.addPolyline(new PolylineOptions()
                .add(start, end)
                .addSpan(new StyleSpan(StrokeStyle.colorBuilder(Color.BLACK).build())));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        initiateMap();
        if (!ListOfRoutes.getInstance().isFirstTime()) {
            routes = ListOfRoutes.getInstance().getRoutes();

            ListOfHazards.getInstance().setHazards();
            hazardsList = ListOfHazards.getInstance().getHazards();

            Log.d("MyMapFragment", "onMapReady1: " + routes.size());
            refreshMap(true);
        } else {
            new android.os.Handler(Looper.getMainLooper()).postDelayed(
                    () -> {
                        routes = ListOfRoutes.getInstance().getRoutes();

                        hazardsList = ListOfHazards.getInstance().getHazards();

                        if (routes != null && !routes.isEmpty()) {
                            Log.d("MyMapFragment", "onMapReady2: " + routes.size());
                            refreshMap(false);
                        }
                    },
                    10000);
        }
    }

    private void initiateMap() {
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        saveMapState();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public void saveMapState() {
        if (mMap != null) {
            CameraPosition position = mMap.getCameraPosition();
            mapViewModel.setCameraPosition(position);
        }
    }
}