package com.example.rahmat_ux;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DonationCardAdapter extends RecyclerView.Adapter<DonationCardAdapter.ViewHolder> {

    private List<DonationItem> items;
    private Context context;

    public DonationCardAdapter(List<DonationItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public DonationCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_donation_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationCardAdapter.ViewHolder holder, int position) {
        DonationItem item = items.get(position);
        holder.title.setText(item.getTitle());
        holder.target.setText("Target: " + item.getTarget());
        holder.creator.setText("Oleh: " + item.getCreator());

        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(context, "Deskripsi: " + item.getTitle(), Toast.LENGTH_SHORT).show();
            // Bisa ganti dengan membuka fragment baru atau dialog
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, target, creator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            target = itemView.findViewById(R.id.tvTarget);
            creator = itemView.findViewById(R.id.tvCreator);
        }
    }
}

