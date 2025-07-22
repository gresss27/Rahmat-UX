package com.example.rahmat_ux.data;

import com.example.rahmat_ux.model.Campaign;
import com.example.rahmat_ux.R;
import com.example.rahmat_ux.model.Donator;
import com.example.rahmat_ux.model.Notification;

import java.util.ArrayList;
import java.util.List;

public class DummyDataRepository {

    public static List<Campaign> getCampaignList() {
        List<Campaign> campaignList = new ArrayList<>();

        // Data 1: Anak Difabel
        campaignList.add(new Campaign(
                "Akses Pendidikan Masih Terbatas , 1x Donasi Bisa Buka Jalan untuk Masa Depan Mereka",
//                R.drawable.campaign_difabel
                R.drawable.banner1,
                "10 Juli 2025", "Sisa 76 hari",
                13440231L, 300000000L,
                30, 50, 20,
                "Bantu penuhi kebutuhan akses pendidikan yang layak untuk anak-anak difabel.",
                "Yayasan Harapan Bangsa", "Lembaga Sosial", R.drawable.logo_organizer
        ));

        // Data 2: Sedekah Subuh (Beras)
        campaignList.add(new Campaign(
                "Akses Nutrisi Masih Terbatas, Donasi Beras Sekarang! Bantu Pulihkan Masa Depan Mereka",
//                R.drawable.campaign_beras,
                R.drawable.banner5,
                "15 Juni 2025", "Sisa 76 hari",
                3500000L, 25000000L,
                90, 10, 5,
                "Bantu pulihkan masa depan mereka dengan memenuhi kebutuhan pangan pokok.",
                "Sedekah Subuh", "Komunitas Relawan", R.drawable.logo_organizer
        ));

        // Data 3: Stunting
        campaignList.add(new Campaign(
                "Akses Nutrisi yang Masih Terbatas bagi Anak dengan Risiko Stunting",
//                R.drawable.campaign_stunting,
                R.drawable.banner3,
                "01 Juli 2025", "Sisa 76 hari",
                10140251L, 250000000L,
                85, 40, 60,
                "ARURAT Stunting & Gizi Buruk Kronis. Cegah stunting dengan memberikan gizi terbaik.",
                "Anak Sehat Indonesia", "Organisasi Kesehatan", R.drawable.logo_organizer
        ));

        // Data 4: Yatim Piatu Disabilitas (Buku)
        campaignList.add(new Campaign(
                "Anak Yatim Difabel Butuh Dukungan!Donasi Buku, Hadirkan Harapan Lewat Pendidikan",
//                R.drawable.campaign_yatim,
                R.drawable.banner2,
                "20 Juni 2025", "Sisa 76 hari",
                5250000L, 50000000L,
                10, 95, 15,
                "Tanpa keluarga, mereka butuh uluran tangan kita. Buku adalah jendela dunia, mari buka jendela itu untuk mereka.",
                "Pustaka Harapan", "Gerakan Literasi", R.drawable.logo_organizer
        ));

        // Data 5: Ternate Banjir Bandang
        campaignList.add(new Campaign(
                "Solidaritas untuk Ternate: Mari Bersama Pulihkan Kehidupan Pasca Banjir Bandang",
//                R.drawable.campaign_gempa,
                R.drawable.banner4,
                "27 Mei 2025", "Sisa 23 hari",
                203324000L, 250000000L,
                75, 75, 75,
                "Bantu pembangunan kembali rumah warga yang hancur akibat banjir bandang.",
                "Bantuanmu Team", "Tim Tanggap Bencana", R.drawable.logo_organizer
        ));

        return campaignList;
    }

    public static List<Donator> getDonatorList() {
        List<Donator> donatorList = new ArrayList<>();

        donatorList.add(new Donator("Andi Pratama", "Rp100.000", "2 menit lalu", R.drawable.campaign_yatim));
        donatorList.add(new Donator("Siti Nurhaliza", "1 Dus Mie Instan", "5 menit lalu", R.drawable.campaign_yatim));
        donatorList.add(new Donator("Bagus Santoso", "Rp250.000", "10 menit lalu", R.drawable.campaign_yatim));
        donatorList.add(new Donator("Dewi Anggraini", "5kg Beras", "15 menit lalu", R.drawable.campaign_yatim));
        donatorList.add(new Donator("Rizky Maulana", "Rp75.000", "30 menit lalu", R.drawable.campaign_yatim));
        donatorList.add(new Donator("Nur Aisyah", "2 Kotak Susu", "35 menit lalu", R.drawable.campaign_yatim));
        donatorList.add(new Donator("Yoga Prabowo", "Rp200.000", "45 menit lalu", R.drawable.campaign_yatim));
        donatorList.add(new Donator("Melati Ramadhani", "Selimut & Pakaian Layak", "1 jam lalu", R.drawable.campaign_yatim));
        donatorList.add(new Donator("Fadli Rahman", "Rp500.000", "1 jam 15 menit lalu", R.drawable.campaign_yatim));
        donatorList.add(new Donator("Indah Lestari", "1 Paket Alat Tulis", "2 jam lalu", R.drawable.campaign_yatim));
        donatorList.add(new Donator("Ahmad Zaki", "Rp150.000", "2 jam 30 menit lalu", R.drawable.campaign_yatim));
        donatorList.add(new Donator("Maria Fransiska", "Paket Makanan Siap Saji", "3 jam lalu", R.drawable.campaign_yatim));
        donatorList.add(new Donator("Hendra Wijaya", "Rp300.000", "4 jam lalu", R.drawable.campaign_yatim));
        donatorList.add(new Donator("Yulia Hartanti", "Mainan Anak-anak", "5 jam lalu", R.drawable.campaign_yatim));
        donatorList.add(new Donator("Gilang Aditya", "Rp50.000", "6 jam lalu", R.drawable.campaign_yatim));

        return donatorList;
    }

    public static List<Notification> getNotificationList() {
        List<Notification> list = new ArrayList<>();
        int imageResId = R.drawable.campaign_yatim; // Same image for all

        list.add(new Notification("18 Feb 2020", "Donasi Berhasil: Ayo Bantu Bengkulu Pulih dari Gempa", "Kamu berdonasi sebesar Rp10.000", imageResId));
        list.add(new Notification("18 Feb 2020", "Donasi Menunggu: Ayo Bantu Bengkulu Pulih dari Gempa", "Yuk antar donasi barangmu ke drop point dalam 3 hari!", imageResId));
        list.add(new Notification("18 Feb 2020", "Donasi Diterima: Ayo Bantu Bengkulu Pulih dari Gempa", "Donasi barangmu telah kami terima. Terima kasih!", imageResId));
        list.add(new Notification("18 Feb 2020", "Waktu Pengantaran Habis: Ayo Bantu Bengkulu Pulih dari Gempa", "Barang belum dikirim ke drop point. Silakan jadwalkan ulang pengantaran.", imageResId));
        list.add(new Notification("18 Feb 2020", "Kampanye Baru: Akses Nutrisi Masih Terbatas, Donasi Beras Sekarang! Bantu Pulihkan Masa Depan Mereka", "Yuk jadi donatur pertama dan bantu selamatkan nyawa anak-anak.", imageResId));

        return list;
    }


}