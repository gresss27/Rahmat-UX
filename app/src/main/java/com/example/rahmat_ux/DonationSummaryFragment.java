package com.example.rahmat_ux;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rahmat_ux.adapter.DonatedItemAdapter;
import com.example.rahmat_ux.data.DonationFlowRepository;
import com.example.rahmat_ux.databinding.FragmentDonationSummaryBinding;
import com.example.rahmat_ux.model.DonatedItem;

import java.util.List;

public class DonationSummaryFragment extends Fragment {

    private FragmentDonationSummaryBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDonationSummaryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        setupClickListeners();
    }

    private void setupRecyclerView() {
        List<DonatedItem> itemList = DonationFlowRepository.getDonatedItems();
        DonatedItemAdapter adapter = new DonatedItemAdapter(itemList);
        binding.recyclerViewDonatedItems.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewDonatedItems.setAdapter(adapter);
    }

    private void setupClickListeners() {
        // Tombol kembali di toolbar
        binding.toolbar.setNavigationOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());

        // Tombol Ganti Drop Point
// Di dalam DonationSummaryFragment.java
        binding.buttonChangeDropPoint.setOnClickListener(v -> {
            new DropPointSelectionFragment().show(getParentFragmentManager(), "DropPointSheet");
        });

        // Tombol Tambah Item
        binding.buttonAddItem.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AddEditItemFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // Tombol Donasi Sekarang
        binding.buttonDonateNow.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Proses donasi...", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}