package com.example.hike_with_me_client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.hike_with_me_client.User.Actions.GetAllUsers;
import com.example.hike_with_me_client.User.Callbacks.Callback_GetAllUsers;
import com.example.hike_with_me_client.User.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        Callback_GetAllUsers callback_getAllUsers = new Callback_GetAllUsers() {
            @Override
            public void success(List<User> users) {
                if(users.size() == 0) {
                    textView.setText("No users found");
                } else {
                    textView.setText("Users found: " + users);

                }
            }

            @Override
            public void error(String message) {
                Log.d("pttt", "Error: " + message);
                textView.setText("Error: " + message);
            }
        };
        new GetAllUsers(callback_getAllUsers).getAllUsers();
    }
}