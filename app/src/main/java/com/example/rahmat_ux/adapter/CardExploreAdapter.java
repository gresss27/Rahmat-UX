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

public class CardExploreAdapter extends RecyclerView.Adapter<CardExploreAdapter.CampaignViewHolder> {

    private List<Campaign> campaignList;

    public CardExploreAdapter(List<Campaign> campaignList) {
        this.campaignList = campaignList;
    }

    @NonNull
    @Override
    public CampaignViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_explore_item, parent, false);
        return new CampaignViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CampaignViewHolder holder, int position) {
        Campaign campaign = campaignList.get(position);

        // --- PERUBAHAN DI SINI ---
        // Menggunakan getMainImageResId() sesuai dengan model Campaign.java
        holder.campaignImage.setImageResource(campaign.getMainImageResId()); // Image

        holder.campaignDescription.setText(campaign.getTitle()); // Description

        // --- CATATAN UNTUK PROGRESS BAR ---
        // Model Campaign.java Anda sekarang memiliki foodProgress, clothingProgress, dan medicineProgress.
        // Anda perlu memutuskan progress mana yang ingin ditampilkan di campaignProgress.
        // Misalnya, jika Anda ingin menampilkan foodProgress:
        // holder.campaignProgress.setProgress(campaign.getFoodProgress());

        // Atau, jika Anda ingin menampilkan total progress dari donasi non-tunai (misalnya rata-rata):
        // int totalProgress = (campaign.getFoodProgress() + campaign.getClothingProgress() + campaign.getMedicineProgress()) / 3;
        // holder.campaignProgress.setProgress(totalProgress);

        // Atau, jika Anda ingin menampilkan progress donasi tunai (jika ada ProgressBar untuk itu):
        // int cashProgressPercentage = (int) ((double) campaign.getAmountCollected() / campaign.getTargetAmount() * 100);
        // holder.campaignProgress.setProgress(cashProgressPercentage);

        // Untuk saat ini, saya akan mengomentari baris progress bar agar tidak error
        // jika Anda belum memutuskan atau belum ada ProgressBar yang sesuai di layout.
        // holder.campaignProgress.setProgress(campaign.getProgressPercentage()); // Baris ini sudah tidak ada di model Campaign
    }

    @Override
    public int getItemCount() {
        return campaignList.size();
    }

    public static class CampaignViewHolder extends RecyclerView.ViewHolder {
        ImageView campaignImage;
        TextView campaignDescription;
        ProgressBar campaignProgress; // Ini masih ada di ViewHolder Anda

        public CampaignViewHolder(View itemView) {
            super(itemView);
            campaignImage = itemView.findViewById(R.id.campaignImage);
            campaignDescription = itemView.findViewById(R.id.campaignDescription);
            campaignProgress = itemView.findViewById(R.id.campaignProgress);
        }
    }
}
