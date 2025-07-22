package com.example.rahmat_ux.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rahmat_ux.R;
import com.example.rahmat_ux.model.Donator;

import java.util.List;

public class DonatorAdapter extends RecyclerView.Adapter<DonatorAdapter.DonatorViewHolder> {

    private List<Donator> donatorList;
    private int maxItems; // use 0 or negative for full list

    public DonatorAdapter(List<Donator> donatorList, int maxItems) {
        this.donatorList = donatorList;
        this.maxItems = maxItems;
    }

    @NonNull
    @Override
    public DonatorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_donator, parent, false);
        return new DonatorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonatorViewHolder holder, int position) {
        Donator donator = donatorList.get(position);
        holder.textName.setText(donator.getName());
        holder.textAmount.setText(donator.getAmount());
        holder.textTime.setText(donator.getTime());
        holder.imageDonator.setImageResource(donator.getImageResId());
    }

    @Override
    public int getItemCount() {
        if (maxItems > 0 && donatorList.size() > maxItems) {
            return maxItems;
        } else {
            return donatorList.size();
        }
    }

    static class DonatorViewHolder extends RecyclerView.ViewHolder {
        ImageView imageDonator;
        TextView textName, textAmount, textTime;

        public DonatorViewHolder(@NonNull View itemView) {
            super(itemView);
            imageDonator = itemView.findViewById(R.id.imageDonator);
            textName = itemView.findViewById(R.id.textName);
            textAmount = itemView.findViewById(R.id.textAmount);
            textTime = itemView.findViewById(R.id.textTime);
        }
    }
}
