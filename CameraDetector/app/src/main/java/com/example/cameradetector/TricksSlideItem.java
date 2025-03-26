package com.example.cameradetector;

import java.util.List;

public class TricksSlideItem {
    private int imageRes;
    private int descriptionRes;
    private List<ImageAndTitle> imageAndTitles;
    public TricksSlideItem(int imageRes, int descriptionRes, List<ImageAndTitle> imageAndTitles) {
        this.imageRes = imageRes;
        this.descriptionRes = descriptionRes;
        this.imageAndTitles = imageAndTitles;
    }

    public int getImageRes() {
        return imageRes;
    }


    public int  getDescription() {
        return descriptionRes;
    }
    public List<ImageAndTitle>  getImageAndTitles() {
        return imageAndTitles;
    }
}