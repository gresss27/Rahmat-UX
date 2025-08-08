package com.example.rahmat_ux;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rahmat_ux.adapter.DonationHistoryAdapter;
import com.example.rahmat_ux.model.DonationHistory;
import com.example.rahmat_ux.data.DummyDataRepository;

import java.util.ArrayList;
import java.util.List;

public class DonationHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DonationHistoryAdapter adapter;
    private List<DonationHistory> allHistories;
    private List<DonationHistory> filteredHistories;

    private AppCompatButton btnAll;
    private AppCompatButton btnUang;
    private AppCompatButton btnBarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_donation_history);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Back Button
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> onBackPressed());

        // Button References
        btnAll = findViewById(R.id.btn_all);
        btnUang = findViewById(R.id.btn_uang);
        btnBarang = findViewById(R.id.btn_barang);

        // RecyclerView Setup
        recyclerView = findViewById(R.id.recycler_view_donation_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load data using Singleton access
        allHistories = DummyDataRepository.getInstance().getDonationHistoryList();
        filteredHistories = new ArrayList<>(allHistories);

        // Set adapter
        adapter = new DonationHistoryAdapter(filteredHistories);
        recyclerView.setAdapter(adapter);

        // Filter Buttons
        btnAll.setOnClickListener(v -> showFiltered("all"));
        btnUang.setOnClickListener(v -> showFiltered("uang"));
        btnBarang.setOnClickListener(v -> showFiltered("barang"));
    }

    private void showFiltered(String category) {
        filteredHistories.clear();

        if (category.equals("all")) {
            filteredHistories.addAll(allHistories);
        } else {
            for (DonationHistory history : allHistories) {
                if (history.getCategory().equals(category)) {
                    filteredHistories.add(history);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }
}
