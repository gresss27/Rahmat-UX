//package com.example.rahmat_ux;
//
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.RecyclerView;
//import com.example.rahmat_ux.adapter.CampaignStatusAdapter;
//import com.example.rahmat_ux.data.DummyDataRepository;
//import com.example.rahmat_ux.model.Campaign;
//import java.util.List;
//import androidx.recyclerview.widget.LinearLayoutManager;
//
//
//public class CategoryDetailActivity extends AppCompatActivity {
//
//    private RecyclerView recyclerView;
//    private CampaignStatusAdapter adapter;
//    private List<Campaign> allCampaigns;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_category_detail);
//
//        recyclerView = findViewById(R.id.recyclerCampaigns);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        allCampaigns = DummyDataRepository.getInstance().getCampaignList();
//
//        adapter = new CampaignStatusAdapter(allCampaigns);
//        recyclerView.setAdapter(adapter);
//    }
//}
package com.example.rahmat_ux;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rahmat_ux.adapter.CampaignStatusAdapter;
import com.example.rahmat_ux.data.DummyDataRepository;
import com.example.rahmat_ux.model.Campaign;

import java.util.List;

public class CategoryDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CampaignStatusAdapter adapter;
    private List<Campaign> allCampaigns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);

        recyclerView = findViewById(R.id.recyclerCampaigns);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        allCampaigns = DummyDataRepository.getInstance().getCampaignsByStatus("Berlangsung");
        adapter = new CampaignStatusAdapter(allCampaigns);
        recyclerView.setAdapter(adapter);

        // ⬇️ Tambahkan tombol back
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
    }
}
