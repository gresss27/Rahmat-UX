package com.example.rahmat_ux.model;

public class User {

    private String name;
    private long balance;

    private String email;
    private String password;

    private String pekerjaan;
    private String phoneNumber;
    private long donationPerSwipe;

    public User(String name,String pekerjaan, String email,String phoneNumber,String password ,long balance, long donationPerSwipe) {
        this.name = name;
        this.email=email;
        this.password=password;
        this.balance = balance;
        this.donationPerSwipe = donationPerSwipe;
        this.pekerjaan=pekerjaan;
        this.phoneNumber=phoneNumber;
    }

    public String getName() {
        return name;
    }

    public boolean checkPassword(String password){
        return password.equals(this.password);
    }

    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }
    public void setphoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPekerjaan(){
        return this.pekerjaan;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
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