package com.example.rahmat_ux; // Sesuaikan dengan nama paketmu

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rahmat_ux.data.DummyDataRepository; // Impor Repository
import com.example.rahmat_ux.databinding.FragmentSocialBinding; // Impor Binding yang sesuai
import com.example.rahmat_ux.model.Campaign; // Impor Model

public class SocialFragment extends Fragment {

    // Gunakan binding class yang sesuai dengan nama file XML
    private FragmentSocialBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSocialBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Beri aksi pada tombol
        binding.buttonLoadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil method dari repository untuk mendapatkan data
                Campaign detail = DummyDataRepository.getDonationDetail();

                // Gunakan data dari objek 'detail' untuk mengisi komponen UI
                binding.textViewDonationTitle.setText(detail.getTitle());
                binding.textViewOrganizerName.setText("Penyelenggara: " + detail.getOrganizerName());
                binding.imageViewOrganizerLogo.setImageResource(detail.getOrganizerImageResId());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}