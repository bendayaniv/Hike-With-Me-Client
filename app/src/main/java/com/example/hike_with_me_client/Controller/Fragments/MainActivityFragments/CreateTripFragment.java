package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments;

import static com.example.hike_with_me_client.Utils.DateVerification.isStartDateBeforeOrSameAsEndDate;
import static com.example.hike_with_me_client.Utils.DateVerification.isValidDate;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hike_with_me_client.Models.Objects.Location;
import com.example.hike_with_me_client.Models.Route.Route;
import com.example.hike_with_me_client.Models.Trip.trip;
import com.example.hike_with_me_client.Models.Trip.TripMethods;
import com.example.hike_with_me_client.R;
import com.example.hike_with_me_client.Utils.GlobalUtilInstances.CurrentUser;
import com.example.hike_with_me_client.Models.User.User;
import com.example.hike_with_me_client.Utils.GlobalUtilInstances.ListOfRoutes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class CreateTripFragment extends Fragment {

    private EditText tripNameEditText;
    private EditText tripStartDateEditText;
    private EditText tripEndDateEditText;
    private EditText tripDescriptionEditText;
    private Button saveTripButton;
    private Button addRouteButton;
    private ImageView tripImageView;
    private ProgressBar progressBar;
    private ArrayList<String> selectedRoutes;
    private TextView routesTextView;
    private static final int PICK_IMAGE_REQUEST = 1;

    private static final int PERMISSION_REQUEST_CODE = 3;
    private static final String TAG = "CreateTripFragment";

    private LinkedHashMap<Integer, Uri> selectedImages = new LinkedHashMap<>();


    public CreateTripFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_trip, container, false);

        // Initialize UI elements
        tripNameEditText = view.findViewById(R.id.trip_name);
        tripStartDateEditText = view.findViewById(R.id.trip_start_date);
        tripEndDateEditText = view.findViewById(R.id.trip_end_date);
        tripDescriptionEditText = view.findViewById(R.id.trip_description);
        routesTextView = view.findViewById(R.id.routes_text_view);
        addRouteButton = view.findViewById(R.id.add_route_button);
        saveTripButton = view.findViewById(R.id.save_trip_button);
        progressBar = view.findViewById(R.id.progressBar);
        tripImageView = view.findViewById(R.id.tripImageView);
        selectedRoutes = new ArrayList<>();
        addRouteButton.setOnClickListener(v -> showRoutesDialog());
        tripImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the image chooser
                openImageChooser(PICK_IMAGE_REQUEST);
            }
        });

        // Set click listener for the save button
        saveTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTrip();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can proceed with image selection
            } else {
                Toast.makeText(getContext(), "Permission denied. Cannot select images.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showRoutesDialog() {
        List<Route> allRoutes = ListOfRoutes.getInstance().getRoutes();
        String[] routeNames = new String[allRoutes.size()];
        boolean[] checkedItems = new boolean[allRoutes.size()];

        for (int i = 0; i < allRoutes.size(); i++) {
            routeNames[i] = allRoutes.get(i).getName();
            checkedItems[i] = selectedRoutes.contains(routeNames[i]);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Select Routes")
                .setMultiChoiceItems(routeNames, checkedItems, (dialog, indexSelected, isChecked) -> {
                    if (isChecked) {
                        if (!selectedRoutes.contains(routeNames[indexSelected])) {
                            selectedRoutes.add(routeNames[indexSelected]);
                        }
                    } else {
                        selectedRoutes.remove(routeNames[indexSelected]);
                    }
                })
                .setPositiveButton("OK", (dialog, id) -> {
                    routesTextView.setText(selectedRoutes.toString());
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());

        builder.create().show();
    }

    private void saveTrip() {
        progressBar.setVisibility(View.VISIBLE);
        // Get data from UI elements
        String name = tripNameEditText.getText().toString().trim();
        String startDate = tripStartDateEditText.getText().toString().trim();
        String endDate = tripEndDateEditText.getText().toString().trim();
        String description = tripDescriptionEditText.getText().toString().trim();
        String uniqueID = UUID.randomUUID().toString();
        // Get the current user ID
        User currentUser = CurrentUser.getInstance().getUser();
        if (currentUser == null) {
            Toast.makeText(getActivity(), "User not logged in", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }
        String userId = currentUser.getId();
        // Validate input data (optional but recommended)
        if (name.isEmpty() || startDate.isEmpty() || endDate.isEmpty() || userId.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        // Get the selected routes from the TextView
        String selectedRoutesText = routesTextView.getText().toString().trim();
        if (selectedRoutesText.isEmpty()) {
            Toast.makeText(getActivity(), "Please add at least one route", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }
        selectedRoutesText = selectedRoutesText.replaceAll("[\\[\\]]", "");

        // Initialize a list to hold the route names
        List<String> routeNamesList = new ArrayList<>();
        // Split the text into parts based on the comma
        String[] parts = selectedRoutesText.split(",");
        // Add each part to the list
        for (String part : parts) {
            String trimmedPart = part.trim();
            Log.d("SelectedRoute1", trimmedPart);
            routeNamesList.add(trimmedPart);
        }

        // Convert the list to an array if needed
        String[] selectedRoutesArray = routeNamesList.toArray(new String[0]);

        //Print each route to verify
        for (String route : selectedRoutesArray) {
            Log.d("SelectedRoute", route);
        }

        if (isValidDate(startDate)&&isValidDate(endDate)) {
            if(isStartDateBeforeOrSameAsEndDate(startDate,endDate)) {
                // Create a trip object
                trip newTrip = new trip();
                newTrip.setName(name);
                newTrip.setStartDate(startDate);
                newTrip.setEndDate(endDate);
                newTrip.setDescription(description);
                newTrip.setUserId(userId);
                newTrip.setId(uniqueID);
                newTrip.setLocations(new Location[]{});
                newTrip.setRoutesNames(selectedRoutesArray);

                saveTripToServer(newTrip);
            }else {
                Toast.makeText(getContext(), "StartDate is after EndDate, Please fix" , Toast.LENGTH_LONG).show();
            }
        } else{
            Toast.makeText(getContext(), "Please Enter a valid date (dd/mm/yy)" , Toast.LENGTH_LONG).show();
        }
    }

    private void saveTripToServer(trip trip) {
        uploadImages(trip);
    }

    private void openImageChooser(int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            if (requestCode == PICK_IMAGE_REQUEST) {
                tripImageView.setImageURI(imageUri);
                selectedImages.put(PICK_IMAGE_REQUEST, imageUri);
                Log.d(TAG, "Image 1 selected: " + imageUri.toString());
            }
        } else {
            Log.d(TAG, "Image selection failed or was cancelled");
        }
    }

    private void uploadImages(trip trip) {
        String userId = CurrentUser.getInstance().getUser().getId();
        String tripName = String.valueOf(tripNameEditText.getText());
        if (!selectedImages.isEmpty()) {
            Log.d(TAG, "Attempting to upload images: " + selectedImages.values().toString());
            try {
                TripMethods.uploadImages(new ArrayList<>(selectedImages.values()), userId, tripName, requireContext(), progressBar, trip);
            } catch (Exception e) {
                Log.e(TAG, "Error during upload: ", e);
                Toast.makeText(getContext(), "Upload failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        } else {
            Log.d(TAG, "No images selected for upload");
            TripMethods.uploadImages(new ArrayList<>(), userId, tripName, requireContext(), progressBar, trip);
        }
    }
}
