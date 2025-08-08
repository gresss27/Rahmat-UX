package com.example.rahmat_ux;

import android.content.Intent;
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
    private Button btnDraft, btnSubmitted, btnOngoing, btnCompleted;
    private List<Campaign> allCampaigns;

    private String currentStatus = "Draft";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_campaign, container, false);

        recyclerView = view.findViewById(R.id.recyclerCampaigns);
        btnDraft = view.findViewById(R.id.btnDraft);
        btnSubmitted = view.findViewById(R.id.btnSubmitted);
        btnOngoing = view.findViewById(R.id.btnOngoing);
        btnCompleted = view.findViewById(R.id.btnCompleted);
        Button btnCreateCampaign = view.findViewById(R.id.btnCreateCampaign);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        allCampaigns = DummyDataRepository.getInstance().getCampaignList();

// Sembunyikan tombol Draft jika tidak ada draft
        boolean hasDraft = checkDraftExistence();
        btnDraft.setVisibility(hasDraft ? View.VISIBLE : View.GONE);
        btnDraft.setOnClickListener(v -> {
            filterCampaigns("Draft");
        });

        btnSubmitted.setOnClickListener(v -> {
            filterCampaigns("Diajukan");
        });

        btnOngoing.setOnClickListener(v -> {
            filterCampaigns("Berlangsung");
        });

        btnCompleted.setOnClickListener(v -> {
            filterCampaigns("Selesai");
        });

        btnCreateCampaign.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CreateCampaignActivity.class);
            startActivity(intent);
        });

        // Pindahkan inisialisasi adapter ke sini
        adapter = new CampaignStatusAdapter(DummyDataRepository.getInstance().getCampaignsByStatus(currentStatus));
        recyclerView.setAdapter(adapter);
        setSelectedButton(currentStatus);

        // Set listener untuk item di RecyclerView
        adapter.setOnItemClickListener(campaign -> {
            if (campaign.getStatus().equals("Draft")) {
                Intent intent = new Intent(getActivity(), CreateCampaignActivity.class);
                intent.putExtra("campaign_data", campaign);
                startActivity(intent);
            } else {
                // Tambahkan aksi lain jika bukan draft
            }
        });

        adapter.setOnDeleteClickListener(campaign -> {
            DummyDataRepository.getInstance().removeCampaign(campaign);

            boolean stillhasDraft = checkDraftExistence();
            btnDraft.setVisibility(stillhasDraft ? View.VISIBLE : View.GONE);

            if ("Draft".equalsIgnoreCase(currentStatus)) {
                if (stillhasDraft) {
                    filterCampaigns("Draft");
                } else {
                    currentStatus = "Diajukan";
                    filterCampaigns(currentStatus);
                }
            } else {
                filterCampaigns(currentStatus); // tetap refresh tab sekarang
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Cek ulang apakah ada draft setelah kembali dari CreateCampaignActivity
        boolean hasDraft = checkDraftExistence();
        btnDraft.setVisibility(hasDraft ? View.VISIBLE : View.GONE);

        // Jika status sebelumnya "Draft" dan sekarang sudah tidak ada, alihkan ke "Diajukan"
        if ("Draft".equalsIgnoreCase(currentStatus) && !hasDraft) {
            currentStatus = "Diajukan";
        }
        filterCampaigns(currentStatus);
    }
    private boolean checkDraftExistence() {
        for (Campaign c : DummyDataRepository.getInstance().getCampaignList()) {
            if ("Draft".equalsIgnoreCase(c.getStatus())) {
                return true;
            }
        }
        return false;
    }
    private void filterCampaigns(String status) {
        currentStatus = status;
        List<Campaign> filtered = DummyDataRepository.getInstance().getCampaignsByStatus(status);
        adapter = new CampaignStatusAdapter(filtered);
        recyclerView.setAdapter(adapter);
        setSelectedButton(status);

        // Tambahkan listener kembali ke adapter baru
        adapter.setOnItemClickListener(campaign -> {
            if ("Draft".equalsIgnoreCase(campaign.getStatus())) {
                Intent intent = new Intent(getActivity(), CreateCampaignActivity.class);
                intent.putExtra("campaign_data", campaign);
                startActivity(intent);
            }
        });

        adapter.setOnDeleteClickListener(campaign -> {
            DummyDataRepository.getInstance().removeCampaign(campaign);

            boolean stillhasDraft = checkDraftExistence();
            btnDraft.setVisibility(stillhasDraft ? View.VISIBLE : View.GONE);

            if ("Draft".equalsIgnoreCase(currentStatus)) {
                if (stillhasDraft) {
                    filterCampaigns("Draft");
                } else {
                    currentStatus = "Diajukan";
                    filterCampaigns(currentStatus);
                }
            } else {
                filterCampaigns(currentStatus); // tetap refresh tab sekarang
            }
        });
    }


    private void setSelectedButton(String selectedStatus) {
        btnDraft.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(),
                selectedStatus.equals("Draft") ? R.color.green_primary : R.color.green_unselected));

        btnSubmitted.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(),
                selectedStatus.equals("Diajukan") ? R.color.green_primary : R.color.green_unselected));

        btnOngoing.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(),
                selectedStatus.equals("Berlangsung") ? R.color.green_primary : R.color.green_unselected));

        btnCompleted.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(),
                selectedStatus.equals("Selesai") ? R.color.green_primary : R.color.green_unselected));
    }
}