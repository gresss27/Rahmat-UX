package com.example.rahmat_ux;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.rahmat_ux.adapter.CarouselBannerAdapter;
import com.example.rahmat_ux.adapter.CardExploreAdapter;
import com.example.rahmat_ux.data.DummyDataRepository;
import com.example.rahmat_ux.data.UserStorage;
import com.example.rahmat_ux.model.Campaign;
import com.example.rahmat_ux.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

public class ExploreFragment extends Fragment {

    private ViewPager2 viewPager;
    private Handler handler;
    private Runnable runnable;
    private int delay = 5000;
    private List<Integer> imageResources;

    private RecyclerView campaignRecyclerView;
    private RecyclerView newCampaignRecyclerView;

    private CardExploreAdapter campaignAdapter;
    private CardExploreAdapter newCampaignAdapter;

    private ImageView profileImageView;

    public ExploreFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_explore, container, false);

        ImageView iconKanan = rootView.findViewById(R.id.icon_kanan);
        iconKanan.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NotificationActivity.class);
            startActivity(intent);
        });

        // ✅ FIX: Use rootView instead of "view"
        profileImageView = rootView.findViewById(R.id.icon_kiri);

        // Load profile image safely
        User currentUser = UserStorage.getInstance().getLoggedInUser();
        String imageUri = currentUser != null ? currentUser.getProfileImageUri() : null;
        if (imageUri != null && !imageUri.isEmpty()) {
            try {
                profileImageView.setImageURI(Uri.parse(imageUri));
            } catch (SecurityException e) {
                profileImageView.setImageResource(R.drawable.profile); // fallback
            }
        } else {
            profileImageView.setImageResource(R.drawable.profile);
        }

        CardView bannerRekomendasi = rootView.findViewById(R.id.bannerRekomendasi);
        bannerRekomendasi.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DonationDetailActivity.class);
            startActivity(intent);
        });

        viewPager = rootView.findViewById(R.id.viewPager);
        handler = new Handler(Looper.getMainLooper());

        imageResources = Arrays.asList(
                R.drawable.banner1,
                R.drawable.banner2,
                R.drawable.banner3,
                R.drawable.banner4,
                R.drawable.banner5
        );

        List<Integer> donationIdList = Arrays.asList(1, 4, 3, 5, 2);
        CarouselBannerAdapter carouselAdapter = new CarouselBannerAdapter(imageResources, donationIdList);
        viewPager.setAdapter(carouselAdapter);

        runnable = () -> {
            if (viewPager != null && imageResources != null && !imageResources.isEmpty()) {
                int currentItem = viewPager.getCurrentItem();
                int nextItem = (currentItem + 1) % imageResources.size();
                viewPager.setCurrentItem(nextItem, true);
                handler.postDelayed(runnable, delay);
            }
        };

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                    handler.removeCallbacks(runnable);
                } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable, delay);
                }
            }
        });

        if (!imageResources.isEmpty()) {
            handler.postDelayed(runnable, delay);
        }

        // Campaign lists
        campaignRecyclerView = rootView.findViewById(R.id.campaignRecyclerView);
        campaignRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        newCampaignRecyclerView = rootView.findViewById(R.id.newCampaignRecyclerView);
        newCampaignRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        updateCampaigns();
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (handler != null && runnable != null && imageResources != null && !imageResources.isEmpty()) {
            handler.postDelayed(runnable, delay);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (viewPager != null && runnable != null) {
            viewPager.unregisterOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {});
            handler.removeCallbacks(runnable);
        }
        handler = null;
        viewPager = null;
        imageResources = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView iconKiri = view.findViewById(R.id.icon_kiri);
        iconKiri.setOnClickListener(v -> {
            BottomNavigationView bottomNav = requireActivity().findViewById(R.id.bottom_navigation);
            bottomNav.setSelectedItemId(R.id.navigation_user);
        });

        EditText searchView = view.findViewById(R.id.kolom_pencarian);
        searchView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {

                String keyword = v.getText().toString().trim().toLowerCase();
                if (keyword.equalsIgnoreCase("Banjir")) {
                    UrgentFragment urgentFragment = UrgentFragment.newInstance(keyword);
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, urgentFragment)
                            .addToBackStack(null)
                            .commit();
                } else {
                    Toast.makeText(getContext(), "Keyword tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });

        // Category buttons
        view.findViewById(R.id.iconKesehatan).setOnClickListener(v -> openCategory("Kesehatan"));
        view.findViewById(R.id.iconBencana).setOnClickListener(v -> openCategory("Bencana Alam"));
        view.findViewById(R.id.iconPendidikan).setOnClickListener(v -> openCategory("Pendidikan"));
        view.findViewById(R.id.iconSosial).setOnClickListener(v -> openCategory("Kegiatan Sosial"));
        view.findViewById(R.id.iconInfrastruktur).setOnClickListener(v -> openCategory("Infrastruktur"));
    }

    private void openCategory(String category) {
        Intent intent = new Intent(getActivity(), CategoryDetailActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }

    private void updateCampaigns() {
        List<Campaign> ongoingCampaigns = DummyDataRepository.getInstance().getCampaignsByStatus("Berlangsung");
        List<Campaign> newCampaigns = DummyDataRepository.getInstance().getCampaignsByIdRange(5, 9);

        if (campaignAdapter == null) {
            campaignAdapter = new CardExploreAdapter(requireContext(), ongoingCampaigns);
            campaignRecyclerView.setAdapter(campaignAdapter);
        } else {
            campaignAdapter.setCampaigns(ongoingCampaigns);
            campaignAdapter.notifyDataSetChanged();
        }

        if (newCampaignAdapter == null) {
            newCampaignAdapter = new CardExploreAdapter(requireContext(), newCampaigns);
            newCampaignRecyclerView.setAdapter(newCampaignAdapter);
        } else {
            newCampaignAdapter.setCampaigns(newCampaigns);
            newCampaignAdapter.notifyDataSetChanged();
        }
    }
}
