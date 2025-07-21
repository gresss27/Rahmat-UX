package com.example.rahmat_ux.data;

// Impor kelas Model dan resource (R)
import com.example.rahmat_ux.model.Campaign;
import com.example.rahmat_ux.R;

public class DummyDataRepository {

    public static Campaign getDonationDetail() {
        // Data palsu untuk detail donasi
        return new Campaign(
                "Ayo Bantu Bengkulu Pulih dari Gempa",
                R.drawable.gambar_gempa, // Pastikan ada file ini di res/drawable
                "27 Mei 2025",
                "Sisa 23 hari",
                203324000L,
                250000000L,
                75,
                75,
                75,
                "Gempa bumi yang mengguncang wilayah Bengkulu pada Selasa, 27 Mei 2025 telah mengakibatkan banyak warga kehilangan tempat tinggal, barang-barang pribadi, dan akses terhadap kebutuhan dasar. Dalam kondisi darurat ini, solidaritas dan bantuan dari kita semua sangat dibutuhkan.",
                "Bantuanmu Team",
                "Pengusaha",
                R.drawable.logo_organizer // Pastikan ada file ini di res/drawable
        );
    }
}