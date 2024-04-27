package com.example.hike_with_me_client;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hike_with_me_client.User.UserMethods;
import com.example.hike_with_me_client.Utils.CurrentUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button logoutButton;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();

        checkingCurrentUser();

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // CRUD Examples

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
//        Trip trip = new Trip("20", "name", "startDate", "endDate", "location", "description", new Route().setName("route"), new User().setEmail("email1").setPassword("password1"));
//        TripMethods.updateTrip(trip, textView);

        // Delete trip
//        TripMethods.deleteTrip("email1password1", "20", textView);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    private void logoutButtonFunctionality() {
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                CurrentUser.getInstance().removeUser();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void checkingCurrentUser() {
        if (currentUser == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else {
            Log.d("pttt", "User is logged in: " + currentUser);
            textView.setText("User is logged in: " + currentUser.getUid());
            UserMethods.getSpecificUser(currentUser.getUid());
        }
    }

    private void initialization() {
        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();

        textView = findViewById(R.id.textView);

        logoutButton = findViewById(R.id.btn_logout);

        logoutButtonFunctionality();

    }
}