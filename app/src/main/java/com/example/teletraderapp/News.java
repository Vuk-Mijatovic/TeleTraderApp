package com.example.teletraderapp;

public class News {
    private String Headline;
    private int ImageId;

    public News(String headline, int imageId) {
        Headline = headline;
        ImageId = imageId;
    }

    public String getHeadline() {
        return Headline;
    }

    public int getImageId() {
        return ImageId;
    }
}
