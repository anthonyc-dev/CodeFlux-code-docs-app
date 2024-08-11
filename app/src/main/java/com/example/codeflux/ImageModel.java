package com.example.codeflux;

public class ImageModel {
    private int imageResource;
    private String description;

    public ImageModel(int imageResource, String description) {
        this.imageResource = imageResource;
        this.description = description;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getDescription() {
        return description;
    }
}