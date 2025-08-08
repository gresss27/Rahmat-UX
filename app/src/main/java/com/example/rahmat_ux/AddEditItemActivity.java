package com.example.rahmat_ux;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.rahmat_ux.databinding.ActivityAddEditItemBinding;
import com.example.rahmat_ux.model.Campaign;
import com.example.rahmat_ux.model.DonatedItem;

import java.util.ArrayList;

public class AddEditItemActivity extends AppCompatActivity {

    private ActivityAddEditItemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        boolean isFirstAdd = getIntent().getBooleanExtra("IS_FIRST_ADD", false);
        Campaign campaign = (Campaign) intent.getSerializableExtra("EXTRA_CAMPAIGN");
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        assert campaign != null;
        binding.radioItem1.setText(campaign.getItem1Name());
        binding.radioItem2.setText(campaign.getItem2Name());
        binding.radioItem3.setText(campaign.getItem3Name());

        // Tombol back di toolbar akan kembali ke Detail Donasi
        binding.btnBack.setOnClickListener(v -> onBackPressed());

        // Tombol "Tambahkan Barang"
        binding.buttonSubmit.setOnClickListener(v -> {
            String name = binding.editTextItemName.getText().toString().trim();
            String quantity = binding.editTextItemQuantity.getText().toString().trim();
            String category = binding.editTextNotes.getText().toString().trim();

            DonatedItem item = new DonatedItem(name, name, quantity);
            if(isFirstAdd){
                ArrayList<DonatedItem> itemList = new ArrayList<>();
                itemList.add(item);
            }
            // 1. Pindah ke halaman Summary
            Intent intent2 = new Intent(AddEditItemActivity.this, GoodsDonationSummaryActivity.class);
            intent2.putExtra("campaign", campaign);
            startActivity(intent2);

            // 2. Tutup halaman ini agar tidak ada di riwayat back stack
            finish();
        });
    }
}
