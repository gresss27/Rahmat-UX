package com.example.rahmat_ux;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rahmat_ux.adapter.CarouselBannerAdapter; // Ensure this path is correct
import com.example.rahmat_ux.adapter.CardExploreAdapter; // Your adapter for RecyclerView
import com.example.rahmat_ux.data.DummyDataRepository; // Repository to fetch dummy data
import com.example.rahmat_ux.model.Campaign; // Your model class for campaigns
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;
import androidx.recyclerview.widget.LinearLayoutManager;


public class ExploreFragment extends Fragment {

    private ViewPager2 viewPager;
    private Handler handler;
    private Runnable runnable;
    private int delay = 5000; // Delay per slide (5 seconds)
    private List<Integer> imageResources; // List of image resources for carousel

    // RecyclerView for campaign cards
    private RecyclerView campaignRecyclerView;
    private RecyclerView newCampaignRecyclerView;


    public ExploreFragment() {
        // Required public empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_explore, container, false);

        // Initialize ViewPager2 for banner carousel
        viewPager = rootView.findViewById(R.id.viewPager);
        handler = new Handler(Looper.getMainLooper());

        imageResources = Arrays.asList(
                R.drawable.banner1,
                R.drawable.banner2,
                R.drawable.banner3,
                R.drawable.banner4,
                R.drawable.banner5
        );

        CarouselBannerAdapter carouselAdapter = new CarouselBannerAdapter(imageResources);
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

        // Initialize RecyclerView for campaigns
        campaignRecyclerView = rootView.findViewById(R.id.campaignRecyclerView);
        campaignRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        newCampaignRecyclerView = rootView.findViewById(R.id.newCampaignRecyclerView);
        newCampaignRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Set adapter for RecyclerView
        List<Campaign> campaignList = DummyDataRepository.getCampaignList();
        CardExploreAdapter campaignAdapter = new CardExploreAdapter(campaignList);
        campaignRecyclerView.setAdapter(campaignAdapter);
        newCampaignRecyclerView.setAdapter(campaignAdapter);
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
}
