package com.example.rahmat_ux.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rahmat_ux.databinding.ItemChannelListBinding; // Diubah
import com.example.rahmat_ux.model.Campaign;
import java.util.List;

public class ChannelListAdapter extends RecyclerView.Adapter<ChannelListAdapter.ChannelViewHolder> {

    private final List<Campaign> campaignList;

    public ChannelListAdapter(List<Campaign> campaignList) {
        this.campaignList = campaignList;
    }

    @NonNull
    @Override
    public ChannelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChannelListBinding binding = ItemChannelListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ChannelViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelViewHolder holder, int position) {
        Campaign campaign = campaignList.get(position);
        holder.bind(campaign);
    }

    @Override
    public int getItemCount() {
        return campaignList.size();
    }

    public static class ChannelViewHolder extends RecyclerView.ViewHolder {
        private final ItemChannelListBinding binding;

        public ChannelViewHolder(ItemChannelListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Campaign campaign) {
            binding.imageViewProfile.setImageResource(campaign.getMainImageResId());
            binding.textViewTitle.setText(campaign.getTitle());
            binding.textViewDescription.setText(campaign.getDescription());
            if (campaign.getTargetAmount() > 0) {
                int progress = (int) ((double) campaign.getAmountCollected() * 100 / campaign.getTargetAmount());
                binding.progressBar.setProgress(progress);
            } else {
                binding.progressBar.setProgress(0);
            }
        }
    }
}