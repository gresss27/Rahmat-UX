package com.example.rahmat_ux.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String name;
    private long balance;

    private String email;
    private String password;

    private String pekerjaan;
    private String phoneNumber;
    private long donationPerSwipe;
    private String profileImageUri;
    public User() {
        this.name = "";
        this.pekerjaan = "";
        this.email = "";
        this.balance = 0;
        this.phoneNumber="";
        this.password="";
        this.donationPerSwipe = 0;
        this.profileImageUri = null;
    }

    public User(String name,String pekerjaan, String email,String phoneNumber,String password ,long balance, long donationPerSwipe, String uri) {
        this.name = name;
        this.email=email;
        this.password=password;
        this.balance = balance;
        this.donationPerSwipe = donationPerSwipe;
        this.pekerjaan=pekerjaan;
        this.phoneNumber=phoneNumber;
        this.profileImageUri=uri;
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


    public String getProfileImageUri() {
        return profileImageUri;
    }

    public void setProfileImageUri(String profileImageUri) {
        this.profileImageUri = profileImageUri;
    }



    protected User(Parcel in) {
        name = in.readString();
        pekerjaan = in.readString();
        email = in.readString();
        balance = in.readLong();
        donationPerSwipe = in.readLong();
        profileImageUri = in.readString();
        password=in.readString();
        phoneNumber=in.readString();
    }


    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(pekerjaan);
        dest.writeString(email);
        dest.writeLong(balance);
        dest.writeLong(donationPerSwipe);
        dest.writeString(profileImageUri);
        dest.writeString(password);
        dest.writeString(phoneNumber);

    }
}





//package com.example.rahmat_ux.model;
//
//public class User {
//
//    private String name;
//    private long balance;
//    private long donationPerSwipe;
//
//
//    public User(String name, long balance, long donationPerSwipe) {
//        this.name = name;
//        this.balance = balance;
//        this.donationPerSwipe = donationPerSwipe;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public long getBalance() {
//        return balance;
//    }
//
//    public void setBalance(long balance) {
//        this.balance = balance;
//    }
//
//    public long getDonationPerSwipe() {
//        return donationPerSwipe;
//    }
//
//    public void setDonationPerSwipe(long donationPerSwipe) {
//        this.donationPerSwipe = donationPerSwipe;
//    }
//}