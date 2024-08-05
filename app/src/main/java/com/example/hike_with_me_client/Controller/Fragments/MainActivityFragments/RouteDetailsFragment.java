package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hike_with_me_client.Models.Route.Route;
import com.example.hike_with_me_client.R;

public class RouteDetailsFragment extends Fragment {

    private static final String ARG_ROUTE_ID = "route_id";
    private Route route;
    private TextView routeNameTextView;
    private TextView routeDescriptionTextView;
    private TextView routeDifficultyTextView;
    private TextView routeLengthTextView;
    private Button backRouteButton;
    FragmentManager fragmentManager;

    public static RouteDetailsFragment newInstance(String routeId) {
        RouteDetailsFragment fragment = new RouteDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getParentFragmentManager();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_details, container, false);
        findViews(view);
        loadRouteDetails();
        setupButtons();
        return view;
    }

    private void findViews(View view) {
        routeNameTextView = view.findViewById(R.id.route_name_text_view);
        routeDescriptionTextView = view.findViewById(R.id.route_description_text_view);
        routeDifficultyTextView = view.findViewById(R.id.route_difficulty_text_view);
        routeLengthTextView = view.findViewById(R.id.route_length_text_view);
        backRouteButton = view.findViewById(R.id.back_route_button);
    }

    private void loadRouteDetails() {
        Log.d("route details", route.toString());
        if (route != null) {
                routeNameTextView.setText(route.getName());
                routeDescriptionTextView.setText(route.getDescription());
                routeLengthTextView.setText(String.valueOf(route.getLength()));
                routeDifficultyTextView.setText(route.getDifficultyLevel());
        }
    }

    private void setupButtons() {
        backRouteButton.setOnClickListener(v -> navigateBack());
    }


    private void navigateBack() {
        FragmentManager fragmentManager = getParentFragmentManager();
        if (fragmentManager != null) {
            fragmentManager.popBackStack(); // Navigate back to the previous fragment
        }
    }
    public void setRoute(Route route) {
        this.route = route;
    }
}
