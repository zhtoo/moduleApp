package com.zht.measureheartrate.util;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.camera.core.ImageProxy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * @Date 2023/2/21 09:09
 * @Author zhanghaitao
 * @Description
 */
public class ImageProcessing {

    public static Bitmap createBitmapFromImageProxy(ImageProxy imageProxy) {
        if (imageProxy == null) {
            return null;
        }
        if (imageProxy.getFormat() != ImageFormat.YUV_420_888) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            int width = imageProxy.getWidth();
            int height = imageProxy.getHeight();
            ImageProxy.PlaneProxy[] planes = imageProxy.getPlanes();
            ByteBuffer yBuffer = planes[0].getBuffer();
            ByteBuffer uBuffer = planes[1].getBuffer();
            ByteBuffer vBuffer = planes[2].getBuffer();
            int pixels = (int) (width * height * 1.5f);
            byte[] yuvArrayNV21 = new byte[pixels];

            int index = 0;

            // 复制Y的数据
            int yRowStride = planes[0].getRowStride();
            int yPixelStride = planes[0].getPixelStride();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int bufferIndex = y * yRowStride + x * yPixelStride;
                    yuvArrayNV21[index++] = yBuffer.get(bufferIndex);
                }
            }
            // 复制U/V数据
            int uvRowStride = planes[1].getRowStride();
            int uvPixelStride = planes[1].getPixelStride();
            int uvWidth = (int) (width / 2F);
            int uvHeight = (int) (height / 2F);
            for (int y = 0; y < uvHeight; y++) {
                for (int x = 0; x < uvWidth; x++) {
                    int bufferIndex = y * uvRowStride + x * uvPixelStride;
                    yuvArrayNV21[index++] = vBuffer.get(bufferIndex);
                    yuvArrayNV21[index++] = uBuffer.get(bufferIndex);
                }
            }
            // YuvImage 仅支持 YUY2和NV21格式的数据
            YuvImage yuvImage = new YuvImage(yuvArrayNV21, ImageFormat.NV21, width, height, null);
            byteArrayOutputStream = new ByteArrayOutputStream();
            yuvImage.compressToJpeg(new Rect(0, 0, width, height), 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return null;
    }


    public boolean saveBitmap(Context context,Bitmap bitmap) {
        OutputStream outputStream = null;
        try {
            //获取要保存的图片的位图
            //创建一个保存的Uri
            ContentValues values = new ContentValues();
            //设置图片名称
            values.put(MediaStore.Images.Media.DISPLAY_NAME, System.currentTimeMillis() + "_code.png");
            //设置图片格式
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
            //设置图片路径
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
            }
            Uri saveUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (TextUtils.isEmpty(saveUri.toString())) {
                return false;
            }
            outputStream =  context.getContentResolver().openOutputStream(saveUri);
            //将位图写出到指定的位置
            //format：格式JPEG 是可以压缩的一个格式 PNG 是一个无损的格式
            //quality：保留原图像90%的品质，压缩10% 这里压缩的是存储大小
            //stream：具体的输出流
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

}
