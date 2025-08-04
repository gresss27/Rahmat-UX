package com.example.rahmat_ux;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class TopUpDetail2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_top_up_detail2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        BANK NAME
        String bankName = getIntent().getStringExtra("bank_name");

        TextView textBankName = findViewById(R.id.textBankName);
        if (bankName != null) {
            textBankName.setText("Virtual Account " + bankName);  // or just bankName if it's already formatted
        }

        ImageView btnBack = findViewById(R.id.btnBack);
        MaterialButton btnKembali = findViewById(R.id.btnKembali);

        View.OnClickListener goToTopUpActivity = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopUpDetail2Activity.this, TopUpActivity.class);
                // Optional: clear back stack to prevent returning to TopUpDetail2Activity
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // Optional: prevent going back to this activity
            }
        };

        btnBack.setOnClickListener(goToTopUpActivity);
        btnKembali.setOnClickListener(goToTopUpActivity);

    }
}