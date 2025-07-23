package com.example.rahmat_ux;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.rahmat_ux.databinding.ActivityAddEditItemBinding;

public class AddEditItemActivity extends AppCompatActivity {

    private ActivityAddEditItemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Tombol back di toolbar akan kembali ke Detail Donasi
        binding.btnBack.setOnClickListener(v -> onBackPressed());

        // Tombol "Tambahkan Barang"
        binding.buttonSubmit.setOnClickListener(v -> {
            // 1. Pindah ke halaman Summary
            Intent intent = new Intent(AddEditItemActivity.this, GoodsDonationSummaryActivity.class);
            startActivity(intent);

            // 2. Tutup halaman ini agar tidak ada di riwayat back stack
            finish();
        });
    }
}
