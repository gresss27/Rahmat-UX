package com.example.rahmat_ux;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rahmat_ux.adapter.CampaignAdapterTest; // Adapter diubah ke ...Test
import com.example.rahmat_ux.data.DummyDataRepository;
import com.example.rahmat_ux.databinding.FragmentSocialBinding; // Binding diubah ke FragmentSocialBinding
import com.example.rahmat_ux.model.Campaign;

import java.util.List;

public class SocialFragment extends Fragment {

    private FragmentSocialBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSocialBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Campaign> campaignList = DummyDataRepository.getCampaignList();

        // Gunakan adapter yang sudah diubah namanya
        CampaignAdapterTest adapter = new CampaignAdapterTest(campaignList);

        // Atur RecyclerView dengan ID yang sesuai
        binding.recyclerViewSocial.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewSocial.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}