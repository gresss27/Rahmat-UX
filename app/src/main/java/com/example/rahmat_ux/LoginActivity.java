package com.example.rahmat_ux;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rahmat_ux.data.UserStorage;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private LinearLayout etLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etLogin = findViewById(R.id.btnLogin); // This is your LinearLayout acting as a button

        // Set click listener
        etLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString();

            // Validation

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.isEmpty()) {
                Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
                return;
            }

            // Authenticate user
            boolean loginSuccess = UserStorage.getInstance().authenticate(email, password);
            if (loginSuccess) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });

        TextView toRegister = findViewById(R.id.redirectToRegister);
        toRegister.setOnClickListener(vi -> {
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
            finish();
        });
    }
}