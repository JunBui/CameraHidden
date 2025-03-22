package com.example.cameradetector;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Executors;

public class ActivityInfraredCamera extends BaseActivityWithToolBar {
    private static final int CAMERA_PERMISSION_CODE = 1;
    private PreviewView previewView;
    private ImageView filter;
    private View redFilterBtn;
    private View greenFilterBtn;
    private View blueFilterBtn;
    private View greyFilterBtn;
    private View lensDetectorBtn;
    private View infraredCameraBtn;
    private View infraredLayout;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {
        super.onCreateActivity(savedInstanceState);
    }

    @Override
    protected void initUiOnCreate() {
        super.initUiOnCreate();
        previewView = findViewById(R.id.cameraView);
        filter = findViewById(R.id.filter);
        infraredLayout = findViewById(R.id.infraredLayout);

        redFilterBtn = findViewById(R.id.redFilter);
        redFilterBtn.setOnClickListener(v->onClickRedFilter());
        ViewScaleAnim(redFilterBtn);

        greenFilterBtn = findViewById(R.id.greenFilter);
        greenFilterBtn.setOnClickListener(v->onClickGreenFilter());
        ViewScaleAnim(greenFilterBtn);

        blueFilterBtn = findViewById(R.id.blueFilter);
        blueFilterBtn.setOnClickListener(v->onClickBlueFilter());
        ViewScaleAnim(blueFilterBtn);

        greyFilterBtn = findViewById(R.id.grayFilter);
        greyFilterBtn.setOnClickListener(v->onClickGreyFilter());
        ViewScaleAnim(greyFilterBtn);

        lensDetectorBtn = findViewById(R.id.lensDetectorBtn);
        lensDetectorBtn.setOnClickListener(v->onClickLensDetectorBtn());
        ViewScaleAnim(lensDetectorBtn);

        infraredCameraBtn = findViewById(R.id.infraredCameraBtn);
        infraredCameraBtn.setOnClickListener(v->onClickInfraredCameraBtn());
        ViewScaleAnim(infraredCameraBtn);
        filter.setImageResource(R.color.transparent);
        requestCameraPermission();
        infraredLayout.setVisibility(INVISIBLE);
    }
    private void onClickInfraredCameraBtn()
    {
        infraredLayout.setVisibility(VISIBLE);
        filter.setImageResource(R.color.red_trans);
    }
    private void onClickLensDetectorBtn()
    {
        infraredLayout.setVisibility(INVISIBLE);
        filter.setImageResource(R.color.transparent);
    }
    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_CODE);
        } else {
            startCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(this, "Camera permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void onClickRedFilter()
    {
        filter.setImageResource(R.color.red_trans);
    }
    private void onClickGreenFilter()
    {
        filter.setImageResource(R.color.green_trans);
    }
    private void onClickBlueFilter()
    {
        filter.setImageResource(R.color.blue_trans);
    }
    private void onClickGreyFilter()
    {
        filter.setImageResource(R.color.grey_trans);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_infrared_camera;
    }

    @Override
    protected void setToolBarTitle() {
        super.setToolBarTitle();
        titleTxt.setText("Infrared Camera");
    }
    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, cameraSelector, preview);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }
}