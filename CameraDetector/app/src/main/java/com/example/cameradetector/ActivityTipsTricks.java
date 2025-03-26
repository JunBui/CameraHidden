package com.example.cameradetector;

import android.content.Intent;
import android.view.View;

public class ActivityTipsTricks extends BaseActivityWithToolBar {

    private View btnTipWifiScanner;
    private View btnTipBluetoothScanner;
    private View btnTipMagneticScanner;
    private View btnTipLensDetector;
    private View btnTipInfraredDetector;
    private View btnBedroomTrick;
    private View btnLivingRoomTrick;
    private View btnBathroomTrick;
    private View btnChangingRoomTrick;
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
        btnTipMagneticScanner.setOnClickListener(v->goToTipsActivity(EnumTips.magneticScanner));

        btnTipLensDetector = findViewById(R.id.btnTipLensDetector);
        btnTipLensDetector.setOnClickListener(v->goToTipsActivity(EnumTips.lensDetection));

        btnTipInfraredDetector = findViewById(R.id.btnTipInfraredDetector);
        btnTipInfraredDetector.setOnClickListener(v->goToTipsActivity(EnumTips.infraredDetection));

        btnBedroomTrick = findViewById(R.id.btnBedroomTrick);
        btnBedroomTrick.setOnClickListener(v->goToTrickActivity(EnumTricks.Bedroom));

        btnLivingRoomTrick = findViewById(R.id.btnLivingRoomTrick);
        btnLivingRoomTrick.setOnClickListener(v->goToTrickActivity(EnumTricks.LivingRoom));

        btnBathroomTrick = findViewById(R.id.btnBathroomTrick);
        btnBathroomTrick.setOnClickListener(v->goToTrickActivity(EnumTricks.Bathroom));

        btnChangingRoomTrick = findViewById(R.id.btnChangingRoomTrick);
        btnChangingRoomTrick.setOnClickListener(v->goToTrickActivity(EnumTricks.ChangingRoom));
    }

    public void goToTrickActivity(EnumTricks enumTricks)
    {
        Intent intent = new Intent(this, ActivityTricks.class);
        intent.putExtra("Tricks_Enum", enumTricks); // Pass enum
        startActivity(intent);
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