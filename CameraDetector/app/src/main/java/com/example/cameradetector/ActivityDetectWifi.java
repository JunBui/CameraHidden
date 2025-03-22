package com.example.cameradetector;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ActivityDetectWifi extends BaseActivityWithToolBar {
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private static final String TAG = "WiFiScanner";
    private WifiManager wifiManager;
    private List<ScanResult> wifiList = new ArrayList<>();
    private WifiAdapter adapter;
    RecyclerView recyclerView;
    public View scanBtnLayout;
    public View scanRadarLayout;
    public View scanBtn;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_detect_wifi;
    }
    @Override
    protected void initUiOnCreate() {
        super.initUiOnCreate();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WifiAdapter(wifiList);
        recyclerView.setAdapter(adapter);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        scanBtnLayout = findViewById(R.id.scanBtnLayout);
        scanRadarLayout = findViewById(R.id.scanRadarLayout);
        scanBtn = findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(v->onClickScanBtn());
    }
    private void onClickScanBtn()
    {
        scanRadarLayout.setVisibility(VISIBLE);
        requestPermissions();
    }
    @Override
    protected void setToolBarTitle() {
        super.setToolBarTitle();
        titleTxt.setText(R.string.wifi_scanner);
    }
    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_CODE);
            } else {
                startWifiScan();
            }
        } else {
            startWifiScan();
        }
    }
    private void startWifiScan() {
        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(this, "Enabling WiFi...", Toast.LENGTH_SHORT).show();
            wifiManager.setWifiEnabled(true);
        }

        // Kiểm tra quyền trước khi quét WiFi
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_CODE);
            return;
        }

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
                if (success) {
                    scanSuccess();  // Gọi hàm này để cập nhật danh sách WiFi
                } else {
                    scanFailure();
                }
            }
        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        try {
            boolean success = wifiManager.startScan();
            if (!success) {
                scanFailure();
            }
        } catch (SecurityException e) {
            Log.e("WiFiScanner", "SecurityException: Missing permissions!", e);
            Toast.makeText(this, "Permission denied! Cannot scan WiFi.", Toast.LENGTH_SHORT).show();
        }
    }
    private void scanSuccess() {
        wifiList.clear();  // Xóa danh sách cũ
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        wifiList.addAll(wifiManager.getScanResults());  // Thêm danh sách mới
        adapter.notifyDataSetChanged();  // Cập nhật RecyclerView
        scanBtnLayout.setVisibility(INVISIBLE);
        Log.d(TAG, "WiFi scan successful: " + wifiList.size() + " networks found.");
    }
    private void scanFailure() {
        Toast.makeText(this, "WiFi scan failed!", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "WiFi scan failed.");
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startWifiScan(); // Gọi lại hàm quét WiFi khi được cấp quyền
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}