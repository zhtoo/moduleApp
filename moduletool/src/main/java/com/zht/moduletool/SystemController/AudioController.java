package com.zht.moduletool.SystemController;

import android.content.Context;
import android.media.AudioManager;

/**
 * Created by ZhangHaitao on 2019/3/26
 */
public class AudioController {

    public static void turnDown(Context context, float yDilta, int heightPixels) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        //当前音量
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        //获取最大音量
        int streamMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //计算音量的改变量
        float changeVolume = yDilta / heightPixels * streamMaxVolume;
        //改变后的音量
        int changeStreamVolume = (int) Math.max(0, currentVolume - changeVolume);
        //将改变后的音量设置给系统
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, changeStreamVolume, AudioManager.FLAG_SHOW_UI);
    }

    public static void turnUp(Context context, float yDilta, int heightPixels) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        //当前音量
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        //获取最大音量
        int streamMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //计算音量的改变量
        float changeVolume = yDilta / heightPixels * streamMaxVolume;
        //改变后的音量
        int changeStreamVolume = (int) Math.min(streamMaxVolume, currentVolume - changeVolume);
        //将改变后的音量设置给系统
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, changeStreamVolume, AudioManager.FLAG_SHOW_UI);
    }
}