package com.example.rahmat_ux;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rahmat_ux.adapter.DonatorAdapter;
import com.example.rahmat_ux.data.DummyDataRepository;
import com.example.rahmat_ux.model.Campaign;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.CircularProgressIndicator;


public class DonationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_donation_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        BACK BUTTON
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Navigates back to the previous screen
            }
        });

        TextView textTitle = findViewById(R.id.textTitle);

        int donationId = getIntent().getIntExtra("campaign_id", -1);

        if (donationId != -1) {
            // Directly use your repository
            Campaign campaign = DummyDataRepository.getInstance().getDonationById(donationId);

            if (campaign != null) {
                // Title
                textTitle.setText(campaign.getTitle());

                // Main Image
                ImageView donationImage = findViewById(R.id.donationImage);
                donationImage.setImageResource(campaign.getMainImageResId());

                // Start Date and Remaining Days
                TextView startDate = findViewById(R.id.startDate);
                startDate.setText("Sejak: " + campaign.getDateStarted());

                TextView daysRemaining = findViewById(R.id.daysRemaining);
                daysRemaining.setText("Sisa " + campaign.getRemainingDays() + " hari");

                // Deskripsi
                TextView tvDeskripsi = findViewById(R.id.tvDeskripsi);
                tvDeskripsi.setText(campaign.getLongDescription());

                // Amount Collected
                TextView amountCollected = findViewById(R.id.amountCollected);
                amountCollected.setText(formatCurrency(campaign.getAmountCollected()));

                // Target Amount
                TextView targetAmount = findViewById(R.id.targetAmount);
                targetAmount.setText("Terkumpul dari " + formatCurrency(campaign.getTargetAmount()));

                // Money Remaining
                TextView moneyRemaining = findViewById(R.id.moneyRemaining);
                long remaining = campaign.getTargetAmount() - campaign.getAmountCollected();
                moneyRemaining.setText(formatCurrency(remaining) + " untuk mencapai target");

                // Progress Bar Money
                ProgressBar progressBar = findViewById(R.id.progressBarMoney);
                int progress = (int) ((double) campaign.getAmountCollected() / campaign.getTargetAmount() * 100);
                progressBar.setProgress(progress);

                // Progress indicators Items
                CircularProgressIndicator progressFood = findViewById(R.id.progressFood);
                TextView percentFood = findViewById(R.id.percentFood);
                TextView labelFood = findViewById(R.id.labelFood);

                CircularProgressIndicator progressClothes = findViewById(R.id.progressClothes);
                TextView percentClothes = findViewById(R.id.percentClothes);
                TextView labelClothes = findViewById(R.id.labelClothes);

                CircularProgressIndicator progressMedicine = findViewById(R.id.progressMedicine);
                TextView percentMedicine = findViewById(R.id.percentMedicine);
                TextView labelMedicine = findViewById(R.id.labelMedicine);

// Set values from the Campaign object
                progressFood.setProgress(campaign.getItem1Progress());
                percentFood.setText(campaign.getItem1Progress() + "%");
                labelFood.setText(campaign.getItem1Name());

                progressClothes.setProgress(campaign.getItem2Progress());
                percentClothes.setText(campaign.getItem2Progress() + "%");
                labelClothes.setText(campaign.getItem2Name());

                progressMedicine.setProgress(campaign.getItem3Progress());
                percentMedicine.setText(campaign.getItem3Progress() + "%");
                labelMedicine.setText(campaign.getItem3Name());


                // Organizer profile section
                ImageView organizerProfile = findViewById(R.id.organizerProfile);
                TextView organizerName = findViewById(R.id.organizerName);
                TextView organizerOccupation = findViewById(R.id.organizerOccupation);

                organizerName.setText(campaign.getOrganizerName());
                organizerOccupation.setText(campaign.getOrganizerOccupation());
                organizerProfile.setImageResource(campaign.getOrganizerImageResId());

            } else {
                textTitle.setText("Donation not found");
            }
        }


//        BACA SELENGKAPNYA DESKRIPSI
        TextView tvDeskripsi = findViewById(R.id.tvDeskripsi);
        TextView tvToggle = findViewById(R.id.tvToggleDeskripsi);

        tvToggle.setOnClickListener(new View.OnClickListener() {
            boolean isExpanded = false;

            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    tvDeskripsi.setMaxLines(5);
                    tvDeskripsi.setEllipsize(TextUtils.TruncateAt.END);
                    tvToggle.setText("Lihat selengkapnya");
                } else {
                    tvDeskripsi.setMaxLines(Integer.MAX_VALUE);
                    tvDeskripsi.setEllipsize(null);
                    tvToggle.setText("Sembunyikan");
                }
                isExpanded = !isExpanded;
            }
        });


//        DIALOG RINCIAN PENGGUNAAN DANA
        MaterialCardView cardRincian = findViewById(R.id.cardRincianPenggunaanDana);
        cardRincian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the custom dialog view
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_rincian_penggunaan_dana, null);

                // Build the dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(DonationDetailActivity.this);
                builder.setView(dialogView);
                builder.setCancelable(true);

                // Create and show the dialog
                AlertDialog dialog = builder.create();

                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }

                dialog.show();

                // Handle the close button inside the dialog
                ImageView btnClose = dialogView.findViewById(R.id.btnClose);
                btnClose.setOnClickListener(view -> dialog.dismiss());

            }
        });

//        DIALOG DONASI SEKARANG
        MaterialButton btnDonasiSekarang = findViewById(R.id.btnDonasiSekarang);
        btnDonasiSekarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the custom dialog view
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_donate_now, null);

                // Build the dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(DonationDetailActivity.this);
                builder.setView(dialogView);
                builder.setCancelable(true);

                // Create and show the dialog
                AlertDialog dialog = builder.create();

                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }

                dialog.show();

                // Handle the close button inside the dialog
                ImageView btnClose = dialogView.findViewById(R.id.btnClose);
                btnClose.setOnClickListener(view -> dialog.dismiss());

                AppCompatButton btnDonasiBarang = dialogView.findViewById(R.id.btnDonasiBarang);
                btnDonasiBarang.setOnClickListener(buttonView -> {
                    dialog.dismiss();
                    // Memulai alur dengan membuka halaman Tambah Barang
                    Intent intent = new Intent(DonationDetailActivity.this, AddEditItemActivity.class);
                    startActivity(intent);
                });

                AppCompatButton btnDonasiUang = dialogView.findViewById(R.id.btnDonasiUang);
                btnDonasiUang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss(); // optional: close dialog
                        Intent intent = new Intent(DonationDetailActivity.this, MoneyDonationActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });


//        CERITA PENGGALANGAN DANA
        CardView cardCerita = findViewById(R.id.cardCeritaPenggalanganDana);
        cardCerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonationDetailActivity.this, DonationStoryActivity.class);
                startActivity(intent);
            }
        });


//        LIST DONASI
        TextView tvDonatorList = findViewById(R.id.tvDonatorList);
        tvDonatorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonationDetailActivity.this, DonatorListActivity.class);
                startActivity(intent);
            }
        });


//      3 DONATUR TERAKHIR
        RecyclerView recyclerDonators = findViewById(R.id.recyclerDonators);
        recyclerDonators.setLayoutManager(new LinearLayoutManager(this));
        DonatorAdapter adapter = new DonatorAdapter(DummyDataRepository.getInstance().getDonatorList(), 3);
        recyclerDonators.setAdapter(adapter);


//        SHARE
        MaterialCardView cardShare = findViewById(R.id.cardShare);

        cardShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareText = "Yuk berdonasi untuk kampanye ini: Ayo Bantu Bengkulu Pulih dari Gempa\nhttps://example.com/campaign/bengkulu";

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Donasi Sekarang");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

                startActivity(Intent.createChooser(shareIntent, "Bagikan melalui"));
            }
        });

    }

    private String formatCurrency(long amount) {
        return "Rp" + String.format("%,d", amount).replace(',', '.');
    }
}