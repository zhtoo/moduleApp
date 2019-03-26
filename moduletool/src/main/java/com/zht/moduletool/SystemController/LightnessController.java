package com.zht.moduletool.SystemController;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.view.WindowManager;

/**
 * Created by ZhangHaitao on 2019/3/26
 */
public class LightnessController {

    public static void turnDown(Context context, float yDilta, int heightPixels) {
        //获取系统当前的亮度
        try {
            int current_brightness = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);

            //计算亮度的改变值
            int brightnessDelta = (int) (yDilta / heightPixels * 255);
            //亮度最下,不能小于25
            int changedBrightness = Math.max(25, current_brightness - brightnessDelta);
            //修改屏幕的亮度
            WindowManager.LayoutParams attributes = ((Activity) context).getWindow().getAttributes();//窗体的属性集合

            attributes.screenBrightness = changedBrightness * 1.0f / 255;
            ((Activity) context).getWindow().setAttributes(attributes);
            //当前的亮度设置给系统
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, changedBrightness);
            //获取SeekBar
//            SeekBar bright_display = ((VideoActivity) context).getBright_display();
//            if (bright_display.getMax() != 255) {
//                bright_display.setMax(255);
//            }
//            bright_display.setVisibility(View.VISIBLE);
//            bright_display.setProgress(changedBrightness);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void turnUp(Context context, float yDilta, int heightPixels) {
        //获取系统当前的亮度
        try {
            int current_brightness = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);

            //计算亮度的改变值
            int brightnessDelta = (int) (yDilta / heightPixels * 255);
            //亮度最下,不能小于25
            int changedBrightness = Math.min(255, current_brightness - brightnessDelta);
            //修改屏幕的亮度
            WindowManager.LayoutParams attributes = ((Activity) context).getWindow().getAttributes();//窗体的属性集合

            attributes.screenBrightness = changedBrightness * 1.0f / 255;
            ((Activity) context).getWindow().setAttributes(attributes);
            //当前的亮度设置给系统
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, changedBrightness);

            //获取SeekBar
//            SeekBar bright_display = ((VideoActivity) context).getBright_display();
//            if (bright_display.getMax() != 255) {
//                bright_display.setMax(255);
//            }
//            bright_display.setVisibility(View.VISIBLE);
//            bright_display.setProgress(changedBrightness);

        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }

}
