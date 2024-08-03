package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.hike_with_me_client.Utils.Constants;
import com.example.hike_with_me_client.Utils.Singleton.CurrentUser;
import com.example.hike_with_me_client.Utils.Singleton.ErrorMessageFromServer;
import com.example.hike_with_me_client.Models.User.User;
import com.example.hike_with_me_client.R;
import com.example.hike_with_me_client.Utils.Singleton.ListOfTrips;

public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PHONE = "phoneNum";
    private static final String ARG_EMAIL = "email";
    private static final String ARG_HOMETOWN = "hometown";

    private String mParam1;
    private String mParam2;
    private String mPhone;
    private String mEmail;
    private String mHometown;
    private FragmentManager fragmentManager;

    private Handler handler;
    private Runnable retryRunnable;
    TextView profileName;
    TextView profileHometown;
    TextView profileEmail;

    ImageView profileImage;
    TextView phoneNum;
    Button editButton;
    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String name, String param2, String phoneNum, String email, String hometown) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, name);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PHONE, phoneNum);
        args.putString(ARG_EMAIL, email);
        args.putString(ARG_HOMETOWN, hometown);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mPhone = getArguments().getString(ARG_PHONE);
            mEmail = getArguments().getString(ARG_EMAIL);
            mHometown = getArguments().getString(ARG_HOMETOWN);
        }
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize UI elements
        profileImage = view.findViewById(R.id.profile_image);
        profileName = view.findViewById(R.id.profile_name);
        phoneNum = view.findViewById(R.id.phone_number);
        profileEmail = view.findViewById(R.id.profile_email);
        profileHometown = view.findViewById(R.id.profile_hometown);
        editButton = view.findViewById(R.id.edit_button);

        // Set data to UI elements
        profileName.setText(mParam1);
        phoneNum.setText(mPhone);
        profileEmail.setText(mEmail);
        profileHometown.setText(mHometown);

        //handler = new Handler(Looper.getMainLooper());
        loadUserProfileFromServer();
        // Set edit button click listener
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement the logic to edit the profile
                // TODO - move to create trip fragment
                Toast.makeText(getContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void loadUserProfileFromServer() {
        retryRunnable = new Runnable() {
            @Override
            public void run() {
                // Fetch the current user profile
                User loadedUser = CurrentUser.getInstance().getUser();

                if (loadedUser != null) {
                    // Update UI with the loaded profile details
                    profileName.setText(loadedUser.getName());
                    profileEmail.setText(loadedUser.getEmail());
                    profileHometown.setText(loadedUser.getHometown());
                    phoneNum.setText(loadedUser.getPhoneNumber());
                    profileImage.setImageResource(R.drawable.profile); // Assuming a placeholder image
                    editButton.setVisibility(View.VISIBLE);
                } else if (ErrorMessageFromServer.getInstance().getErrorMessageFromServer() != null &&
                        !ErrorMessageFromServer.getInstance().getErrorMessageFromServer().isEmpty()) {
                    // Display error message if available
                    profileName.setText(R.string.name);
                    profileEmail.setText(R.string.email);
                    profileHometown.setText(R.string.hometown);
                    phoneNum.setText(R.string.phone_number);
                    editButton.setVisibility(View.GONE);
                    //emptyProfileListTV.setVisibility(View.VISIBLE);
                    //emptyProfileListTV.setText(ErrorMessageFromServer.getInstance().getErrorMessageFromServer());
                } else {
                    // Retry fetching user profile if no data and no error message
                    handler.postDelayed(retryRunnable, Constants.RETRY_INTERVAL);
                }
            }
        };

        handler.post(retryRunnable);
    }


    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUserProfileFromServer();
    }

    @Override
    public void onPause() {
        super.onPause();
        ErrorMessageFromServer.getInstance().setErrorMessageFromServer(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ErrorMessageFromServer.getInstance().setErrorMessageFromServer(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ErrorMessageFromServer.getInstance().setErrorMessageFromServer(null);
    }

    @Override
    public void onStop() {
        super.onStop();
        ErrorMessageFromServer.getInstance().setErrorMessageFromServer(null);
    }
}
