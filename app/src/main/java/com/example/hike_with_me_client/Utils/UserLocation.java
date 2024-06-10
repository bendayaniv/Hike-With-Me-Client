package com.example.hike_with_me_client.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.hike_with_me_client.Models.Objects.CurrentUser;
import com.example.hike_with_me_client.Models.Objects.Location;
import com.example.hike_with_me_client.Models.User.UserMethods;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.Task;

import java.util.Date;

@SuppressLint("StaticFieldLeak")
public class UserLocation {

    private static UserLocation instance = null;
    private Context context;
    private LocationRequest locationRequest;
    private LocationManager getSystemService;

    private UserLocation() {
    }

    public static void initUserLocation() {
        if (instance == null) {
            instance = new UserLocation();
        }
    }

    public static UserLocation getInstance() {
        return instance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setLocationRequest(LocationRequest locationRequest) {
        this.locationRequest = locationRequest;
    }

    public void setGetSystemService(LocationManager getSystemService) {
        this.getSystemService = getSystemService;
    }

    public void turnOnGPS() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(context.getApplicationContext()).checkLocationSettings(builder.build());
        result.addOnCompleteListener(task -> {
            try {
                LocationSettingsResponse response = task.getResult(ApiException.class);
                Toast.makeText(context, "GPS is already turned on: " + response.toString(), Toast.LENGTH_SHORT).show();
            } catch (ApiException e) {
                switch (e.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                            resolvableApiException.startResolutionForResult((Activity) context, Constants.REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        //Device does not have location
                        break;
                }
            }
        });
    }

    public boolean isGPSEnabled() {
        // Initialize the location manager and get the system service of the location
        LocationManager locationManager = getSystemService;
        // Check if the GPS is provided
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public int checkingForGpsAndLocationPermissions(int requestCode, int[] grantResults) {
        if (requestCode == Constants.REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (isGPSEnabled()) {
                    if (getCurrentLocation() == 0) return 0;
                } else {
                    turnOnGPS();
                }
            } else {
                return 0;
            }
        }
        return 1;
    }

    public int checkingForCurrentLocationAvailability(int requestCode, int resultCode) {
        if (requestCode == Constants.REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                if (getCurrentLocation() == 0) return 0;
            }
        }
        return 1;
    }

    @SuppressLint("ObsoleteSdkInt")
    public int getCurrentLocation() {
        // Check for the user permission to get the location
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Check if the user gave permission to his location
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // Check if the GPS is enabled
                if (isGPSEnabled()) {
                    // Get the current location
                    LocationServices.getFusedLocationProviderClient(context).requestLocationUpdates(locationRequest, new LocationCallback() {
                        @Override
                        public void onLocationResult(@NonNull LocationResult locationResult) {
                            super.onLocationResult(locationResult);
                            // Only need the last location
                            LocationServices.getFusedLocationProviderClient(context).removeLocationUpdates(this);
                            if (!locationResult.getLocations().isEmpty()) {
                                int index = locationResult.getLocations().size() - 1;
                                double currentLatitude = locationResult.getLocations().get(index).getLatitude();
                                double currentLongitude = locationResult.getLocations().get(index).getLongitude();
                                CurrentUser.getInstance().getUser().setLocation(new Location(currentLatitude, currentLongitude));
                                UserMethods.updateUser(CurrentUser.getInstance().getUser());
                            }
                        }
                    }, Looper.getMainLooper());
                }
                // If the GPS is not enabled
                else {
                    turnOnGPS();
                }
            }
            // If the user didn't give permission to his location
            else {
                return 0;
            }
        }
        return 1;
    }
}
