package com.example.cameradetector;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

public class MyVibrateManager {

    public static void Vibrate(Context context)
    {
        VibrateOneShot(context,250,200);
//        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//        // Check if vibrator is not null and device supports vibration
//        if (vibrator != null) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                // Use VibrationEffect for devices with API 26+
//                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE)); // Vibrate for 500ms
//            } else {
//                // Use legacy vibration method for devices below API 26
//                vibrator.vibrate(100); // Vibrate for 500ms
//            }
//        }
    }
    public static void VibrateOneShot(Context context, long duration, int amplitude) {
        // Get the Vibrator service
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        // Check if the device supports vibration
        if (vibrator != null && vibrator.hasVibrator()) {
            // Use VibrationEffect on API 26+ devices
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // VibrationEffect.DEFAULT_AMPLITUDE means using device's default intensity level
                long[] pattern = {0, duration, 0, 0};  // Wait 0ms, vibrate 500ms, pause 300ms, vibrate 500ms// Define amplitude for each corresponding timing (0 = no vibration, 255 = maximum vibration intensity)
                int[] unsafeAmplitudes = {0, amplitude, 0, 0};  // Silent, Medium (128), Silent, Max intensity (255)
                // Ensure all amplitudes are safely clamped within valid range
                int[] safeAmplitudes = new int[unsafeAmplitudes.length];
                for (int i = 0; i < unsafeAmplitudes.length; i++) {
                    safeAmplitudes[i] = clampAmplitude(unsafeAmplitudes[i]);
                }

                VibrationEffect effect = VibrationEffect.createWaveform(pattern, safeAmplitudes,-1); // '-1' means no repetition
                vibrator.vibrate(effect);
                Log.d("VibrationUtil", "Vibration triggered using VibrationEffect.");
            } else {
                // For older Android versions, use the deprecated vibrate method
                vibrator.vibrate(duration);
                Log.d("VibrationUtil", "Vibration triggered using deprecated vibrate method.");
            }
        } else {
            Log.e("VibrationUtil", "No vibrator found on the device.");
        }
    }

    // Helper function to clamp amplitudes
    public static int clampAmplitude(int amplitude) {
        // Amplitude must be between 0 (no vibration) and 255 (maximum vibration)
        return Math.max(0, Math.min(255, amplitude));
    }
}
