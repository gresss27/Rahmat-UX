package com.example.rahmat_ux;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rahmat_ux.data.DummyDataRepository;
import com.example.rahmat_ux.model.Campaign;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import java.util.List;

public class SwipeFragment extends Fragment {

    private FrameLayout cardContainer;
    private List<Campaign> campaignList;
    private int currentIndex = 0;
    private LayoutInflater inflater;

    private GestureDetector gestureDetector;
    private View currentCardView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_swipe, container, false);
        this.inflater = inflater;
        gestureDetector = new GestureDetector(getContext(), new GestureListener());

        cardContainer = view.findViewById(R.id.cardContainer);
        campaignList = DummyDataRepository.getCampaignList();

        Button btnDecline = view.findViewById(R.id.btnDecline);
        Button btnAccept = view.findViewById(R.id.btnAccept);

        btnDecline.setOnClickListener(v -> swipeLeft());
        btnAccept.setOnClickListener(v -> swipeRight());

        // Tampilkan kartu pertama
        showNextCard();
        cardContainer.setOnTouchListener((v, event) -> {
            if (currentCardView != null) {
                gestureDetector.onTouchEvent(event);
            }
            return true;
        });
        return view;
    }

    private void showNextCard() {
        cardContainer.removeAllViews();  // Clear old views

        // Show current and next card (if exists)
        for (int i = 0; i < 2; i++) {
            int index = (currentIndex + i) % campaignList.size();
            Campaign campaign = campaignList.get(index);
            View cardView = inflater.inflate(R.layout.item_donation_card, cardContainer, false);

            // Fill data
            ImageView thumbnail = cardView.findViewById(R.id.thumbnailImage);
            TextView title = cardView.findViewById(R.id.campaignTitle);
            TextView amount = cardView.findViewById(R.id.campaignAmount);
            ImageView organizerImage = cardView.findViewById(R.id.organizerProfile);
            TextView organizerName = cardView.findViewById(R.id.organizerName);

            title.setText(campaign.getTitle());
            amount.setText(String.format("Terkumpul: Rp%,d", campaign.getAmountCollected()));
            thumbnail.setImageResource(campaign.getMainImageResId());
            organizerName.setText(campaign.getOrganizerName());
            organizerImage.setImageResource(campaign.getOrganizerImageResId());

            cardContainer.addView(cardView, 0); // Add to bottom of the stack
        }

        currentCardView = cardContainer.getChildAt(cardContainer.getChildCount() - 1);
    }

    private void swipeLeft() {
        if (cardContainer.getChildCount() == 0) return;
        View topCard = cardContainer.getChildAt(cardContainer.getChildCount() - 1);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(
                topCard,
                PropertyValuesHolder.ofFloat("translationX", -1000f),
                PropertyValuesHolder.ofFloat("rotation", -20f),
                PropertyValuesHolder.ofFloat("alpha", 0f)
        );
        animator.setDuration(400);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();

        animator.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                cardContainer.removeView(topCard);
                currentIndex = (currentIndex + 1) % campaignList.size(); // Infinite loop
                showNextCard();
            }
        });
    }

    private void swipeRight() {
        if (cardContainer.getChildCount() == 0) return;
        View topCard = cardContainer.getChildAt(cardContainer.getChildCount() - 1);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(
                topCard,
                PropertyValuesHolder.ofFloat("translationX", 1000f),
                PropertyValuesHolder.ofFloat("rotation", 20f),
                PropertyValuesHolder.ofFloat("alpha", 0f)
        );
        animator.setDuration(400);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();

        Toast.makeText(getContext(), "Donasi berhasil!", Toast.LENGTH_SHORT).show();

        animator.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                cardContainer.removeView(topCard);
                currentIndex = (currentIndex + 1) % campaignList.size(); // Infinite loop
                showNextCard();
            }
        });
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffX = e2.getX() - e1.getX();
            float diffY = e2.getY() - e1.getY();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        swipeRight();
                    } else {
                        swipeLeft();
                    }
                    return true;
                }
            }
            return false;
        }
    }


}

