package com.example.cameradetector;

import android.graphics.drawable.GradientDrawable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivityTricks extends BaseActivityWithToolBar {

    private ViewPager2 viewPager;
    private LinearLayout dotsContainer;
    private int totalPages;

    @Override
    protected void initUiOnCreate() {
        super.initUiOnCreate();
        viewPager = findViewById(R.id.viewPager);
        dotsContainer = findViewById(R.id.dotsContainer);
        List<TricksSlideItem> sliderItems = new ArrayList<>();
        EnumTricks myEnumValue = (EnumTricks) getIntent().getSerializableExtra("Tricks_Enum");
        if (myEnumValue != null) {
            // Use the enum value
            switch (myEnumValue) {
                case Bedroom:
                    List<ImageAndTitle> imageAndTitles = new ArrayList<>();
                    imageAndTitles.add(new ImageAndTitle(R.drawable.img_electrical_outlet, R.string.electrical_outlet));
                    imageAndTitles.add(new ImageAndTitle(R.drawable.img_lenses_hole, R.string.small_lenses_or_holes));
                    imageAndTitles.add(new ImageAndTitle(R.drawable.img_smoke_detector, R.string.smoke_detectors));
                    imageAndTitles.add(new ImageAndTitle(R.drawable.img_clocks, R.string.clocks));
                    sliderItems.addAll(Arrays.asList(
                            new TricksSlideItem(0, R.string.trick_bed_1, imageAndTitles),
                            new TricksSlideItem(R.drawable.image_infrared_camera, R.string.trick_2, new ArrayList<ImageAndTitle>())
                    ));
                    break;
                case LivingRoom:
                    List<ImageAndTitle> imageAndTitlesLivingRoom = new ArrayList<>();
                    imageAndTitlesLivingRoom.add(new ImageAndTitle(R.drawable.img_picture_frame, R.string.picture_frames));
                    imageAndTitlesLivingRoom.add(new ImageAndTitle(R.drawable.img_bookshelves, R.string.bookshelves));
                    imageAndTitlesLivingRoom.add(new ImageAndTitle(R.drawable.img_smoke_detector, R.string.smoke_detectors));
                    imageAndTitlesLivingRoom.add(new ImageAndTitle(R.drawable.img_plant_pot, R.string.plant_pot));
                    sliderItems.addAll(Arrays.asList(
                            new TricksSlideItem(0, R.string.trick_living_2, imageAndTitlesLivingRoom)
                    ));
                    break;
                case Bathroom:
                    List<ImageAndTitle> imageAndTitlesBathRoom = new ArrayList<>();
                    imageAndTitlesBathRoom.add(new ImageAndTitle(R.drawable.img_shower_head, R.string.shower_head));
                    imageAndTitlesBathRoom.add(new ImageAndTitle(R.drawable.img_electrical_outlet, R.string.electrical_outlet));
                    imageAndTitlesBathRoom.add(new ImageAndTitle(R.drawable.img_lenses_hole, R.string.small_lenses_or_holes));
                    imageAndTitlesBathRoom.add(new ImageAndTitle(R.drawable.img_small_bottle, R.string.small_bottles));
                    sliderItems.addAll(Arrays.asList(
                            new TricksSlideItem(0, R.string.trick_1, imageAndTitlesBathRoom),
                            new TricksSlideItem(R.drawable.img_two_ways_mirror, R.string.trick_bath_2, new ArrayList<ImageAndTitle>())
                    ));
                    break;
                case ChangingRoom:
                    List<ImageAndTitle> imageAndTitlesChangingRoom = new ArrayList<>();
                    imageAndTitlesChangingRoom.add(new ImageAndTitle(R.drawable.img_clothes_hanger, R.string.clothes_hanger));
                    imageAndTitlesChangingRoom.add(new ImageAndTitle(R.drawable.img_electrical_outlet, R.string.electrical_outlet));
                    imageAndTitlesChangingRoom.add(new ImageAndTitle(R.drawable.img_lenses_hole, R.string.small_lenses_or_holes));
                    imageAndTitlesChangingRoom.add(new ImageAndTitle(R.drawable.img_smoke_detector, R.string.smoke_detectors));
                    sliderItems.addAll(Arrays.asList(
                            new TricksSlideItem(0, R.string.trick_1, imageAndTitlesChangingRoom),
                            new TricksSlideItem(R.drawable.img_changing_room, R.string.trick_2, new ArrayList<ImageAndTitle>())
                    ));
                    break;
            }
        }
        sliderItems.add(new TricksSlideItem(R.drawable.img_listen, R.string.trick_3, new ArrayList<ImageAndTitle>()));
        sliderItems.add(new TricksSlideItem(R.drawable.img_security_check, R.string.trick_4, new ArrayList<ImageAndTitle>()));

        totalPages = sliderItems.size();
        viewPager.setAdapter(new TrickSliderAdapter(getContext(), sliderItems));

        setupDots();
        updateDots(0);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateDots(position);
            }
        });
    }

    private void setupDots() {
        dotsContainer.removeAllViews();
        for (int i = 0; i < totalPages; i++) {
            ImageView dot = new ImageView(this);
            int dotSize = 30;  // Đổi kích thước (px)
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dotSize, dotSize);
            params.setMargins(10, 0, 10, 0);  // Khoảng cách giữa các dot
            dot.setLayoutParams(params);
            dotsContainer.addView(dot);
        }
    }

    private void updateDots(int currentPage) {
        for (int i = 0; i < totalPages; i++) {
            ImageView dot = (ImageView) dotsContainer.getChildAt(i);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.OVAL);
            drawable.setSize(30, 30);
            drawable.setColor(i == currentPage ? getContext().getColor(R.color.blue_dark) : getContext().getColor(R.color.gray_dark));
            dot.setImageDrawable(drawable);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tricks;
    }

    @Override
    protected void setToolBarTitle() {
        super.setToolBarTitle();
        EnumTricks myEnumValue = (EnumTricks) getIntent().getSerializableExtra("Tricks_Enum");
        if (myEnumValue != null) {
            // Use the enum value
            switch (myEnumValue) {
                case Bedroom:
                    titleTxt.setText(R.string.bedroom);
                    break;
                case LivingRoom:
                    titleTxt.setText(R.string.living_room);
                    break;
                case Bathroom:
                    titleTxt.setText(R.string.bathroom);
                    break;
                case ChangingRoom:
                    titleTxt.setText(R.string.changing_room);
                    break;
            }
        }
    }
}