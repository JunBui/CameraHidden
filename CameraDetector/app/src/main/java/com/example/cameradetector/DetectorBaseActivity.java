package com.example.cameradetector;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraSelector;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.cameradetector.R;
import com.example.cameradetector.SpeedometerView;
import com.example.cameradetector.MySoundManager;
import com.example.cameradetector.MyVibrateManager;
import com.google.common.util.concurrent.ListenableFuture;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class DetectorBaseActivity extends BaseActivityWithToolBar implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor magneticSensor;
    private boolean isSensorRegistered = false;
    protected ImageView magneticStrengthBg;
    private TextView magneticStrength;
    private ImageButton tipsButton;
    private TextView xPostextView;
    private TextView yPostextView;
    private TextView zPostextView;
    private GraphView graphView;
    private MySoundManager mySoundManager;
    protected ImageButton flashButton;
    protected ImageButton soundButton;
    protected ImageButton vibrateButton;
    protected ImageButton cameraButton;
    private static boolean vibrateEnable = true;
    private static boolean soundEnable = true;
    private static boolean flashEnable = false;
    private static boolean cameraEnable = false;
    private List<DataPoint> dataPoints = new ArrayList<>();
    private Handler handler = new Handler(Looper.getMainLooper());
    private boolean isFunctionCalled = false;
    private CameraManager cameraManager;
    private String cameraId;
//    private ImageView needle;
    private SpeedometerView speedometerView;
    private float maxSpeed = 300f;
    private PreviewView previewView;
    private Camera camera;
    private CameraControl cameraControl;
    private View cardViewInfo;
    private View graphViewBg;
    protected CardView btnStartDetect;
    protected View btnStopDetect;
    private boolean isDetecting = false;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {
        super.onCreateActivity(savedInstanceState);
        InitUi();
        InitSound();
        InitSensor();
        InitFlash();
        startCamera();
        modifyUi();
    }
    protected void modifyUi()
    {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_metal_detector;
    }

    private void InitUi()
    {
        cardViewInfo = findViewById(R.id.cardViewInfo);
        graphViewBg = findViewById(R.id.graphViewBg);
        btnStartDetect = findViewById(R.id.btnStartDetect);
        btnStopDetect = findViewById(R.id.btnStopDetect);
        btnStartDetect.setOnClickListener(v -> OnClickStartDetect());
        btnStopDetect.setOnClickListener(v -> OnClickStopDetect());
        ViewScaleAnim(btnStartDetect);
        ViewScaleAnim(btnStopDetect);

        previewView = findViewById(R.id.previewView);
        previewView.setVisibility(INVISIBLE);

        magneticStrength = findViewById(R.id.magneticStrength);
        xPostextView = findViewById(R.id.xPosText);
        yPostextView = findViewById(R.id.yPosText);
        zPostextView = findViewById(R.id.zPosText);
        speedometerView = findViewById(R.id.progressArc);
//        needle = findViewById(R.id.needle);
        graphView = findViewById(R.id.graphView);
        vibrateButton = findViewById(R.id.buttonVibrate);
        vibrateButton.setOnClickListener(v -> OnClickVibrateButton());
        soundButton = findViewById(R.id.buttonSound);
        soundButton.setOnClickListener(v -> OnClickSoundButton());
        flashButton = findViewById(R.id.buttonFlash);
        flashButton.setOnClickListener(v -> OnClickFlashButton());
        cameraButton = findViewById(R.id.buttonCamera);
        cameraButton.setOnClickListener(v -> OnClickCameraButton());
//        tipsButton = findViewById(R.id.tipsButton);
//        tipsButton.setOnClickListener(v -> showTips(DetectorBaseActivity.this));
//        if(TungSaveManager.getInstance().getBool(DetectorBaseActivity.this,"first_time_show_detector_tips" ,true))
//        {
//            TungSaveManager.getInstance().saveBool(DetectorBaseActivity.this,"first_time_show_detector_tips" ,false);
//            showTips(this);
//        }
        InitGraphView();
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();
                androidx.camera.core.Preview preview = new androidx.camera.core.Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                camera = cameraProvider.bindToLifecycle(
                        this, cameraSelector);
                cameraControl = camera.getCameraControl();
                cameraProvider.unbindAll(); // Ensure previous bindings are cleared
                cameraProvider.bindToLifecycle(this, cameraSelector, preview);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }
    private void showTips(Context context)
    {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_user_tips_detector);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(R.color.transparent);
        }
        View btnClose = dialog.findViewById(R.id.btnClose);

        btnClose.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }
    private void OnClickSoundButton()
    {
        if(soundEnable)
        {
            soundButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_sound_off));
            soundEnable = false;
        }
        else
        {
            soundButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_sound_on));
            soundEnable = true;
        }
    }
    private void OnClickFlashButton()
    {
        try {
            flashEnable = !flashEnable;
            flashButton.setImageDrawable(ContextCompat.getDrawable(getContext(), flashEnable?R.drawable.icon_flash_on_base:R.drawable.icon_flash_off_base));
            cameraManager.setTorchMode(cameraId, flashEnable); // Turn flashlight ON/OFF
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    private void OnClickCameraButton()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }
        else
        {
            cameraEnable = !cameraEnable;
            cameraButton.setImageDrawable(ContextCompat.getDrawable(getContext(), cameraEnable?R.drawable.icon_camera:R.drawable.icon_camera_off));
//            cameraManager.setTorchMode(cameraId, flashEnable);
            previewView.setVisibility(cameraEnable?VISIBLE: INVISIBLE);
            graphViewBg.setVisibility(cameraEnable? INVISIBLE:VISIBLE);
            cardViewInfo.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, cameraEnable?R.color.transparent:R.color.white)));
        }
    }
    private void HandleGraph(float current)
    {
        current = Math.max(0,Math.min(current, 600));
        graphView.removeAllSeries();
        if(dataPoints.size()>35)
        {
            dataPoints.remove(dataPoints.size() - 1);
        }
        for (int i = 0; i<dataPoints.size();i++)
        {
            DataPoint newPoint = new DataPoint(dataPoints.get(i).getX()+1, dataPoints.get(i).getY());
            dataPoints.set(i,newPoint);
        }
        dataPoints.add(0,new DataPoint(0,current));
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints.toArray(new DataPoint[0]));
        series.setColor(Color.MAGENTA);
        graphView.addSeries(series);
    }

    private void OnClickStartDetect()
    {
        isDetecting = true;
        refreshDetectButton();
    }
    private void OnClickStopDetect()
    {
        isDetecting = false;
        refreshDetectButton();
    }
    private void refreshDetectButton()
    {
        btnStartDetect.setVisibility(!isDetecting?VISIBLE:INVISIBLE);
        btnStopDetect.setVisibility(isDetecting?VISIBLE:INVISIBLE);
    }
    private void OnClickVibrateButton()
    {
        if(vibrateEnable)
        {
            vibrateButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_vibrate_off));
            vibrateEnable = false;
        }
        else
        {
            vibrateButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_vibrate_on));
            vibrateEnable = true;
        }
    }
    private void InitGraphView()
    {
// Set manual X bounds
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(35);
        graphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);

// Set manual Y bounds
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(600);
    }
    private void InitSound() {
        mySoundManager = new MySoundManager(getContext());
        mySoundManager.loadSound(getContext(),R.raw.metal_detector_weak,"metal_detector_weak");
    }

    private void InitSensor() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        // Registering the listener for the sensor
        if (magneticSensor != null) {
            sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }
    private void InitFlash() {
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = cameraManager.getCameraIdList()[0]; // Get back camera ID
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    float magneticFieldStrength;
    private final float smoothingFactor = 0.2f;
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD && isDetecting) {
            float newMagneticFieldStrength = calculateMagneticStrength(event.values);
            magneticFieldStrength = (1 - smoothingFactor) * magneticFieldStrength + smoothingFactor * newMagneticFieldStrength;

            xPostextView.setText(String.format("%.0f", event.values[0]));
            yPostextView.setText(String.format("%.0f", event.values[1]));
            zPostextView.setText(String.format("%.0f", event.values[2]));

            if (!isFunctionCalled) {
                // Delay the function call by 500ms (example)
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Call your function here
                        HandleGraph(magneticFieldStrength);

                        if (magneticFieldStrength > 50) {
                            if(vibrateEnable)
                                MyVibrateManager.Vibrate(getContext());
                        }
                        if(soundEnable)
                            mySoundManager.PlayMetalDetectorSound("metal_detector_weak",magneticFieldStrength);
                        isFunctionCalled = false;
                    }
                }, 300);  // 500 milliseconds delay
                isFunctionCalled = true;
            }
        }
        else
        {
            magneticFieldStrength *= 0.8f;
            if (magneticFieldStrength < 0.07f) {
                magneticFieldStrength = 0; // Stop when very close to zero
            }
        }
        updateSpeed(magneticFieldStrength);
        magneticStrength.setText(String.format("%.2f", magneticFieldStrength) + " uT");
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Handle accuracy changes if needed
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isSensorRegistered) {
            sensorManager.unregisterListener(this);
            isSensorRegistered = false;
        }
    }
    protected void onResume() {
        super.onResume();
        if (!isSensorRegistered && magneticSensor != null) {
            sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_UI);
            isSensorRegistered = true;
        }
    }

    // Function to calculate magnetic strength (simple sum of x, y, z components)
    private float calculateMagneticStrength(float[] values) {
        return (float) Math.sqrt(Math.pow(values[0], 2) + Math.pow(values[1], 2) + Math.pow(values[2], 2));
    }
    public void updateSpeed(float speed) {
        speed = Math.min(speed,maxSpeed);
        float angle = ((speed / maxSpeed) * 300) - 120;
//        needle.setRotation(angle-240);
        speedometerView.setSpeed(speed);
    }
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 2;
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Handle the result of the permission request
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                OnClickCameraButton();
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show();
            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                    String cameraString = getString(R.string.camera);
                    showSettingsDialog(cameraString);
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private void showSettingsDialog(String require) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_permission_settings);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
        Button btnOpenSettings = dialog.findViewById(R.id.btn_open_settings);
        btnOpenSettings.setText(getString(R.string.enable) + " "+require+ " " + getString(R.string.permission));
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);

        TextView title = dialog.findViewById(R.id.title);
        title.setText(require + " " + getString(R.string.permission_required));

        btnOpenSettings.setOnClickListener(v -> {
            dialog.dismiss();
            openAppSettings();
        });

        btnCancel.setOnClickListener(v ->{
            dialog.dismiss();
        });
        dialog.show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isSensorRegistered) {
            sensorManager.unregisterListener(this);
            isSensorRegistered = false;
        }
    }
}
