package com.example.hike_with_me_client.Controller.Activites;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hike_with_me_client.Controller.Fragments.MainActivityFragments.MapsFragment;
import com.example.hike_with_me_client.Interfaces.Activities.GoToLoginActivityCallback;
import com.example.hike_with_me_client.R;
import com.example.hike_with_me_client.Models.User.UserMethods;
import com.example.hike_with_me_client.Utils.CurrentUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button logoutButton;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    private MapsFragment mapsFragment;

    GoToLoginActivityCallback goToLoginActivityCallback = new GoToLoginActivityCallback() {
        @Override
        public void goToLoginActivityCallback() {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set direction on all devices from LEFT to RIGHT
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        initialization();

        checkingCurrentUser();

        createFragments();

        defaultFragment();
    }

    private void logoutButtonFunctionality() {
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                CurrentUser.getInstance().removeUser();
                goToLoginActivityCallback.goToLoginActivityCallback();
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void checkingCurrentUser() {
        if (currentUser == null) {
            goToLoginActivityCallback.goToLoginActivityCallback();
        } else {
            Log.d("pttt", "User is logged in: " + currentUser);
            textView.setText("User is logged in: " + currentUser.getUid());
            UserMethods.getSpecificUser(currentUser.getUid());
        }
    }

    private void createFragments() {
        mapsFragment = new MapsFragment();
    }

    private void defaultFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, mapsFragment).commit();
    }

    private void initialization() {
        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();

        textView = findViewById(R.id.textView);

        logoutButton = findViewById(R.id.btn_logout);

        logoutButtonFunctionality();

    }
}