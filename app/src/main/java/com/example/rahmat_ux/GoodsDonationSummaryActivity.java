package com.example.rahmat_ux;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.rahmat_ux.databinding.ActivityGoodsDonationSummaryBinding;

public class GoodsDonationSummaryActivity extends AppCompatActivity {

    private ActivityGoodsDonationSummaryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGoodsDonationSummaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Hapus pemanggilan setupRecyclerView() karena sudah tidak ada
        // setupRecyclerView();

        setupClickListeners();
    }

    private void setupClickListeners() {
        // Tombol kembali di toolbar
        binding.btnBack.setOnClickListener(v -> onBackPressed());

        // Tombol Ganti Drop Point
        binding.buttonChangeDropPoint.setOnClickListener(v -> {
            Toast.makeText(this, "Buka halaman pilih drop point...", Toast.LENGTH_SHORT).show();
            // new DropPointSelectionFragment().show(getSupportFragmentManager(), "DropPointSheet");
        });

        // Tombol Tambah Item (tetap sama)
        binding.buttonAddItem.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddEditItemActivity.class);
            startActivity(intent);
        });

        // Listener untuk tombol Edit yang sekarang statis
        binding.buttonEdit1.setOnClickListener(v -> openEditActivity("Item 1"));
        binding.buttonEdit2.setOnClickListener(v -> openEditActivity("Item 2"));
        binding.buttonEdit3.setOnClickListener(v -> openEditActivity("Item 3"));


        // Tombol Donasi Sekarang
        binding.buttonDonateNow.setOnClickListener(v -> {
            // Logika untuk menampilkan bottom sheet konfirmasi
            // showConfirmationBottomSheet();
            Toast.makeText(this, "Proses donasi...", Toast.LENGTH_SHORT).show();
        });
    }

    // Helper method untuk membuka halaman edit
    private void openEditActivity(String itemName) {
        Toast.makeText(this, "Mengedit " + itemName, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AddEditItemActivity.class);
        // Anda bisa mengirim data item yang akan diedit di sini
        // intent.putExtra("ITEM_NAME", itemName);
        startActivity(intent);
    }
}
