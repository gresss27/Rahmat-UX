package com.example.rahmat_ux;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull; // Pastikan import ini ada
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

// Ganti import adapter lama dengan adapter baru
import com.example.rahmat_ux.adapter.SummaryAdapter;
import com.example.rahmat_ux.databinding.ActivityGoodsDonationSummaryBinding;
import com.example.rahmat_ux.model.Campaign;
import com.example.rahmat_ux.model.DonatedItem;
import com.example.rahmat_ux.model.DropPoint;
import com.example.rahmat_ux.repository.DropPointRepository;

import java.util.ArrayList;
import java.util.List;

// MODIFIKASI: Implementasikan interface dari adapter yang baru
public class GoodsDonationSummaryActivity extends AppCompatActivity
        implements KonfirmasiDonasiSheet.BottomSheetListener,
        PilihDropPointSheet.DropPointSelectionListener,
        SummaryAdapter.OnActionClickListener {

    private static final String TAG = "GoodsDonationSummary";

    private ActivityGoodsDonationSummaryBinding binding;
    private Campaign campaign;
    private DropPoint selectedDropPoint;
    private ArrayList<DonatedItem> itemList;
    private List<Object> combinedList = new ArrayList<>(); // List gabungan untuk adapter
    private SummaryAdapter adapter;
    private ActivityResultLauncher<Intent> formLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGoodsDonationSummaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Ambil data awal dari Intent
        Intent prevIntent = getIntent();
        itemList = (ArrayList<DonatedItem>) prevIntent.getSerializableExtra("INITIAL_LIST");
        if (itemList == null) {
            itemList = new ArrayList<>();
        }
        campaign = (Campaign) prevIntent.getSerializableExtra("campaign");

        // Inisialisasi launcher (TETAP DIPERLUKAN)
        initializeLauncher();

        // Setup tombol-tombol yang ada di layout utama (bukan di dalam list)
        setupStaticClickListeners();

        // Panggil method untuk menyiapkan dan menampilkan semua data
        buildAndDisplayList();
    }

    private void buildAndDisplayList() {
        combinedList.clear();
        // ... setup default drop point ...

        // 1. Tambahkan data untuk header progress
        if (campaign != null) {
            combinedList.add(campaign);
        }

        // 2. Tambahkan data untuk header drop point
        if (selectedDropPoint == null) {
            List<DropPoint> allDropPoints = DropPointRepository.getInstance().getDropPoints();
            if (allDropPoints != null && !allDropPoints.isEmpty()) {
                this.selectedDropPoint = allDropPoints.get(0);
            }
        }

        // 3. SEKARANG, tambahkan selectedDropPoint yang sudah ada isinya ke dalam list
        //    Pengecekan ini penting untuk kasus di mana repository juga kosong.
        if (selectedDropPoint != null) {
            combinedList.add(selectedDropPoint);
        }


        // 3. MODIFIKASI: Tambahkan SELURUH itemList sebagai SATU objek
        if (itemList != null && !itemList.isEmpty()) {
            combinedList.add(itemList); // Masukkan ArrayList<DonatedItem> sebagai satu item
        }

        // Hapus penanda footer, karena sudah digabung ke dalam grup donasi
        // combinedList.add("FOOTER_TAMBAH");

        adapter = new SummaryAdapter(combinedList, this);
        binding.recyclerViewSummary.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewSummary.setAdapter(adapter);
    }

// Di dalam file GoodsDonationSummaryActivity.java

    private void initializeLauncher() {
        formLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                (ActivityResult result) -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {

                        DonatedItem barangHasil = (DonatedItem) result.getData().getSerializableExtra("HASIL_BARANG");
                        if (barangHasil == null) return; // Pengecekan keamanan

                        int posisiEdit = result.getData().getIntExtra("POSISI_EDIT_KEMBALI", -1);

                        if (posisiEdit != -1) {
                            // Ini adalah HASIL EDIT (logika ini sudah benar)
                            // Pastikan posisi yang diedit masih valid
                            if (posisiEdit < itemList.size()) {
                                itemList.set(posisiEdit, barangHasil);
                            }

                        } else {
                            // ==========================================================
                            // PERBAIKAN UTAMA: Ini adalah HASIL TAMBAH BARU
                            // ==========================================================

                            // 1. Tambahkan item baru ke dalam daftar sumber data
                            itemList.add(barangHasil);


                        }

                        // ==========================================================
                        // 2. SELALU bangun ulang dan tampilkan list setelah ada perubahan
                        // (baik itu edit maupun tambah baru)
                        // ==========================================================
                        buildAndDisplayList();

                    }
                }
        );
    }

    private void setupStaticClickListeners() {
        binding.btnBack.setOnClickListener(v -> onBackPressed());
        binding.buttonDonateNow.setOnClickListener(v -> {
            KonfirmasiDonasiSheet bottomSheet = new KonfirmasiDonasiSheet();
            bottomSheet.show(getSupportFragmentManager(), "KonfirmasiDonasiSheet");
        });
    }

    // =====================================================================
    // IMPLEMENTASI METHOD-METHOD DARI SEMUA INTERFACE
    // =====================================================================

    // Dari SummaryAdapter.OnActionClickListener (untuk tombol di dalam RecyclerView)
    @Override
    public void onGantiDropPointClick() {
        PilihDropPointSheet bottomSheet = new PilihDropPointSheet();
        bottomSheet.show(getSupportFragmentManager(), "PilihDropPointSheet");
    }

    @Override
    public void onTambahItemClick() {
        Intent intent = new Intent(this, AddEditItemActivity.class);
        // Kirim campaign agar form tahu item apa saja yang bisa dipilih
        intent.putExtra("EXTRA_CAMPAIGN", campaign);
        formLauncher.launch(intent);
    }

    @Override
    public void onEditItemClick(int position, DonatedItem item) {
        Intent intent = new Intent(this, AddEditItemActivity.class);
        intent.putExtra("BARANG_UNTUK_DIEDIT", item);
        intent.putExtra("POSISI_EDIT", position);
        intent.putExtra("EXTRA_CAMPAIGN", campaign);
        formLauncher.launch(intent);
    }

    @Override
    public void onDeleteItemClick(int position, DonatedItem item) {
        new AlertDialog.Builder(this)
                .setTitle("Hapus Donasi")
                .setMessage("Apakah Anda yakin ingin menghapus item '" + item.getName() + "'?")
                .setPositiveButton("Hapus", (dialog, which) -> {
                    itemList.remove(item);
                    // Bangun ulang seluruh list dan tampilkan lagi
                    buildAndDisplayList();
                })
                .setNegativeButton("Batal", null)
                .show();
    }

    // Dari KonfirmasiDonasiSheet.BottomSheetListener
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

            return;
        }
        if (selectedDropPoint == null) {
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

    // Dari PilihDropPointSheet.DropPointSelectionListener
    @Override
    public void onDropPointSelected(@NonNull DropPoint dropPoint) {
        this.selectedDropPoint = dropPoint;
        // Bangun ulang seluruh list dengan drop point yang baru dan tampilkan lagi
        buildAndDisplayList();
    }


}