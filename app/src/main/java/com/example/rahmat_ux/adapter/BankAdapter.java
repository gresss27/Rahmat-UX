package com.example.rahmat_ux.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rahmat_ux.R;
import com.example.rahmat_ux.model.Bank;

import java.util.List;

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.ViewHolder> {

    private final List<Bank> bankList;
    private final OnBankClickListener listener;

    public interface OnBankClickListener {
        void onBankClick(Bank bank);
    }

    public BankAdapter(List<Bank> bankList, OnBankClickListener listener) {
        this.bankList = bankList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bank_transfer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bank bank = bankList.get(position);
        holder.bankName.setText(bank.getBankName());
        holder.bankImage.setImageResource(bank.getBankLogoResId());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBankClick(bank);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bankList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bankImage;
        TextView bankName;

        ViewHolder(View itemView) {
            super(itemView);
            bankImage = itemView.findViewById(R.id.imageBank);
            bankName = itemView.findViewById(R.id.textBankName);
        }
    }
}
