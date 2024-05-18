package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hike_with_me_client.Models.Route.Route;
import com.example.hike_with_me_client.R;

public class RouteFragment extends Fragment {

    private Route route;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_route, container, false);
    }

    public void setRoute(Route route) {
        this.route = route;
        Log.d("RouteFragment", "setRoute: " + route.toString());
    }
}