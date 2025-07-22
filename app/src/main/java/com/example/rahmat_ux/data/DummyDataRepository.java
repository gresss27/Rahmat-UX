package com.example.rahmat_ux.data;

import com.example.rahmat_ux.model.Campaign;
import com.example.rahmat_ux.R;

import java.util.ArrayList;
import java.util.List;

public class DummyDataRepository {

    public static List<Campaign> getCampaignList() {
        List<Campaign> campaignList = new ArrayList<>();

        campaignList.add(new Campaign(
                "URGENT! Puluhan Anak-anak Difabel Butuh Bantuanmu",
                R.drawable.campaign_difabel,
                "10 Juli 2025", "Sisa 76 hari",
                13440231L, 300000000L,
                30, 50, 20,
                "Bantu penuhi kebutuhan akses pendidikan yang layak untuk anak-anak difabel.",
                "Yayasan Harapan Bangsa", "Lembaga Sosial", R.drawable.logo_organizer,
                "Berlangsung", 76
        ));

        campaignList.add(new Campaign(
                "Akses Nutrisi Masih Terbatas, Donasi Beras Sekarang!",
                R.drawable.campaign_beras,
                "15 Juni 2025", "Sisa 76 hari",
                3500000L, 25000000L,
                90, 10, 5,
                "Bantu pulihkan masa depan mereka dengan memenuhi kebutuhan pangan pokok.",
                "Sedekah Subuh", "Komunitas Relawan", R.drawable.logo_organizer,
                "Berlangsung", 76
        ));

        campaignList.add(new Campaign(
                "Akses Nutrisi yang Masih Terbatas bagi Anak dengan Risiko Stunting",
                R.drawable.campaign_stunting,
                "01 Juli 2025", "Sisa 76 hari",
                10140251L, 250000000L,
                85, 40, 60,
                "ARURAT Stunting & Gizi Buruk Kronis. Cegah stunting dengan memberikan gizi terbaik.",
                "Anak Sehat Indonesia", "Organisasi Kesehatan", R.drawable.logo_organizer,
                "Berlangsung", 76
        ));

        campaignList.add(new Campaign(
                "Yatim Difabel Butuh Dukungan! Donasi Buku, Hadirkan Harapan Lewat Pendidikan",
                R.drawable.campaign_yatim,
                "20 Juni 2025", "Sisa 76 hari",
                5250000L, 50000000L,
                10, 95, 15,
                "Tanpa keluarga, mereka butuh uluran tangan kita. Buku adalah jendela dunia, mari buka jendela itu untuk mereka.",
                "Pustaka Harapan", "Gerakan Literasi", R.drawable.logo_organizer,
                "Berlangsung", 76
        ));

        campaignList.add(new Campaign(
                "Ayo Bantu Bengkulu Pulih dari Gempa",
                R.drawable.campaign_gempa,
                "27 Mei 2025", "Sisa 23 hari",
                203324000L, 250000000L,
                75, 75, 75,
                "Bantu pembangunan kembali rumah warga yang hancur akibat gempa.",
                "Bantuanmu Team", "Tim Tanggap Bencana", R.drawable.logo_organizer,
                "Berlangsung", 23
        ));

        campaignList.add(new Campaign(
                "Bantu Renovasi Sekolah Rusak di Pelosok",
                R.drawable.campaign_gempa,
                "18 Juli 2025", "Sisa 45 hari",
                0L, 100000000L,
                0, 0, 0,
                "Sekolah di pedalaman masih kekurangan fasilitas belajar. Yuk bantu wujudkan ruang belajar yang layak.",
                "Aksi Cerdas Nusantara", "Komunitas Pendidikan", R.drawable.logo_organizer,
                "Diajukan", 45
        ));

        campaignList.add(new Campaign(
                "Donasi Air Bersih untuk Warga Kekeringan",
                R.drawable.campaign_gempa,
                "01 Mei 2025", "Selesai",
                125000000L, 125000000L,
                100, 100, 100,
                "Kekeringan melanda desa kami. Terima kasih atas bantuannya yang telah mengalirkan air kehidupan.",
                "Gerakan Air Untuk Semua", "Organisasi Sosial", R.drawable.logo_organizer,
                "Selesai", 0
        ));



        return campaignList;
    }

    public static List<Campaign> getCampaignsByStatus(String status) {
        List<Campaign> all = getCampaignList();
        List<Campaign> filtered = new ArrayList<>();
        for (Campaign c : all) {
            if (c.getStatus().equalsIgnoreCase(status)) {
                filtered.add(c);
            }
        }
        return filtered;
    }
}