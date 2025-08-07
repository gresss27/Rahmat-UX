package com.example.rahmat_ux.data;

import com.example.rahmat_ux.R;
import com.example.rahmat_ux.model.Bank;
import com.example.rahmat_ux.model.Campaign;
import com.example.rahmat_ux.model.DonationHistory;
import com.example.rahmat_ux.model.Donator;
import com.example.rahmat_ux.model.Notification;
import com.example.rahmat_ux.model.Story;
import com.example.rahmat_ux.model.User;

import java.util.ArrayList;
import java.util.List;

public class DummyDataRepository {

    private static DummyDataRepository instance;
    private List<Campaign> campaignList;
    private User currentUser;

    private DummyDataRepository() {
        campaignList = new ArrayList<>();

// Data 1: Anak Difabel
        campaignList.add(new Campaign(
                1,
                "URGENT! Puluhan Anak-anak Difabel Butuh Bantuanmu",
                R.drawable.campaign_difabel,
                "10 Juli 2025", "Sisa 76 hari",
                13440231L, 300000000L,
                "Kursi Roda", 30,
                "Buku Braille", 50,
                "Alat Bantu Dengar", 20,
                "Bantu penuhi kebutuhan akses pendidikan yang layak untuk anak-anak difabel.",
                "Ratusan anak-anak difabel di berbagai wilayah masih menghadapi kesulitan dalam mengakses pendidikan dan peralatan yang mendukung aktivitas mereka sehari-hari.\n\nKami membuka donasi untuk membantu mereka mendapatkan:\n• Kursi Roda: Mobilitas yang lebih baik bagi anak-anak dengan keterbatasan gerak.\n• Buku Braille: Akses belajar untuk anak-anak tunanetra.\n• Alat Bantu Dengar: Membantu anak-anak dengan gangguan pendengaran dalam berkomunikasi dan belajar.\n\nMari bersama bantu ciptakan lingkungan inklusif bagi mereka. Setiap donasi Anda membuka pintu harapan.",
                "Yayasan Harapan Bangsa", "Lembaga Sosial", R.drawable.organizer1,
                "Terima kasih kakak kakak atas bantuannya, semoga Allah SWT memberi berkah",
                "Berlangsung", 76,
                new ArrayList<>(), "", "" // stories, coverImageUri, storyTitle
        ));

        campaignList.add(new Campaign(
                2,
                "Akses Nutrisi Masih Terbatas, Donasi Beras Sekarang! Bantu Pulihkan Masa Depan Mereka",
                R.drawable.banner5,
                "15 Juni 2025", "Sisa 76 hari",
                3500000L, 25000000L,
                "Beras", 90,
                "Minyak", 10,
                "Gula", 5,
                "Bantu pulihkan masa depan mereka dengan memenuhi kebutuhan pangan pokok.",
                "Banyak keluarga prasejahtera masih kesulitan memenuhi kebutuhan pangan dasar mereka. Di tengah kondisi ekonomi yang sulit, akses terhadap makanan bergizi masih menjadi tantangan besar.\n\nKami mengajak Anda untuk berdonasi:\n• Beras: Makanan pokok yang sangat dibutuhkan.\n• Minyak: Untuk memasak makanan sehari-hari.\n• Gula: Sebagai sumber energi tambahan.\n\nSedekah subuhmu bisa menjadi penyelamat bagi banyak keluarga. Yuk bantu mereka mulai hari dengan penuh harapan.",
                "Sedekah Subuh", "Komunitas Relawan", R.drawable.organizer2,
                "Beras sudah diberikan kepada seluruh orang yang membutuhkan, terima kasih orang baik!",
                "Berlangsung", 76,
                new ArrayList<>(), "", ""
        ));

        campaignList.add(new Campaign(
                3,
                "Akses Nutrisi yang Masih Terbatas bagi Anak dengan Risiko Stunting",
                R.drawable.banner3,
                "01 Juli 2025", "Sisa 76 hari",
                10140251L, 250000000L,
                "Susu", 85,
                "Vitamin", 40,
                "Biskuit Bayi", 60,
                "DARURAT Stunting & Gizi Buruk Kronis. Cegah stunting dengan memberikan gizi terbaik.",
                "Stunting atau gagal tumbuh akibat kekurangan gizi masih menjadi masalah serius di Indonesia. Anak-anak yang mengalami stunting akan kesulitan berkembang secara fisik dan mental.\n\nMari bantu cegah stunting dengan memberikan:\n• Susu: Asupan kalsium dan protein penting untuk pertumbuhan.\n• Vitamin: Menjaga daya tahan tubuh anak-anak.\n• Biskuit Bayi: Makanan tambahan bergizi tinggi.\n\nDonasi Anda adalah investasi bagi masa depan generasi penerus bangsa. Bersama kita bisa cegah stunting!",
                "Anak Sehat Indonesia", "Organisasi Kesehatan", R.drawable.organizer3,
                "Bantuanmu sudah menyelamatkan nasib banyak anak dari stunting dan kekurangan gizi!",
                "Berlangsung", 76,
                new ArrayList<>(), "", ""
        ));

        campaignList.add(new Campaign(
                4,
                "Anak Yatim Difabel Butuh Dukungan! Donasi Buku, Hadirkan Harapan Lewat Pendidikan",
                R.drawable.banner2,
                "20 Juni 2025", "Sisa 76 hari",
                5250000L, 50000000L,
                "Buku Cerita", 10,
                "Buku Pelajaran", 95,
                "Alat Tulis", 15,
                "Tanpa keluarga, mereka butuh uluran tangan kita. Buku adalah jendela dunia...",
                "Anak-anak yatim piatu dengan disabilitas membutuhkan dukungan kita agar tetap bisa belajar dan meraih masa depan yang cerah.\n\nMelalui donasi ini, Anda dapat membantu mereka mendapatkan:\n• Buku Cerita: Untuk membangkitkan imajinasi dan semangat belajar.\n• Buku Pelajaran: Mendukung proses belajar formal.\n• Alat Tulis: Kebutuhan dasar dalam kegiatan belajar mengajar.\n\nMari hadirkan harapan lewat pendidikan. Setiap buku adalah cahaya bagi masa depan mereka.",
                "Pustaka Harapan", "Gerakan Literasi", R.drawable.organizer4,
                "5000 buku sudah diberikan kepada anak-anak yang gemar membaca, bantuanmu sangat diapresiasi!",
                "Berlangsung", 76,
                new ArrayList<>(), "", ""
        ));

        campaignList.add(new Campaign(
                5,
                "Solidaritas untuk Ternate: Mari Bersama Pulihkan Kehidupan Pasca Banjir Bandang",
                R.drawable.banner4,
                "27 Mei 2025", "Sisa 23 hari",
                203324000L, 250000000L,
                "Paket Sembako", 75,
                "Pakaian dan Selimut", 75,
                "Obat-obatan", 75,
                "Bantu pembangunan kembali rumah warga yang hancur akibat gempa.",
                "Banjir bandang yang melanda Ternate telah menghancurkan banyak rumah dan fasilitas umum. Ribuan warga kini tinggal di pengungsian dan membutuhkan bantuan segera.\n\nKami mengajak Anda berdonasi:\n• Paket Sembako: Untuk memenuhi kebutuhan makan sehari-hari.\n• Pakaian dan Selimut: Melindungi dari dingin dan penyakit.\n• Obat-obatan: Menjaga kesehatan di tengah kondisi darurat.\n\nMari bersama bangkitkan kembali Ternate. Solidaritas Anda sangat berarti!",
                "Bantuanmu Team", "Tim Tanggap Bencana", R.drawable.organizer5,
                "10 rumah baru telah dibangun dan banyak keluarga sudah mendapatkan tempat tinggal!",
                "Berlangsung", 23,
                new ArrayList<>(), "", ""
        ));

        campaignList.add(new Campaign(
                6,
                "Bantu Renovasi Sekolah Rusak di Pelosok",
                R.drawable.campaign_gempa,
                "18 Juli 2025", "Sisa 45 hari",
                0L, 100000000L,
                "Papan Tulis", 0,
                "Bangku", 0,
                "Atap Sekolah", 0,
                "Sekolah di pedalaman masih kekurangan fasilitas belajar. Yuk bantu wujudkan ruang belajar yang layak.",
                "Di pelosok negeri, masih banyak sekolah yang tidak layak pakai. Anak-anak belajar di ruang kelas yang rusak tanpa fasilitas memadai.\n\nBantu mereka mendapatkan:\n• Papan Tulis: Sarana utama dalam proses belajar mengajar.\n• Bangku: Tempat duduk yang nyaman untuk belajar.\n• Atap Sekolah: Melindungi dari panas dan hujan.\n\nMari wujudkan ruang belajar yang aman dan nyaman. Donasi Anda adalah harapan mereka untuk masa depan lebih baik.",
                "Aksi Cerdas Nusantara", "Komunitas Pendidikan", R.drawable.organizer6,
                "Aajwndjanwdawdawjdnj",
                "Diajukan", 45,
                new ArrayList<>(), "", ""
        ));

        campaignList.add(new Campaign(
                7,
                "Donasi Air Bersih untuk Warga Kekeringan",
                R.drawable.campaign_gempa,
                "01 Mei 2025", "Selesai",
                125000000L, 125000000L,
                "Tandon Air", 100,
                "Pompa", 100,
                "Selang", 100,
                "Kekeringan melanda desa kami. Terima kasih atas bantuannya yang telah mengalirkan air kehidupan.",
                "Musim kemarau yang panjang menyebabkan kekeringan parah di desa kami. Akses terhadap air bersih menjadi sangat sulit dan berdampak pada kesehatan warga.\n\nDengan donasi Anda, kami dapat menyediakan:\n• Tandon Air: Menyimpan air untuk kebutuhan sehari-hari.\n• Pompa: Mengalirkan air dari sumber.\n• Selang: Distribusi air ke rumah-rumah warga.\n\nTerima kasih telah menjadi bagian dari gerakan mengalirkan kehidupan. Air bersih adalah hak semua orang.",
                "Gerakan Air Untuk Semua", "Organisasi Sosial", R.drawable.organizer7,
                "asemlah",
                "Selesai", 0,
                new ArrayList<>(), "", ""
        ));
        currentUser = new User("John Doe", 50000, 10000);

    }

    public static DummyDataRepository getInstance() {
        if (instance == null) {
            instance = new DummyDataRepository();
        }
        return instance;
    }

    public User getCurrentUser() { return currentUser; }
    public void updateCurrentUserBalance(long newBalance) { currentUser.setBalance(newBalance); }
    public void updateCurrentUserDonationPerSwipe(long newNominal) { currentUser.setDonationPerSwipe(newNominal); }

    public List<Campaign> getCampaignList() { return campaignList; }

    public void addCampaign(Campaign campaign) {
        if (campaign.getId() == 0) {
            campaign.setId(campaignList.size() + 1);
        }
        this.campaignList.add(campaign);
    }

    public void updateCampaign(Campaign updatedCampaign) {
        for (int i = 0; i < campaignList.size(); i++) {
            if (campaignList.get(i).getId() == updatedCampaign.getId()) {
                campaignList.set(i, updatedCampaign);
                return;
            }
        }
    }

    public void removeCampaign(Campaign campaign) {
        campaignList.remove(campaign);
    }

    public List<Campaign> getCampaignsByStatus(String status) {
        List<Campaign> filtered = new ArrayList<>();
        for (Campaign c : campaignList) {
            if (c.getStatus().equalsIgnoreCase(status)) {
                filtered.add(c);
            }
        }
        return filtered;
    }

    public Campaign getDonationById(int id) {
        for (Campaign c : campaignList) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    public List<Donator> getDonatorList() {
        List<Donator> donatorList = new ArrayList<>();
        donatorList.add(new Donator("Andi Pratama", "Rp100.000", "2 menit lalu", R.drawable.donator1));
        donatorList.add(new Donator("Siti Nurhaliza", "1 Dus Mie Instan", "5 menit lalu", R.drawable.donator2));
        donatorList.add(new Donator("Bagus Santoso", "Rp250.000", "10 menit lalu", R.drawable.donator3));
        donatorList.add(new Donator("Dewi Anggraini", "5kg Beras", "15 menit lalu", R.drawable.donator4));
        donatorList.add(new Donator("Rizky Maulana", "Rp75.000", "30 menit lalu", R.drawable.donator5));
        donatorList.add(new Donator("Nur Aisyah", "2 Kotak Susu", "35 menit lalu", R.drawable.donator6));
        donatorList.add(new Donator("Yoga Prabowo", "Rp200.000", "45 menit lalu", R.drawable.donator7));
        donatorList.add(new Donator("Melati Ramadhani", "Selimut & Pakaian Layak", "1 jam lalu", R.drawable.donator8));
        donatorList.add(new Donator("Fadli Rahman", "Rp500.000", "1 jam 15 menit lalu", R.drawable.donator9));
        donatorList.add(new Donator("Indah Lestari", "1 Paket Alat Tulis", "2 jam lalu", R.drawable.donator10));
        donatorList.add(new Donator("Ahmad Zaki", "Rp150.000", "2 jam 30 menit lalu", R.drawable.donator11));
        donatorList.add(new Donator("Maria Fransiska", "Paket Makanan Siap Saji", "3 jam lalu", R.drawable.donator12));
        donatorList.add(new Donator("Hendra Wijaya", "Rp300.000", "4 jam lalu", R.drawable.donator13));
        donatorList.add(new Donator("Yulia Hartanti", "Mainan Anak-anak", "5 jam lalu", R.drawable.donator14));
        donatorList.add(new Donator("Gilang Aditya", "Rp50.000", "6 jam lalu", R.drawable.donator15));
        return donatorList;
    }

    public List<Notification> getNotificationList() {
        List<Notification> list = new ArrayList<>();
        int imageResId = R.drawable.campaign_yatim; // Same image for all
        list.add(new Notification("18 Feb 2020", "Donasi Berhasil: Ayo Bantu Bengkulu Pulih dari Gempa", "Kamu berdonasi sebesar Rp10.000", imageResId));
        list.add(new Notification("18 Feb 2020", "Donasi Menunggu: Ayo Bantu Bengkulu Pulih dari Gempa", "Yuk antar donasi barangmu ke drop point dalam 3 hari!", imageResId));
        list.add(new Notification("18 Feb 2020", "Donasi Diterima: Ayo Bantu Bengkulu Pulih dari Gempa", "Donasi barangmu telah kami terima. Terima kasih!", imageResId));
        list.add(new Notification("18 Feb 2020", "Waktu Pengantaran Habis: Ayo Bantu Bengkulu Pulih dari Gempa", "Barang belum dikirim ke drop point. Silakan jadwalkan ulang pengantaran.", imageResId));
        list.add(new Notification("18 Feb 2020", "Kampanye Baru: Akses Nutrisi Masih Terbatas, Donasi Beras Sekarang! Bantu Pulihkan Masa Depan Mereka", "Yuk jadi donatur pertama dan bantu selamatkan nyawa anak-anak.", imageResId));
        return list;
    }

    public List<Bank> getBankList() {
        List<Bank> list = new ArrayList<>();
        list.add(new Bank("Bank BCA", R.drawable.bank_bca));
        list.add(new Bank("Bank Mandiri", R.drawable.bank_mandiri));
        list.add(new Bank("Bank BRI", R.drawable.bank_bri));
        list.add(new Bank("Bank BNI", R.drawable.bank_bni));
        list.add(new Bank("Bank Syariah Indonesia", R.drawable.bank_syariah));
        return list;
    }

    public List<DonationHistory> getDonationHistoryList() {
        List<DonationHistory> list = new ArrayList<>();

        list.add(new DonationHistory(
                "Ayo Bantu Bengkulu Pulih dari Gempa",
                "Donasi Berhasil",
                "-10.000",
                R.drawable.banner1,
                "uang"
        ));

        list.add(new DonationHistory(
                "Top Up Saldo",
                "Top Up Berhasil",
                "+500.000",
                R.drawable.bank_bca,
                "uang"
        ));

        list.add(new DonationHistory(
                "Ayo Bantu Bengkulu Pulih dari Gempa",
                "Donasi Diterima",
                "3 stel pakaian",
                R.drawable.banner1,
                "barang"
        ));

        list.add(new DonationHistory(
                "Ayo Bantu Bengkulu Pulih dari Gempa",
                "Donasi Berhasil",
                "-25.000",
                R.drawable.banner1,
                "uang"
        ));

        list.add(new DonationHistory(
                "Ayo Bantu Bengkulu Pulih dari Gempa",
                "Donasi Diterima",
                "2 paket obat",
                R.drawable.banner1,
                "barang"
        ));

        list.add(new DonationHistory(
                "Top Up Saldo",
                "Top Up Berhasil",
                "+100.000",
                R.drawable.bank_bca,
                "uang"
        ));

        list.add(new DonationHistory(
                "Ayo Bantu Bengkulu Pulih dari Gempa",
                "Donasi Diterima",
                "5 kg beras",
                R.drawable.banner1,
                "barang"
        ));

        return list;
    }
}