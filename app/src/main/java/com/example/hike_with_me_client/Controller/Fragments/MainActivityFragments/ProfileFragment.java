package com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize UI elements
        ImageView profileImage = view.findViewById(R.id.profile_image);
        TextView profileName = view.findViewById(R.id.profile_name);
        TextView phoneNum = view.findViewById(R.id.phone_number);
        TextView profileEmail = view.findViewById(R.id.profile_email);
        TextView profileHometown = view.findViewById(R.id.profile_hometown);
        Button editButton = view.findViewById(R.id.edit_button);

        // Set data to UI elements
        profileName.setText(mParam1);
        phoneNum.setText(mPhone);
        profileEmail.setText(mEmail);
        profileHometown.setText(mHometown);

        // Set edit button click listener
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement the logic to edit the profile
                // For example, you can open a new fragment or an activity to edit the profile
            }
        });

        return view;
    }

    private void loadUserProfile() {
        retryRunnable = new Runnable() {
            @Override
            public void run() {
                User loadedUser = CurrentUser.getInstance().getUser();//?

                if (loadedUser != null) {
                    // Update UI with the loaded profile details
                    profileName.setText(loadedUser.getName());
                    profileEmail.setText(loadedUser.getEmail());
                    profileHometown.setText(loadedUser.getHometown());
                   // profileDetailsContainer.setVisibility(View.VISIBLE);
                    editButton.setVisibility(View.VISIBLE);
                } else if (ErrorMessageFromServer.getInstance().getErrorMessageFromServer() != null &&
                        !ErrorMessageFromServer.getInstance().getErrorMessageFromServer().isEmpty()) {
                   // profileDetailsContainer.setVisibility(View.GONE);
                    editButton.setVisibility(View.VISIBLE);
                } else {
                    handler.postDelayed(retryRunnable, 3000); // Retry interval in milliseconds
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
    }

    @Override
    public void onPause() {
        super.onPause();
        CurrentUser.getInstance().setUser(null);
        ErrorMessageFromServer.getInstance().setErrorMessageFromServer(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        CurrentUser.getInstance().setUser(null);
        ErrorMessageFromServer.getInstance().setErrorMessageFromServer(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CurrentUser.getInstance().setUser(null);
        ErrorMessageFromServer.getInstance().setErrorMessageFromServer(null);
    }

    @Override
    public void onStop() {
        super.onStop();
        CurrentUser.getInstance().setUser(null);
        ErrorMessageFromServer.getInstance().setErrorMessageFromServer(null);
    }
}
