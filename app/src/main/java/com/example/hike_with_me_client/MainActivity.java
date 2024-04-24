package com.example.hike_with_me_client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.hike_with_me_client.Recommendation.Actions.AddRecommendation;
import com.example.hike_with_me_client.Recommendation.Actions.GetRecommendationsByRoute;
import com.example.hike_with_me_client.Recommendation.Callbacks.Callback_AddRecommendation;
import com.example.hike_with_me_client.Recommendation.Callbacks.Callback_GetRecommendationsByRoute;
import com.example.hike_with_me_client.Recommendation.Recommendation;
import com.example.hike_with_me_client.Route.Route;
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

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

//        // Getting all users
//        Callback_GetAllUsers callback_getAllUsers = new Callback_GetAllUsers() {
//            @Override
//            public void success(List<User> users) {
//                if(users.size() == 0) {
//                    textView.setText("No users found");
//                } else {
//                    textView.setText("Users found: " + users);
//
//                }
//            }
//
//            @Override
//            public void error(String message) {
//                Log.d("pttt", "Error: " + message);
//                textView.setText("Error: " + message);
//            }
//        };
//        new GetAllUsers(callback_getAllUsers).getAllUsers();

//        // Getting specific user
//        Callback_GetUser callback_getUser = new Callback_GetUser() {
//            @Override
//            public void success(User user) {
//                textView.setText("User found: " + user);
//            }
//
//            @Override
//            public void error(String message) {
//                Log.d("pttt", "Error: " + message);
//                textView.setText("Error: " + message);
//            }
//        };
//        new GetUser(callback_getUser).getUser("bendayaniv@gmailcomShalva");

        // Add new user
//        User user = new User("Ben Dayan", "bendayaniv@gmailcomShalva", "Shalva", "1234567890");
//        Callback_AddUser callback_addUser = new Callback_AddUser() {
//            @Override
//            public void success(User user) {
//                textView.setText("User added: " + user);
//            }
//
//            @Override
//            public void error(String message) {
//                Log.d("pttt", "Error: " + message);
//                textView.setText("Error: " + message);
//            }
//        };
//        new AddUser(callback_addUser).addUser(user);

//        // Update user
//        User user = new User("Ben Dayan", "bendayaniv@gmailcomShalva", "Shalva", "1234567890");
//        Callback_UpdateUser callback_updateUser = new Callback_UpdateUser() {
//            @Override
//            public void success(User user) {
//                textView.setText("User updated: " + user);
//            }
//
//            @Override
//            public void error(String message) {
//                Log.d("pttt", "Error: " + message);
//                textView.setText("Error: " + message);
//            }
//        };
//        new UpdateUser(callback_updateUser).updateUser("bendayaniv@gmailcomShalvaShalva", user.name, user.email, "Shalva222", user.phoneNumber);

//        // Delete user
//        Callback_DeleteUser callback_deleteUser = new Callback_DeleteUser() {
//            @Override
//            public void success(User user) {
//                textView.setText("User deleted: " + user);
//            }
//
//            @Override
//            public void error(String message) {
//                Log.d("pttt", "Error: " + message);
//                textView.setText("Error: " + message);
//            }
//        };
//        new DeleteUser(callback_deleteUser).deleteUser("bendayaniv@gmailcomShalvaShalva");


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Getting all recommendations by route
//        Callback_GetRecommendationsByRoute callback_getAllRecommendationsByRoute = new Callback_GetRecommendationsByRoute() {
//            @Override
//            public void success(List<Recommendation> recommendations) {
//                if(recommendations.size() == 0) {
//                    textView.setText("No recommendations found");
//                } else {
//                    textView.setText("Recommendations found: " + recommendations);
//
//                }
//            }
//
//            @Override
//            public void error(String message) {
//                Log.d("pttt", "Error: " + message);
//                textView.setText("Error: " + message + "\nNo recommendations found");
//            }
//        };
//        new GetRecommendationsByRoute(callback_getAllRecommendationsByRoute).getRecommendationsByRoute("routeName");

        // Adding recommendation
//        Recommendation recommendation = new Recommendation("4", 4, "description", new User().setEmail("email").setPassword("password"), new Route().setName("routeName"));
//        Callback_AddRecommendation callback_addRecommendation = new Callback_AddRecommendation() {
//            @Override
//            public void success(Recommendation recommendation) {
//                textView.setText("Recommendation added: " + recommendation);
//            }
//
//            @Override
//            public void error(String message) {
//                Log.d("pttt", "Error: " + message);
//                textView.setText("Error: " + message);
//            }
//        };
//        new AddRecommendation(callback_addRecommendation).addRecommendation(recommendation);

    }
}