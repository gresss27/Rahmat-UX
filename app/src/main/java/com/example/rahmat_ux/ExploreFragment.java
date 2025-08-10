package com.example.rahmat_ux;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;



import com.example.rahmat_ux.adapter.CarouselBannerAdapter;
import com.example.rahmat_ux.adapter.CardExploreAdapter;
import com.example.rahmat_ux.data.DummyDataRepository;
import com.example.rahmat_ux.model.Campaign;

import java.util.Arrays;
import java.util.List;
import androidx.recyclerview.widget.LinearLayoutManager;


public class ExploreFragment extends Fragment {

    private ViewPager2 viewPager;
    private Handler handler;
    private Runnable runnable;
    private int delay = 5000;
    private List<Integer> imageResources;


    private RecyclerView campaignRecyclerView;
    private RecyclerView newCampaignRecyclerView;

    // Adapters
    private CardExploreAdapter campaignAdapter;
    private CardExploreAdapter newCampaignAdapter;

    public ExploreFragment() {



    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_explore, container, false);



        ImageView iconKanan = rootView.findViewById(R.id.icon_kanan);
        iconKanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotificationActivity.class);
                startActivity(intent);
            }
        });



        CardView bannerRekomendasi = rootView.findViewById(R.id.bannerRekomendasi);
        bannerRekomendasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DonationDetailActivity.class);
                startActivity(intent);
            }
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

        List<Integer> donationIdList = Arrays.asList(
                1, 4, 3, 5, 2
        );

        CarouselBannerAdapter carouselAdapter = new CarouselBannerAdapter(imageResources, donationIdList);
        viewPager.setAdapter(carouselAdapter);

        runnable = new Runnable() {
            @Override
            public void run() {
                if (viewPager == null || imageResources == null || imageResources.isEmpty()) {
                    return;
                }
                int currentItem = viewPager.getCurrentItem();
                int nextItem = (currentItem + 1) % imageResources.size(); // Loop to the beginning
                viewPager.setCurrentItem(nextItem, true);
                handler.postDelayed(this, delay);
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

        // Initialize RecyclerViews and their adapters
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
        if (handler != null && runnable != null && !imageResources.isEmpty()) {
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

        iconKiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomNavigationView bottomNav = requireActivity().findViewById(R.id.bottom_navigation);
                bottomNav.setSelectedItemId(R.id.navigation_user);
            }
        });


        EditText searchView = view.findViewById(R.id.kolom_pencarian);

        searchView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {

                String keyword = v.getText().toString().trim().toLowerCase();

                if (keyword.equalsIgnoreCase("Banjir")) {
                    // Pindah ke UrgentFragment
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






        ImageView iconKesehatan = view.findViewById(R.id.iconKesehatan);
        ImageView iconBencana = view.findViewById(R.id.iconBencana);
        ImageView iconPendidikan = view.findViewById(R.id.iconPendidikan);
        ImageView iconSosial = view.findViewById(R.id.iconSosial);
        ImageView iconInfrastruktur = view.findViewById(R.id.iconInfrastruktur);

        iconKesehatan.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CategoryDetailActivity.class);
            intent.putExtra("category", "Kesehatan");
            startActivity(intent);
        });

        iconBencana.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CategoryDetailActivity.class);
            intent.putExtra("category", "Bencana Alam");
            startActivity(intent);
        });

        iconPendidikan.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CategoryDetailActivity.class);
            intent.putExtra("category", "Pendidikan");
            startActivity(intent);
        });

        iconSosial.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CategoryDetailActivity.class);
            intent.putExtra("category", "Kegiatan Sosial");
            startActivity(intent);
        });

        iconInfrastruktur.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CategoryDetailActivity.class);
            intent.putExtra("category", "Infrastruktur");
            startActivity(intent);
        });

    }







    private void updateCampaigns() {
        List<Campaign> ongoingCampaigns = DummyDataRepository.getInstance().getCampaignsByStatus("Berlangsung");
        List<Campaign> newCampaigns = DummyDataRepository.getInstance().getCampaignsByStatus("Diajukan");


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
