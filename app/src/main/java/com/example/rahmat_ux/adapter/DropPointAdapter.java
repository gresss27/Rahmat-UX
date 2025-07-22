package com.example.rahmat_ux.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rahmat_ux.DropPointSelectionFragment;
import com.example.rahmat_ux.databinding.ItemDropPointBinding;
import com.example.rahmat_ux.model.DropPoint;
import java.util.List;

public class DropPointAdapter extends RecyclerView.Adapter<DropPointAdapter.ViewHolder> {

    private final List<DropPoint> dropPointList;
    private final DropPointSelectionFragment fragment; // Untuk menutup bottom sheet

    public DropPointAdapter(List<DropPoint> dropPointList, DropPointSelectionFragment fragment) {
        this.dropPointList = dropPointList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDropPointBinding binding = ItemDropPointBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DropPoint dropPoint = dropPointList.get(position);
        holder.bind(dropPoint);
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

        public void bind(DropPoint dropPoint) {
            binding.textLocationName.setText(dropPoint.getName());
            binding.textLocationAddress.setText(dropPoint.getAddress());
            binding.textLocationDistance.setText(dropPoint.getDistance());

            itemView.setOnClickListener(v -> {
                Toast.makeText(v.getContext(), dropPoint.getName() + " dipilih", Toast.LENGTH_SHORT).show();
                fragment.dismiss(); // Menutup BottomSheet
            });
        }
    }
}