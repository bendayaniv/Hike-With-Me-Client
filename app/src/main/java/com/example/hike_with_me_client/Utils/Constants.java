package com.example.hike_with_me_client.Utils;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Constants {

    public static final int FINE_PERMISSION_CODE = 1;
    public static final int REQUEST_CHECK_SETTINGS = 10001;
    public static final int REQUEST_CODE = 10002;
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
    public static final String PASSWORD = "password";
    public static final String EMPTY_PASSWORD = "Please enter password...";
    public static final String INVALID_PASSWORD_LENGTH = "Password must be at least 6 characters...";
    public static final String NAME = "name";
    public static final String EMPTY_NAME = "Please enter name...";
    public static final String PHONE_NUMBER = "phone number";
    public static final String EMPTY_PHONE_NUMBER = "Please enter phone number...";
    public static final String INVALID_PHONE_NUMBER = "Phone number must contain only numbers...";


    public static void toastMessageToUserWithProgressBar(Context context, String message, ProgressBar progressBar) {
        if(progressBar != null) progressBar.setVisibility(View.GONE);
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void CRUDExamples() {
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Getting all users
//        UserMethods.getAllUsers(textView);

        // Getting specific user
//        UserMethods.getSpecificUser("bendayaniv@gmailcomShalva", textView);

        // Add new user
//        User user = new User("Ben Dayan", "bendayaniv@gmailcomShalva", "Shalva", "1234567890");
//        UserMethods.addUser(user, textView);

        // Update user
//        User user = new User("Ben Dayan", "bendayaniv@gmailcomShalva", "Shalva", "1234567890");
//        UserMethods.updateUser(user, textView);

        // Delete user
//        UserMethods.deleteUser("bendayaniv@gmailcomShalvaShalva", textView);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Getting all recommendations by route
//        RecommendationMethods.getRecommendationsByRoute("routeName2", textView);

        // Adding recommendation
//        Recommendation recommendation = new Recommendation("6", 4, "description", "reporterName", "routeName2");
//        RecommendationMethods.addRecommendation(recommendation, textView);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Getting all hazards by route
//        HazardMethods.getHazardsByRoute("routeName", textView);

        // Adding hazard
//        Date currentDate = new Date();
//        // Format the date as needed (e.g., "dd-MM-yyyy" for day-month-year format)
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        String formattedDate = dateFormat.format(currentDate);
//        Hazard hazard = new Hazard("5", Constants.HazardType.PLANTS, "description", Constants.Level.LOW, "userName", "routeName2", new Location(32.123, 35.123, currentDate));
//        HazardMethods.addHazard(hazard, textView);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Getting all routes
//        RouteMethods.getAllRoutes(textView);

        // Getting route by name
//        RouteMethods.getSpecificRoute("name", textView);

        // Adding route
//        Route route = new Route("10", "name4", "description", Constants.Level.LOW, "length", new Location(32.123, 35.123, null));
//        RouteMethods.addRoute(route, textView);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Getting trips by user
//        TripMethods.getTripsByUser("userId", textView);

        // Creating a new trip
//        Trip trip = new Trip("21", "name", "startDate", "endDate", new Location[]{}, "description", "routeName", CurrentUser.getInstance().getUser().getId());
//        TripMethods.createTrip(trip);

        // Update trip
//        TripMethods.getSpecificTrip(CurrentUser.getInstance().getUser().getId(), "2");
//        //add location to locations in trip
//        trip.addLocation(new Location(32.123, 35.123, null));
//        //add another 3 locations locations to existing locations in trip
//        trip.addLocation(new Location(32.123, 35.123, null));
//        trip.addLocation(new Location(32.123, 35.123, null));
//        trip.addLocation(new Location(32.123, 35.123, null));
//        TripMethods.updateTrip(trip, textView);

        // Delete trip
//        Trip trip = new Trip().setId("20").setUserId("userId2");
//        TripMethods.deleteTrip(trip.getUserId(), trip.getId(), textView);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
}
