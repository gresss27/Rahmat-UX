// Buat file baru: PilihDropPointSheet.java
package com.example.rahmat_ux;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import com.example.rahmat_ux.adapter.DropPointAdapter;
import com.example.rahmat_ux.databinding.BottomSheetPilihDropPointBinding;
import com.example.rahmat_ux.model.DropPoint;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class PilihDropPointSheet extends BottomSheetDialogFragment {

    private BottomSheetPilihDropPointBinding binding;
    private DropPointSelectionListener mListener;

    private DropPointAdapter adapter;
    private com.example.rahmat_ux.repository.DropPointRepository repository;


    // Interface untuk komunikasi kembali ke Activity
    public interface DropPointSelectionListener {
        void onDropPointSelected(DropPoint dropPoint);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetPilihDropPointBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. DAPATKAN INSTANCE REPOSITORY
        repository = com.example.rahmat_ux.repository.DropPointRepository.getInstance();

        // 2. AMBIL DATA AWAL DARI REPOSITORY
        List<DropPoint> initialDropPoints = repository.getDropPoints();

        // 3. BUAT ADAPTER DENGAN DATA DARI REPOSITORY
        adapter = new DropPointAdapter(initialDropPoints, selectedDropPoint -> {
            // Saat item diklik, kirim data kembali ke Activity dan tutup sheet
            if (mListener != null) {
                mListener.onDropPointSelected(selectedDropPoint);
            }
            dismiss();
        });

        // Setup RecyclerView
        binding.recyclerViewDropPoint.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewDropPoint.setAdapter(adapter);

        // 4. IMPLEMENTASIKAN LOGIKA PENCARIAN
        binding.editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Tidak perlu melakukan apa-apa di sini
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Saat pengguna mengetik, cari data di repository
                String query = s.toString();
                List<DropPoint> filteredList = repository.searchDropPoints(query);
                // Update data di adapter
                adapter.updateData(filteredList);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Tidak perlu melakukan apa-apa di sini
            }
        });
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (DropPointSelectionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " harus mengimplementasikan DropPointSelectionListener");
        }
    }
}