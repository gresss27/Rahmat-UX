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

public class TopUpDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_top_up_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        CROSS BUTTON
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopUpDetailActivity.this, TopUpActivity.class);
                // Optional: clear the back stack so InputBalanceActivity is not revisited
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish(); // close TopUpDetailActivity
            }
        });

        // Get data from intent
        String bankName = getIntent().getStringExtra("selected_bank_name");

        // Set it to TextView
        TextView bankText = findViewById(R.id.textBankName);
        if (bankName != null) {
            bankText.setText("Virtual Account " + bankName);
        }

//        KONFIRMASI PEMBAYARAN
        MaterialButton btnKonfirmasi = findViewById(R.id.btnKonfirmasi);
        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopUpDetailActivity.this, TopUpDetail2Activity.class);
                intent.putExtra("bank_name", bankName); // pass the bank name
                startActivity(intent);
            }
        });

    }
}
