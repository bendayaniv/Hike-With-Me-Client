package com.example.hike_with_me_client;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hike_with_me_client.Hazard.Hazard;
import com.example.hike_with_me_client.Hazard.HazardMethods;
import com.example.hike_with_me_client.Trip.Trip;
import com.example.hike_with_me_client.Trip.TripMethods;
import com.example.hike_with_me_client.Utils.Constants;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Login extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    Button buttonLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView registerNow;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        findViews();

        registerFunctionality();

        buttonLoginFunctionality();

        CRUDExamples();
    }

    private void CRUDExamples() {
        textView = findViewById(R.id.logInTextView);

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
//        Hazard hazard = new Hazard("5", Constants.HazardType.PLANTS, "description", Constants.Level.LOW, "userName", "routeName2", formattedDate);
//        HazardMethods.addHazard(hazard, textView);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Getting trips by user
//        TripMethods.getTripsByUser("userId", textView);

        // Creating a new trip
//        Trip trip = new Trip("21", "name", "startDate", "endDate", "location", "description", "routeName", "userId2");
//        TripMethods.createTrip(trip, textView);

        // Update trip
        Trip trip = new Trip("21", "name", "startDate", "newEndDate", "location", "description", "routeName", "userId2");
        TripMethods.updateTrip(trip, textView);

        // Delete trip
//        TripMethods.deleteTrip("email1password1", "20", textView);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    }

    private void buttonLoginFunctionality() {
        buttonLogin.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            String email = String.valueOf(editTextEmail.getText());
            String password = String.valueOf(editTextPassword.getText());

            if(checkingLogin(email, password) == 1) return;

            signInWithFirebaseAuth(email, password);
        });
    }

    private void signInWithFirebaseAuth(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Toast.makeText(Login.this, "Login successful", Toast.LENGTH_LONG).show();

                        // Move to the main activity
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Log.d("Login", "onComplete: " + Objects.requireNonNull(task.getException()).getMessage());
                        // If sign in fails, display a message to the user.
                        Toast.makeText(Login.this, "Login failed! Please try again", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private int checkingLogin(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            Constants.toastMessageToUserWithProgressBar(Login.this, "Please enter email...", progressBar);
            return 1;
        }

        if(TextUtils.isEmpty(password)) {
            Constants.toastMessageToUserWithProgressBar(Login.this, "Please enter password...", progressBar);
            return 1;
        }
        return 0;
    }

    private void registerFunctionality() {
        registerNow.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Register.class);
            startActivity(intent);
            finish();
        });
    }

    private void findViews() {
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressBar);
        registerNow = findViewById(R.id.registerNow);
    }
}