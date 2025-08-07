package com.example.rahmat_ux.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private OnItemClickListener listener;
    private OnDeleteClickListener deleteClickListener;

    // Interface klik item
    public interface OnItemClickListener {
        void onItemClick(Campaign campaign);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Interface klik delete
    public interface OnDeleteClickListener {
        void onDeleteClick(Campaign campaign);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteClickListener = listener;
    }

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
        if ("Draft".equalsIgnoreCase(status)) {
            holder.deleteDraftButton.setVisibility(View.VISIBLE);
            holder.deleteDraftButton.setOnClickListener(v -> {
                if (deleteClickListener != null) {
                    deleteClickListener.onDeleteClick(campaign);
                }
            });
        } else {
            holder.deleteDraftButton.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(campaign);
            }
        });

        if (status.equals("Diajukan")) {
            holder.collected.setVisibility(View.GONE);
            holder.target.setVisibility(View.GONE);
            holder.progressBar.setVisibility(View.GONE);
            holder.remainingDays.setText("Sedang diproses");
        } else {
            holder.collected.setVisibility(View.VISIBLE);
            holder.target.setVisibility(View.VISIBLE);
            holder.progressBar.setVisibility(View.VISIBLE);

            int progress = 0;
            if (campaign.getTargetAmount() > 0) {
                progress = (int) ((campaign.getAmountCollected() * 100.0) / campaign.getTargetAmount());
            }
            holder.progressBar.setProgress(progress);
            holder.collected.setText(String.format("Rp%,d", campaign.getAmountCollected()));
            holder.target.setText(String.format("Rp%,d", campaign.getTargetAmount()));

            holder.remainingDays.setText(
                    status.equals("Selesai") ? "Selesai" : "Sisa " + campaign.getRemainingDays() + " hari"
            );
        }
    }

    @Override
    public int getItemCount() {
        return campaigns.size();
    }

    static class CampaignViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title, collected, target, remainingDays;
        ProgressBar progressBar;
        Button deleteDraftButton;

        CampaignViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.imgCampaignThumbnail);
            title = itemView.findViewById(R.id.tvCampaignTitle);
            collected = itemView.findViewById(R.id.tvCampaignCollected);
            target = itemView.findViewById(R.id.tvCampaignTarget);
            remainingDays = itemView.findViewById(R.id.tvRemainingDays);
            progressBar = itemView.findViewById(R.id.progressBar);
            deleteDraftButton = itemView.findViewById(R.id.btnDeleteDraft);
        }
    }
}
