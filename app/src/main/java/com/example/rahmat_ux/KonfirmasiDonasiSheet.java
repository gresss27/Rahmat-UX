package com.example.rahmat_ux; // Sesuaikan dengan package Anda

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rahmat_ux.databinding.BottomSheetConfirmationBinding; // Nama binding akan otomatis dibuat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class KonfirmasiDonasiSheet extends BottomSheetDialogFragment {

    private BottomSheetConfirmationBinding binding;
    private BottomSheetListener mListener;

    // 1. Buat interface untuk komunikasi kembali ke Activity
    public interface BottomSheetListener {
        void onKonfirmasiClicked();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Gunakan ViewBinding untuk inflate layout
        binding = BottomSheetConfirmationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set listener untuk tombol "Gak Jadi"
        binding.btnBatal.setOnClickListener(v -> {
            // Menutup Bottom Sheet
            dismiss();
        });

        // Set listener untuk tombol "Konfirmasi"
        binding.btnKonfirmasi.setOnClickListener(v -> {
            // Beri tahu Activity bahwa tombol konfirmasi ditekan
            if (mListener != null) {
                mListener.onKonfirmasiClicked();
            }
            // Tutup Bottom Sheet
            dismiss();
        });
    }

    // 2. Method untuk menyambungkan Activity sebagai listener
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            // Pastikan Activity yang memanggil mengimplementasikan interface
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " harus mengimplementasikan BottomSheetListener");
        }
    }
}