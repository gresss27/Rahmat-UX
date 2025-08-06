package com.example.rahmat_ux.model;

public class User {

    private String name;
    private long balance;
    private long donationPerSwipe;

    public User(String name, long balance, long donationPerSwipe) {
        this.name = name;
        this.balance = balance;
        this.donationPerSwipe = donationPerSwipe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getDonationPerSwipe() {
        return donationPerSwipe;
    }

    public void setDonationPerSwipe(long donationPerSwipe) {
        this.donationPerSwipe = donationPerSwipe;
    }
}