package com.example.hike_with_me_client.Controller.Fragments.LoginFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.hike_with_me_client.Interfaces.Activities.Callback_GoToMainActivity;
import com.example.hike_with_me_client.Interfaces.Fragments.LoginFragments.Callback_GoToRegisterFragment;
import com.example.hike_with_me_client.R;
import com.example.hike_with_me_client.Utils.Constants;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginFragment extends Fragment {

    TextInputEditText editTextEmail, editTextPassword;
    Button buttonLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView registerNow;
    private Callback_GoToRegisterFragment goToRegisterFragmentCallback;
    private Callback_GoToMainActivity goToMainActivityCallback;

    public void setCallbacks(Callback_GoToRegisterFragment callback, Callback_GoToMainActivity goToMainActivityCallback) {
        this.goToRegisterFragmentCallback = callback;
        this.goToMainActivityCallback = goToMainActivityCallback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mAuth = FirebaseAuth.getInstance();

        findViews(view);

        registerFunctionality();

        buttonLoginFunctionality();

        return view;
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
                    if (task.isSuccessful()) {
                        Constants.toastMessageToUserWithProgressBar(getActivity(), "Login successful", progressBar);

                        // Move to the main activity
                        goToMainActivityCallback.goToMainActivityCallback();

                    } else {
                        Log.d("Login", "onComplete: " + Objects.requireNonNull(task.getException()).getMessage());
                        // If sign in fails, display a message to the user.
                        Constants.toastMessageToUserWithProgressBar(getActivity(), "Login failed! Please try again", progressBar);
                    }
                });
    }

    private int checkingLogin(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            Constants.toastMessageToUserWithProgressBar(getActivity(), "Please enter email...", progressBar);
            return 1;
        }

        if(TextUtils.isEmpty(password)) {
            Constants.toastMessageToUserWithProgressBar(getActivity(), "Please enter password...", progressBar);
            return 1;
        }
        return 0;
    }

    private void registerFunctionality() {
        registerNow.setOnClickListener(v -> {
            goToRegisterFragmentCallback.goToRegisterFragmentCallback();
        });
    }

    private void findViews(View view) {
        editTextEmail = view.findViewById(R.id.email);
        editTextPassword = view.findViewById(R.id.password);
        buttonLogin = view.findViewById(R.id.btn_login);
        progressBar = view.findViewById(R.id.progressBar);
        registerNow = view.findViewById(R.id.registerNow);
    }
}