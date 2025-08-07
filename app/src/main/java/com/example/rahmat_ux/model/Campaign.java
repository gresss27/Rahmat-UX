package com.example.rahmat_ux.model;

import java.io.Serializable;
import java.util.List;

public class Campaign implements Serializable {

    // Data Utama
    private int id;
    private String title;
    private int mainImageResId;
    private final String dateStarted;
    private final String timeRemaining;

    // Data Donasi Tunai
    private long amountCollected;
    private long targetAmount;

    // Data Donasi Non-Tunai (custom items)
    private final String item1Name;
    private final String item2Name;
    private final String item3Name;
    private final int item1Progress;
    private final int item2Progress;
    private final int item3Progress;

    // Data Deskripsi & Penyelenggara
    private String description;
    private String longDescription;
    private String organizerName;
    private String organizerOccupation;
    private int organizerImageResId;
    private String lastChat;
    private String status;
    private final int remainingDays;
    private String coverImageUri;
    private String storyTitle;

    private List<Story> stories;

    // --- Constructors ---
    public Campaign(int id, String title, int mainImageResId, String dateStarted, String timeRemaining,
                    long amountCollected, long targetAmount, String item1Name, int item1Progress,
                    String item2Name, int item2Progress,
                    String item3Name, int item3Progress, String longDescription, String coverImageUri, String storyTitle,
                    String description, String organizerName, String organizerOccupation,
                    int organizerImageResId, String lastChat, String status, int remainingDays) {
        this.id = id;
        this.title = title;
        this.mainImageResId = mainImageResId;
        this.dateStarted = dateStarted;
        this.timeRemaining = timeRemaining;
        this.amountCollected = amountCollected;
        this.targetAmount = targetAmount;
        this.item1Name = item1Name;
        this.item2Name = item2Name;
        this.item3Name = item3Name;
        this.item1Progress = item1Progress;
        this.item2Progress = item2Progress;
        this.item3Progress = item3Progress;
        this.longDescription = longDescription;
        this.coverImageUri = coverImageUri;
        this.storyTitle = storyTitle;
        this.description = description;
        this.organizerName = organizerName;
        this.organizerOccupation = organizerOccupation;
        this.organizerImageResId = organizerImageResId;
        this.lastChat = lastChat;
        this.status = status;
        this.remainingDays = remainingDays;
    }

    public Campaign(int id, String title, int mainImageResId, String dateStarted, String timeRemaining,
                    long amountCollected, long targetAmount,
                    String item1Name, int item1Progress,
                    String item2Name, int item2Progress,
                    String item3Name, int item3Progress,
                    String description, String longDescription,
                    String organizerName, String organizerOccupation,
                    int organizerImageResId, String lastChat, String status, int remainingDays,
                    List<Story> stories, String coverImageUri, String storyTitle) {
        this.id = id;
        this.title = title;
        this.mainImageResId = mainImageResId;
        this.dateStarted = dateStarted;
        this.timeRemaining = timeRemaining;
        this.amountCollected = amountCollected;
        this.targetAmount = targetAmount;

        this.item1Name = item1Name;
        this.item2Name = item2Name;
        this.item3Name = item3Name;
        this.item1Progress = item1Progress;
        this.item2Progress = item2Progress;
        this.item3Progress = item3Progress;

        this.description = description;
        this.longDescription = longDescription;

        this.organizerName = organizerName;
        this.organizerOccupation = organizerOccupation;
        this.organizerImageResId = organizerImageResId;
        this.lastChat = lastChat;
        this.status = status;
        this.remainingDays = remainingDays;
        this.stories = stories;
        this.coverImageUri = coverImageUri;
        this.storyTitle = storyTitle;
    }


    // --- Getters ---
    public int getId() { return id; }
    public String getTitle() { return title; }
    public int getMainImageResId() { return mainImageResId; }
    public String getDateStarted() { return dateStarted; }
    public String getTimeRemaining() { return timeRemaining; }

    public long getAmountCollected() { return amountCollected; }
    public long getTargetAmount() { return targetAmount; }
    public void setAmountCollected(long amountCollected) { this.amountCollected = amountCollected; }

    public String getItem1Name() { return item1Name; }
    public String getItem2Name() { return item2Name; }
    public String getItem3Name() { return item3Name; }

    public int getItem1Progress() { return item1Progress; }
    public int getItem2Progress() { return item2Progress; }
    public int getItem3Progress() { return item3Progress; }

    public String getDescription() { return description; }

    public String getLongDescription() { return longDescription; }

    public String getOrganizerName() { return organizerName; }
    public String getOrganizerOccupation() { return organizerOccupation; }
    public int getOrganizerImageResId() { return organizerImageResId; }

    public String getLastChat() { return lastChat; }
    public String getStatus() { return status; }
    public int getRemainingDays() { return remainingDays; }
    public List<Story> getStories() { return stories; }
    public String getCoverImageUri() { return coverImageUri; }
    public String getStoryTitle() { return storyTitle; }

    public void setStories(List<Story> stories) { this.stories = stories; }
    // --- Setter Methods ---
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setTargetAmount(long targetAmount) { this.targetAmount = targetAmount; }
    public void setDescription(String description) { this.description = description; }
    public void setOrganizerName(String organizerName) { this.organizerName = organizerName; }
    public void setOrganizerOccupation(String organizerOccupation) { this.organizerOccupation = organizerOccupation; }
    public void setOrganizerImageResId(int organizerImageResId) { this.organizerImageResId = organizerImageResId; }
    public void setLastChat(String lastChat) { this.lastChat = lastChat; }
    public void setStatus(String status) { this.status = status; }
    public void setCoverImageUri(String coverImageUri) { this.coverImageUri = coverImageUri; }
    public void setStoryTitle(String storyTitle) { this.storyTitle = storyTitle; }
    public void setMainImageResId(int mainImageResId) { this.mainImageResId = mainImageResId; }
    public void setLongDescription(String longDescription) { this.longDescription = longDescription; }
}
