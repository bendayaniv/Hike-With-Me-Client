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

import com.example.hike_with_me_client.Interfaces.Activities.GoToMainActivityCallback;
import com.example.hike_with_me_client.Interfaces.Fragments.LoginFragments.GoToLoginFragmentCallback;
import com.example.hike_with_me_client.Models.User.User;
import com.example.hike_with_me_client.Models.User.UserMethods;
import com.example.hike_with_me_client.R;
import com.example.hike_with_me_client.Utils.Constants;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegisterFragment extends Fragment {

    TextInputEditText editTextEmail, editTextPassword, editTextName, editTextPhoneNumber;
    Button buttonRegister;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView loginNow;
    private GoToLoginFragmentCallback goToLoginFragmentCallback;
    private GoToMainActivityCallback goToMainActivityCallback;

    public void setCallbacks(GoToLoginFragmentCallback goToLoginFragmentCallback, GoToMainActivityCallback goToMainActivityCallback) {
        this.goToLoginFragmentCallback = goToLoginFragmentCallback;
        this.goToMainActivityCallback = goToMainActivityCallback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        mAuth = FirebaseAuth.getInstance();

        findViews(view);

        loginFunctionality();

        buttonRegisterFunctionality();

        return view;
    }

    private void buttonRegisterFunctionality() {
        buttonRegister.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            String email = String.valueOf(editTextEmail.getText());
            String password = String.valueOf(editTextPassword.getText());
            String textName = String.valueOf(editTextName.getText());
            String phoneNumber = String.valueOf(editTextPhoneNumber.getText());

            if(checkingRegisterInformation(email, password, textName, phoneNumber) == 1) return;

            registerInFirebaseAuth(email, password, textName, phoneNumber);
        });
    }

    private int checkingRegisterInformation(String email, String password, String textName, String phoneNumber) {
        Object[][] registerInformation = {
                {email, Constants.EMPTY_EMAIL},
                {password, Constants.EMPTY_PASSWORD, Constants.INVALID_PASSWORD_LENGTH},
                {textName, Constants.EMPTY_NAME},
                {phoneNumber, Constants.EMPTY_PHONE_NUMBER, Constants.INVALID_PHONE_NUMBER}
        };

        for (Object[] objects : registerInformation) {
            if (TextUtils.isEmpty((String) objects[0])) {
                Constants.toastMessageToUserWithProgressBar(getActivity(), (String) objects[1], progressBar);
                return 1;
            }
            if(((String) objects[1]).contains(Constants.EMAIL) && ((String) objects[0]).length() < 6) {
                Constants.toastMessageToUserWithProgressBar(getActivity(), (String) objects[2], progressBar);
                return 1;
            }
            if(((String) objects[1]).contains(Constants.PHONE_NUMBER) && !((String) objects[0]).matches("[0-9]+")) {
                Constants.toastMessageToUserWithProgressBar(getActivity(), (String) objects[2], progressBar);
                return 1;
            }
        }
        return 0;
    }

    private void registerInFirebaseAuth(String email, String password, String textName, String phoneNumber) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Constants.toastMessageToUserWithProgressBar(getActivity(), "User created successfully", progressBar);

                        // Create new user at the Realtime Database
                        User user = new User(Objects.requireNonNull(mAuth.getCurrentUser()).getUid(), textName, email, password, phoneNumber);
                        UserMethods.addUser(user);

                        // Move to the main activity
                        goToMainActivityCallback.goToMainActivityCallback();

                    } else {
                        Log.d("pttt", "createUserWithEmail:failure", task.getException());
                        // If sign up fails, display a message to the user.
                        Constants.toastMessageToUserWithProgressBar(getActivity(), "Authentication failed", progressBar);
                    }
                });
    }

    private void loginFunctionality() {
        loginNow.setOnClickListener(v -> {
            goToLoginFragmentCallback.goToLoginFragmentCallback();
        });
    }

    private void findViews(View view) {
        editTextEmail = view.findViewById(R.id.email);
        editTextPassword = view.findViewById(R.id.password);
        editTextName = view.findViewById(R.id.name);
        editTextPhoneNumber = view.findViewById(R.id.phoneNumber);
        buttonRegister = view.findViewById(R.id.btn_register);
        progressBar = view.findViewById(R.id.progressBar);
        loginNow = view.findViewById(R.id.loginNow);
    }
}