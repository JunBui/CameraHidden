package com.example.cameradetector;

import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BluetoothDeviceAdapter extends RecyclerView.Adapter<BluetoothDeviceAdapter.ViewHolder>{
    private final List<String> deviceList;

    public BluetoothDeviceAdapter(List<String> deviceList) {
        this.deviceList = deviceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.deviceName.setText(deviceList.get(position));
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView deviceName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceName = itemView.findViewById(android.R.id.text1);
        }
    }
}
