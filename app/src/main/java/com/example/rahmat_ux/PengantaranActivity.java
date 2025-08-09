package com.example.rahmat_ux; // Sesuaikan dengan package Anda

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rahmat_ux.databinding.ActivityPengantaranBinding;
import com.example.rahmat_ux.model.DonatedItem;
import com.example.rahmat_ux.model.DropPoint;
import java.util.ArrayList;

public class PengantaranActivity extends AppCompatActivity {

    private ActivityPengantaranBinding binding;

    private static final String TAG = "PengantaranActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Activity dimulai."); // JEJAK A: Cek apakah activity berhasil terbuka

        binding = ActivityPengantaranBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Ambil data yang dikirim dari activity sebelumnya
        Intent intent = getIntent();
        ArrayList<DonatedItem> donatedItems = (ArrayList<DonatedItem>) intent.getSerializableExtra("DAFTAR_DONASI");
        DropPoint selectedDropPoint = (DropPoint) intent.getSerializableExtra("DROP_POINT_TERPILIH");
        if (donatedItems == null) {
            Log.e(TAG, "GAGAL TERIMA: Data 'DAFTAR_DONASI' yang diterima adalah NULL!");
        } else {
            Log.d(TAG, "DATA DITERIMA: Berhasil menerima " + donatedItems.size() + " item donasi.");
        }

        if (selectedDropPoint == null) {
            Log.e(TAG, "GAGAL TERIMA: Data 'DROP_POINT_TERPILIH' yang diterima adalah NULL!");
        } else {
            Log.d(TAG, "DATA DITERIMA: Berhasil menerima drop point '" + selectedDropPoint.getName() + "'.");
        }
        // Panggil method untuk mengisi UI dengan data
        populateUi(donatedItems, selectedDropPoint);

        // Set listener untuk tombol kembali
        binding.btnBack.setOnClickListener(v -> finish());
        binding.buttonKembali.setOnClickListener(v -> finish());
    }

    private void populateUi(ArrayList<DonatedItem> items, DropPoint dropPoint) {
        if (items == null || dropPoint == null) {
            // Handle jika data tidak ada
            return;
        }

        // Set jumlah jenis barang
        binding.textItemCount.setText(items.size() + " jenis");

        // Hapus view lama jika ada (untuk keamanan)
        binding.layoutDaftarBarang.removeAllViews();

        // Loop melalui daftar barang dan buat TextView untuk setiap item
        for (DonatedItem item : items) {
            TextView itemTextView = new TextView(this);
            // Format teks: • Nama Barang (Jumlah)
            itemTextView.setText("• " + item.getName() + " (" + item.getQuantity() + ")");
            itemTextView.setTextSize(14f); // Ukuran teks dalam sp

            // Atur margin atas untuk setiap item
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 8, 0, 0); // Margin atas 8dp
            itemTextView.setLayoutParams(params);

            // Tambahkan TextView ke dalam LinearLayout
            binding.layoutDaftarBarang.addView(itemTextView);
        }

        // Set info drop point
        binding.textDropPointName.setText(dropPoint.getName());
        binding.textDropPointAddress.setText(dropPoint.getAddress());
    }
}