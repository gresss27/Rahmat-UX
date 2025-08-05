package com.example.rahmat_ux.model;

public class Story {
    // Tipe cerita: "text", "image"
    private String type;

    // Konten cerita: teks untuk type "text", URI gambar untuk type "image"
    private String content;

    public Story(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}