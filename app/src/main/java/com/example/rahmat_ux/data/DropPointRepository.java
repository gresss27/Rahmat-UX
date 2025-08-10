package com.example.rahmat_ux.repository; // Sesuaikan dengan package Anda

import com.example.rahmat_ux.model.DropPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DropPointRepository {

    // --- Bagian untuk Pola Desain Singleton ---
    private static DropPointRepository instance;
    private List<DropPoint> dropPointList = new ArrayList<>();

    // Private constructor agar tidak bisa dibuat dari luar kelas
    private DropPointRepository() {
        // Di sinilah kita membuat data dummy saat repository pertama kali dibuat
        loadDummyDropPoints();
    }

    // Method publik untuk mendapatkan satu-satunya instance dari repository ini
    public static synchronized DropPointRepository getInstance() {
        if (instance == null) {
            instance = new DropPointRepository();
        }
        return instance;
    }
    // --- Akhir Bagian Singleton ---


    // Method untuk memuat data dummy
    private void loadDummyDropPoints() {
        dropPointList.add(new DropPoint("Alang Alang Lebar", "Jalan Letnan Mukmin, Gang Melati, No. 1477", "500m"));
        dropPointList.add(new DropPoint("Sukarami", "Jl. Kol. H. Burlian No.KM. 10, Karya Baru", "1.2km"));
        dropPointList.add(new DropPoint("Ilir Barat I", "Jl. Radial No.1, 24 Ilir, Bukit Kecil", "2.5km"));
        dropPointList.add(new DropPoint("Posko Utama Kamboja", "Jl. Kamboja, Kamboja, Kec. Ilir Tim. I", "3.1km"));
        dropPointList.add(new DropPoint("Gudang Logistik Plaju", "Jl. Jenderal Ahmad Yani, Plaju Ulu", "4.0km"));
    }

    // --- Method Publik untuk Diakses oleh UI ---

    /**
     * Mengembalikan seluruh daftar drop point.
     */
    public List<DropPoint> getDropPoints() {
        return dropPointList;
    }

    /**
     * Mencari drop point berdasarkan query (nama atau alamat).
     * @param query Teks pencarian dari pengguna.
     * @return Daftar drop point yang sudah difilter.
     */
    public List<DropPoint> searchDropPoints(String query) {
        // Jika query kosong, kembalikan semua data
        if (query == null || query.trim().isEmpty()) {
            return dropPointList;
        }

        // Ubah query menjadi huruf kecil untuk pencarian case-insensitive
        String lowerCaseQuery = query.toLowerCase();

        // Filter list menggunakan Stream API (memerlukan Java 8+)
        return dropPointList.stream()
                .filter(dropPoint ->
                        dropPoint.getName().toLowerCase().contains(lowerCaseQuery) ||
                                dropPoint.getAddress().toLowerCase().contains(lowerCaseQuery)
                )
                .collect(Collectors.toList());
    }
}