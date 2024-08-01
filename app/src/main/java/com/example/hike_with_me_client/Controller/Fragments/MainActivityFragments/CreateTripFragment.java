package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.hike_with_me_client.R;

public class CreateTripFragment extends Fragment {

    private EditText tripNameEditText;
    private EditText tripStartDateEditText;
    private EditText tripEndDateEditText;
    private EditText tripDescriptionEditText;
    private EditText tripUserIdEditText;
    private EditText tripImagesUrlsEditText;
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
        tripUserIdEditText = view.findViewById(R.id.trip_user_id);
        tripImagesUrlsEditText = view.findViewById(R.id.trip_images_urls);
        saveTripButton = view.findViewById(R.id.save_trip_button);

        // Set click listener for the save button
        saveTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement the logic to save the trip
            }
        });

        return view;
    }
}
