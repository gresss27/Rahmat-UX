package com.example.rahmat_ux;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class InputBalanceActivity extends AppCompatActivity {

    private String bankName;
    private int bankImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_input_balance);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //        BACK BUTTON
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Navigates back to the previous screen
            }
        });

        // Get bank data from intent
        bankName = getIntent().getStringExtra("selected_bank_name");
        bankImage = getIntent().getIntExtra("selected_bank_image", R.drawable.bank_bca); // default

        // Handle button click
        MaterialButton btnLanjut = findViewById(R.id.btnLanjut);
        btnLanjut.setOnClickListener(v -> {
            Intent intent = new Intent(InputBalanceActivity.this, TopUpDetailActivity.class);
            intent.putExtra("selected_bank_name", bankName);
            intent.putExtra("selected_bank_image", bankImage);
            startActivity(intent);
        });
    }
}
