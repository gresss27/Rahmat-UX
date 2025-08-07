package com.example.rahmat_ux.model;

import java.io.Serializable;
import java.util.List;

public class Campaign implements Serializable {

    private int id;
    private String title;
    private String dateStarted;
    private String timeRemaining;
    private long amountCollected;
    private long targetAmount;
    private final int foodProgress;
    private final int clothingProgress;
    private final int medicineProgress;
    private String description;
    private String organizerName;
    private String organizerOccupation;
    private int organizerImageResId;
    private String lastChat;
    private String status;
    private int remainingDays;
    private List<Story> stories;
    private String coverImageUri;
    private String storyTitle;
    private int mainImageResId;

    // Konstruktor utama untuk data dummy yang sudah ada
    public Campaign(int id, String title, int mainImageResId, String dateStarted, String timeRemaining,
                    long amountCollected, long targetAmount, int foodProgress, int clothingProgress,
                    int medicineProgress, String description, String organizerName, String organizerOccupation,
                    int organizerImageResId, String lastChat, String status, int remainingDays) {
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

    // Konstruktor untuk membuat kampanye baru (draft)
    public Campaign(int id, String title, String dateStarted, String timeRemaining, long amountCollected,
                    long targetAmount, String description, String organizerName, String organizerOccupation,
                    int organizerImageResId, String status, int remainingDays, List<Story> stories,
                    String coverImageUri, String storyTitle, int mainImageResId) {
        this.id = id;
        this.title = title;
        this.dateStarted = dateStarted;
        this.timeRemaining = timeRemaining;
        this.amountCollected = amountCollected;
        this.targetAmount = targetAmount;
        this.foodProgress = 0;
        this.clothingProgress = 0;
        this.medicineProgress = 0;
        this.description = description;
        this.organizerName = organizerName;
        this.organizerOccupation = organizerOccupation;
        this.organizerImageResId = organizerImageResId;
        this.lastChat = "";
        this.status = status;
        this.remainingDays = remainingDays;
        this.stories = stories;
        this.coverImageUri = coverImageUri;
        this.storyTitle = storyTitle;
        this.mainImageResId = mainImageResId;
    }

    // --- Getter Methods ---
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDateStarted() { return dateStarted; }
    public String getTimeRemaining() { return timeRemaining; }
    public long getAmountCollected() { return amountCollected; }
    public long getTargetAmount() { return targetAmount; }
    public int getFoodProgress() { return foodProgress; }
    public int getClothingProgress() { return clothingProgress; }
    public int getMedicineProgress() { return medicineProgress; }
    public String getDescription() { return description; }
    public String getOrganizerName() { return organizerName; }
    public String getOrganizerOccupation() { return organizerOccupation; }
    public int getOrganizerImageResId() { return organizerImageResId; }
    public int getRemainingDays() { return remainingDays; }
    public String getStatus() { return status; }
    public String getLastChat() { return lastChat; }
    public List<Story> getStories() { return stories; }
    public String getCoverImageUri() { return coverImageUri; }
    public String getStoryTitle() { return storyTitle; }
    public int getMainImageResId() { return mainImageResId; }

    // --- Setter Methods ---
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAmountCollected(long amountCollected) { this.amountCollected = amountCollected; }
    public void setTargetAmount(long targetAmount) { this.targetAmount = targetAmount; }
    public void setDescription(String description) { this.description = description; }
    public void setOrganizerName(String organizerName) { this.organizerName = organizerName; }
    public void setOrganizerOccupation(String organizerOccupation) { this.organizerOccupation = organizerOccupation; }
    public void setOrganizerImageResId(int organizerImageResId) { this.organizerImageResId = organizerImageResId; }
    public void setLastChat(String lastChat) { this.lastChat = lastChat; }
    public void setStatus(String status) { this.status = status; }
    public void setStories(List<Story> stories) { this.stories = stories; }
    public void setCoverImageUri(String coverImageUri) { this.coverImageUri = coverImageUri; }
    public void setStoryTitle(String storyTitle) { this.storyTitle = storyTitle; }
    public void setMainImageResId(int mainImageResId) { this.mainImageResId = mainImageResId; }
}
