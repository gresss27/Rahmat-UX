package com.example.rahmat_ux.model;

import java.util.List;

public class Campaign {

    // Data Utama
    private final int id; // Unique ID for each campaign
    private final String title;
    private final int mainImageResId;
    private final String dateStarted;
    private final String timeRemaining;

    // Data Donasi Tunai
    private long amountCollected;
    private long targetAmount;

    // Data Donasi Non-Tunai
    private final int foodProgress;
    private final int clothingProgress;
    private final int medicineProgress;

    // Data Deskripsi & Penyelenggara
    private final String description;
    private final String organizerName;
    private final String organizerOccupation;
    private final int organizerImageResId;
    private final String lastChat;
    private final String status;
    private final int remainingDays;
    private List<Story> stories; // Tambahkan properti ini

    public Campaign(int id, String title, int mainImageResId, String dateStarted, String timeRemaining,
                    long amountCollected, long targetAmount,
                    int foodProgress, int clothingProgress, int medicineProgress,
                    String description, String organizerName, String organizerOccupation, int organizerImageResId, String lastChat, String status, int remainingDays) {
        this.id = id;
        this.title = title;
        this.mainImageResId = mainImageResId;
        this.dateStarted = dateStarted;
        this.timeRemaining = timeRemaining;
        this.amountCollected = amountCollected;
        this.targetAmount = targetAmount;
        this.foodProgress = foodProgress;
        this.clothingProgress = clothingProgress;
        this.medicineProgress = medicineProgress;
        this.description = description;
        this.organizerName = organizerName;
        this.organizerOccupation = organizerOccupation;
        this.organizerImageResId = organizerImageResId;
        this.lastChat = lastChat;
        this.status = status;
        this.remainingDays = remainingDays;
    }

    public Campaign(int id, String title, int mainImageResId, String dateStarted, String timeRemaining, long amountCollected, long targetAmount, int foodProgress, int clothingProgress, int medicineProgress, String description, String organizerName, String organizerOccupation, int organizerImageResId, String lastChat, String status, int remainingDays, List<Story> stories) {
        this.id = id;
        this.title = title;
        this.mainImageResId = mainImageResId;
        this.dateStarted = dateStarted;
        this.timeRemaining = timeRemaining;
        this.amountCollected = amountCollected;
        this.targetAmount = targetAmount;
        this.foodProgress = foodProgress;
        this.clothingProgress = clothingProgress;
        this.medicineProgress = medicineProgress;
        this.description = description;
        this.organizerName = organizerName;
        this.organizerOccupation = organizerOccupation;
        this.organizerImageResId = organizerImageResId;
        this.lastChat = lastChat;
        this.status = status;
        this.remainingDays = remainingDays;
        this.stories = stories;
    }


    // --- Getter Methods ---
    public int getId() { return id; }
    public String getTitle() { return title; }
    public int getMainImageResId() { return mainImageResId; }
    public String getDateStarted() { return dateStarted; }
    public String getTimeRemaining() { return timeRemaining; }
    public long getAmountCollected() { return amountCollected; }
    public long getTargetAmount() { return targetAmount; }
    public int getFoodProgress() { return foodProgress; }
    public int getClothingProgress() { return clothingProgress; }
    public int getMedicineProgress() { return medicineProgress; }
    public String getDescription() { return description; }
    public String getOrganizerName() { return organizerName; }

    public String getOrganizerOccupation() {
        return organizerOccupation;
    }

    public int getOrganizerImageResId() { return organizerImageResId; }

    public int getRemainingDays() {
        return remainingDays;
    }

    public String getStatus() {
        return status;
    }

    public void setAmountCollected(long amountCollected) {
        this.amountCollected = amountCollected;
    }
    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public String getLastChat(){
        return lastChat;
    }
}