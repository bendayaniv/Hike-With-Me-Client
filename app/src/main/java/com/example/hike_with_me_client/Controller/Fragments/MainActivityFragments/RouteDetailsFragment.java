package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hike_with_me_client.Models.Route.Route;
import com.example.hike_with_me_client.Models.Route.RouteMethods;
import com.example.hike_with_me_client.Utils.Singleton.CurrentUser;
import com.example.hike_with_me_client.Models.User.User;
import com.example.hike_with_me_client.R;

import java.util.UUID;

public class RouteDetailsFragment extends Fragment {

    private static final String ARG_ROUTE_ID = "route_id";
    private Route route;
    private TextView routeNameTextView;
    private EditText routeDescriptionEditText;
    private Spinner routeDifficultySpinner;
    private EditText routeLengthEditText;
    private Button saveRouteButton;
    private Button backRouteButton;
    FragmentManager fragmentManager;

    public static RouteDetailsFragment newInstance(String routeId) {
        RouteDetailsFragment fragment = new RouteDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ROUTE_ID, routeId);
        fragment.setArguments(args);
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
        setupSpinner();
        loadRouteDetails();
        setupButtons();
        return view;
    }

    private void findViews(View view) {
        routeNameTextView = view.findViewById(R.id.route_name_text_view);
        routeDescriptionEditText = view.findViewById(R.id.route_description_edit_text);
        routeDifficultySpinner = view.findViewById(R.id.route_difficulty_spinner);
        routeLengthEditText = view.findViewById(R.id.route_length_edit_text);
        saveRouteButton = view.findViewById(R.id.save_route_button);
        backRouteButton = view.findViewById(R.id.back_route_button);
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

    private void setupButtons() {
        saveRouteButton.setOnClickListener(v -> saveRoute());
        backRouteButton.setOnClickListener(v -> navigateBack());
    }

    private void saveRoute() {
        // Collect data from UI elements
        String name = routeNameTextView.getText().toString().trim();
        String description = routeDescriptionEditText.getText().toString().trim();
        String lengthString = routeLengthEditText.getText().toString().trim();
        String difficulty = routeDifficultySpinner.getSelectedItem().toString();
        String uniqueID = UUID.randomUUID().toString();

        // Validate input data
        if (name.isEmpty() || description.isEmpty() || lengthString.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double length;
        String lengthAsString;
        try {
            length = Double.parseDouble(lengthString);
            lengthAsString = String.valueOf(length);
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Invalid length format", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a route object
        Route newRoute = new Route();
        newRoute.setName(name);
        newRoute.setDescription(description);
        newRoute.setLength(lengthAsString);
        newRoute.setDifficultyLevel(difficulty);
        newRoute.setId(uniqueID);
        // Send route object to the server
        saveRouteToServer(newRoute);
    }

    private void saveRouteToServer(Route newRoute) {
        RouteMethods.saveRoute(newRoute, this.getContext());
    }

    private void navigateBack() {
        FragmentManager fragmentManager = getParentFragmentManager();
        if (fragmentManager != null) {
            fragmentManager.popBackStack(); // Navigate back to the previous fragment
        }
    }
}
