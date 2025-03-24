package com.example.cameradetector;

public class TutorialSlideItem {
    private int imageRes;
    private final int titleRes;
    private final int descriptionRes;

    public TutorialSlideItem(int imageRes, int titleRes, int descriptionRes) {
        this.imageRes = imageRes;
        this.titleRes = titleRes;
        this.descriptionRes = descriptionRes;
    }

    public int getImageRes() {
        return imageRes;
    }

    public int  getTitle() {
        return titleRes;
    }

    public int  getDescription() {
        return descriptionRes;
    }
}
