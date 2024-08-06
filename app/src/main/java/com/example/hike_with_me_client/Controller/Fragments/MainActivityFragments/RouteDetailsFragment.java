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
import android.widget.Toast;

import com.example.hike_with_me_client.Models.Route.Route;
import com.example.hike_with_me_client.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RouteDetailsFragment extends Fragment {

    private static final String ARG_ROUTE_ID = "route_id";
    private Route route;
    private TextView routeNameTextView;
    private TextView routeDescriptionTextView;
    private TextView routeDifficultyTextView;
    private TextView routeLengthTextView;
    private Button backRouteButton;
    FragmentManager fragmentManager;
    FloatingActionButton fab;

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
        fab = view.findViewById(R.id.fab);
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
        fab.setOnClickListener(v -> showBottomSheetDialog());
    }

    private void showBottomSheetDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        TextView optionRecommendation = bottomSheetView.findViewById(R.id.option_recommendation);
        TextView optionHazard = bottomSheetView.findViewById(R.id.option_hazard);

        optionRecommendation.setOnClickListener(v -> {
            // Handle recommendation option
            Toast.makeText(getContext(), "Recommendation", Toast.LENGTH_SHORT).show();
            //bottomSheetDialog.dismiss();
        });

        optionHazard.setOnClickListener(v -> {
            // Handle hazard option
            Toast.makeText(getContext(), "Hazard", Toast.LENGTH_SHORT).show();
            //bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
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
