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

//        Constants.CRUDExamples();
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