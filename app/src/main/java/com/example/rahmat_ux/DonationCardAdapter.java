package com.example.rahmat_ux;
// this page named DonationCardAdapter
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rahmat_ux.model.Campaign;

import java.util.List;

public class DonationCardAdapter extends RecyclerView.Adapter<DonationCardAdapter.CardViewHolder> {

    private final List<Campaign> campaignList;
    private final Context context;

    public DonationCardAdapter(List<Campaign> campaignList, Context context) {
        this.campaignList = campaignList;
        this.context = context;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_donation_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Campaign campaign = campaignList.get(position);
        holder.title.setText(campaign.getTitle());
        holder.image.setImageResource(campaign.getMainImageResId());
        long remaining = campaign.getTargetAmount() - campaign.getAmountCollected();
        holder.amount.setText(String.format("Rp%,d untuk mencapai target", remaining));
        holder.name.setText(campaign.getOrganizerName());
        holder.profile.setImageResource(campaign.getOrganizerImageResId());
    }

    @Override
    public int getItemCount() {
        return campaignList.size();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView image, profile;
        TextView title, amount, name;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.thumbnailImage);
            profile = itemView.findViewById(R.id.organizerProfile);
            title = itemView.findViewById(R.id.campaignTitle);
            amount = itemView.findViewById(R.id.campaignAmount);
            name = itemView.findViewById(R.id.organizerName);
        }
    }
}
