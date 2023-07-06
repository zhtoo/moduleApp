package com.zht.measureheartrate;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Size;
import android.view.Surface;

import androidx.annotation.NonNull;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.TorchState;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;
import com.zht.measureheartrate.util.HeartRateProcessing;
import com.zht.measureheartrate.util.ImageProcessing;

import java.util.concurrent.ExecutionException;

/**
 * @Date 2023/2/21 15:33
 * @Author zhanghaitao
 * @Description
 */
public class CameraXHelper {

    private final static String TAG = "CameraXHelper";

    private ProcessCameraProvider mCameraProvider;
    private Camera mCamera;
    private HeartRateProcessing heartRateProcessing;
    private ImageAnalysis mImageAnalyzer;

    private Size previewSize;

    private CameraXHelper() {
    }

    private static CameraXHelper instance;

    public static CameraXHelper getInstance() {
        synchronized (CameraXHelper.class) {
            if (instance == null) {
                instance = new CameraXHelper();
            }
            return instance;
        }
    }

    public void setProcessing(HeartRateProcessing rateProcessing) {
        this.heartRateProcessing = rateProcessing;
    }
    public void setPreviewSize(Size previewSize) {
        this.previewSize = previewSize;
    }

    public void startCamera(LifecycleOwner lifecycleOwner, Context context, PreviewView previewView) {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(context);
        cameraProviderFuture.addListener(() -> {
            try {
                mCameraProvider = cameraProviderFuture.get();
                // Preview 预览参数配置
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                ImageAnalysis.Builder imageAnalyzerBuilder = new ImageAnalysis.Builder();
                imageAnalyzerBuilder.setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_YUV_420_888);
                imageAnalyzerBuilder.setTargetRotation(Surface.ROTATION_0);
                if (previewSize != null) {
                    imageAnalyzerBuilder.setTargetResolution(previewSize);
                }
                mImageAnalyzer = imageAnalyzerBuilder.build();
                startAnalyzer(context);

                // 在绑定前进行解绑
                mCameraProvider.unbindAll();
                // 绑定生命周期和UseCase
                mCamera = mCameraProvider.bindToLifecycle(lifecycleOwner,
                        CameraSelector.DEFAULT_BACK_CAMERA, mImageAnalyzer, preview);
                openFlashlight(mCamera, true);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(context));
    }

    public void stopCamera() {
        if (mImageAnalyzer != null) {
            mImageAnalyzer.clearAnalyzer();
        }
        if (mCameraProvider != null) {
            mCameraProvider.unbindAll();
        }
        mCameraProvider = null;
        mCamera = null;

        mImageAnalyzer = null;
        previewSize = null;
    }

    public void release() {
        stopCamera();
        if(heartRateProcessing !=null){
            heartRateProcessing.release();
        }
        heartRateProcessing = null;
    }

    public void startAnalyzer(Context context) {
        if (mImageAnalyzer == null) {
            return;
        }
        mImageAnalyzer.setAnalyzer(ContextCompat.getMainExecutor(context), new ImageAnalysis.Analyzer() {
            @Override
            public void analyze(@NonNull ImageProxy image) {
                // 处理数据
                Bitmap bitmap = ImageProcessing.createBitmapFromImageProxy(image);
                if (bitmap != null) {
                    int height = bitmap.getHeight();
                    int width = bitmap.getWidth();
                    int[] pixelArr = new int[width * height];
                    bitmap.getPixels(pixelArr, 0, width, 0, 0, width, height);
                    long sum = 0;
                    for (int pixelColor : pixelArr) {
                        float redColor = ((pixelColor >> 16) & 0xff);//提取颜色中的红色
                        sum += redColor;
                    }
                    double average = (double) sum / pixelArr.length;
                    if (heartRateProcessing != null) {
                        heartRateProcessing.inputData(average);
                    }
                }
                image.close();
            }
        });
    }

    public void stopAnalyzer() {
        if (mImageAnalyzer != null) {
            mImageAnalyzer.clearAnalyzer();
        }
    }

    public void openFlashlight(boolean open) {
        if (mCamera == null) {
            return;
        }
        openFlashlight(mCamera, open);
    }

    private void openFlashlight(Camera camera, boolean open) {
        if (camera == null) {
            return;
        }
        CameraInfo cameraInfo = camera.getCameraInfo();
        if (cameraInfo == null) {
            return;
        }
        boolean flashOpen = cameraInfo.getTorchState().getValue() == TorchState.ON;
        if (!(flashOpen ^ open)) {
            return;
        }
        CameraControl cameraControl = camera.getCameraControl();
        if (cameraControl == null) {
            return;
        }
        if (flashOpen) {
            cameraControl.enableTorch(false);
        } else {
            cameraControl.enableTorch(true);
        }
    }

}
