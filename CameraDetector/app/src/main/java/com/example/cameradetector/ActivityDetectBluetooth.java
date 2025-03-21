package com.example.cameradetector;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActivityDetectBluetooth extends BaseActivityWithToolBar {
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int PERMISSION_REQUEST_CODE_LOCATION = 101;
    private static final int REQUEST_ENABLE_BT = 101;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private ArrayList<String> deviceList;
    private BluetoothDeviceAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detect_bluetooth;
    }

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {
        super.onCreateActivity(savedInstanceState);

        recyclerView = findViewById(R.id.device_list);
        deviceList = new ArrayList<>();
        adapter = new BluetoothDeviceAdapter(deviceList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth not supported", Toast.LENGTH_SHORT).show();
            return;
        }
        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
        checkAndRequestPermissions();
    }
    private void checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Android 12 trở lên (API 31+)
            if (checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.BLUETOOTH_SCAN,
                        Manifest.permission.BLUETOOTH_CONNECT
                }, PERMISSION_REQUEST_CODE);
                return;
            }
        } else {
            // Android 11 trở xuống (API < 31)
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE_LOCATION);
                return;
            }
        }

        startBluetoothScan();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean allGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }
            if (allGranted) {
                startBluetoothScan();
            } else {
                if (isPermissionPermanentlyDenied()) {
                    showSettingsDialog();
                } else {
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void startBluetoothScan() {
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            return;
        }

        deviceList.clear();
        adapter.notifyDataSetChanged();

        Toast.makeText(this, "Scanning for Bluetooth devices...", Toast.LENGTH_SHORT).show();
        bluetoothLeScanner.startScan(scanCallback);
    }
    private final ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BluetoothDevice device = result.getDevice();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                // Android 12 trở lên - Sử dụng BLUETOOTH_CONNECT
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    Log.i("Permission","bluetooth");
                    return;
                }
            } else {
                // Android 11 trở xuống - Sử dụng ACCESS_FINE_LOCATION
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
            if (device != null && device.getName() != null) {
                String deviceInfo = device.getName() + " - " + device.getAddress();
                if (!deviceList.contains(deviceInfo)) {
                    deviceList.add(deviceInfo);
                    Log.i("Check device list","Check device list:" + deviceList.size());
                    adapter.notifyDataSetChanged();
                }
            }
        }
    };
    private boolean isPermissionPermanentlyDenied() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return !shouldShowRequestPermissionRationale(Manifest.permission.BLUETOOTH_SCAN) &&
                    !shouldShowRequestPermissionRationale(Manifest.permission.BLUETOOTH_CONNECT);
        } else {
            return !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    private void showSettingsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Permission Required")
                .setMessage("This app needs Bluetooth permissions. Please grant them in Settings.")
                .setPositiveButton("Open Settings", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}