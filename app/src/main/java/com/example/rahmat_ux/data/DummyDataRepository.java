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
import java.util.Collections;
import java.util.List;

public class DummyDataRepository {

    private static DummyDataRepository instance;
    private List<Campaign> campaignList;
    private User currentUser;

    private DummyDataRepository() {
        campaignList = new ArrayList<>();

        // Data 1: IPhone 16 Pro Max
        campaignList.add(new Campaign(
                1,
                "Bantu Saya Beli iPhone 16 Pro Max",
                R.drawable.campaign1,
                "11 Agustus 2025", "Sisa 30 hari",
                5000000L, 30000000L,
                "Casing iPhone 16 Pro Max", 20, 10, "unit",
                "Charger 120W Original", 10, 5, "unit",
                "AirPods Pro 3", 0, 3, "unit",
                "Mendukung pengadaan perangkat iPhone 16 Pro Max beserta perlengkapan pendukung untuk menunjang produktivitas dan kualitas konten digital.",
                "Perangkat iPhone 16 Pro Max diharapkan menjadi sarana utama dalam mendokumentasikan kegiatan, membuat konten digital, serta memperlancar komunikasi secara profesional.\n\nKami membuka penggalangan dana untuk membantu memenuhi kebutuhan berikut:\n• Casing iPhone 16 Pro Max: Perlindungan optimal untuk perangkat.\n• Charger 120W Original: Mendukung pengisian cepat dan aman.\n• AirPods Pro 3: Memudahkan komunikasi dan monitoring audio berkualitas tinggi.\n\nDukungan Anda akan sangat membantu dalam mewujudkan sarana teknologi yang memadai.",
                "Willies", "Content Creator", R.drawable.organizer1,
                "Terima kasih atas partisipasi dan dukungan yang diberikan. Setiap kontribusi membawa kami lebih dekat pada tujuan.",
                "Berlangsung", 30,
                new ArrayList<>(), "", ""
        ));

        // Data 2: Patah Kaki
        campaignList.add(new Campaign(
                2,
                "Bantu Pemulihan Saudara Andi yang Mengalami Patah Kaki",
                R.drawable.campaign2,
                "11 Agustus 2025", "Sisa 45 hari",
                2750000L, 15000000L,
                "Kursi Roda", 50, 1, "unit",
                "Tongkat Penyangga", 30, 2, "unit",
                "Biaya Rehabilitasi", 70, 10, "sesi",
                "Bantu mendukung proses pemulihan Andi agar dapat kembali beraktivitas secara normal.",
                "Andi, seorang pekerja harian, mengalami kecelakaan lalu lintas yang mengakibatkan patah tulang kaki bagian kanan. Saat ini, ia membutuhkan bantuan untuk menunjang mobilitas serta biaya perawatan medis.\n\nKami membuka donasi untuk membantu memenuhi kebutuhan berikut:\n• Kursi Roda: Membantu mobilitas sementara selama proses pemulihan.\n• Tongkat Penyangga: Alat bantu berjalan pasca operasi.\n• Biaya Rehabilitasi: Mendukung terapi fisik untuk mengembalikan kekuatan dan fungsi kaki.\n\nSetiap dukungan yang Anda berikan akan membawa Andi selangkah lebih dekat pada kesembuhan.",
                "Keluarga Andi", "Keluarga Pasien", R.drawable.organizer2,
                "Terima kasih atas setiap doa dan dukungan yang diberikan. Semoga Tuhan membalas kebaikan Anda.",
                "Berlangsung", 45,
                new ArrayList<>(), "", ""
        ));

        // Data 3: Paru-paru
        campaignList.add(new Campaign(
                3,
                "Bantu Biaya Perawatan Ibu Siti yang Terkena Penyakit Paru-Paru",
                R.drawable.campaign3,
                "11 Agustus 2025", "Sisa 60 hari",
                4500000L, 25000000L,
                "Biaya Rawat Inap", 90, 10, "hari",
                "Obat dan Suplemen", 80, 60, "hari",
                "Biaya Terapi Pernapasan", 70, 12, "sesi",
                "Mendukung biaya pengobatan dan pemulihan Ibu Siti yang menderita penyakit paru-paru akibat paparan asap rokok.",
                "Ibu Siti, seorang ibu rumah tangga, didiagnosa menderita penyakit paru-paru kronis setelah bertahun-tahun menghirup asap rokok dari suaminya. Saat ini, kondisinya memerlukan perawatan intensif, pengobatan rutin, serta terapi pernapasan untuk memperbaiki kualitas hidupnya.\n\nKami membuka donasi untuk membantu memenuhi kebutuhan berikut:\n• Biaya Rawat Inap: Mendukung perawatan di rumah sakit untuk stabilisasi kondisi.\n• Obat dan Suplemen: Mengurangi gejala dan membantu proses penyembuhan.\n• Biaya Terapi Pernapasan: Melatih dan memperkuat fungsi paru-paru.\n\nSetiap bantuan Anda akan sangat berarti dalam meringankan beban keluarga dan memberikan harapan kesembuhan bagi Ibu Siti.",
                "Keluarga Ibu Siti", "Keluarga Pasien", R.drawable.organizer3,
                "Terima kasih atas doa dan bantuan yang diberikan. Semoga setiap kebaikan dibalas berlipat ganda.",
                "Berlangsung", 60,
                new ArrayList<>(), "", ""
        ));

        // Data 4: Invasi Air Jahat
        campaignList.add(new Campaign(
                4,
                "WASPADA! INVASi AIR JAHAT! Kulkas & Kasur Belajar Berenang!",
                R.drawable.campaign_banjir,
                "10 Agustus 2025", "Sisa 50 hari",
                1000000L, 50000000L,
                "Pakaian", 20, 100, "karung",       // item1, progress1, target1, unit1
                "Obat-obatan", 10, 50, "kotak",    // item2, progress2, target2, unit2
                "Makanan", 30, 200, "paket",      // item3, progress3, target3, unit3
                "Airnya udah sebetis, sandal jepit hanyut entah kemana. Plis help!",
                "BREAKING NEWS! Desa kami telah terpilih menjadi lokasi syuting 'Waterworld' tanpa pemberitahuan sebelumnya. Sofa udah pasrah, galon air penuh trepidasi, dan koleksi semut di toples gula sudah mengungsi.\n\nKami butuh bantuan Anda untuk menyediakan:\n• Pakaian: Karena jemuran kemarin sore sekarang lagi tur keliling kampung.\n• Obat-obatan: Buat ngobatin pusing mikirin cicilan barang yang kerendam.\n• Makanan: Karena kompor gas menolak bekerja sama dalam kondisi lembab.\n\nDonasimu akan menjadi Super Hero di tengah bencana waterboom dadakan ini!",
                "Komite Penyelamat Sandal Jepit Nasional", "Divisi Logistik & Hiburan", R.drawable.organizer4,
                "Laporan diterima! Tim sudah turun untuk menyelamatkan beberapa sandal yang tersisa. Kalian semua pahlawan!",
                "Berlangsung", 29,
                new ArrayList<>(), "", ""
        ));

        // Data 5: Bantuan Darurat Korban Banjir
        campaignList.add(new Campaign(
                5,
                "Bantuan Darurat untuk Korban Banjir di Desa Mekar Jaya",
                R.drawable.campaign5,
                "11 Agustus 2025", "Sisa 20 hari",
                6500000L, 25000000L,
                "Paket Sembako", 50, 200, "paket",
                "Selimut dan Pakaian", 30, 150, "set",
                "Peralatan Kebersihan", 10, 100, "paket",
                "Membantu memenuhi kebutuhan dasar warga Desa Mekar Jaya yang terdampak banjir.",
                "Banjir bandang melanda Desa Mekar Jaya dan merendam ratusan rumah. Banyak warga kehilangan harta benda dan membutuhkan bantuan segera berupa makanan, pakaian, dan peralatan kebersihan.\n\nKami membuka donasi untuk membantu kebutuhan berikut:\n• Paket Sembako: Memenuhi kebutuhan pangan selama masa darurat.\n• Selimut dan Pakaian: Menjaga warga tetap hangat dan sehat.\n• Peralatan Kebersihan: Membantu pembersihan rumah pasca banjir.\n\nSetiap bantuan akan sangat berarti bagi warga yang terdampak.",
                "Relawan Desa Mekar Jaya", "Relawan", R.drawable.organizer5,
                "Terima kasih atas bantuan dan doa yang diberikan.",
                "Berlangsung", 20,
                new ArrayList<>(), "", ""
        ));

        // Data 6: Pemulihan Sekolah Pasca Banjir
        campaignList.add(new Campaign(
                6,
                "Pemulihan Sekolah SD Harapan Baru Pasca Banjir",
                R.drawable.campaign6,
                "11 Agustus 2025", "Sisa 35 hari",
                2800000L, 15000000L,
                "Meja dan Kursi", 15, 50, "unit",
                "Buku dan Alat Tulis", 20, 200, "paket",
                "Peralatan Kebersihan", 45, 20, "paket",
                "Mendukung pemulihan fasilitas belajar mengajar di SD Harapan Baru setelah terkena banjir.",
                "Banjir besar telah merusak ruang kelas, peralatan, dan buku-buku di SD Harapan Baru. Anak-anak membutuhkan dukungan untuk dapat kembali belajar dengan nyaman dan aman.\n\nKami membuka donasi untuk membantu kebutuhan berikut:\n• Meja dan Kursi: Mengganti perabot yang rusak.\n• Buku dan Alat Tulis: Memulihkan sarana belajar siswa.\n• Peralatan Kebersihan: Membersihkan sekolah dari lumpur dan kotoran.\n\nMari bersama-sama membantu anak-anak agar kembali bersekolah tanpa hambatan.",
                "Komite Sekolah SD Harapan Baru", "Komite Sekolah", R.drawable.organizer6,
                "Terima kasih telah berkontribusi dalam memulihkan semangat belajar anak-anak.",
                "Berlangsung", 35,
                new ArrayList<>(), "", ""
        ));

        // Data 7: Biaya Operasi Katarak
        campaignList.add(new Campaign(
                7,
                "Bantu Biaya Operasi Katarak Pak Darman",
                R.drawable.campaign7,
                "11 Agustus 2025", "Sisa 40 hari",
                3200000L, 18000000L,
                "Biaya Operasi", 50, 1, "tindakan",
                "Obat Pasca Operasi", 50, 30, "hari",
                "Kacamata Khusus", 80, 1, "unit",
                "Mendukung biaya operasi katarak untuk mengembalikan penglihatan Pak Darman.",
                "Pak Darman, seorang pensiunan guru, mengalami penurunan penglihatan akibat katarak di kedua matanya. Kondisi ini membuatnya kesulitan beraktivitas dan membaca. Operasi katarak menjadi satu-satunya cara untuk memulihkan penglihatannya.\n\nKami membuka donasi untuk membantu memenuhi kebutuhan berikut:\n• Biaya Operasi: Mengangkat katarak di kedua mata.\n• Obat Pasca Operasi: Mendukung proses penyembuhan.\n• Kacamata Khusus: Membantu penglihatan pasca operasi.\n\nDukungan Anda akan sangat berarti bagi Pak Darman untuk kembali melihat dengan jelas.",
                "Keluarga Pak Darman", "Keluarga Pasien", R.drawable.organizer7,
                "Terima kasih telah membantu. Setiap sumbangan berarti untuk kesembuhan beliau.",
                "Berlangsung", 40,
                new ArrayList<>(), "", ""
        ));

        // Data 8: Pengobatan Anak Penderita Leukemia
        campaignList.add(new Campaign(
                8,
                "Bantu Pengobatan Adik Farel yang Menderita Leukemia",
                R.drawable.campaign8,
                "11 Agustus 2025", "Sisa 90 hari",
                7800000L, 50000000L,
                "Kemoterapi", 20, 12, "sesi",
                "Obat Harian", 15, 90, "hari",
                "Nutrisi Khusus", 10, 60, "paket",
                "Mendukung biaya pengobatan Adik Farel agar dapat melawan penyakit leukemia.",
                "Farel, bocah berusia 8 tahun, didiagnosa menderita leukemia dan membutuhkan pengobatan jangka panjang. Saat ini ia memerlukan kemoterapi, obat-obatan, serta asupan gizi khusus untuk memperkuat daya tahan tubuhnya.\n\nKami membuka donasi untuk membantu kebutuhan berikut:\n• Kemoterapi: Menekan perkembangan sel kanker.\n• Obat Harian: Mengurangi efek samping dan menjaga kondisi tubuh.\n• Nutrisi Khusus: Memenuhi kebutuhan gizi selama pengobatan.\n\nDukungan Anda akan memberi Farel kesempatan untuk sembuh dan menjalani masa depan yang lebih cerah.",
                "Keluarga Farel", "Keluarga Pasien", R.drawable.organizer8,
                "Setiap doa dan bantuan Anda adalah harapan bagi kesembuhan Farel.",
                "Berlangsung", 90,
                new ArrayList<>(), "", ""
        ));

        // Data 9: Perawatan Lansia dengan Stroke
        campaignList.add(new Campaign(
                9,
                "Bantu Perawatan Nenek Rukiyah yang Mengalami Stroke",
                R.drawable.campaign9,
                "11 Agustus 2025", "Sisa 50 hari",
                4200000L, 20000000L,
                "Biaya Fisioterapi", 30, 15, "sesi",
                "Obat Harian", 20, 60, "hari",
                "Kursi Roda", 60, 1, "unit",
                "Membantu perawatan Nenek Rukiyah agar dapat kembali beraktivitas pasca stroke.",
                "Nenek Rukiyah, usia 72 tahun, mengalami stroke yang mengakibatkan kelumpuhan sebagian tubuh. Saat ini ia membutuhkan fisioterapi intensif, obat harian, dan kursi roda untuk menunjang mobilitas.\n\nKami membuka donasi untuk membantu memenuhi kebutuhan berikut:\n• Biaya Fisioterapi: Mengembalikan kekuatan otot dan koordinasi tubuh.\n• Obat Harian: Menjaga tekanan darah dan kesehatan jantung.\n• Kursi Roda: Memudahkan mobilitas selama pemulihan.\n\nSetiap bantuan akan sangat membantu meringankan beban keluarga dan mempercepat pemulihan beliau.",
                "Keluarga Nenek Rukiyah", "Keluarga Pasien", R.drawable.organizer9,
                "Terima kasih atas perhatian dan dukungan yang diberikan.",
                "Berlangsung", 50,
                new ArrayList<>(), "", ""
        ));

        // Data 10: Sumur Air Bersih
        campaignList.add(new Campaign(
                10,
                "Perbaikan Sumur Air Bersih untuk Desa Sukamaju",
                R.drawable.campaign10,
                "11 Agustus 2025", "Sisa 25 hari",
                20000000L, 20000000L,
                "Pompa Air", 100, 1, "unit",
                "Pipa dan Saluran", 100, 50, "meter",
                "Biaya Pengerjaan", 100, 1, "proyek",
                "Mendukung perbaikan sumur air bersih agar warga Desa Sukamaju kembali mendapatkan akses air layak.",
                "Warga Desa Sukamaju selama ini mengandalkan sumur utama untuk kebutuhan sehari-hari. Namun, pompa airnya rusak dan saluran pipa mengalami kebocoran sehingga pasokan air terhenti. Situasi ini mengganggu aktivitas warga, mulai dari memasak hingga mandi.\n\nKami membuka donasi untuk membantu memenuhi kebutuhan berikut:\n• Pompa Air: Mengganti pompa lama yang rusak.\n• Pipa dan Saluran: Memperbaiki jaringan distribusi air.\n• Biaya Pengerjaan: Menutupi ongkos tukang dan material pendukung.\n\nSetiap dukungan Anda akan membantu warga kembali mendapatkan akses air bersih yang layak.",
                "Tanggap Air Bersih", "Organisasi Masyarakat", R.drawable.organizer10,
                "Terima kasih atas bantuan yang telah diberikan. Setiap sumbangan adalah langkah menuju kehidupan yang lebih sehat.",
                "Selesai", 25,
                new ArrayList<>(), "", ""
        ));



        currentUser = new User("John Doe","Mas Supir","JohnDoe@gmail.com","08123812","passwordJohnDoe", 50000, 10000,"");

    }

    public static DummyDataRepository getInstance() {
        if (instance == null) {
            instance = new DummyDataRepository();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void updateCurrentUserBalance(long newBalance) {
        currentUser.setBalance(newBalance);
    }

    public void updateCurrentUserDonationPerSwipe(long newNominal) {
        currentUser.setDonationPerSwipe(newNominal);
    }

    public List<Campaign> getCampaignList() {
        return campaignList;
    }
    public List<Campaign> getCampaignsByIdRange(int startId, int endId) {
        List<Campaign> filtered = new ArrayList<>();
        for (Campaign c : campaignList) {
            int currentId = c.getId();
            // Cek apakah ID campaign berada di antara startId dan endId
            if (currentId >= startId && currentId <= endId) {
                filtered.add(c);
            }
        }
        return filtered;
    }
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
        list.add(new Notification("14 Agt 2025", "Donasi Diproses: WASPADA! INVASi AIR JAHAT! Kulkas & Kasur Belajar Berenang!", "Barang donasimu telah dikirim ke lokasi kampanye.", R.drawable.campaign_banjir));
        list.add(new Notification("14 Agt 2025", "Donasi Diterima: WASPADA! INVASi AIR JAHAT! Kulkas & Kasur Belajar Berenang!", "Donasi barangmu telah kami terima. Terima kasih!", R.drawable.campaign_banjir));
        list.add(new Notification("14 Agt 2025", "Donasi Menunggu: WASPADA! INVASi AIR JAHAT! Kulkas & Kasur Belajar Berenang!", "Yuk antar donasi barangmu ke drop point dalam 3 hari!", R.drawable.campaign_banjir));
        list.add(new Notification("14 Agt 2025", "Donasi Berhasil: WASPADA! INVASi AIR JAHAT! Kulkas & Kasur Belajar Berenang!", "Kamu berdonasi sebesar Rp10.000", R.drawable.campaign_banjir));
        list.add(new Notification("14 Agt 2025", "Kampanye Baru: Pemulihan Sekolah SD Harapan Baru Pasca Banjir", "Yuk jadi donatur pertama dan bantu selamatkan nyawa anak-anak.", R.drawable.campaign9));
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
                "Top Up Saldo",
                "Top Up Berhasil",
                "+500.000",
                R.drawable.bank_bca,
                "uang"
        ));

        list.add(new DonationHistory(
                "Bantu Pemulihan Saudara Andi yang Mengalami Patah Kaki",
                "Donasi Berhasil",
                "-10.000",
                R.drawable.campaign2,
                "uang"
        ));

        list.add(new DonationHistory(
                "Bantu Biaya Perawatan Ibu Siti yang Terkena Penyakit Paru-Paru",
                "Donasi Berhasil",
                "-10.000",
                R.drawable.campaign3,
                "uang"
        ));

        list.add(new DonationHistory(
                "WASPADA! INVASi AIR JAHAT! Kulkas & Kasur Belajar Berenang!",
                "Donasi Berhasil",
                "-480.000",
                R.drawable.campaign_banjir,
                "uang"
        ));

        list.add(new DonationHistory(
                "WASPADA! INVASi AIR JAHAT! Kulkas & Kasur Belajar Berenang!",
                "Donasi Diterima",
                "3 stel pakaian",
                R.drawable.campaign_banjir,
                "barang"
        ));

        list.add(new DonationHistory(
                "WASPADA! INVASi AIR JAHAT! Kulkas & Kasur Belajar Berenang!",
                "Donasi Diterima",
                "2 paket obat",
                R.drawable.campaign_banjir,
                "barang"
        ));

        list.add(new DonationHistory(
                "WASPADA! INVASi AIR JAHAT! Kulkas & Kasur Belajar Berenang!",
                "Donasi Diterima",
                "5 kg beras",
                R.drawable.campaign_banjir,
                "barang"
        ));

        list.add(new DonationHistory(
                "Top Up Saldo",
                "Top Up Berhasil",
                "+100.000",
                R.drawable.bank_bca,
                "uang"
        ));

        Collections.reverse(list);

        return list;
    }
}