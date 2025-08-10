package com.example.rahmat_ux.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rahmat_ux.R;
import com.example.rahmat_ux.model.DonationHistory;

import java.util.List;

public class DonationHistoryAdapter extends RecyclerView.Adapter<DonationHistoryAdapter.ViewHolder> {

    private List<DonationHistory> historyList;

    public DonationHistoryAdapter(List<DonationHistory> historyList) {
        this.historyList = historyList;
    }

    public void setFilteredList(List<DonationHistory> filteredList) {
        this.historyList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DonationHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donation_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationHistoryAdapter.ViewHolder holder, int position) {
        DonationHistory item = historyList.get(position);
        holder.title.setText(item.getTitle());
        holder.status.setText(item.getStatus());
        holder.amount.setText(item.getAmount());
        holder.image.setImageResource(item.getImageResId());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, status, amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageViewHistory);
            title = itemView.findViewById(R.id.textTitle);
            status = itemView.findViewById(R.id.textStatus);
            amount = itemView.findViewById(R.id.textAmount);
        }
    }
}
