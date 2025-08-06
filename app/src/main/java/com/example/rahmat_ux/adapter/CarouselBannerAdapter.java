package com.example.rahmat_ux.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rahmat_ux.DonationDetailActivity;
import com.example.rahmat_ux.R;
import java.util.List;

public class CarouselBannerAdapter extends RecyclerView.Adapter<CarouselBannerAdapter.CarouselViewHolder> {
    private List<Integer> imageResources;  // Menggunakan List<Integer> untuk ID gambar drawable
    private List<Integer> donationIds;

    public CarouselBannerAdapter(List<Integer> imageResources, List<Integer> donationIds) {
        this.imageResources = imageResources;
        this.donationIds = donationIds;
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carousel_banner_item, parent, false);
        return new CarouselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        int imageRes = imageResources.get(position);
        int donationId = donationIds.get(position);

        holder.imageView.setImageResource(imageRes);

        holder.imageView.setOnClickListener(v -> {
            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, DonationDetailActivity.class);
            intent.putExtra("campaign_id", donationId); // Pass the correct ID
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return imageResources.size();
    }

    public static class CarouselViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public CarouselViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.carousel_image);
        }
    }
}
