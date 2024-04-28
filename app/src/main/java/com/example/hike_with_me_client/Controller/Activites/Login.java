package com.example.hike_with_me_client.Controller.Activites;

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

import com.example.hike_with_me_client.R;
import com.example.hike_with_me_client.Utils.Constants;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Login extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    Button buttonLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView registerNow;
//    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        findViews();

        registerFunctionality();

        buttonLoginFunctionality();

//        Constants.CRUDExamples();
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
//        textView = findViewById(R.id.logInTextView);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressBar);
        registerNow = findViewById(R.id.registerNow);
    }
}