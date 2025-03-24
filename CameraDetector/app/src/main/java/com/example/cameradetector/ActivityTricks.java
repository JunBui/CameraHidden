package com.example.cameradetector;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Arrays;
import java.util.List;

public class ActivityTricks extends BaseActivityWithToolBar {

    private ViewPager2 viewPager;
    private LinearLayout dotsContainer;
    private int totalPages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tricks);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    @Override
    protected void initUiOnCreate() {
        super.initUiOnCreate();
        viewPager = findViewById(R.id.viewPager);
        dotsContainer = findViewById(R.id.dotsContainer);

        List<TutorialSlideItem> sliderItems = Arrays.asList(
                new TutorialSlideItem(R.drawable.tutorial_adjust_zoom, R.string.zoom_picture, R.string.use_the_right_slider_to_adjust_the_zoom_level),
                new TutorialSlideItem(R.drawable.tutorial_adjust_brightness, R.string.adjust_the_brightness, R.string.adjust_the_brightness_by_dragging_the_slider_left_or_right)
        );

        totalPages = sliderItems.size();
        viewPager.setAdapter(new TutorialSliderAdapter(getContext(),sliderItems));

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
    }
}