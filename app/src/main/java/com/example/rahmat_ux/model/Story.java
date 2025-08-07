package com.example.rahmat_ux.model;

import java.io.Serializable;

public class Story implements Serializable {
    private String type; // "text", "image"
    private String content;

    public Story(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}