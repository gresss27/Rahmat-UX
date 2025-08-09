package com.example.rahmat_ux;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rahmat_ux.adapter.DonatedItemAdapter;
import com.example.rahmat_ux.databinding.ActivityGoodsDonationSummaryBinding;
import com.example.rahmat_ux.model.Campaign;
import com.example.rahmat_ux.model.DonatedItem;
import com.example.rahmat_ux.model.DropPoint;

import java.util.ArrayList;

public class GoodsDonationSummaryActivity extends AppCompatActivity implements KonfirmasiDonasiSheet.BottomSheetListener, PilihDropPointSheet.DropPointSelectionListener{
    private static final String TAG = "GoodsDonationSummary";

    private DropPoint selectedDropPoint;
    private ActivityGoodsDonationSummaryBinding binding;
    private ArrayList<DonatedItem> itemList;
    private DonatedItemAdapter adapter; // Asumsikan adapter sudah dibuat
    private ActivityResultLauncher<Intent> formLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent prevIntent = getIntent();
        itemList = (ArrayList<DonatedItem>) prevIntent.getSerializableExtra("INITIAL_LIST");
        if (itemList == null) {
            itemList = new ArrayList<>(); // Inisialisasi jika tidak ada data awal
        }
        Campaign campaign = (Campaign) prevIntent.getSerializableExtra("campaign");
        super.onCreate(savedInstanceState);
        binding = ActivityGoodsDonationSummaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (campaign != null) {
            binding.item1Name.setText(campaign.getItem1Name());
            binding.item2Name.setText(campaign.getItem2Name());
            binding.item3Name.setText(campaign.getItem3Name());
            binding.item1Progress.setProgress(campaign.getItem1Progress());
            binding.item2Progress.setProgress(campaign.getItem2Progress());
            binding.item3Progress.setProgress(campaign.getItem3Progress());
            binding.item1Percent.setText(campaign.getItem1Progress() + "%");
            binding.item2Percent.setText(campaign.getItem2Progress() + "%");
            binding.item3Percent.setText(campaign.getItem3Progress() + "%");
        }
        adapter = new DonatedItemAdapter(itemList);
        binding.recyclerViewDonasi.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewDonasi.setAdapter(adapter);

        adapter.setOnItemActionListener(new DonatedItemAdapter.OnItemActionListener() {
            @Override
            public void onEditClick(int position) {
                DonatedItem itemToEdit = itemList.get(position);
                Intent intent = new Intent(GoodsDonationSummaryActivity.this, AddEditItemActivity.class);
                intent.putExtra("BARANG_UNTUK_DIEDIT", itemToEdit);
                intent.putExtra("POSISI_EDIT", position);
                formLauncher.launch(intent); // Gunakan launcher
            }

            @Override
            public void onDeleteClick(int position) {
                // Tampilkan dialog konfirmasi sebelum menghapus
                new AlertDialog.Builder(GoodsDonationSummaryActivity.this)
                        .setTitle("Hapus Donasi")
                        .setMessage("Apakah Anda yakin ingin menghapus item '" + itemList.get(position).getName() + "'?")
                        .setPositiveButton("Hapus", (dialog, which) -> {
                            // Jika pengguna menekan "Hapus"
                            // 1. Hapus item dari list data
                            itemList.remove(position);
                            // 2. Beri tahu adapter bahwa item pada posisi tersebut telah dihapus
                            adapter.notifyItemRemoved(position);
                            // Optional: Tampilkan pesan konfirmasi
                            Toast.makeText(GoodsDonationSummaryActivity.this, "Item dihapus", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Batal", null) // Tidak melakukan apa-apa jika dibatalkan
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        formLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                (ActivityResult result) -> {
                    Log.d(TAG, "Launcher menerima sebuah hasil.");
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Log.d(TAG, "Hasilnya OK dan data tidak null."); // <-- LOG 5
                        // Ambil data barang yang dikembalikan
                        DonatedItem barangHasil = (DonatedItem) result.getData().getSerializableExtra("HASIL_BARANG");

                        if (barangHasil == null) {
                            Log.e(TAG, "ERROR: barangHasil null! Mungkin key intent salah?"); // <-- LOG ERROR
                            return;
                        }
                        Log.d(TAG, "Berhasil mendapatkan barang: " + barangHasil.getName()); // <-- LOG 6
                        // Cek apakah ini hasil edit atau tambah baru
                        int posisiEdit = result.getData().getIntExtra("POSISI_EDIT_KEMBALI", -1);

                        if (posisiEdit != -1) {
                            // Ini adalah HASIL EDIT
                            itemList.set(posisiEdit, barangHasil);
                            adapter.notifyItemChanged(posisiEdit);
                            Toast.makeText(this, "Item berhasil diperbarui", Toast.LENGTH_SHORT).show();
                        } else {
                            // Ini adalah HASIzL TAMBAH BARU
                            itemList.add(barangHasil);
                            adapter.notifyItemInserted(itemList.size() - 1);
                            Toast.makeText(this, "Item berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Log.w(TAG, "Hasil tidak OK atau data null. ResultCode: " + result.getResultCode()); // <-- LOG WARNING
                    }
                }
        );
        setupClickListeners();
    }

    private void setupClickListeners() {
        // Tombol kembali di toolbar
        binding.btnBack.setOnClickListener(v -> onBackPressed());

        // Tombol Ganti Drop Point
        binding.buttonChangeDropPoint.setOnClickListener(v -> {
            PilihDropPointSheet bottomSheet = new PilihDropPointSheet();
            bottomSheet.show(getSupportFragmentManager(), "PilihDropPointSheet");
        });

        // Tombol Tambah Item (tetap sama)
        binding.buttonAddItem.setOnClickListener(v -> {
            // =================================================================================
            // LANGKAH 3: MODIFIKASI TOMBOL TAMBAH UNTUK MENGGUNAKAN LAUNCHER
            // =================================================================================
            // MODIFIKASI: Gunakan launcher agar bisa menerima hasil
            Intent intent = new Intent(this, AddEditItemActivity.class);
            formLauncher.launch(intent);
        });

        // Listener untuk tombol Edit yang sekarang stati


        // Tombol Donasi Sekarang
        binding.buttonDonateNow.setOnClickListener(v -> {
            // Buat instance dari Bottom Sheet Anda
            KonfirmasiDonasiSheet bottomSheet = new KonfirmasiDonasiSheet();
            // Tampilkan
            bottomSheet.show(getSupportFragmentManager(), "KonfirmasiDonasiSheet");
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

    @Override
    public void onKonfirmasiClicked() {
        Log.d(TAG, "onKonfirmasiClicked dipanggil."); // JEJAK 1: Cek apakah method ini dipanggil

        // JEJAK 2: Cek kondisi data sebelum mengirim
        if (itemList == null) {
            Log.e(TAG, "GAGAL: itemList bernilai NULL!");
        } else {
            Log.d(TAG, "Kondisi data: Jumlah item di itemList = " + itemList.size());
        }

        if (selectedDropPoint == null) {
            Log.e(TAG, "GAGAL: selectedDropPoint bernilai NULL! Pengguna belum memilih atau nilai awal tidak di-set.");
        } else {
            Log.d(TAG, "Kondisi data: Drop point yang dipilih = " + selectedDropPoint.getName());
        }
        // PERUBAHAN 3: Lengkapi logika konfirmasi

        // Pastikan ada barang dan drop point yang dipilih sebelum melanjutkan
        if (itemList == null || itemList.isEmpty()) {
            Toast.makeText(this, "Anda belum menambahkan barang donasi.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedDropPoint == null) {
            Toast.makeText(this, "Silakan pilih drop point terlebih dahulu.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Buat Intent untuk membuka PengantaranActivity
        Intent intent = new Intent(this, PengantaranActivity.class);

        // Masukkan semua data yang dibutuhkan oleh halaman berikutnya
        intent.putExtra("DAFTAR_DONASI", itemList);
        intent.putExtra("DROP_POINT_TERPILIH", selectedDropPoint);
        Log.d(TAG, "DATA SIAP: Memulai PengantaranActivity..."); // JEJAK 3: Cek sebelum pindah halaman
        // Buka activity baru
        startActivity(intent);

        // Anda bisa memanggil finish() jika tidak ingin pengguna kembali ke halaman summary ini
        // finish();
    }

    @Override
    public void onDropPointSelected(DropPoint dropPoint) {
        // PERUBAHAN 2: Simpan drop point yang dipilih ke variabel
        this.selectedDropPoint = dropPoint;
        Log.d(TAG, "onDropPointSelected dipanggil, menyimpan: " + dropPoint.getName());

        // Update UI (kode ini sudah Anda miliki)
        binding.textDropPointName.setText(dropPoint.getName());
        binding.textDropPointAddress.setText(dropPoint.getAddress());
        Toast.makeText(this, "Drop point diubah menjadi: " + dropPoint.getName(), Toast.LENGTH_SHORT).show();
    }
}
