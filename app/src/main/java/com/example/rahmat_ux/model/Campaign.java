package com.example.rahmat_ux.model;

public class Campaign {

    // Data Utama
    private final String title;
    private final int mainImageResId;
    private final String dateStarted;
    private final String timeRemaining;

    // Data Donasi Tunai
    private final long amountCollected;
    private final long targetAmount;

    // Data Donasi Non-Tunai
    private final int foodProgress;
    private final int clothingProgress;
    private final int medicineProgress;

    // Data Deskripsi & Penyelenggara
    private final String description;
    private final String organizerName;
    private final String organizerOccupation;
    private final int organizerImageResId;

    // Tambahkan ini di bagian atas atribut
    private final String status; // Diajukan, Berlangsung, Selesai
    private final int remainingDays;


    public Campaign(String title, int mainImageResId, String dateStarted, String timeRemaining,
                    long amountCollected, long targetAmount,
                    int foodProgress, int clothingProgress, int medicineProgress,
                    String description, String organizerName, String organizerOccupation, int organizerImageResId, String status, int remainingDays) {
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
        this.status = status;
        this.remainingDays = remainingDays;
    }

    // --- Getter Methods ---

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
}