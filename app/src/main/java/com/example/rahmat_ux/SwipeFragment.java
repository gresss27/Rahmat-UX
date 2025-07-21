package com.example.rahmat_ux;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;


public class SwipeFragment extends Fragment {

    private ViewPager2 viewPager;
    private List<DonationItem> donationItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_swipe, container, false);
        viewPager = view.findViewById(R.id.viewPager);

        // Dummy data
        donationItems = Arrays.asList(
                new DonationItem("Bantu Anak Sekolah", "Rp5.000.000", "Hana"),
                new DonationItem("Donasi Makanan", "Rp2.000.000", "Rudi")
        );

        DonationCardAdapter adapter = new DonationCardAdapter(donationItems, getContext());
        viewPager.setAdapter(adapter);

        return view;
    }
}
