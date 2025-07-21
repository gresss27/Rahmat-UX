package com.example.rahmat_ux.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rahmat_ux.databinding.ItemCampaignTestBinding; // Diubah ke ...TestBinding
import com.example.rahmat_ux.model.Campaign;
import java.util.List;

public class CampaignAdapterTest extends RecyclerView.Adapter<CampaignAdapterTest.CampaignViewHolderTest> {

    private final List<Campaign> campaignList;

    public CampaignAdapterTest(List<Campaign> campaignList) {
        this.campaignList = campaignList;
    }

    @NonNull
    @Override
    public CampaignViewHolderTest onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout ...test.xml
        ItemCampaignTestBinding binding = ItemCampaignTestBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CampaignViewHolderTest(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CampaignViewHolderTest holder, int position) {
        Campaign campaign = campaignList.get(position);
        holder.bind(campaign);
    }

    @Override
    public int getItemCount() {
        return campaignList.size();
    }

    // ViewHolder class juga diubah namanya
    public static class CampaignViewHolderTest extends RecyclerView.ViewHolder {
        private final ItemCampaignTestBinding binding;

        public CampaignViewHolderTest(ItemCampaignTestBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Campaign campaign) {
            binding.imageViewCampaign.setImageResource(campaign.getMainImageResId());
            binding.textViewTitle.setText(campaign.getTitle());
            binding.textViewOrganizer.setText(campaign.getOrganizerName());
            binding.textViewAmount.setText("Terkumpul " + campaign.getAmountCollected()); // Di model pakai long, pastikan konversi ke String
            binding.textViewTime.setText(campaign.getTimeRemaining());
        }
    }
}