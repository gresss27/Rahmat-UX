package com.example.rahmat_ux; // Sesuaikan dengan package Anda

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
        Class<?> tujuanActivity = DonationDetailActivity.class; // <-- GANTI INI JIKA PERLU

// Listener untuk tombol kembali di toolbar
        binding.btnBack.setOnClickListener(v -> {
            Intent backIntent = new Intent(PengantaranActivity.this, tujuanActivity);
            // Tambahkan flag ini untuk membersihkan tumpukan activity di atasnya
            backIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(backIntent);
            finish(); // Tutup activity ini
        });

// Listener untuk tombol "Kembali" di bawah
        binding.buttonKembali.setOnClickListener(v -> {
            Intent backIntent = new Intent(PengantaranActivity.this, tujuanActivity);
            // Tambahkan flag ini untuk membersihkan tumpukan activity di atasnya
            backIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(backIntent);
            finish(); // Tutup activity ini
        });
    }

// Di dalam file PengantaranActivity.java

    private void populateUi(ArrayList<DonatedItem> items, DropPoint dropPoint) {
        if (items == null || dropPoint == null) {
            return;
        }

        // Set jumlah jenis barang
        binding.textItemCount.setText(items.size() + " jenis");

        // Hapus view lama jika ada
        binding.layoutDaftarBarang.removeAllViews();

        // Loop melalui daftar barang dan buat layout kompleks untuk setiap item
        for (DonatedItem item : items) {
            // Buat RelativeLayout sebagai container untuk satu baris
            RelativeLayout itemLayout = new RelativeLayout(this);
            itemLayout.setLayoutParams(new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            itemLayout.setPadding(0, 8, 0, 8); // Beri sedikit padding vertikal

            // Buat TextView untuk nama barang (di kiri)
            TextView nameTextView = new TextView(this);
            nameTextView.setText("• " + item.getName());
            nameTextView.setTextSize(14f);
            RelativeLayout.LayoutParams nameParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            nameParams.addRule(RelativeLayout.ALIGN_PARENT_START);
            nameParams.addRule(RelativeLayout.CENTER_VERTICAL);
            nameTextView.setLayoutParams(nameParams);

            // Buat TextView untuk jumlah barang (di kanan)
            TextView quantityTextView = new TextView(this);
            quantityTextView.setText(item.getQuantity());
            quantityTextView.setTextSize(14f);
            RelativeLayout.LayoutParams quantityParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            quantityParams.addRule(RelativeLayout.ALIGN_PARENT_END);
            quantityParams.addRule(RelativeLayout.CENTER_VERTICAL);
            quantityTextView.setLayoutParams(quantityParams);

            // Tambahkan kedua TextView ke dalam RelativeLayout
            itemLayout.addView(nameTextView);
            itemLayout.addView(quantityTextView);

            // Tambahkan RelativeLayout (satu baris lengkap) ke dalam container utama
            binding.layoutDaftarBarang.addView(itemLayout);
        }

        // Set info drop point
        binding.textDropPointName.setText(dropPoint.getName());
        binding.textDropPointAddress.setText(dropPoint.getAddress());
    }
}