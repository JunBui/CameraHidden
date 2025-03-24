package com.example.cameradetector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends BaseActivity {
    private View btnFindCamera;
    private View btnWifi;
    private View btnBluetooth;
    private View btnMagnetic;
    private View btnTips;
    private View btnSetting;
    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {

        btnFindCamera = findViewById(R.id.btnFindCamera);
        btnFindCamera.setOnClickListener(v -> onClickBtnFindCamera());
        ViewScaleAnim(btnFindCamera);

        btnWifi = findViewById(R.id.btnWifi);
        btnWifi.setOnClickListener(v -> onClickBtnWifi());
        ViewScaleAnim(btnWifi);

        btnBluetooth = findViewById(R.id.btnBluetooth);
        btnBluetooth.setOnClickListener(v -> onClickBtnBluetooth());
        ViewScaleAnim(btnBluetooth);

        btnMagnetic = findViewById(R.id.btnMagnetic);
        btnMagnetic.setOnClickListener(v -> onClickBtnMagnetic());
        ViewScaleAnim(btnMagnetic);

        btnTips = findViewById(R.id.btnTips);
        btnTips.setOnClickListener(v -> onClickBtnTips());
        ViewScaleAnim(btnTips);

        btnSetting = findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(v -> onClickBtnSetting());
        ViewScaleAnim(btnSetting);
    }
    private void onClickBtnFindCamera()
    {
        Intent intent = new Intent(MainActivity.this, ActivityInfraredCamera.class);
        startActivity(intent);
    }
    private void onClickBtnWifi()
    {
        Intent intent = new Intent(MainActivity.this, ActivityDetectWifi.class);
        startActivity(intent);
    }
    private void onClickBtnBluetooth()
    {
        Intent intent = new Intent(MainActivity.this, ActivityDetectBluetooth.class);
        startActivity(intent);
    }
    private void onClickBtnMagnetic()
    {
        Intent intent = new Intent(MainActivity.this, DetectorBaseActivity.class);
        startActivity(intent);
    }
    private void onClickBtnSetting()
    {
        Intent intent = new Intent(this, ActivitySetting.class);
        startActivity(intent);
    }
    private void onClickBtnTips()
    {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}