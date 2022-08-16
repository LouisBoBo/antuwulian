package com.slxk.gpsantu.mvp.utils;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;

import java.io.IOException;

public class MediaManager {


    private static final String TAG = "MediaManager";
    private static MediaPlayer mPlayer;
    private static boolean isPause;
    //以下开始传感器监听
    static SensorManager mSensorManager;
    static Sensor sensor;
    private static Context mContext;

    //声音管理器
    static AudioManager mAudioManager;

    public static void playSound(String filePathString,
                                 OnCompletionListener onCompletionListener) {
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
            //保险起见，设置报错监听
            mPlayer.setOnErrorListener(new OnErrorListener() {

                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mPlayer.reset();
                    return false;
                }
            });
        } else {
            mPlayer.reset();//就重置
        }

        try {
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnCompletionListener(onCompletionListener);
            mPlayer.setDataSource(filePathString);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IllegalArgumentException | SecurityException | IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止播放
     */
    public static void pause() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
            isPause = true;
        }
    }

    /**
     * 继续播放
     */
    public static void resume() {
        if (mPlayer != null && isPause) {
            mPlayer.start();
            isPause = false;
        }
    }

    /**
     * 停止播放
     */
    public static void release() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}
