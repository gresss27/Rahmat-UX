package com.example.rahmat_ux.model;

public class DonationHistory {
    private String title;
    private String status;
    private String amount;
    private int imageResId;
    private String category; // "uang" or "barang"

    public DonationHistory(String title, String status, String amount, int imageResId, String category) {
        this.title = title;
        this.status = status;
        this.amount = amount;
        this.imageResId = imageResId;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public String getAmount() {
        return amount;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getCategory() {
        return category;
    }
}
