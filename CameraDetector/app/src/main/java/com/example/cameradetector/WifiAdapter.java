package com.example.cameradetector;

import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WifiAdapter extends RecyclerView.Adapter<WifiAdapter.ViewHolder> {
    private final List<ScanResult> wifiList;

    public WifiAdapter(List<ScanResult> wifiList) {
        this.wifiList = wifiList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wifi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScanResult wifi = wifiList.get(position);
        holder.ssid.setText("SSID: " + wifi.SSID);
        holder.bssid.setText("BSSID: " + wifi.BSSID);
        holder.level.setText("Signal: " + wifi.level + " dBm");
        double distance = calculateDistance(wifi.level, wifi.frequency);
        holder.distance.setText("Distance: " + String.format("%.2f", distance) + " meters");
    }

    @Override
    public int getItemCount() {
        return wifiList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ssid, bssid, level, distance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ssid = itemView.findViewById(R.id.ssid);
            bssid = itemView.findViewById(R.id.bssid);
            level = itemView.findViewById(R.id.level);
            distance = itemView.findViewById(R.id.distance);
        }
    }

    // Tính toán khoảng cách từ RSSI và tần số
    private double calculateDistance(int rssi, int frequency) {
        return Math.pow(10.0, (27.55 - (20 * Math.log10(frequency)) + Math.abs(rssi)) / 20.0);
    }
}
