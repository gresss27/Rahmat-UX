package com.example.rahmat_ux.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rahmat_ux.databinding.ItemDonatedGoodBinding;
import com.example.rahmat_ux.databinding.ItemFooterTambahBinding;
import com.example.rahmat_ux.databinding.ItemGroupDonasiBinding;
import com.example.rahmat_ux.databinding.ItemHeaderDropPointBinding;
import com.example.rahmat_ux.databinding.ItemHeaderProgressBinding;
import com.example.rahmat_ux.model.Campaign;
import com.example.rahmat_ux.model.DonatedItem;
import com.example.rahmat_ux.model.DropPoint;

import java.util.List;

public class SummaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // 1. Definisikan tipe view sebagai konstanta
    private static final int VIEW_TYPE_PROGRESS = 0;
    private static final int VIEW_TYPE_DROP_POINT = 1;
    private static final int VIEW_TYPE_DONATION_GROUP = 2;

    private List<Object> combinedList;
    private OnActionClickListener listener;

    // Interface untuk komunikasi dari Adapter ke Activity
    public interface OnActionClickListener {
        void onGantiDropPointClick();
        void onTambahItemClick();
        void onEditItemClick(int position, DonatedItem item);
        void onDeleteItemClick(int position, DonatedItem item);
    }

    public SummaryAdapter(List<Object> combinedList, OnActionClickListener listener) {
        this.combinedList = combinedList;
        this.listener = listener;
    }

    // Method ini menentukan tipe view berdasarkan posisi dan jenis objek di dalam list
    @Override
    public int getItemViewType(int position) {
        Object item = combinedList.get(position);
        if (item instanceof Campaign) {
            return VIEW_TYPE_PROGRESS;
        } else if (item instanceof DropPoint) {
            return VIEW_TYPE_DROP_POINT;
        } else if (item instanceof List) { // Kita menandai grup donasi sebagai sebuah List
            return VIEW_TYPE_DONATION_GROUP;
        }
        return -1;
    }

    // =================================================================
    // KELAS-KELAS VIEWHOLDER UNTUK SETIAP JENIS TAMPILAN
    // =================================================================

    /** ViewHolder untuk layout item_header_progress.xml */
    private static class ProgressViewHolder extends RecyclerView.ViewHolder {
        private final ItemHeaderProgressBinding binding;

        ProgressViewHolder(ItemHeaderProgressBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Campaign campaign) {
            binding.item1Name.setText(campaign.getItem1Name());
            binding.item1Progress.setProgress(campaign.getItem1Progress());
            binding.item1Percent.setText(campaign.getItem1Progress() + "%");

            binding.item2Name.setText(campaign.getItem2Name());
            binding.item2Progress.setProgress(campaign.getItem2Progress());
            binding.item2Percent.setText(campaign.getItem2Progress() + "%");

            binding.item3Name.setText(campaign.getItem3Name());
            binding.item3Progress.setProgress(campaign.getItem3Progress());
            binding.item3Percent.setText(campaign.getItem3Progress() + "%");
        }
    }

    /** ViewHolder untuk layout item_header_drop_point.xml */
    private static class DropPointViewHolder extends RecyclerView.ViewHolder {
        private final ItemHeaderDropPointBinding binding;

        DropPointViewHolder(ItemHeaderDropPointBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(DropPoint dropPoint, OnActionClickListener listener) {
            binding.textDropPointName.setText(dropPoint.getName());
            binding.textDropPointAddress.setText(dropPoint.getAddress());
            binding.buttonChangeDropPoint.setOnClickListener(v -> listener.onGantiDropPointClick());
        }
    }

    /** ViewHolder untuk layout item_group_donasi.xml (yang akan berisi banyak item) */
    private static class DonationGroupViewHolder extends RecyclerView.ViewHolder {
        private final ItemGroupDonasiBinding binding;

        DonationGroupViewHolder(ItemGroupDonasiBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(List<DonatedItem> items, OnActionClickListener listener) {
            // PENTING: Hapus semua view lama sebelum menambahkan yang baru, untuk menghindari duplikasi saat di-recycle
            binding.containerItems.removeAllViews();

            LayoutInflater inflater = LayoutInflater.from(itemView.getContext());

            // Loop untuk menambahkan setiap item donasi ke dalam container
            for (int i = 0; i < items.size(); i++) {
                final int currentPosition = i;
                final DonatedItem item = items.get(i);

                // Inflate layout item donasi individu
                ItemDonatedGoodBinding itemBinding = ItemDonatedGoodBinding.inflate(inflater, binding.containerItems, false);

                // Isi datanya
                itemBinding.textItemName.setText(item.getName());
                itemBinding.textCategory.setText(item.getCategory());
                itemBinding.textItemQuantity.setText(item.getQuantity());
                // Set listener untuk edit & hapus, kirim posisi dan item-nya
                itemBinding.buttonEdit.setOnClickListener(v -> listener.onEditItemClick(currentPosition, item));
                itemBinding.buttonDelete.setOnClickListener(v -> listener.onDeleteItemClick(currentPosition, item));

                // Tambahkan view item ini ke dalam container di CardView
                binding.containerItems.addView(itemBinding.getRoot());
            }

            // Tambahkan footer "Tambah Lagi" di paling bawah container
            ItemFooterTambahBinding footerBinding = ItemFooterTambahBinding.inflate(inflater, binding.containerItems, false);
            footerBinding.buttonAddItem.setOnClickListener(v -> listener.onTambahItemClick());
            binding.containerItems.addView(footerBinding.getRoot());
        }
    }


    // =================================================================
    // IMPLEMENTASI METHOD UTAMA ADAPTER
    // =================================================================

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_TYPE_PROGRESS:
                return new ProgressViewHolder(ItemHeaderProgressBinding.inflate(inflater, parent, false));
            case VIEW_TYPE_DROP_POINT:
                return new DropPointViewHolder(ItemHeaderDropPointBinding.inflate(inflater, parent, false));
            case VIEW_TYPE_DONATION_GROUP:
                return new DonationGroupViewHolder(ItemGroupDonasiBinding.inflate(inflater, parent, false));
            default:
                // Selalu sediakan fallback view kosong untuk menghindari crash
                return new RecyclerView.ViewHolder(new View(parent.getContext())) {};
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object dataObject = combinedList.get(position);
        if (holder.getItemViewType() == VIEW_TYPE_PROGRESS) {
            ((ProgressViewHolder) holder).bind((Campaign) dataObject);
        } else if (holder.getItemViewType() == VIEW_TYPE_DROP_POINT) {
            ((DropPointViewHolder) holder).bind((DropPoint) dataObject, listener);
        } else if (holder.getItemViewType() == VIEW_TYPE_DONATION_GROUP) {
            ((DonationGroupViewHolder) holder).bind((List<DonatedItem>) dataObject, listener);
        }
    }

    @Override
    public int getItemCount() {
        return combinedList.size();
    }

    // Method untuk me-refresh seluruh daftar
    public void updateList(List<Object> newList) {
        this.combinedList.clear();
        this.combinedList.addAll(newList);
        notifyDataSetChanged();
    }
}