package com.example.rahmat_ux;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

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
        DonatorAdapter adapter = new DonatorAdapter(DummyDataRepository.getDonatorList(), 3);
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
}