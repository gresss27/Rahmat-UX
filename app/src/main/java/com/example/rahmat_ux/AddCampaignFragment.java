package com.example.rahmat_ux;
// this file name is AddCampaignFragment
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rahmat_ux.adapter.CampaignStatusAdapter;
import com.example.rahmat_ux.data.DummyDataRepository;
import com.example.rahmat_ux.model.Campaign;
import java.util.List;

public class AddCampaignFragment extends Fragment {

    private RecyclerView recyclerView;
    private CampaignStatusAdapter adapter;
    private Button btnSubmitted, btnOngoing, btnCompleted;
    private List<Campaign> allCampaigns;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_campaign, container, false);

        recyclerView = view.findViewById(R.id.recyclerCampaigns);
        btnSubmitted = view.findViewById(R.id.btnSubmitted);
        btnOngoing = view.findViewById(R.id.btnOngoing);
        btnCompleted = view.findViewById(R.id.btnCompleted);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        allCampaigns = DummyDataRepository.getCampaignList();

        btnSubmitted.setOnClickListener(v -> {
            filterCampaigns("Diajukan");
            setSelectedButton("Diajukan");
        });

        btnOngoing.setOnClickListener(v -> {
            filterCampaigns("Berlangsung");
            setSelectedButton("Berlangsung");
        });

        btnCompleted.setOnClickListener(v -> {
            filterCampaigns("Selesai");
            setSelectedButton("Selesai");
        });
        filterCampaigns("Diajukan");
        return view;
    }

    private void filterCampaigns(String status) {
        List<Campaign> filtered = DummyDataRepository.getCampaignsByStatus(status);
        adapter = new CampaignStatusAdapter(filtered);
        recyclerView.setAdapter(adapter);
    }

    private void setSelectedButton(String selectedStatus) {
        // Reset warna semua tombol
        btnSubmitted.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(),
                selectedStatus.equals("Diajukan") ? R.color.green_primary : R.color.green_unselected));

        btnOngoing.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(),
                selectedStatus.equals("Berlangsung") ? R.color.green_primary : R.color.green_unselected));

        btnCompleted.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(),
                selectedStatus.equals("Selesai") ? R.color.green_primary : R.color.green_unselected));
    }

}