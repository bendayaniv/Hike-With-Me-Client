package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hike_with_me_client.Models.Route.Route;
import com.example.hike_with_me_client.R;

public class RouteDetailsFragment extends Fragment {

    private static final String ARG_ROUTE_ID = "route_id";
    private Route route;
    private TextView routeNameTextView;
    private EditText routeDescriptionEditText;
    private Spinner routeDifficultySpinner;
    private EditText routeLengthEditText;

    public static RouteDetailsFragment newInstance(String routeId) {
        RouteDetailsFragment fragment = new RouteDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ROUTE_ID, routeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_details, container, false);
        findViews(view);
        setupSpinner();
        loadRouteDetails();
        return view;
    }

    private void findViews(View view) {
        routeNameTextView = view.findViewById(R.id.route_name_text_view);
        routeDescriptionEditText = view.findViewById(R.id.route_description_edit_text);
        routeDifficultySpinner = view.findViewById(R.id.route_difficulty_spinner);
        routeLengthEditText = view.findViewById(R.id.route_length_edit_text);
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.difficulty_levels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        routeDifficultySpinner.setAdapter(adapter);
    }

    private void loadRouteDetails() {
        if (getArguments() != null) {
            String routeId = getArguments().getString(ARG_ROUTE_ID);
            // Fetch route details using routeId

            if (route != null) {
                routeNameTextView.setText(route.getName());
                routeDescriptionEditText.setText(route.getDescription());

                // Set the default value of the spinner
                String difficulty = route.getDifficultyLevel();
                ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) routeDifficultySpinner.getAdapter();
                int position = adapter.getPosition(difficulty != null ? difficulty : "Unknown");
                routeDifficultySpinner.setSelection(position);

                routeLengthEditText.setText(String.valueOf(route.getLength()));
            }
        }
    }
}
