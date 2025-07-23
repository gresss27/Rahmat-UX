package com.example.rahmat_ux;// Di dalam file CampaignChannelFragment.java

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rahmat_ux.databinding.FragmentCampaignChannelBinding; // <-- Pastikan nama binding benar
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CampaignChannelFragment extends Fragment {

    private FragmentCampaignChannelBinding binding;

    @Override
    public void onStart() {
        super.onStart();
        // Sembunyikan BottomNavigationView saat fragment ini mulai
        if (getActivity() != null) {
            BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottom_navigation); // Ganti dengan ID BottomNav-mu
            if (bottomNav != null) {
                bottomNav.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // Tampilkan kembali BottomNavigationView saat fragment ini tidak lagi terlihat
        if (getActivity() != null) {
            BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottom_navigation); // Ganti dengan ID BottomNav-mu
            if (bottomNav != null) {
                bottomNav.setVisibility(View.VISIBLE);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCampaignChannelBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.toolbar.setNavigationOnClickListener(v -> {
            if (getActivity() != null) {
                // INI DIA KODENYA
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            String title = bundle.getString("campaign_title");
            String organizerName = bundle.getString("organizer_name");
            int campaignImage = bundle.getInt("campaign_image");
            long amountCollected = bundle.getLong("amount_collected");
            long targetAmount = bundle.getLong("target_amount");
            long amountNeeded = targetAmount - amountCollected;

            binding.textViewTitle.setText(title);
            binding.imageViewCampaign.setImageResource(campaignImage);
            binding.textViewSupporterCount.setText(organizerName);
            binding.textViewAmountCollected.setText("Rp" + String.format("%,d", amountCollected));
            binding.textViewTarget.setText("Rp" + String.format("%,d", targetAmount));
            binding.textViewNeeded.setText("Rp" + String.format("%,d", amountNeeded));

            if (targetAmount > 0) {
                int progress = (int) ((double) amountCollected * 100 / targetAmount);
                binding.progressBarDetail.setProgress(progress);
            }
        }
    }

    // ... (onDestroyView) ...
}