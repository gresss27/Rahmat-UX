package com.example.rahmat_ux.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rahmat_ux.R;
import java.util.List;

public class CarouselBannerAdapter extends RecyclerView.Adapter<CarouselBannerAdapter.CarouselViewHolder> {
    private List<Integer> imageResources;  // Menggunakan List<Integer> untuk ID gambar drawable

    public CarouselBannerAdapter(List<Integer> imageResources) {
        this.imageResources = imageResources;
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carousel_banner_item, parent, false);
        return new CarouselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        int imageRes = imageResources.get(position);  // Mendapatkan ID gambar dari List<Integer>
        holder.imageView.setImageResource(imageRes);  // Memuat gambar dari drawable menggunakan ID
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
