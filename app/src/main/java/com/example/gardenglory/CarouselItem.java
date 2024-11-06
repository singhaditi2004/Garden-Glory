package com.example.gardenglory;

// CarouselItem.java
public class CarouselItem {
    private String title;
    private int imageResId;

    public CarouselItem(String title, int imageResId) {
        this.title = title;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResId() {
        return imageResId;
    }
}
