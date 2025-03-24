package com.example.cameradetector;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.cameradetector.R;

public class ActivitySetting extends BaseActivityWithToolBar {
    private View btnLanguage;
    private View btnPolicy;
    private View btnRate;
//    private TextView versionTxt;
    @Override
    protected void initUiOnCreate() {
        super.initUiOnCreate();
        btnLanguage = findViewById(R.id.languageBtn);
        btnLanguage.setOnClickListener(v -> onClickLanguageBtn());

        btnPolicy = findViewById(R.id.policyBtn);
        btnPolicy.setOnClickListener(v -> onClickPolicyBtn());

        btnRate = findViewById(R.id.rateAppBtn);
        btnRate.setOnClickListener(v -> onClickRateBtn());

//        versionTxt = findViewById(R.id.versionTxt);
    }

    private void onClickLanguageBtn() {
        Log.i("language","language");
    }
    private void onClickPolicyBtn() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
        startActivity(intent);
    }
    private void onClickRateBtn() {
        CustomRateAppBottomSheetDialog.createRateDialog(getSupportFragmentManager());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }
    @Override
    protected void setToolBarTitle() {
        super.setToolBarTitle();
        titleTxt.setText(R.string.setting);
    }
}