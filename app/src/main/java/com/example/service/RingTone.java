package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;

import java.util.Timer;
import java.util.TimerTask;

public class RingTone extends Service {
    MediaPlayer mp;
    Handler handler;
    Runnable volChange;
    float Lvol = 1.0f, Rvol = 1.0f;
    boolean _switch = false;
    public RingTone() {}

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mp = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        mp.setLooping(true);
        mp.setVolume(Lvol, Rvol);

        volChange = new Runnable() {
            @Override
            public void run() {
                if(Lvol <= 0 && Rvol <= 0)
                    _switch = true;
                else if(Lvol >= 1.0f && Rvol >= 1.0f)
                    _switch = false;

                if(_switch){
                    Lvol += 0.01f;
                    Rvol += 0.01f;
                }
                else{
                    Lvol -= 0.01f;
                    Rvol -= 0.01f;
                }
                mp.setVolume(Lvol, Rvol);
                handler.postDelayed(this, 100);
            }
        };

        handler = new Handler();
        handler.postDelayed(volChange, 100);

    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mp.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mp != null && mp.isPlaying()){
            handler.removeCallbacks(volChange);
            mp.stop();
            mp.release();
        }
    }
}