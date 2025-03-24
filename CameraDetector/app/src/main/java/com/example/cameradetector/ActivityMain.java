package com.example.cameradetector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActivityMain extends BaseActivity {
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
        Intent intent = new Intent(ActivityMain.this, ActivityInfraredCamera.class);
        startActivity(intent);
    }
    private void onClickBtnWifi()
    {
        Intent intent = new Intent(ActivityMain.this, ActivityDetectWifi.class);
        startActivity(intent);
    }
    private void onClickBtnBluetooth()
    {
        Intent intent = new Intent(ActivityMain.this, ActivityDetectBluetooth.class);
        startActivity(intent);
    }
    private void onClickBtnMagnetic()
    {
        Intent intent = new Intent(ActivityMain.this, DetectorBaseActivity.class);
        startActivity(intent);
    }
    private void onClickBtnSetting()
    {
        Intent intent = new Intent(this, ActivitySetting.class);
        startActivity(intent);
    }
    private void onClickBtnTips()
    {
        Intent intent = new Intent(this, ActivityTipsTricks.class);
        startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}