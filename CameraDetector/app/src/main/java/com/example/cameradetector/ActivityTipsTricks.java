package com.example.cameradetector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityTipsTricks extends BaseActivityWithToolBar {

    private View btnTipWifiScanner;
    private View btnTipBluetoothScanner;
    private View btnTipMagneticScanner;
    private View btnTipLensDetector;
    private View btnTipInfraredDetector;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_tips_tricks;
    }

    @Override
    protected void initUiOnCreate() {
        super.initUiOnCreate();

        btnTipWifiScanner = findViewById(R.id.btnTipWifiScanner);
        btnTipWifiScanner.setOnClickListener(v->goToTipsActivity(EnumTips.wifiScanner));

        btnTipBluetoothScanner = findViewById(R.id.btnTipBluetoothScanner);
        btnTipBluetoothScanner.setOnClickListener(v->goToTipsActivity(EnumTips.bluetoothScanner));

        btnTipMagneticScanner = findViewById(R.id.btnTipMagneticScanner);
        btnTipMagneticScanner.setOnClickListener(v->goToTipsActivity(EnumTips.MagneticScanner));

        btnTipLensDetector = findViewById(R.id.btnTipLensDetector);
        btnTipLensDetector.setOnClickListener(v->goToTipsActivity(EnumTips.LensDetection));

        btnTipInfraredDetector = findViewById(R.id.btnTipInfraredDetector);
        btnTipInfraredDetector.setOnClickListener(v->goToTipsActivity(EnumTips.InfraredDetection));
    }

    public void goToTipsActivity(EnumTips enumTips)
    {
        Intent intent = new Intent(this, ActivityTips.class);
        intent.putExtra("Tips_Enum", enumTips); // Pass enum
        startActivity(intent);
    }
    @Override
    protected void setToolBarTitle() {
        super.setToolBarTitle();
        titleTxt.setText(R.string.tips_and_trick);
    }
}