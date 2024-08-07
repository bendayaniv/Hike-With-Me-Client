package com.example.hike_with_me_client.Controller.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentManager;

import com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments.CommunityListFragment;
import com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments.MainPage.MainPageFragment;
import com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments.TripsListFragment;
import com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments.ProfileFragment;
import com.example.hike_with_me_client.Interfaces.Activities.Callback_GoToLoginActivity;
import com.example.hike_with_me_client.Interfaces.Fragments.MainActivityFragments.LogoutListener;
import com.example.hike_with_me_client.R;
import com.example.hike_with_me_client.Models.User.UserMethods;
import com.example.hike_with_me_client.Services.LocationService;
import com.example.hike_with_me_client.Utils.NotificationManager;
import com.example.hike_with_me_client.Utils.GlobalUtilInstances.CurrentUser;
import com.example.hike_with_me_client.Utils.Constants;
import com.example.hike_with_me_client.Utils.MainPageFragment.SavedLastClick;
import com.example.hike_with_me_client.Utils.GlobalUtilInstances.UserLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements LogoutListener {

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private MainPageFragment mainPageFragment;
    private CommunityListFragment communityListFragment;
    private TripsListFragment tripsListFragment;
    private ProfileFragment profileFragment;
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

    @Override
    public void onLogoutRequested() {
        logoutButtonFunctionality();
    }

    private void logoutButtonFunctionality() {
        stopLocationService();
        mAuth.signOut();
        CurrentUser.getInstance().getUser().setActive(false);
        CurrentUser.getInstance().getUser().setLocation(null);
        UserMethods.updateUser(CurrentUser.getInstance().getUser());
        CurrentUser.getInstance().removeUser();
        goToLoginActivityCallback.goToLoginActivityCallback();
    }

    @SuppressLint("SetTextI18n")
    private void checkingCurrentUser() {
        if (currentUser == null) {
            goToLoginActivityCallback.goToLoginActivityCallback();
        } else {
            startLocationService();
            UserMethods.getSpecificUser(currentUser.getUid());
            UserLocation.getInstance().getCurrentLocation();
        }
    }

    private void createFragments() {
        mainPageFragment = new MainPageFragment();
        mainPageFragment.setFragmentManager(fragmentManager);
        communityListFragment = new CommunityListFragment();
        profileFragment = new ProfileFragment();
        profileFragment.setFragmentManager(fragmentManager);
        tripsListFragment = new TripsListFragment();
        tripsListFragment.setFragmentManager(fragmentManager);
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
    }

    private void initialiseBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Log.d("MyMainActivity", "onCreate: " + item.getItemId());
            switch (item.getItemId()) {
                case Constants.MENU_HOME:
                    Log.d("MyMainActivity", "onCreate1 MENU_HOME: " + item.getItemId());
                    mainPageFragment();
                    break;
                case Constants.MENU_TRIPS:
                    Log.d("MyMainActivity", "onCreate2 MENU_TRIPS: " + item.getItemId());
                    fragmentManager.beginTransaction().replace(R.id.main_fragment_container, tripsListFragment).commit();
                    break;
                case Constants.MENU_COMMUNITY:
                    Log.d("MyMainActivity", "onCreate3 MENU_COMMUNITY: " + item.getItemId());
                    fragmentManager.beginTransaction().replace(R.id.main_fragment_container, communityListFragment).commit();
                    break;
                case Constants.MENU_PROFILE:
                    Log.d("MyMainActivity", "onCreate3 MENU_PROFILE: " + item.getItemId());
                    fragmentManager.beginTransaction().replace(R.id.main_fragment_container, profileFragment).commit();
                    break;
            }
            return true;
        });
    }

    private void initializeUserLocation() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000); // 10 seconds
        locationRequest.setFastestInterval(5000); // 5 seconds

        UserLocation.getInstance().setContext(MainActivity.this);
        UserLocation.getInstance().setLocationRequest(locationRequest);
        UserLocation.getInstance().setGetSystemService((LocationManager) getSystemService(Context.LOCATION_SERVICE));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.d("MainActivity", "onRequestPermissionsResult: requestCode=" + requestCode);

        switch (requestCode) {
            case Constants.LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Location permission granted
                    UserLocation.getInstance().getCurrentLocation();
                } else {
                    // Location permission denied
                    Log.d("MainActivity", "Location permission denied");
                    // Handle location permission denied
                }
                break;
            case Constants.NOTIFICATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Notification permission granted
                    Log.d("MainActivity", "Notification permission granted");
                    // Handle notification permission granted
                } else {
                    // Notification permission denied
                    Log.d("MainActivity", "Notification permission denied");
                    // Handle notification permission denied
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (UserLocation.getInstance().checkingForCurrentLocationAvailability(requestCode, resultCode) == 0)
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.LOCATION_PERMISSION_REQUEST_CODE);
    }

    private void startLocationService() {
        Intent intent = new Intent(this, LocationService.class);
        intent.setAction(LocationService.START_FOREGROUND_SERVICE);
        startForegroundServiceCompat(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleNotificationClick(intent);
    }

    private void handleNotificationClick(Intent intent) {
        if (intent != null && NotificationManager.OPEN_APP_DISMISS_NOTIFICATIONS.equals(intent.getAction())) {
            // Dismiss all notifications
            NotificationManagerCompat.from(this).cancelAll();
        }
        mainPageFragment();
    }

    private void stopLocationService() {
        Intent intent = new Intent(this, LocationService.class);
        intent.setAction(LocationService.STOP_FOREGROUND_SERVICE);
        startForegroundServiceCompat(intent);
    }

    private void startForegroundServiceCompat(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }

    // Service Methods //
    private void checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, Constants.NOTIFICATION_PERMISSION_REQUEST_CODE);
            } else {
                // Notification permission is already granted
                Log.d("MainActivity", "Notification permission already granted");
            }
        } else {
            Log.d("MainActivity", "Notification permission not required for this Android version");
        }
    }

    private void checkAndRequestLocationPermission() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Location permission is already granted
            Log.d("MainActivity", "Location permission already granted");
        }
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
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAndRequestLocationPermission();
        checkAndRequestNotificationPermission();
    }

}