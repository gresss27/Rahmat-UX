package com.example.rahmat_ux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.rahmat_ux.databinding.ActivityAddEditItemBinding;
import com.example.rahmat_ux.model.Campaign;
import com.example.rahmat_ux.model.DonatedItem;
import java.util.ArrayList;

public class AddEditItemActivity extends AppCompatActivity {
    private static final String TAG = "AddEditItemActivity";

    private ActivityAddEditItemBinding binding;
    // BARU: Variabel untuk menyimpan status edit dan posisi
    private boolean isModeEdit = false;
    private int posisiEdit = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // MODIFIKASI: Pindahkan super.onCreate() ke atas
        binding = ActivityAddEditItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        boolean isFirstAdd = intent.getBooleanExtra("IS_FIRST_ADD", false);
        Campaign campaign = (Campaign) intent.getSerializableExtra("EXTRA_CAMPAIGN"); // MODIFIKASI: Gunakan campaign dari intent yang sama

        if (campaign != null) {
            binding.radioItem1.setText(campaign.getItem1Name());
            binding.radioItem2.setText(campaign.getItem2Name());
            binding.radioItem3.setText(campaign.getItem3Name());
        }

        // =====================================================================
        // MODIFIKASI 1: TAMBAHKAN LOGIKA UNTUK MODE EDIT
        // =====================================================================
        // Cek apakah intent membawa data untuk diedit
        if (intent.hasExtra("BARANG_UNTUK_DIEDIT")) {
            isModeEdit = true;
            DonatedItem barangEdit = (DonatedItem) intent.getSerializableExtra("BARANG_UNTUK_DIEDIT");
            posisiEdit = intent.getIntExtra("POSISI_EDIT", -1);

            // Isi form dengan data dari barang yang akan diedit
            if (barangEdit != null) {
                binding.editTextItemName.setText(barangEdit.getName());
                binding.editTextItemQuantity.setText(barangEdit.getQuantity());
                binding.editTextNotes.setText(barangEdit.getCategory()); // Asumsi 'notes' adalah 'category'
            }
        }

        // Tombol back di toolbar
        binding.btnBack.setOnClickListener(v -> onBackPressed());

        // Tombol "Tambahkan Barang"
        binding.buttonSubmit.setOnClickListener(v -> {
            Log.d(TAG, "Tombol Simpan ditekan."); // <-- LOG 1
            String name = binding.editTextItemName.getText().toString().trim();
            String quantity = binding.editTextItemQuantity.getText().toString().trim();
            int selectedId = binding.radioGroupCategory.getCheckedRadioButtonId();

            String category = "";
            if (selectedId != -1) {
                // 3. Cari RadioButton berdasarkan ID yang didapat
                RadioButton selectedRadioButton = findViewById(selectedId);
                // 4. Ambil teks dari RadioButton tersebut
                category = selectedRadioButton.getText().toString();
            } else {
                // Jika tidak ada kategori yang dipilih, tampilkan pesan dan hentikan proses
                Toast.makeText(AddEditItemActivity.this, "Silakan pilih kategori barang", Toast.LENGTH_SHORT).show();
                return; // Keluar dari listener, jangan lanjutkan proses simpan
            }

            // Validasi sederhana
            if (name.isEmpty() || quantity.isEmpty()) {
                // Tampilkan error jika perlu
                return;
            }

            DonatedItem item = new DonatedItem(name, category, quantity);
            Log.d(TAG, "Item yang dibuat: " + item.getName()); // <-- LOG 2
            if (isFirstAdd) {
                // Logika untuk penambahan pertama, sudah benar
                ArrayList<DonatedItem> itemList = new ArrayList<>();
                itemList.add(item);
                Intent intentAddItem = new Intent(AddEditItemActivity.this, GoodsDonationSummaryActivity.class);
                intentAddItem.putExtra("campaign", campaign); // Perbaiki key menjadi "campaign"
                intentAddItem.putExtra("INITIAL_LIST", itemList);
                startActivity(intentAddItem);
                finish();
            } else {
                // Jika ini adalah penambahan/edit dari Activity C
                Intent resultIntent = new Intent();

                // =====================================================================
                // MODIFIKASI 2 & 3: PERBAIKI DATA YANG DIKIRIM KEMBALI
                // =====================================================================

                // Gunakan kunci yang benar: "HASIL_BARANG"
                resultIntent.putExtra("HASIL_BARANG", item);

                // Jika ini mode edit, kirim juga posisinya kembali
                if (isModeEdit) {
                    resultIntent.putExtra("POSISI_EDIT_KEMBALI", posisiEdit);
                }

                setResult(Activity.RESULT_OK, resultIntent);
                finish(); // Tutup activity dan kembali ke GoodsDonationSummaryActivity
            }
        });
    }
}