package com.example.hike_with_me_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hike_with_me_client.User.User;
import com.example.hike_with_me_client.User.UserMethods;
import com.example.hike_with_me_client.Utils.Constants;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Register extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword, editTextName, editTextPhoneNumber;
    Button buttonRegister;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView loginNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        findViews();

        loginFunctionality();

        buttonRegisterFunctionality();
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
                Constants.toastMessageToUserWithProgressBar(Register.this, (String) objects[1], progressBar);
                return 1;
            }
            if(((String) objects[1]).contains(Constants.EMAIL) && ((String) objects[0]).length() < 6) {
                Constants.toastMessageToUserWithProgressBar(Register.this, (String) objects[2], progressBar);
                return 1;
            }
            if(((String) objects[1]).contains(Constants.PHONE_NUMBER) && !((String) objects[0]).matches("[0-9]+")) {
                Constants.toastMessageToUserWithProgressBar(Register.this, (String) objects[2], progressBar);
                return 1;
            }
        }
        return 0;
    }

    private void registerInFirebaseAuth(String email, String password, String textName, String phoneNumber) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Toast.makeText(Register.this, "User created successfully", Toast.LENGTH_LONG).show();

                        // Create new user at the Realtime Database
                        User user = new User(Objects.requireNonNull(mAuth.getCurrentUser()).getUid(), textName, email, password, phoneNumber);
                        UserMethods.addUser(user);

                        // Move to the main activity
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Log.d("pttt", "createUserWithEmail:failure", task.getException());
                        // If sign up fails, display a message to the user.
                        Toast.makeText(Register.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loginFunctionality() {
        loginNow.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });
    }

    private void findViews() {
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextName = findViewById(R.id.name);
        editTextPhoneNumber = findViewById(R.id.phoneNumber);
        buttonRegister = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressBar);
        loginNow = findViewById(R.id.loginNow);
    }
}