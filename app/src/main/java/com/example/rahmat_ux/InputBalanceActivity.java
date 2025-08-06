package com.example.rahmat_ux;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class InputBalanceActivity extends AppCompatActivity {

    private String bankName;
    private int bankImage;
    private EditText editTextSaldo;
    private Button selectedQuickButton = null; // NEW: track selected button

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

        // Back button
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> onBackPressed());

        // Get bank data from intent
        bankName = getIntent().getStringExtra("selected_bank_name");
        bankImage = getIntent().getIntExtra("selected_bank_image", R.drawable.bank_bca);

        // References
        editTextSaldo = findViewById(R.id.editTextSaldo);
        GridLayout gridLayout = findViewById(R.id.quick_choice_grid);
        MaterialButton btnLanjut = findViewById(R.id.btnLanjut);

        // Currency formatting
        editTextSaldo.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    editTextSaldo.removeTextChangedListener(this);

                    String cleanString = s.toString().replace(".", "").replace(",", "");
                    if (!cleanString.equals("")) {
                        try {
                            long parsed = Long.parseLong(cleanString);
                            DecimalFormat formatter = new DecimalFormat("#,###");
                            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.GERMANY);
                            symbols.setGroupingSeparator('.');
                            formatter.setDecimalFormatSymbols(symbols);
                            String formatted = formatter.format(parsed);

                            current = formatted;
                            editTextSaldo.setText(formatted);
                            editTextSaldo.setSelection(formatted.length());
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }

                    editTextSaldo.addTextChangedListener(this);
                }
            }
        });

        // Quick choice buttons highlight
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View child = gridLayout.getChildAt(i);
            if (child instanceof Button) {
                Button btn = (Button) child;
                btn.setOnClickListener(v -> {
                    String tag = v.getTag().toString();
                    editTextSaldo.setText(tag);

                    // Reset previous button
                    if (selectedQuickButton != null) {
                        selectedQuickButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.white));
                        selectedQuickButton.setTextColor(ContextCompat.getColor(this, R.color.black));
                    }

                    // Highlight current
                    btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.green_primary));
                    btn.setTextColor(ContextCompat.getColor(this, R.color.white));

                    selectedQuickButton = btn;
                });
            }
        }

        // Handle "Lanjut"
        btnLanjut.setOnClickListener(v -> {
            String amount = editTextSaldo.getText().toString().trim();

            if (amount.isEmpty()) {
                editTextSaldo.setError("Jumlah tidak boleh kosong");
                return;
            }

            Intent intent = new Intent(InputBalanceActivity.this, TopUpDetailActivity.class);
            intent.putExtra("selected_bank_name", bankName);
            intent.putExtra("selected_bank_image", bankImage);
            intent.putExtra("amount", amount.replace(".", "")); // remove dots
            startActivity(intent);
        });
    }
}
