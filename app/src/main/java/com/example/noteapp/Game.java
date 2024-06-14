package com.example.noteapp;

public class Game {
    private String title;
    private String imageUrl;

    public Game(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
