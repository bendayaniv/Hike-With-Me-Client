package com.example.hike_with_me_client.Controller.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hike_with_me_client.Controller.Fragments.LoginFragments.LoginFragment;
import com.example.hike_with_me_client.Controller.Fragments.LoginFragments.RegisterFragment;
import com.example.hike_with_me_client.Interfaces.Activities.GoToMainActivityCallback;
import com.example.hike_with_me_client.Interfaces.Fragments.LoginFragments.GoToLoginFragmentCallback;
import com.example.hike_with_me_client.Interfaces.Fragments.LoginFragments.GoToRegisterFragmentCallback;
import com.example.hike_with_me_client.R;

public class LoginActivity extends AppCompatActivity {

    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;

    GoToLoginFragmentCallback goToLoginFragmentCallback = new GoToLoginFragmentCallback() {

        @Override
        public void goToLoginFragmentCallback() {
            getSupportFragmentManager().beginTransaction().replace(R.id.login_fragment_container, loginFragment).commit();
        }
    };

    GoToRegisterFragmentCallback goToRegisterFragmentCallback = new GoToRegisterFragmentCallback() {
        @Override
        public void goToRegisterFragmentCallback() {
            getSupportFragmentManager().beginTransaction().replace(R.id.login_fragment_container, registerFragment).commit();
        }
    };

    GoToMainActivityCallback goToMainActivityCallback = new GoToMainActivityCallback() {
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
}