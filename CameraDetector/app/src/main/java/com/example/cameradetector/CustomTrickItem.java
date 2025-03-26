package com.example.cameradetector;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CustomTrickItem extends FrameLayout {
    public ImageView getDescriptionImage() {
        return descriptionImage;
    }
    private ImageView descriptionImage;
    private TextView orderText;
    private TextView description;

    public RecyclerView recyclerView;

    public CustomTrickItem(Context context) {
        super(context);
        init(context, null);
    }

    public CustomTrickItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomTrickItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.custom_tricks_item, this, true);
        recyclerView = findViewById(R.id.recyclerView);
        descriptionImage = findViewById(R.id.descriptionImage);
//        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        orderText = findViewById(R.id.orderText);

        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTutorialItem, 0, 0);
            try {
                int descriptionImageResId = a.getResourceId(R.styleable.CustomTutorialItem_description_image, 0);
                if (descriptionImageResId != 0) {
                    descriptionImage.setImageResource(descriptionImageResId);
                }
                String descriptionString = a.getString(R.styleable.CustomTutorialItem_description);
                if (descriptionString != null) {
                    description.setText(descriptionString);
                }
            } finally {
                a.recycle();
            }
        }
    }
    public void setDescriptionImage(int resId) {
        if (descriptionImage != null) {
            descriptionImage.setImageResource(resId);
        }
    }
    public void setDescriptionImageBitmap(Bitmap bitmap) {
        if (descriptionImage != null) {
            descriptionImage.setImageBitmap(bitmap);
        }
    }

//    public void setTitleText(String text) {
//        title.setText(text);
//    }
    public void setDescription(String text) {
        description.setText(text);
    }
    public void setOrderText(String text) {
        orderText.setText(text);
    }
}