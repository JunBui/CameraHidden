package com.example.cameradetector;

import android.widget.TextView;

public class ActivityTips extends BaseActivityWithToolBar {

    private TextView tips01;
    private TextView tips02;
    private TextView tips03;
    private TextView tips04;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tips;
    }

    @Override
    protected void initUiOnCreate() {
        super.initUiOnCreate();

        tips01 = findViewById(R.id.tips_01);
        tips02 = findViewById(R.id.tips_02);
        tips03 = findViewById(R.id.tips_03);
        tips04 = findViewById(R.id.tips_04);
        EnumTips myEnumValue = (EnumTips) getIntent().getSerializableExtra("Tips_Enum");

        if (myEnumValue != null) {
            // Use the enum value
            switch (myEnumValue)
            {
                case wifiScanner:
                    tips01.setText(R.string.tip_1);
                    tips02.setText(R.string.tip_2);
                    tips03.setText(R.string.tip_3);
                    tips04.setText(R.string.tip_4);
                    break;
                case bluetoothScanner:
                    tips01.setText(R.string.tip_1_bluetooth);
                    tips02.setText(R.string.tip_2_bluetooth);
                    tips03.setText(R.string.tip_3);
                    tips04.setText(R.string.tip_4);
                    break;
                case magneticScanner:
                    tips01.setText(R.string.tip_1_magnetic);
                    tips02.setText(R.string.tip_2_magnetic);
                    tips03.setText(R.string.tip_3_magnetic);
                    tips04.setText(R.string.tip_4_magnetic);
                    break;
                case lensDetection:
                    tips01.setText(R.string.tip_1_lens);
                    tips02.setText(R.string.tip_2_lens);
                    tips03.setText(R.string.tip_3_lens);
                    tips04.setText(R.string.tip_4_lens);
                    break;
                case infraredDetection:
                    tips01.setText(R.string.tip_1_infrared);
                    tips02.setText(R.string.tip_2_infrared);
                    tips03.setText(R.string.tip_3_infrared);
                    tips04.setText(R.string.tip_4_infrared);
                    break;
            }
        }
    }

    @Override
    protected void setToolBarTitle() {
        super.setToolBarTitle();
        EnumTips myEnumValue = (EnumTips) getIntent().getSerializableExtra("Tips_Enum");

        if (myEnumValue != null) {
            // Use the enum value
            switch (myEnumValue)
            {
                case wifiScanner:
                    titleTxt.setText(R.string.how_to_use_wifi_scanner);
                    break;
                case bluetoothScanner:
                    titleTxt.setText(R.string.how_to_use_bluetooth_scanner);
                    break;
                case magneticScanner:
                    titleTxt.setText(R.string.how_to_use_magnetic_scanner);
                    break;
                case lensDetection:
                    titleTxt.setText(R.string.how_to_use_lens_detection);
                    break;
                case infraredDetection:
                    titleTxt.setText(R.string.how_to_use_infrared_detection);
                    break;
            }
        }
    }
}