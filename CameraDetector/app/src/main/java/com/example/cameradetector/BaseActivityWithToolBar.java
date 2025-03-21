package com.example.cameradetector;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cameradetector.R;

public class BaseActivityWithToolBar extends BaseActivity {
    private ImageButton backButton;
    TextView titleTxt;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {

    }

    @Override
    protected void initUiOnCreate() {
        super.initUiOnCreate();
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> OnClickBackButton());
        setToolBarTitle();
    }

    protected void setToolBarTitle()
    {
        titleTxt = findViewById(R.id.titleToolBar);
    }
    protected void OnClickBackButton() {
        finish();
    }
    protected void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
