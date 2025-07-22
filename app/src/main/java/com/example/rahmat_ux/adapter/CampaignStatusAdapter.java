package com.example.rahmat_ux.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rahmat_ux.R;
import com.example.rahmat_ux.model.Campaign;

import java.util.List;

public class CampaignStatusAdapter extends RecyclerView.Adapter<CampaignStatusAdapter.CampaignViewHolder> {

    private final List<Campaign> campaigns;

    public CampaignStatusAdapter(List<Campaign> campaigns) {
        this.campaigns = campaigns;
    }

    @NonNull
    @Override
    public CampaignViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.campaign_item, parent, false);
        return new CampaignViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CampaignViewHolder holder, int position) {
        Campaign campaign = campaigns.get(position);
        holder.title.setText(campaign.getTitle());

        if (campaign.getStatus().equals("Diajukan")) {
            holder.status.setText("Menunggu persetujuan");
            holder.progressBar.setVisibility(View.GONE);
            holder.remainingDays.setText("Sisa " + campaign.getRemainingDays() + " hari");
        } else {
            holder.progressBar.setVisibility(View.VISIBLE);
            int progress = (int) ((campaign.getAmountCollected() * 100.0) / campaign.getTargetAmount());
            holder.status.setText(String.format("Terkumpul: Rp%,d / Rp%,d", campaign.getAmountCollected(), campaign.getTargetAmount()));
            holder.progressBar.setProgress(progress);
            holder.remainingDays.setText(campaign.getStatus().equals("Selesai") ? "Selesai" : "Sisa " + campaign.getRemainingDays() + " hari");
        }

        holder.thumbnail.setImageResource(campaign.getMainImageResId());
    }

    @Override
    public int getItemCount() {
        return campaigns.size();
    }

    static class CampaignViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title, status, remainingDays;
        ProgressBar progressBar;

        CampaignViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.imgCampaignThumbnail);
            title = itemView.findViewById(R.id.tvCampaignTitle);
            status = itemView.findViewById(R.id.tvCampaignStatus);
            remainingDays = itemView.findViewById(R.id.tvRemainingDays);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
