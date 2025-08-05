package com.example.rahmat_ux;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rahmat_ux.adapter.ChannelListAdapter;
import com.example.rahmat_ux.data.DummyDataRepository;
import com.example.rahmat_ux.databinding.FragmentSocialBinding;
import com.example.rahmat_ux.model.Campaign;

import java.util.List;

// --- PERUBAHAN 1: Implementasikan interface di sini ---
public class SocialFragment extends Fragment implements ChannelListAdapter.OnItemClickListener {

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

        List<Campaign> campaignList = DummyDataRepository.getInstance().getCampaignList();
        ChannelListAdapter adapter = new ChannelListAdapter(campaignList);

        // --- PERUBAHAN 2: Daftarkan listener-nya di sini ---
        adapter.setOnItemClickListener(this);

        binding.recyclerViewSocial.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewSocial.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Metode ini sekarang akan dipanggil saat item di RecyclerView diklik
    @Override
    public void onItemClick(Campaign campaign) {
        CampaignChannelFragment detailFragment = new CampaignChannelFragment();

        Bundle bundle = new Bundle();
        bundle.putString("campaign_title", campaign.getTitle());
        bundle.putString("organizer_name", campaign.getOrganizerName());
        bundle.putInt("campaign_image", campaign.getMainImageResId());
        bundle.putLong("amount_collected", campaign.getAmountCollected());
        bundle.putLong("target_amount", campaign.getTargetAmount());

        detailFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }
}