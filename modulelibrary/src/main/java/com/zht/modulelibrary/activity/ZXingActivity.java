package com.zht.modulelibrary.activity;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.common.util.Logger;
import com.zht.kotlin.cameraX.CameraActivity;
import com.zht.modulelibrary.databinding.ActivityZxingBinding;
import com.zht.modulelibrary.zxing.DecodeFormatManager;
import com.zht.modulelibrary.zxing.ZXingHelper;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Date 2023/1/9 17:34
 * @Author zhanghaitao
 * @Description 自定义相机：
 */
@Route(path = ARoutePathConstants.Library.ZXing_ACTIVITY)
public class ZXingActivity extends CameraActivity {

    private MultiFormatReader multiFormatReader;
    private Map<DecodeHintType, Object> hints;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityZxingBinding binding = ActivityZxingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        multiFormatReader = new MultiFormatReader();
        hints = new EnumMap<>(DecodeHintType.class);

        Collection<BarcodeFormat> decodeFormats = EnumSet.noneOf(BarcodeFormat.class);
        decodeFormats.addAll(DecodeFormatManager.PRODUCT_FORMATS);//一维码（商品）
        decodeFormats.addAll(DecodeFormatManager.INDUSTRIAL_FORMATS);//一维码（工业）
        decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);// //二维码
//        decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);//Data Matrix
//        decodeFormats.addAll(DecodeFormatManager.AZTEC_FORMATS);//  //Aztec
//        decodeFormats.addAll(DecodeFormatManager.PDF417_FORMATS);//PDF417
        hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);

        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        //  hints.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK, resultPointCallback);
        multiFormatReader.setHints(hints);
    }

    public void clickPreview(View view) {
        startCamera();
    }

    public void clickTakePhoto(View view) {
        takePhoto();
    }

    private boolean sava = false;

    @Override
    public void onCatchPreviewImage(Bitmap bitmap) {
        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        RGBLuminanceSource source = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), pixels);
        if (source != null) {
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
            try {
                Result rawResult = multiFormatReader.decodeWithState(binaryBitmap);
                if (rawResult != null) {
                    Toast.makeText(this, rawResult.getText(), Toast.LENGTH_SHORT).show();
                }
            } catch (ReaderException e) {
                e.printStackTrace();
            } finally {
                multiFormatReader.reset();
            }
        }
    }

    public boolean saveBitmap(Bitmap bitmap) {
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
            Uri saveUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (TextUtils.isEmpty(saveUri.toString())) {
                return false;
            }
            outputStream = getContentResolver().openOutputStream(saveUri);
            //将位图写出到指定的位置
            //第一个参数：格式JPEG 是可以压缩的一个格式 PNG 是一个无损的格式
            //第二个参数：保留原图像90%的品质，压缩10% 这里压缩的是存储大小
            //第三个参数：具体的输出流
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
