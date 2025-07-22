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
        holder.thumbnail.setImageResource(campaign.getMainImageResId());

        String status = campaign.getStatus();

        if (campaign.getStatus().equals("Diajukan")) {
            // Sembunyikan data progress
            holder.collected.setVisibility(View.GONE);
            holder.target.setVisibility(View.GONE);
            holder.progressBar.setVisibility(View.GONE);

            // Tampilkan status proses
            holder.remainingDays.setText("Sedang diproses");

        } else {
            int progress = (int) ((campaign.getAmountCollected() * 100.0) / campaign.getTargetAmount());
            holder.progressBar.setProgress(progress);
            holder.progressBar.setVisibility(View.VISIBLE);

            holder.collected.setVisibility(View.VISIBLE);
            holder.target.setVisibility(View.VISIBLE);

            holder.collected.setText(String.format("Rp%,d", campaign.getAmountCollected()));
            holder.target.setText(String.format("Rp%,d", campaign.getTargetAmount()));

            if (status.equals("Selesai")) {
                holder.remainingDays.setText("Selesai");
            } else {
                holder.remainingDays.setText("Sisa " + campaign.getRemainingDays() + " hari");
            }
        }
    }

    @Override
    public int getItemCount() {
        return campaigns.size();
    }

    static class CampaignViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title, collected, target, remainingDays, status;
        ProgressBar progressBar;

        CampaignViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.imgCampaignThumbnail);
            title = itemView.findViewById(R.id.tvCampaignTitle);
            collected = itemView.findViewById(R.id.tvCampaignCollected);
            target = itemView.findViewById(R.id.tvCampaignTarget);
            remainingDays = itemView.findViewById(R.id.tvRemainingDays);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
