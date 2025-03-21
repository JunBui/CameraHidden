package com.example.cameradetector;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Handler;

import java.util.HashMap;

public class MySoundManager {
    private SoundPool soundPool;
    private HashMap<String, Integer> soundMap;
    private Handler handler;
    private boolean isPlaying = false;
    public MySoundManager(Context context) {

        // Initialize SoundPool
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(5) // Maximum simultaneous sounds
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(5, android.media.AudioManager.STREAM_MUSIC, 0);
        }

        soundMap = new HashMap<>();
        handler = new Handler();
    }
    public void loadSound(Context context, int soundResourceId, String soundId) {
        int loadedSoundId = soundPool.load(context, soundResourceId, 1);
        soundMap.put(soundId, loadedSoundId);
    }

    // Play a sound by its ID
//    public void playSound(int soundId) {
//        if (isLoaded && soundMap.containsKey(soundId)) {
//            soundPool.play(soundMap.get(soundId), 1.0f, 1.0f, 0, 0, 1.0f); // Volume (L, R), priority, loop, rate
//        }
//    }
    public void PlayMetalDetectorSound(String soundId, float value)
    {
        int interval = 0;
        if(value>70 && value<150)
        {
            interval = 800;
        }
        else if (value >= 150 && value<250) {
            interval = 400;
        }
        else if (value>=250)
        {
            interval = 200;
        }
        else
        {
            return;
        }
        playSoundAtIntervals(soundId,interval);
    }
    public void playSoundAtIntervals(String soundId, int interval) {


        if (!isPlaying) {
            // Delay the function call by 500ms (example)
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    soundPool.play(soundMap.get(soundId), 1.0f, 1.0f, 0, 0, 1.0f);
                    isPlaying = false;
                }
            }, interval);  // 500 milliseconds delay
            isPlaying = true;
        }
    }
}
