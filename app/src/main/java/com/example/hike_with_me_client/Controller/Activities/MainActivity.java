package com.example.hike_with_me_client.Controller.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.hike_with_me_client.Controller.Fragments.LoginFragments.LoginFragment;
import com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments.MainPage.MainPageFragment;
import com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments.RouteFragment;
import com.example.hike_with_me_client.Interfaces.Activities.Callback_GoToLoginActivity;
import com.example.hike_with_me_client.R;
import com.example.hike_with_me_client.Models.User.UserMethods;
import com.example.hike_with_me_client.Models.Objects.CurrentUser;
import com.example.hike_with_me_client.Utils.Constants;
import com.example.hike_with_me_client.Utils.SavedLastClick;
import com.example.hike_with_me_client.Utils.UserLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button logoutButton;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private MainPageFragment mainPageFragment;
    private RouteFragment routeFragment;
    private LoginFragment loginFragment = new LoginFragment();
    FusedLocationProviderClient fusedLocationProviderClient;
    private BottomNavigationView bottomNavigationView;

    private FragmentManager fragmentManager;


    Callback_GoToLoginActivity goToLoginActivityCallback = () -> {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set direction on all devices from LEFT to RIGHT
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        SavedLastClick.initSavedLastClick();

        initialization();

        checkingCurrentUser();

        createFragments();

        mainPageFragment();
    }

    private void logoutButtonFunctionality() {
        logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            CurrentUser.getInstance().removeUser();
            goToLoginActivityCallback.goToLoginActivityCallback();
        });

    }

    @SuppressLint("SetTextI18n")
    private void checkingCurrentUser() {
        if (currentUser == null) {
            goToLoginActivityCallback.goToLoginActivityCallback();
        } else {
            Log.d("MyMainActivity", "User is logged in: " + currentUser);
//            textView.setText("User is logged in: " + currentUser.getUid());
            UserMethods.getSpecificUser(currentUser.getUid());
        }

    }

    private void createFragments() {
        mainPageFragment = new MainPageFragment();
        mainPageFragment.setFragmentManager(fragmentManager);

        routeFragment = new RouteFragment();

        loginFragment = new LoginFragment();
    }

    private void mainPageFragment() {
        fragmentManager.beginTransaction().replace(R.id.main_fragment_container, mainPageFragment).commit();
    }

    private void initialization() {

        fragmentManager = getSupportFragmentManager();

        initializeUserLocation();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();

        bottomNavigationView = findViewById(R.id.mainBottomNavigation);

        initialiseBottomNavigation();

//        textView = findViewById(R.id.textView);
//
//        logoutButton = findViewById(R.id.btn_logout);

//        logoutButtonFunctionality();

    }

    private void initialiseBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Log.d("MyMainActivity", "onCreate: " + item.getItemId());
            switch (item.getItemId()) {
                case Constants.MENU_HOME:
                    Log.d("MyMainActivity", "onCreate1: " + item.getItemId());
                    mainPageFragment.onBeforeNavigateAway();
                    mainPageFragment();
                    break;
                case Constants.MENU_TRIPS:
                    Log.d("MyMainActivity", "onCreate2: " + item.getItemId());
                    fragmentManager.beginTransaction().replace(R.id.main_fragment_container, loginFragment).commit();
                    break;
                case Constants.MENU_PROFILE:
                    Log.d("MyMainActivity", "onCreate3: " + item.getItemId());
                    fragmentManager.beginTransaction().replace(R.id.main_fragment_container, routeFragment).commit();
                    break;
            }
            return true;
        });
    }

    private void initializeUserLocation() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(500);
        locationRequest.setFastestInterval(200);

        UserLocation.getInstance().setContext(MainActivity.this);
        UserLocation.getInstance().setLocationRequest(locationRequest);
        UserLocation.getInstance().setGetSystemService((LocationManager) getSystemService(Context.LOCATION_SERVICE));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (UserLocation.getInstance().checkingForGpsAndLocationPermissions(requestCode, grantResults) == 1) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (UserLocation.getInstance().checkingForCurrentLocationAvailability(requestCode, resultCode) == 0)
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.REQUEST_CODE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (UserLocation.getInstance().getCurrentLocation() == 0)
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.REQUEST_CODE);
    }

}