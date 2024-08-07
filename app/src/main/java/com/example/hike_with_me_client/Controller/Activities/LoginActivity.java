package com.example.hike_with_me_client.Controller.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.hike_with_me_client.Controller.Fragments.LoginFragments.LoginFragment;
import com.example.hike_with_me_client.Controller.Fragments.LoginFragments.RegisterFragment;
import com.example.hike_with_me_client.Interfaces.Activities.Callback_GoToMainActivity;
import com.example.hike_with_me_client.Interfaces.Fragments.LoginFragments.Callback_GoToLoginFragment;
import com.example.hike_with_me_client.Interfaces.Fragments.LoginFragments.Callback_GoToRegisterFragment;
import com.example.hike_with_me_client.R;
import com.example.hike_with_me_client.Utils.Constants;
import com.example.hike_with_me_client.Utils.GlobalUtilInstances.UserLocation;

public class LoginActivity extends AppCompatActivity {

    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;

    Callback_GoToLoginFragment goToLoginFragmentCallback = new Callback_GoToLoginFragment() {
        @Override
        public void goToLoginFragmentCallback() {
            getSupportFragmentManager().beginTransaction().replace(R.id.login_fragment_container, loginFragment).commit();
        }
    };

    Callback_GoToRegisterFragment goToRegisterFragmentCallback = new Callback_GoToRegisterFragment() {
        @Override
        public void goToRegisterFragmentCallback() {
            getSupportFragmentManager().beginTransaction().replace(R.id.login_fragment_container, registerFragment).commit();
        }
    };

    Callback_GoToMainActivity goToMainActivityCallback = new Callback_GoToMainActivity() {
        @Override
        public void goToMainActivityCallback() {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Set direction on all devices from LEFT to RIGHT
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        createFragments();
        defaultFragment();
        checkLocationPermission();
    }

    private void createFragments() {
        loginFragment = new LoginFragment();
        loginFragment.setCallbacks(goToRegisterFragmentCallback, goToMainActivityCallback);
        registerFragment = new RegisterFragment();
        registerFragment.setCallbacks(goToLoginFragmentCallback, goToMainActivityCallback);
    }

    private void defaultFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.login_fragment_container, loginFragment).commit();
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Permission has already been granted
            // You can proceed with location-related tasks here if needed
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted
                Toast.makeText(this, "Location permission granted", Toast.LENGTH_SHORT).show();
                // You can proceed with location-related tasks here if needed
                UserLocation.getInstance().getCurrentLocation();
            } else {
                // Permission denied
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
                // Handle the case where the user denied the permission
                // You might want to disable location-based features or show an explanation
            }
        }
    }
}