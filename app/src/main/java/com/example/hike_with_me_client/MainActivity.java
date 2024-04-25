package com.example.hike_with_me_client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.hike_with_me_client.Hazard.Hazard;
import com.example.hike_with_me_client.Hazard.HazardMethods;
import com.example.hike_with_me_client.Recommendation.Actions.AddRecommendation;
import com.example.hike_with_me_client.Recommendation.Actions.GetRecommendationsByRoute;
import com.example.hike_with_me_client.Recommendation.Callbacks.Callback_AddRecommendation;
import com.example.hike_with_me_client.Recommendation.Callbacks.Callback_GetRecommendationsByRoute;
import com.example.hike_with_me_client.Recommendation.Recommendation;
import com.example.hike_with_me_client.Recommendation.RecommendationMethods;
import com.example.hike_with_me_client.Route.Route;
import com.example.hike_with_me_client.Route.RouteMethods;
import com.example.hike_with_me_client.Trip.Trip;
import com.example.hike_with_me_client.Trip.TripMethods;
import com.example.hike_with_me_client.User.Actions.AddUser;
import com.example.hike_with_me_client.User.Actions.DeleteUser;
import com.example.hike_with_me_client.User.Actions.GetAllUsers;
import com.example.hike_with_me_client.User.Actions.GetUser;
import com.example.hike_with_me_client.User.Actions.UpdateUser;
import com.example.hike_with_me_client.User.Callbacks.Callback_AddUser;
import com.example.hike_with_me_client.User.Callbacks.Callback_DeleteUser;
import com.example.hike_with_me_client.User.Callbacks.Callback_GetAllUsers;
import com.example.hike_with_me_client.User.Callbacks.Callback_GetUser;
import com.example.hike_with_me_client.User.Callbacks.Callback_UpdateUser;
import com.example.hike_with_me_client.User.User;
import com.example.hike_with_me_client.User.UserMethods;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

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
//        RecommendationMethods.getRecommendationsByRoute("routeName", textView);

        // Adding recommendation
//        Recommendation recommendation = new Recommendation("4", 4, "description", new User().setEmail("email").setPassword("password"), new Route().setName("routeName"));
//        RecommendationMethods.addRecommendation(recommendation, textView);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Getting all hazards by route
//        HazardMethods.getHazardsByRoute("route2", textView);

        // Adding hazard
//        Date currentDate = new Date();
//        // Format the date as needed (e.g., "dd-MM-yyyy" for day-month-year format)
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        String formattedDate = dateFormat.format(currentDate);
//        Hazard hazard = new Hazard("4", Constants.HazardType.PLANTS, "description", Constants.HazardSeverity.LOW, new User().setEmail("email").setPassword("password"), new Route().setName("route2"), formattedDate);
//        HazardMethods.addHazard(hazard, textView);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Getting all routes
//        RouteMethods.getAllRoutes(textView);

        // Getting route by name
//        RouteMethods.getSpecificRoute("name", textView);

        // Adding route
//        Route route = new Route("10", "name4", "description", Constants.Level.LOW, "length", "location");
//        RouteMethods.addRoute(route, textView);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Getting trips by user
//        TripMethods.getTripsByUser("email1password1", textView);

        // Creating a new trip
//        Trip trip = new Trip("20", "name", "startDate", "endDate", "location", "description", new Route().setName("route"), new User().setEmail("email1").setPassword("password1"));
//        TripMethods.createTrip(trip, textView);

        // Update trip
        Trip trip = new Trip("20", "name", "startDate", "endDate", "location", "description", new Route().setName("route"), new User().setEmail("email1").setPassword("password1"));
        TripMethods.updateTrip(trip, textView);

        // Delete trip
//        TripMethods.deleteTrip("email1password1", "20", textView);
    }
}