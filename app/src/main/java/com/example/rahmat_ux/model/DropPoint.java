// Buat file baru: model/DropPoint.java
package com.example.rahmat_ux.model; // Sesuaikan dengan package Anda

import java.io.Serializable;

public class DropPoint implements Serializable {
    private String name;
    private String address;
    private String distance;

    public DropPoint(String name, String address, String distance) {
        this.name = name;
        this.address = address;
        this.distance = distance;
    }

    // Buat Getter untuk semua field
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDistance() {
        return distance;
    }
}