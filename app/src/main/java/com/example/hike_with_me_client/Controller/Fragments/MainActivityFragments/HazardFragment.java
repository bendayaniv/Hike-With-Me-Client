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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hike_with_me_client.Adapters.EnumAdapter;
import com.example.hike_with_me_client.Models.Hazard.Hazard;
import com.example.hike_with_me_client.Models.Hazard.HazardMethods;
import com.example.hike_with_me_client.Models.Objects.Location;
import com.example.hike_with_me_client.Models.User.User;
import com.example.hike_with_me_client.R;
import com.example.hike_with_me_client.Utils.Constants;
import com.example.hike_with_me_client.Utils.Singleton.CurrentUser;

import java.util.Date;
import java.util.UUID;

public class HazardFragment extends Fragment {

    private static final String ARG_ROUTE_NAME = "route_name";
    private String routeName;

    private TextView routeNameTextView;
    private TextView descriptionLabel;
    private TextView severityLabel;
    private TextView hazardTypeLabel;
    private TextView routeNameLabel;
    private EditText hazardDescriptionEditText;
    private Spinner severitySpinner;
    private Spinner hazardTypeSpinner;
    private Button saveButton;
    private Button backButton;
    FragmentManager fragmentManager;
    RouteDetailsFragment routeDetailsFragment;

    public static HazardFragment newInstance(String routeName) {
        HazardFragment fragment = new HazardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ROUTE_NAME, routeName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getParentFragmentManager();
        if (getArguments() != null) {
            routeName = getArguments().getString(ARG_ROUTE_NAME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hazard, container, false);
        findViews(view);
        setUpButtons();
        return view;
    }

    private void setUpButtons() {
        saveButton.setOnClickListener(v -> saveHazard());
        backButton.setOnClickListener(v -> navigateBack());
    }

    private void findViews(View view) {
        routeNameTextView = view.findViewById(R.id.route_name_text_view);
        hazardDescriptionEditText = view.findViewById(R.id.hazard_description_edit_text);
        severitySpinner = view.findViewById(R.id.severity_spinner);
        hazardTypeSpinner = view.findViewById(R.id.hazard_type_spinner);
        routeNameLabel = view.findViewById(R.id.route_name_label);
        descriptionLabel = view.findViewById(R.id.description_label);
        hazardTypeLabel = view.findViewById(R.id.hazard_type_label);
        severityLabel = view.findViewById(R.id.severity_label);
        saveButton = view.findViewById(R.id.save_button);
        backButton = view.findViewById(R.id.back_button);
        routeNameTextView.setText(routeName);
        EnumAdapter<Constants.Level> severityAdapter = new EnumAdapter<>(getContext(), Constants.Level.values());
        severitySpinner.setAdapter(severityAdapter);
        EnumAdapter<Constants.HazardType> hazardTypeAdapter = new EnumAdapter<>(getContext(), Constants.HazardType.values());
        hazardTypeSpinner.setAdapter(hazardTypeAdapter);
    }

    private void saveHazard() {
        // Get data from UI elements
        String description = hazardDescriptionEditText.getText().toString().trim();
        Constants.Level severity = (Constants.Level) severitySpinner.getSelectedItem();
        Constants.HazardType hazardType = (Constants.HazardType) hazardTypeSpinner.getSelectedItem();
        String uniqueID = UUID.randomUUID().toString();

        // Get the current user ID
        User currentUser = CurrentUser.getInstance().getUser();
        if (currentUser == null) {
            Toast.makeText(getActivity(), "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }
        String userId = currentUser.getId();
        Location location = currentUser.getLocation();
        Date date = new Date(2016, 11, 18);
        Log.d("Date", date.toString());
        location.setDate(date);
        String pointType = "Hazard";
        // Validate input data (optional but recommended)
        if (description.isEmpty() || userId.isEmpty() || routeName == null || hazardType == null || severity == null) {
            Toast.makeText(getActivity(), "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a hazard object
        Hazard newHazard = new Hazard(location, pointType, uniqueID, hazardType, description, severity, userId, routeName);
        saveHazardToServer(newHazard);
    }

    private void saveHazardToServer(Hazard hazard) {
        HazardMethods.addHazard(hazard, getContext());
        // Navigate back or reset the form as needed
        navigateBack();
    }


    private void navigateBack() {
        if (routeDetailsFragment == null) {
            if (fragmentManager != null) {
                fragmentManager.popBackStack(); // Navigate back to the previous fragment
            }
        } else {
            fragmentManager.beginTransaction().replace(R.id.main_fragment_container, routeDetailsFragment).commit();
        }
    }
    public void setRouteDetailsFragment(RouteDetailsFragment fragment) {
        this.routeDetailsFragment = fragment;
    }
}
