package com.example.rahmat_ux;

import android.content.Intent; // <-- Tambahkan import ini
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.rahmat_ux.databinding.ActivityDeliveryDetailsBinding;

public class DeliveryDetailsActivity extends AppCompatActivity {

    private ActivityDeliveryDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeliveryDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Tombol panah di toolbar juga bisa menggunakan logika yang sama
        binding.toolbar.setNavigationOnClickListener(v -> returnToDonationDetail());

        // Tombol "Kembali" utama di bagian bawah
        binding.buttonBackToHome.setOnClickListener(v -> returnToDonationDetail());
    }

    private void returnToDonationDetail() {
        // 1. Buat Intent untuk kembali ke DonationDetailActivity
        Intent intent = new Intent(DeliveryDetailsActivity.this, DonationDetailActivity.class);

        // 2. Tambahkan flag untuk membersihkan semua activity di atasnya
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        // 3. Mulai activity
        startActivity(intent);

        // 4. Tutup activity saat ini
        finish();
    }
}