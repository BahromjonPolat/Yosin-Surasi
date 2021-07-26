package uz.itjunior.yaseen.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import uz.itjunior.yaseen.R;

public class PlayerService extends Service {
    private static final String TAG = "PlayerService";

    private MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        player = MediaPlayer.create(this, R.raw.yasin00);
        player.setLooping(false);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int resId = intent.getIntExtra("resId", 0);

        if (player != null) {
            player.release();
        }

        player = MediaPlayer.create(this, resId);
        player.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (player != null && player.isPlaying()) {
            player.stop();
            player.reset();
            player.release();
        }
    }
}
