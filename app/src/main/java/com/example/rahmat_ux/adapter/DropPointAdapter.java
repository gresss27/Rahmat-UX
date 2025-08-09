// Buat file baru: adapter/DropPointAdapter.java
package com.example.rahmat_ux.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rahmat_ux.databinding.ItemDropPointBinding; // Dibuat otomatis dari view binding
import com.example.rahmat_ux.model.DropPoint;
import java.util.List;

public class DropPointAdapter extends RecyclerView.Adapter<DropPointAdapter.ViewHolder> {

    private final List<DropPoint> dropPointList;
    private final OnDropPointClickListener listener;

    public interface OnDropPointClickListener {
        void onDropPointClick(DropPoint dropPoint);
    }

    public DropPointAdapter(List<DropPoint> dropPointList, OnDropPointClickListener listener) {
        this.dropPointList = dropPointList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDropPointBinding binding = ItemDropPointBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(dropPointList.get(position));
    }

    @Override
    public int getItemCount() {
        return dropPointList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemDropPointBinding binding;

        public ViewHolder(ItemDropPointBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final DropPoint dropPoint) {
            binding.textDpName.setText(dropPoint.getName());
            binding.textDpAddress.setText(dropPoint.getAddress());
            binding.textDpDistance.setText(dropPoint.getDistance());

            itemView.setOnClickListener(v -> listener.onDropPointClick(dropPoint));
        }
    }

    public void updateData(List<DropPoint> newList) {
        this.dropPointList.clear();
        this.dropPointList.addAll(newList);
        notifyDataSetChanged(); // Memberi tahu RecyclerView untuk refresh tampilan
    }
}