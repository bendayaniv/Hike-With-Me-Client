package com.example.hike_with_me_client.Controller.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments.CommunityListFragment;
import com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments.MainPage.MainPageFragment;
import com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments.TripsListFragment;
import com.example.hike_with_me_client.Interfaces.Activities.Callback_GoToLoginActivity;
import com.example.hike_with_me_client.Models.Objects.Location;
import com.example.hike_with_me_client.R;
import com.example.hike_with_me_client.Models.User.UserMethods;
import com.example.hike_with_me_client.Services.LocationService;
import com.example.hike_with_me_client.Utils.Singleton.CurrentUser;
import com.example.hike_with_me_client.Utils.Constants;
import com.example.hike_with_me_client.Utils.MainPageFragment.SavedLastClick;
import com.example.hike_with_me_client.Utils.Singleton.UserLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    Button logoutButton;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private MainPageFragment mainPageFragment;
    private CommunityListFragment communityListFragment;
    private TripsListFragment tripsListFragment;
    FusedLocationProviderClient fusedLocationProviderClient;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;


    Callback_GoToLoginActivity goToLoginActivityCallback = () -> {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    };

    private BroadcastReceiver locationBroadcastReceiver = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {

//            String json = intent.getStringExtra(LocationService.BROADCAST_NOTIFICATION_KEY);
            String json = intent.getStringExtra(LocationService.BROADCAST_LOCATION_KEY);
            int counter = intent.getIntExtra(LocationService.BROADCAST_COUNTER_KEY, 0);
            try {
                Location myLoc = new Gson().fromJson(json, Location.class);
//                panel_LBL_info.setText(json + "\nCounter: " + counter);
                Log.d("MyMainActivity", "locationBroadcastReceiver: " + myLoc.toString());

            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }
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

//        startService();
        startLocationService();
    }

    private void logoutButtonFunctionality() {
        logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            CurrentUser.getInstance().getUser().setActive(false);
            CurrentUser.getInstance().getUser().setLocation(null);
            UserMethods.updateUser(CurrentUser.getInstance().getUser());
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
            UserMethods.getSpecificUser(currentUser.getUid());
        }

    }

    private void createFragments() {
        mainPageFragment = new MainPageFragment();
        mainPageFragment.setFragmentManager(fragmentManager);


        communityListFragment = new CommunityListFragment();

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

//        logoutButton = findViewById(R.id.btn_logout);

//        logoutButtonFunctionality();

    }

    private void initialiseBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Log.d("MyMainActivity", "onCreate: " + item.getItemId());
            switch (item.getItemId()) {
                case Constants.MENU_HOME:
                    startLocationService();
                    Log.d("MyMainActivity", "onCreate1: " + item.getItemId());
                    mainPageFragment();
                    break;
                case Constants.MENU_TRIPS:
                    Log.d("MyMainActivity", "onCreate2: " + item.getItemId());
                    fragmentManager.beginTransaction().replace(R.id.main_fragment_container, tripsListFragment).commit();
                    break;
                case Constants.MENU_COMMUNITY:
                    Log.d("MyMainActivity", "onCreate3 community: " + item.getItemId());
                    fragmentManager.beginTransaction().replace(R.id.main_fragment_container, communityListFragment).commit();
                    break;
                case Constants.MENU_PROFILE:
                    stopLocationService();
                    Log.d("MyMainActivity", "onCreate3: " + item.getItemId());
                    fragmentManager.beginTransaction().replace(R.id.main_fragment_container, tripsListFragment).commit();
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

    private void startLocationService() {
        Intent intent = new Intent(this, LocationService.class);
        intent.setAction(LocationService.START_FOREGROUND_SERVICE);
        startForegroundServiceCompat(intent);

        // Set initial notification states
        enableStickyNotification(false);  // Start without sticky notification
        enablePopUpNotifications(true);   // Enable pop-up notifications
    }

    private void stopLocationService() {
        Intent intent = new Intent(this, LocationService.class);
        intent.setAction(LocationService.STOP_FOREGROUND_SERVICE);
        startForegroundServiceCompat(intent);
    }

    private void enableStickyNotification(boolean enable) {
        Intent intent = new Intent(this, LocationService.class);
        intent.setAction(enable ? LocationService.ACTION_ENABLE_STICKY_NOTIFICATION
                : LocationService.ACTION_DISABLE_STICKY_NOTIFICATION);
        startForegroundServiceCompat(intent);
    }

    private void enablePopUpNotifications(boolean enable) {
        Intent intent = new Intent(this, LocationService.class);
        intent.setAction(enable ? LocationService.ACTION_ENABLE_POPUP_NOTIFICATIONS
                : LocationService.ACTION_DISABLE_POPUP_NOTIFICATIONS);
        startForegroundServiceCompat(intent);
    }

    private void startForegroundServiceCompat(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
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
        IntentFilter intentFilter = new IntentFilter(LocationService.BROADCAST_LOCATION);
        LocalBroadcastManager.getInstance(this).registerReceiver(locationBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(locationBroadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (UserLocation.getInstance().getCurrentLocation() == 0)
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.REQUEST_CODE);
    }

}