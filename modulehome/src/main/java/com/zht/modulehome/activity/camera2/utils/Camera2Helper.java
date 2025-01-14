package com.zht.modulehome.activity.camera2.utils;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.util.Log;
import android.util.Size;

import java.util.Arrays;
import java.util.List;

/**
 * @Date 2024/11/26 16:49
 * @Author zhanghaitao
 * @Description
 */
public class Camera2Helper {

    private volatile static Camera2Helper instance = null;

    //    private Camera2Helper() {
//    }
//
//    public static Camera2Helper getInstance() {
//        if (instance == null) {
//            synchronized (ARouter.class) {
//                if (instance == null) {
//                    instance = new Camera2Helper();
//                }
//            }
//        }
//        return instance;
//    }
//
//    private static Context appContext;
    private static CameraManager cameraManager;
//
//
//    public static void init(Context context) {
//        if (appContext == null) {
//            appContext = context.getApplicationContext();
//            cameraManager = (CameraManager) appContext.getSystemService(Context.CAMERA_SERVICE);
//        }
//    }


    /**
     * 根据输出类获取指定相机的输出尺寸列表
     *
     * @param cameraId 相机id
     * @param clz      输出类
     * @return
     */
    public List<Size> getCameraOutputSizes(String cameraId, Class clz) {
        try {
            CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap configs = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            return Arrays.asList(configs.getOutputSizes(clz));
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据输出格式获取指定相机的输出尺寸列表
     *
     * @param cameraId 相机id
     * @param format   输出格式
     * @return
     */
    public List<Size> getCameraOutputSizes(String cameraId, int format) {
        try {
            CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap configs = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            return Arrays.asList(configs.getOutputSizes(format));
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 释放相机资源
     *
     * @param cameraDevice
     */
    public void releaseCamera(CameraDevice cameraDevice) {
        if (cameraDevice != null) {
            cameraDevice.close();
            cameraDevice = null;
        }
    }


    /**
     * 返回相近的相机预览尺寸
     */
    public static Size findSimilarSize(CameraCharacteristics characteristics, float previewAspectRatio, Class klass) {
        Size similarSize = null;
        try {
            StreamConfigurationMap configs = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            Size[] outputSizes = configs.getOutputSizes(klass);

            if(outputSizes == null) return similarSize;
            for (Size outputSize : outputSizes) {
                Log.e("123",String.format("Size(%d,%d)", outputSize.getWidth(), outputSize.getHeight()));
                if (similarSize == null) {
                    similarSize = outputSize;
                } else {
                    float aspectRatio = getSizeAspectRatio(outputSize);
                    float similarAspectRatio = getSizeAspectRatio(similarSize);
                    if (Math.abs(aspectRatio - previewAspectRatio) < Math.abs(similarAspectRatio - previewAspectRatio)) {
                        similarSize = outputSize;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return similarSize;
    }


    private static float getSizeAspectRatio(Size size) {
        if (size.getHeight() == 0 || size.getWidth() == 0) {
            return -1;
        }
        return (float) size.getWidth() / (float) size.getHeight();
    }


}
