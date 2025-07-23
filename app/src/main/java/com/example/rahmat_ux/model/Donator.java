package com.example.rahmat_ux.model;

public class Donator {
    private String name;
    private String amount;
    private String time;
    private int imageResId;

    public Donator(String name, String amount, String time, int imageResId) {
        this.name = name;
        this.amount = amount;
        this.time = time;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public String getAmount() { return amount; }
    public String getTime() { return time; }
    public int getImageResId() { return imageResId; }
}
