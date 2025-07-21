package com.example.rahmat_ux.data;

import com.example.rahmat_ux.model.Campaign;
import com.example.rahmat_ux.R;

import java.util.ArrayList;
import java.util.List;

public class DummyDataRepository {

    public static List<Campaign> getCampaignList() {
        List<Campaign> campaignList = new ArrayList<>();

        // Data 1: Anak Difabel
        campaignList.add(new Campaign(
                "URGENT! Puluhan Anak-anak Difabel Butuh Bantuanmu",
                R.drawable.campaign_difabel,
                "10 Juli 2025", "Sisa 76 hari",
                13440231L, 300000000L,
                30, 50, 20,
                "Bantu penuhi kebutuhan akses pendidikan yang layak untuk anak-anak difabel.",
                "Yayasan Harapan Bangsa", "Lembaga Sosial", R.drawable.logo_organizer
        ));

        // Data 2: Sedekah Subuh (Beras)
        campaignList.add(new Campaign(
                "Akses Nutrisi Masih Terbatas, Donasi Beras Sekarang!",
                R.drawable.campaign_beras,
                "15 Juni 2025", "Sisa 76 hari",
                3500000L, 25000000L,
                90, 10, 5,
                "Bantu pulihkan masa depan mereka dengan memenuhi kebutuhan pangan pokok.",
                "Sedekah Subuh", "Komunitas Relawan", R.drawable.logo_organizer
        ));

        // Data 3: Stunting
        campaignList.add(new Campaign(
                "Akses Nutrisi yang Masih Terbatas bagi Anak dengan Risiko Stunting",
                R.drawable.campaign_stunting,
                "01 Juli 2025", "Sisa 76 hari",
                10140251L, 250000000L,
                85, 40, 60,
                "ARURAT Stunting & Gizi Buruk Kronis. Cegah stunting dengan memberikan gizi terbaik.",
                "Anak Sehat Indonesia", "Organisasi Kesehatan", R.drawable.logo_organizer
        ));

        // Data 4: Yatim Piatu Disabilitas (Buku)
        campaignList.add(new Campaign(
                "Yatim Difabel Butuh Dukungan! Donasi Buku, Hadirkan Harapan Lewat Pendidikan",
                R.drawable.campaign_yatim,
                "20 Juni 2025", "Sisa 76 hari",
                5250000L, 50000000L,
                10, 95, 15,
                "Tanpa keluarga, mereka butuh uluran tangan kita. Buku adalah jendela dunia, mari buka jendela itu untuk mereka.",
                "Pustaka Harapan", "Gerakan Literasi", R.drawable.logo_organizer
        ));

        // Data 5: Gempa Bengkulu
        campaignList.add(new Campaign(
                "Ayo Bantu Bengkulu Pulih dari Gempa",
                R.drawable.campaign_gempa,
                "27 Mei 2025", "Sisa 23 hari",
                203324000L, 250000000L,
                75, 75, 75,
                "Bantu pembangunan kembali rumah warga yang hancur akibat gempa.",
                "Bantuanmu Team", "Tim Tanggap Bencana", R.drawable.logo_organizer
        ));

        return campaignList;
    }
}