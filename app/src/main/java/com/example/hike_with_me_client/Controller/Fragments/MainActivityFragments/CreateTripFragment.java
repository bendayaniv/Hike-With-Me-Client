package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hike_with_me_client.Interfaces.Trip.Callbacks.Callback_CreateTrip;
import com.example.hike_with_me_client.Models.Trip.Actions.CreateTrip;
import com.example.hike_with_me_client.Models.Trip.Trip;
import com.example.hike_with_me_client.Models.Trip.TripMethods;
import com.example.hike_with_me_client.R;
import com.example.hike_with_me_client.Utils.Singleton.ListOfTrips;
import com.example.hike_with_me_client.Utils.Singleton.CurrentUser;
import com.example.hike_with_me_client.Models.User.User;

import java.util.ArrayList;
import java.util.Collections;

public class CreateTripFragment extends Fragment {

    private EditText tripNameEditText;
    private EditText tripStartDateEditText;
    private EditText tripEndDateEditText;
    private EditText tripDescriptionEditText;
    //private EditText tripImagesUrlsEditText;
    //private EditText tripUserIdEditText;
    private Button saveTripButton;

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
        //tripUserIdEditText = view.findViewById(R.id.trip_user_id);
        //tripImagesUrlsEditText = view.findViewById(R.id.trip_images_urls);
        saveTripButton = view.findViewById(R.id.save_trip_button);

        // Set click listener for the save button
        saveTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTrip();
            }
        });

        return view;
    }

    private void saveTrip() {
        // Get data from UI elements
        String name = tripNameEditText.getText().toString().trim();
        String startDate = tripStartDateEditText.getText().toString().trim();
        String endDate = tripEndDateEditText.getText().toString().trim();
        String description = tripDescriptionEditText.getText().toString().trim();
        //String userId = tripUserIdEditText.getText().toString().trim();
        //String imagesUrlsString = tripImagesUrlsEditText.getText().toString().trim();

        // Get the current user ID
        User currentUser = CurrentUser.getInstance().getUser();
        if (currentUser == null) {
            Toast.makeText(getActivity(), "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }
        String userId = currentUser.getId();

        // Validate input data (optional but recommended)
        if (name.isEmpty() || startDate.isEmpty() || endDate.isEmpty() || userId.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert the images URLs from a comma-separated string to an ArrayList
        ArrayList<String> imagesUrls = new ArrayList<>();
        //if (!imagesUrlsString.isEmpty()) {
        //    String[] urlsArray = imagesUrlsString.split(",");
        //    Collections.addAll(imagesUrls, urlsArray);
        //}

        // Create a Trip object
        Trip newTrip = new Trip();
        newTrip.setName(name);
        newTrip.setStartDate(startDate);
        newTrip.setEndDate(endDate);
        newTrip.setDescription(description);
        newTrip.setUserId(userId);
        //newTrip.setImagesUrls(imagesUrls);

        // Show a confirmation message
        Toast.makeText(getActivity(), "Trip saved successfully!", Toast.LENGTH_SHORT).show();
        saveTripToServer(newTrip);
    }

    private void saveTripToServer(Trip trip) {
        TripMethods.createTrip(trip);
    }

    private void clearInputFields() {
        tripNameEditText.setText("");
        tripStartDateEditText.setText("");
        tripEndDateEditText.setText("");
        tripDescriptionEditText.setText("");
        //tripUserIdEditText.setText("");
        //tripImagesUrlsEditText.setText("");
    }
}
