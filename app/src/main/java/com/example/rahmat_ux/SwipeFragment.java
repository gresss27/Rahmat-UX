package com.example.rahmat_ux;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

import java.util.List;

public class SwipeFragment extends Fragment {

    private FrameLayout cardContainer;
    private List<Campaign> campaignList;
    private int currentIndex = 0;
    private LayoutInflater inflater;

    private GestureDetector gestureDetector;
    private View currentCardView;

    @SuppressLint("ClickableViewAccessibility")
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

        // Tambahkan gesture swipe manual
        cardContainer.setOnTouchListener((v, event) -> {
            if (currentCardView != null) {
                gestureDetector.onTouchEvent(event);
            }
            return true;
        });

        return view;
    }

    @SuppressLint("DefaultLocale")
    private void showNextCard() {
        cardContainer.removeAllViews();

        for (int i = 0; i < 2; i++) {
            int index = (currentIndex + i) % campaignList.size();
            Campaign campaign = campaignList.get(index);
            View cardView = inflater.inflate(R.layout.item_donation_card, cardContainer, false);

            // Bind data
            ImageView thumbnail = cardView.findViewById(R.id.thumbnailImage);
            TextView title = cardView.findViewById(R.id.campaignTitle);
            TextView amount = cardView.findViewById(R.id.campaignAmount);
            ImageView organizerImage = cardView.findViewById(R.id.organizerProfile);
            TextView organizerName = cardView.findViewById(R.id.organizerName);

            title.setText(campaign.getTitle());
            long remaining = campaign.getTargetAmount() - campaign.getAmountCollected();
            amount.setText(String.format("Rp%,d untuk mencapai target", remaining));
            thumbnail.setImageResource(campaign.getMainImageResId());
            organizerName.setText(campaign.getOrganizerName());
            organizerImage.setImageResource(campaign.getOrganizerImageResId());

            // Simpan data ke tag agar bisa dipakai di gesture tap
            cardView.setTag(R.id.tag_campaign_data, campaign);
            cardContainer.addView(cardView, 0);

//            Title diklik pindah ke donation detail
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DonationDetailActivity.class);
                    startActivity(intent);
                }
            });

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
                currentIndex = (currentIndex + 1) % campaignList.size(); // Looping
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
                currentIndex = (currentIndex + 1) % campaignList.size(); // Looping
                showNextCard();
            }
        });
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            assert e1 != null;
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
        private void toggleOverlay() {
            if (currentCardView == null) return;

            View overlayBackground = currentCardView.findViewById(R.id.cardOverlayBackground);
            View overlayContent = currentCardView.findViewById(R.id.cardOverlayContent);
            TextView overlayTitle = currentCardView.findViewById(R.id.overlayTitle);
            TextView overlayDescription = currentCardView.findViewById(R.id.overlayDescription);

            // Komponen dasar yang akan disembunyikan
            TextView baseTitle = currentCardView.findViewById(R.id.campaignTitle);
            TextView baseAmount = currentCardView.findViewById(R.id.campaignAmount);
            TextView baseOrganizerName = currentCardView.findViewById(R.id.organizerName);
            ImageView baseOrganizerProfile = currentCardView.findViewById(R.id.organizerProfile);

            boolean isOverlayVisible = overlayContent.getVisibility() == View.VISIBLE;

            Campaign campaign = campaignList.get((currentIndex - 1 + campaignList.size()) % campaignList.size());

            if (isOverlayVisible) {
                overlayContent.animate()
                        .translationY(overlayContent.getHeight())
                        .alpha(0f)
                        .setDuration(300)
                        .withEndAction(() -> {
                            overlayContent.setVisibility(View.GONE);
                            overlayBackground.setVisibility(View.GONE);
                            baseTitle.setVisibility(View.VISIBLE);
                            baseAmount.setVisibility(View.VISIBLE);
                            baseOrganizerName.setVisibility(View.VISIBLE);
                            baseOrganizerProfile.setVisibility(View.VISIBLE);
                        });
            } else {
                overlayTitle.setText(campaign.getTitle());
                overlayDescription.setText(campaign.getDescription());

                overlayBackground.setVisibility(View.VISIBLE);
                overlayContent.setVisibility(View.VISIBLE);
                overlayContent.setTranslationY(overlayContent.getHeight());
                overlayContent.setAlpha(0f);
                overlayContent.animate()
                        .translationY(0f)
                        .alpha(1f)
                        .setDuration(300);

                baseTitle.setVisibility(View.INVISIBLE);
                baseAmount.setVisibility(View.INVISIBLE);
                baseOrganizerName.setVisibility(View.INVISIBLE);
                baseOrganizerProfile.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public boolean onSingleTapUp(@NonNull MotionEvent e) {
            toggleOverlay(); // panggil fungsi toggle overlay saat tap
            return true;
        }
    }
}
