package com.example.rahmat_ux.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rahmat_ux.databinding.ItemDonatedGoodBinding;
import com.example.rahmat_ux.model.DonatedItem;
import java.util.List;

public class DonatedItemAdapter extends RecyclerView.Adapter<DonatedItemAdapter.ViewHolder> {

    private final List<DonatedItem> itemList;
    // MODIFIKASI 1: Ganti nama listener agar lebih umum
    private OnItemActionListener actionListener;

    // MODIFIKASI 2: Perbarui Interface untuk menangani Edit dan Hapus.
    // Kita gunakan 'position' agar lebih efisien saat menghapus dari list.
    public interface OnItemActionListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

    // MODIFIKASI 3: Perbarui nama setter method
    public void setOnItemActionListener(OnItemActionListener listener) {
        this.actionListener = listener;
    }

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
        // MODIFIKASI 4: Kirim 'position' ke method bind
        holder.bind(item, position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemDonatedGoodBinding binding;

        public ViewHolder(ItemDonatedGoodBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        // MODIFIKASI 5: Method bind sekarang menerima 'position'
        public void bind(DonatedItem item, int position) {
            binding.textCategory.setText(item.getCategory());
            binding.textItemName.setText(item.getName());
            binding.textItemQuantity.setText(item.getQuantity());

            // Listener untuk Tombol Edit
            binding.buttonEdit.setOnClickListener(v -> {
                if (actionListener != null) {
                    // Kirim posisi item yang diklik
                    actionListener.onEditClick(position);
                }
            });

            // MODIFIKASI 6: Tambahkan Listener untuk Tombol Hapus
            binding.buttonDelete.setOnClickListener(v -> {
                if (actionListener != null) {
                    // Kirim posisi item yang diklik
                    actionListener.onDeleteClick(position);
                }
            });
        }
    }
}