package com.zht.measureheartrate.util;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.util.Log;
import android.util.Size;

import com.zht.measureheartrate.BuildConfig;

import java.util.Arrays;
import java.util.List;

/**
 * @Date 2023/2/21 13:45
 * @Author zhanghaitao
 * @Description  CameraXHelper
 */
public class CameraUtil {

    private final static String TAG = "CameraUtil";

    public static List<Size> getCameraSize(Context context) {
        try {
            CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
            if (cameraManager == null) {
                return null;
            }
            CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(String.valueOf(0));
            if (cameraCharacteristics == null) {
                return null;
            }
            StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            Size[] outputSizes = streamConfigurationMap != null ? streamConfigurationMap.getOutputSizes(SurfaceTexture.class) : null;
            if (outputSizes == null || outputSizes.length == 0) {
                return null;
            }
            if (BuildConfig.DEBUG) {
                for (Size outputSize : outputSizes) {
                    Log.e(TAG, String.format("Size(%d,%d)", outputSize.getWidth(), outputSize.getHeight()));
                }
            }

            return Arrays.asList(outputSizes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Size getCameraMinSize(Context context) {
        List<Size> cameraSize = getCameraSize(context);
        if (cameraSize == null || cameraSize.size() == 0) {
            return null;
        }
        Size size = cameraSize.get(0);
        int index = 1;
        while (index < cameraSize.size()) {
            Size compareSize = cameraSize.get(index);
            if ((size.getWidth() * size.getHeight()) > (compareSize.getWidth() * compareSize.getHeight())) {
                size = compareSize;
            }
            index++;
        }
        return size;
    }

    public static Size getCameraMaxSize(Context context) {
        List<Size> cameraSize = getCameraSize(context);
        if (cameraSize == null || cameraSize.size() == 0) {
            return null;
        }
        Size size = cameraSize.get(0);
        int index = 1;
        while (index < cameraSize.size()) {
            Size compareSize = cameraSize.get(index);
            if ((size.getWidth() * size.getHeight()) < (compareSize.getWidth() * compareSize.getHeight())) {
                size = compareSize;
            }
            index++;
        }
        return size;
    }




}
