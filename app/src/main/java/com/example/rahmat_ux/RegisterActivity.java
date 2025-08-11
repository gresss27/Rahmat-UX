package com.example.rahmat_ux;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rahmat_ux.model.User;
import com.example.rahmat_ux.data.UserStorage;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private EditText etUsername, etEmail, etPassword, etPasswordConfirm, etPekerjaan, etphoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get input fields
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPasswordConfirm = findViewById(R.id.etPasswordConfirm);
        etPekerjaan = findViewById(R.id.etPekerjaan);
        etphoneNumber=findViewById(R.id.etphoneNumber);

        LinearLayout btnRegister = findViewById(R.id.btnRegister); // Make sure you have this ID in XML
        TextView toLogin=findViewById(R.id.redirectToLogin);

        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString();
            String confirmPassword = etPasswordConfirm.getText().toString();
            String pekerjaan=etPekerjaan.getText().toString();
            String phoneNumber=etphoneNumber.getText().toString();

            // Validation
            if (username.isEmpty()) {
                Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (pekerjaan.isEmpty()) {
                Toast.makeText(this, "Pekerjaan is required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
                return;
            }

            if (phoneNumber.isEmpty()) {
                Toast.makeText(this, "Phone number is required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.isEmpty()) {
                Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Password confirmation doesn't match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Register user (default balance = 100000, donationPerSwipe = 5000)
            User newUser = new User(username, pekerjaan,email,phoneNumber, password, 0, 10000,"");
            UserStorage.getInstance().addUser(newUser);
            UserStorage.getInstance().authenticate(email,password);

            Toast.makeText(this, "Registered!", Toast.LENGTH_LONG).show();
//            Toast.makeText(this, String.valueOf(UserStorage.getInstance().getLoggedInUser().getBalance()), Toast.LENGTH_SHORT).show();
            User nowUser=UserStorage.getInstance().findUserbyEmail(email);
//            if(nowUser!=null && nowUser.getEmail()!=null){
//                Toast.makeText(this, "ada nih bwang", Toast.LENGTH_SHORT).show();
//            }
            // Go to MainActivity
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        });

        toLogin.setOnClickListener(vi->{
            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}