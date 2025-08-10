package com.example.rahmat_ux;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rahmat_ux.data.UserStorage;
import com.google.android.material.button.MaterialButton;

public class MoneyDonationVirtualAccountDetailActivity extends AppCompatActivity {

    private TextView textVA;
    private Button btnSalin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_money_donation_virtual_account_detail);
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
                Intent intent = new Intent(MoneyDonationVirtualAccountDetailActivity.this, MoneyDonationVirtualAccountActivity.class);
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

// AMOUNT DISPLAY
        TextView textAmount = findViewById(R.id.textAmount);
        String amount = getIntent().getStringExtra("amount");
        if (amount != null) {
            textAmount.setText("Rp" + formatCurrency(amount));
        }

// KONFIRMASI PEMBAYARAN
        MaterialButton btnKonfirmasi = findViewById(R.id.btnKonfirmasi);
        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoneyDonationVirtualAccountDetailActivity.this, MoneyDonationVirtualAccountDetail2Activity.class);
                intent.putExtra("bank_name", bankName); // pass the bank name
                intent.putExtra("amount", amount);       // pass the amount too
                startActivity(intent);
            }
        });

        textVA = findViewById(R.id.textVA);
        btnSalin = findViewById(R.id.btnSalin);

        btnSalin.setOnClickListener(v -> {
            String accountNumber = textVA.getText().toString();
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Virtual Account", accountNumber);
            clipboard.setPrimaryClip(clip);

            Toast.makeText(this, "Nomor rekening disalin!", Toast.LENGTH_SHORT).show();
        });

    }

    private String formatCurrency(String amount) {
        try {
            long number = Long.parseLong(amount.replace(".", "").replace(",", ""));
            return String.format("%,d", number).replace(',', '.');
        } catch (NumberFormatException e) {
            return amount;
        }
    }
}