package com.example.cameradetector;

public class ImageAndTitle {

    private int imageRes;

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public int getDescriptionRes() {
        return descriptionRes;
    }

    public void setDescriptionRes(int descriptionRes) {
        this.descriptionRes = descriptionRes;
    }

    private int descriptionRes;

    public ImageAndTitle(int imageRes, int descriptionRes) {
        this.imageRes = imageRes;
        this.descriptionRes = descriptionRes;
    }
}
