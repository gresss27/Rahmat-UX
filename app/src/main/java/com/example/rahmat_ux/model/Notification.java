package com.example.rahmat_ux.model;

public class Notification {
    private String time;
    private String title;
    private String subtitle;
    private int imageResId;

    public Notification(String time, String title, String subtitle, int imageResId) {
        this.time = time;
        this.title = title;
        this.subtitle = subtitle;
        this.imageResId = imageResId;
    }

    public String getTime() { return time; }
    public String getTitle() { return title; }
    public String getSubtitle() { return subtitle; }
    public int getImageResId() { return imageResId; }
}

