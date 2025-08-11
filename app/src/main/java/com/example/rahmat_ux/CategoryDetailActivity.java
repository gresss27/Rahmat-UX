package com.example.rahmat_ux;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rahmat_ux.adapter.CampaignStatusAdapter;
import com.example.rahmat_ux.data.DummyDataRepository;
import com.example.rahmat_ux.model.Campaign;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CampaignStatusAdapter adapter;
    private List<Campaign> campaigns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);

        initViews();
        loadCampaigns();
        setupBackButton();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerCampaigns);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadCampaigns() {
        // Ambil semua campaign dengan status "Berlangsung"
        List<Campaign> allCampaigns = DummyDataRepository
                .getInstance()
                .getCampaignsByStatus("Berlangsung");

        // Kalau mau filter ID tertentu (contoh: 2, 3, 7, 8, 9)
        List<Integer> targetIds = Arrays.asList(2, 3, 7, 8, 9);

        // Filter pakai Java Stream (butuh min SDK 24)
        campaigns = allCampaigns.stream()
                .filter(c -> targetIds.contains(c.getId()))
                .collect(Collectors.toList());

        // Kalau tidak mau filter ID, tinggal pakai allCampaigns saja
        // campaigns = allCampaigns;

        adapter = new CampaignStatusAdapter(campaigns);
        recyclerView.setAdapter(adapter);
    }

    private void setupBackButton() {
        ImageView backButton = findViewById(R.id.backButton);
        if (backButton != null) {
            backButton.setOnClickListener(v -> finish());
        }
    }
}
