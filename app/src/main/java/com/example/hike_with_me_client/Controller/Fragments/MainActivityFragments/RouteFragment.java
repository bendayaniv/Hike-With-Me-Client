package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hike_with_me_client.Models.Route.Route;
import com.example.hike_with_me_client.R;

public class RouteFragment extends Fragment {

    private Route route;
    private TextView routeNameText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_route, container, false);
        findViews(view);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initViews(View view) {
        if(route != null) {
            createRouteDetails(view);
        }
    }

    private void createRouteDetails(View view) {
        routeNameText.setText(route.getName());
    }

    private void findViews(View view) {
        routeNameText = view.findViewById(R.id.route_fragment_text_view);
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}