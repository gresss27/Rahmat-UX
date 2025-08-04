package com.example.rahmat_ux.model;

public class Bank {
    private String bankName;
    private int bankLogoResId;

    public Bank(String bankName, int bankLogoResId) {
        this.bankName = bankName;
        this.bankLogoResId = bankLogoResId;
    }

    public String getBankName() {
        return bankName;
    }

    public int getBankLogoResId() {
        return bankLogoResId;
    }
}
