package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments.MainPage;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hike_with_me_client.Models.Objects.CurrentUser;
import com.example.hike_with_me_client.Models.Objects.ObjectLocation;
import com.example.hike_with_me_client.Models.Route.Route;
import com.example.hike_with_me_client.R;
import com.example.hike_with_me_client.Utils.ListOfRoutes;
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
    ArrayList<Route> routes;
    Context context;
    ArrayList<Marker> list = new ArrayList<>();

    public void setContext(Context context) {
        this.context = context;
    }

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

    public void zoom(double _latitude, double _longitude) {
        LatLng randomPlace = new LatLng(_latitude, _longitude);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(randomPlace)
                .zoom(10)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void refreshMap() {
        mMap.clear();
        list.clear();
        for (Route route : routes) {
            LatLng routeLocation = new LatLng(route.getLocation().getLatitude(), route.getLocation().getLongitude());

            MarkerOptions marker = new MarkerOptions()
                    .position(routeLocation)
                    .title(route.getName())
                    .icon(bitmapDescriptorFromVector(context, R.drawable.hazard_sign));
            list.add(mMap.addMarker(marker));

            mMap.setOnCameraMoveListener(() -> {
                for (Marker m : list) {
                    // TODO - handling with the type of the marker (route or hazard)
                    m.setVisible(mMap.getCameraPosition().zoom > 8);
                }
            });
        }

        if (SavedLastClick.getInstance().getLastClickedRoute() == null) {
            ObjectLocation location = CurrentUser.getInstance().getLocation();
            zoom(location.getLatitude(), location.getLongitude());
        }
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
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                () -> {
                    routes = ListOfRoutes.getInstance().getRoutes();
                    if (routes != null && !routes.isEmpty()) {
                        refreshMap();
                    }

                    initiateMap();
                },
                10000);
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}