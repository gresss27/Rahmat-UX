package com.example.rahmat_ux.model;

public class DropPoint {
    private String name;
    private String address;
    private String distance;

    public DropPoint(String name, String address, String distance) {
        this.name = name;
        this.address = address;
        this.distance = distance;
    }

    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getDistance() { return distance; }
}