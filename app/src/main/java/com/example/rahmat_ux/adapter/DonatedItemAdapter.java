package com.example.rahmat_ux.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rahmat_ux.AddEditItemFragment;
import com.example.rahmat_ux.databinding.ItemDonatedGoodBinding;
import com.example.rahmat_ux.model.DonatedItem;
import java.util.List;

public class DonatedItemAdapter extends RecyclerView.Adapter<DonatedItemAdapter.ViewHolder> {

    private final List<DonatedItem> itemList;

    public DonatedItemAdapter(List<DonatedItem> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDonatedGoodBinding binding = ItemDonatedGoodBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonatedItem item = itemList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemDonatedGoodBinding binding;

        public ViewHolder(ItemDonatedGoodBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(DonatedItem item) {
            // Di dalam DonatedItemAdapter.java, di dalam method bind()
            binding.buttonEdit.setOnClickListener(v -> {
                // Di sini kamu perlu cara untuk berkomunikasi kembali ke Fragment,
                // lalu dari Fragment, panggil kode ini:

                AddEditItemFragment editFragment = new AddEditItemFragment();
                Bundle bundle = new Bundle();
                bundle.putString("ITEM_NAME", item.getName());
                bundle.putString("ITEM_QUANTITY", item.getQuantity());
                editFragment.setArguments(bundle);

                // Lalu lakukan transaksi fragment dari DonationSummaryFragment...
            });
            binding.textCategory.setText(item.getCategory());
            binding.textItemName.setText(item.getName());
            binding.textItemQuantity.setText(item.getQuantity());
        }
    }
}