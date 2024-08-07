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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments.MainPage.MainPageFragment;
import com.example.hike_with_me_client.Models.Recommendation.Recommendation;
import com.example.hike_with_me_client.Models.Recommendation.RecommendationMethods;
import com.example.hike_with_me_client.Models.User.User;
import com.example.hike_with_me_client.R;
import com.example.hike_with_me_client.Utils.Singleton.CurrentUser;

import java.util.UUID;

public class RecommendationFragment extends Fragment {

    private static final String ARG_ROUTE_NAME = "route_name";
    private String routeName;

    private TextView routeNameTextView;
    private RatingBar ratingBar;
    private EditText recommendationDescriptionEditText;
    private Button saveButton;
    private Button backButton;
    FragmentManager fragmentManager;
    RouteDetailsFragment routeDetailsFragment;

    public static RecommendationFragment newInstance(String routeName) {
        RecommendationFragment fragment = new RecommendationFragment();
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
        View view = inflater.inflate(R.layout.fragment_recommendation, container, false);
        findViews(view);
        setUpButtons();
        return view;
    }

    private void setUpButtons() {
        saveButton.setOnClickListener(v -> saveRecommendation());
        backButton.setOnClickListener(v -> navigateBack());
    }

    private void findViews(View view) {
        routeNameTextView = view.findViewById(R.id.route_name_text_view);
        ratingBar = view.findViewById(R.id.rating_bar);
        recommendationDescriptionEditText = view.findViewById(R.id.recommendation_description_edit_text);
        saveButton = view.findViewById(R.id.save_button);
        backButton = view.findViewById(R.id.back_button);
        routeNameTextView.setText(routeName);
    }

    private void saveRecommendation() {
        // Get data from UI elements
        String description = recommendationDescriptionEditText.getText().toString().trim();
        int rating = (int) ratingBar.getRating();
        String uniqueID = UUID.randomUUID().toString();

        // Get the current user ID
        User currentUser = CurrentUser.getInstance().getUser();
        if (currentUser == null) {
            Toast.makeText(getActivity(), "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }
        String userId = currentUser.getId();

        // Validate input data (optional but recommended)
        if (description.isEmpty() || userId.isEmpty() || routeName == null || rating == 0) {
            Toast.makeText(getActivity(), "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a recommendation object
        Recommendation newRecommendation = new Recommendation();
        newRecommendation.setId(uniqueID);
        newRecommendation.setDescription(description);
        newRecommendation.setRate(rating);
        newRecommendation.setReporterName(userId);
        newRecommendation.setRouteName(routeName);

        saveRecommendationToServer(newRecommendation);
    }

    private void saveRecommendationToServer(Recommendation recommendation) {
        RecommendationMethods.addRecommendation(recommendation, getContext());
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

