package com.example.rahmat_ux.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String name;
    private String job;
    private String email;
    private long balance;
    private long donationPerSwipe;
    private String profileImageUri;

    public User() {
        this.name = "";
        this.job = "";
        this.email = "";
        this.balance = 0;
        this.donationPerSwipe = 0;
        this.profileImageUri = null;
    }


    public User(String name, String job, String email, long balance, long donationPerSwipe, String profileImageUri) {
        this.name = name;
        this.job = job;
        this.email = email;
        this.balance = balance;
        this.donationPerSwipe = donationPerSwipe;
        this.profileImageUri = profileImageUri;
    }

    // --- Getters ---
    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public String getEmail() {
        return email;
    }

    public long getBalance() {
        return balance;
    }

    public long getDonationPerSwipe() {
        return donationPerSwipe;
    }

    // --- Setters ---
    public void setName(String name) {
        this.name = name;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBalance(long balance) {
        this.balance = balance;
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
        job = in.readString();
        email = in.readString();
        balance = in.readLong();
        donationPerSwipe = in.readLong();
        profileImageUri = in.readString();
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
        dest.writeString(job);
        dest.writeString(email);
        dest.writeLong(balance);
        dest.writeLong(donationPerSwipe);
        dest.writeString(profileImageUri);
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