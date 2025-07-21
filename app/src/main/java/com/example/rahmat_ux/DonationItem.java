package com.example.rahmat_ux;

public class DonationItem {
    private String title, target, creator;

    public DonationItem(String title, String target, String creator) {
        this.title = title;
        this.target = target;
        this.creator = creator;
    }

    public String getTitle() { return title; }
    public String getTarget() { return target; }
    public String getCreator() { return creator; }
}
