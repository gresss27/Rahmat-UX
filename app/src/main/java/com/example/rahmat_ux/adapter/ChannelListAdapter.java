package com.example.rahmat_ux.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rahmat_ux.R;
import com.example.rahmat_ux.databinding.ItemChannelListBinding;
import com.example.rahmat_ux.model.Campaign;

import java.util.List;

public class ChannelListAdapter extends RecyclerView.Adapter<ChannelListAdapter.ChannelViewHolder> {

    private final List<Campaign> campaignList;
    private OnItemClickListener listener; // Listener untuk klik item

    // 1. Interface untuk berkomunikasi dengan Fragment
    public interface OnItemClickListener {
        void onItemClick(Campaign campaign);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ChannelListAdapter(List<Campaign> campaignList) {
        this.campaignList = campaignList;
    }

    @NonNull
    @Override
    public ChannelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChannelListBinding binding = ItemChannelListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ChannelViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelViewHolder holder, int position) {
        Campaign campaign = campaignList.get(position);
        holder.bind(campaign);
    }

    @Override
    public int getItemCount() {
        return campaignList.size();
    }

    // 2. Hapus kata kunci 'static' agar bisa mengakses 'listener'
    public class ChannelViewHolder extends RecyclerView.ViewHolder {
        private final ItemChannelListBinding binding;

        public ChannelViewHolder(ItemChannelListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Campaign campaign) {
            // Mengatur data ke view
            binding.imageViewProfile.setImageResource(campaign.getMainImageResId());
            binding.textViewTitle.setText(campaign.getTitle());

            // 3. Perbaiki dari getLastChat() menjadi getDescription()
            binding.textViewDescription.setText(campaign.getDescription());

            // Mengatur progress bar
            if (campaign.getTargetAmount() > 0) {
                int progress = (int) ((double) campaign.getAmountCollected() * 100 / campaign.getTargetAmount());
                binding.progressBar.setProgress(progress);
            } else {
                binding.progressBar.setProgress(0);
            }

            // 4. Tambahkan kembali OnClickListener untuk seluruh item
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(campaign);
                }
            });

            // OnClickListener untuk ikon opsi (ini sudah benar)
            binding.imageViewOptions.setOnClickListener(v -> {
                Context context = v.getContext();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.custom_popup_menu_channel, null);

                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

                LinearLayout detailButton = popupView.findViewById(R.id.menu_detail);
                LinearLayout deleteButton = popupView.findViewById(R.id.menu_delete);

                detailButton.setOnClickListener(item -> {
                    Toast.makeText(context, "Lihat Detail: " + campaign.getTitle(), Toast.LENGTH_SHORT).show();
                    popupWindow.dismiss();
                });

                deleteButton.setOnClickListener(item -> {
                    Toast.makeText(context, "Hapus: " + campaign.getTitle(), Toast.LENGTH_SHORT).show();
                    popupWindow.dismiss();
                });

                int xOffsetDp = -120;
                float density = context.getResources().getDisplayMetrics().density;
                int xOffsetPixels = (int) (xOffsetDp * density);

                popupWindow.showAsDropDown(binding.imageViewOptions, xOffsetPixels, 0);
            });
        }
    }
}