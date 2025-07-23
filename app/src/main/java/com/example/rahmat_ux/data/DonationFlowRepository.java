package com.example.rahmat_ux.data;

import com.example.rahmat_ux.model.DonatedItem;
import com.example.rahmat_ux.model.DropPoint;
import java.util.ArrayList;
import java.util.List;

public class DonationFlowRepository {

    public static List<DonatedItem> getDonatedItems() {
        List<DonatedItem> items = new ArrayList<>();

        // Kategori: Bahan Makanan
        items.add(new DonatedItem("Bahan Makanan", "Indomie Goreng", "3 kardus"));
        items.add(new DonatedItem("Bahan Makanan", "Beras Rojolele", "2 karung (50 kg)"));

        // Kategori: Pakaian dan Selimut
        items.add(new DonatedItem("Pakaian dan Selimut", "Selimut Dewasa", "5 buah"));

        // Kategori: Obat-obatan
        items.add(new DonatedItem("Obat-obatan", "Paracetamol", "2 box"));

        return items;
    }

    public static List<DropPoint> getDropPoints() {
        List<DropPoint> points = new ArrayList<>();
        points.add(new DropPoint("Posko Utama Alang Lebar", "Jalan Salman Mufrad, No. 1477", "500m"));
        points.add(new DropPoint("Gudang Logistik Sukarami", "Jalan Kebun Bunga, Komplek Pergudangan", "1.2 km"));
        points.add(new DropPoint("Kantor Cabang Kemuning", "Jalan Jenderal Sudirman, No. 21", "2.5 km"));
        return points;
    }
}