package com.example.hike_with_me_client.Utils;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Constants {

    public static final int MENU_HOME = 2131231024;
    public static final int MENU_TRIPS = 2131231026;
    public static final int MENU_COMMUNITY = 2131231023;
    public static final int MENU_PROFILE = 2131231025;

    public static final int REQUEST_CHECK_SETTINGS = 10001;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 10002;
    public static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 10003;

    public static final long RETRY_INTERVAL = 1500;

    public enum HazardType {
        ROCKS,
        WATER,
        ANIMALS,
        PLANTS,
        OTHER
    }

    public enum Level {
        LOW,
        MEDIUM,
        HIGH
    }

    public static final String EMAIL = "email";
    public static final String EMPTY_EMAIL = "Please enter email...";
    public static final String EMPTY_PASSWORD = "Please enter password...";
    public static final String INVALID_PASSWORD_LENGTH = "Password must be at least 6 characters...";
    public static final String EMPTY_NAME = "Please enter name...";
    public static final String PHONE_NUMBER = "phone number";
    public static final String EMPTY_PHONE_NUMBER = "Please enter phone number...";
    public static final String INVALID_PHONE_NUMBER = "Phone number must contain only numbers...";

    public static void toastMessageToUserWithProgressBar(Context context, String message, ProgressBar progressBar) {
        if (progressBar != null) progressBar.setVisibility(View.GONE);
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
