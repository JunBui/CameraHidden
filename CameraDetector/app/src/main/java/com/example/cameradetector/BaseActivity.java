package com.example.cameradetector;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cameradetector.R;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        onCreateActivity(savedInstanceState);
        initUiOnCreate();
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    protected abstract void onCreateActivity(Bundle savedInstanceState);
    protected void initUiOnCreate()
    {

    }
    protected int getLayoutId() {
        return R.layout.activity_main; // Default layout, override in child activities
    }
    protected void ViewScaleAnim(View view)
    {
        view.setOnTouchListener((v, event) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(50).start();
                    break;
                case MotionEvent.ACTION_DOWN:
                    v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(50).start();
                    break;
                case MotionEvent.ACTION_CANCEL:
                    v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(50).start();
                    break;
            }
            return false;
        });
    }
    protected Context getContext()
    {
        return this;
    }
}
