package com.example.rahmat_ux;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.rahmat_ux.databinding.FragmentAddEditItemBinding;

public class AddEditItemFragment extends Fragment {

    private FragmentAddEditItemBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddEditItemBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Cek apakah ada data yang dikirim (menandakan mode edit)
        if (getArguments() != null) {
            // --- INI ADALAH MODE EDIT ---
            binding.toolbar.setTitle("Edit Barang");
            binding.buttonSubmit.setText("Update Barang");

            // Ambil data dari bundle dan tampilkan di form
            String itemName = getArguments().getString("ITEM_NAME", "");
            String itemQuantity = getArguments().getString("ITEM_QUANTITY", "");

            binding.editTextItemName.setText(itemName);
            binding.editTextItemQuantity.setText(itemQuantity);

        } else {
            // --- INI ADALAH MODE TAMBAH ---
            binding.toolbar.setTitle("Tambah Barang");
            binding.buttonSubmit.setText("Tambahkan Barang");
        }

        // Atur listener untuk tombol kembali di toolbar
        binding.toolbar.setNavigationOnClickListener(v -> getParentFragmentManager().popBackStack());

        // Atur listener untuk tombol utama (submit)
        binding.buttonSubmit.setOnClickListener(v -> {
            // Karena hanya frontend, kita tampilkan pesan dan kembali
            Toast.makeText(getContext(), "Perubahan disimpan!", Toast.LENGTH_SHORT).show();
            getParentFragmentManager().popBackStack();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}