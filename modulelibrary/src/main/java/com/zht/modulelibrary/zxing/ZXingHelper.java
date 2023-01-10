package com.zht.modulelibrary.zxing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Date 2022/12/15 16:46
 * @Author zhanghaitao
 * @Description
 */
public class ZXingHelper {

    public static Bitmap createQRCode(String content, int heightPix) {
        return createQRCode(content, heightPix, Color.parseColor("#2C3E50"), Color.TRANSPARENT);
    }

    public static Bitmap createQRCode(String content, int heightPix, int codeColor, int bgColor) {
        try {
            //配置参数
            Map<EncodeHintType, Object> hints = new LinkedHashMap();
            // 编码格式
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //容错级别
//            hints.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.L);//7%
//            hints.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.M);// 15%
//            hints.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.Q);// 25%
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 30%
            // 二维码边距
            hints.put(EncodeHintType.MARGIN, "1");

            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, heightPix, heightPix, hints);
            int[] pixels = new int[heightPix * heightPix];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < heightPix; y++) {
                for (int x = 0; x < heightPix; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * heightPix + x] = codeColor;
                    } else {
                        pixels[y * heightPix + x] = bgColor;
                    }
                }
            }
            // 生成二维码图片的格式
            Bitmap bitmap = Bitmap.createBitmap(heightPix, heightPix, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, heightPix, 0, 0, heightPix, heightPix);
            return bitmap;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 解码
     */
    @Deprecated
    public static String decodeQRCode(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        return decodeQRCode(bitmap);
    }

    @Deprecated
    public static String decodeQRCode(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        Result result = null;
        try {
            Map<DecodeHintType, Object> hints = new LinkedHashMap();
            //添加可以解析的编码类型
            Vector decodeFormats = new Vector<BarcodeFormat>();
            decodeFormats.add(DecodeFormatManager.ONE_D_FORMATS);
            decodeFormats.add(DecodeFormatManager.QR_CODE_FORMATS);
            decodeFormats.add(DecodeFormatManager.DATA_MATRIX_FORMATS);
            decodeFormats.add(DecodeFormatManager.AZTEC_FORMATS);
            decodeFormats.add(DecodeFormatManager.PDF417_FORMATS);

            hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
            hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
            hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int[] pixels = new int[width * height];

            bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

            RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);

            if (source == null) {
                return null;
            }

            QRCodeReader reader = new QRCodeReader();
            boolean isReDecode;
            try {
                BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
                result = reader.decode(binaryBitmap, hints);
                isReDecode = false;
            } catch (Exception e) {
                isReDecode = true;
            }

            if (isReDecode) {
                try {
                    BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source.invert()));
                    result = reader.decode(binaryBitmap, hints);
                    isReDecode = false;
                } catch (Exception e) {
                    isReDecode = true;
                }
            }

            if (isReDecode) {
                try {
                    BinaryBitmap binaryBitmap = new BinaryBitmap(new GlobalHistogramBinarizer(source));
                    result = reader.decode(binaryBitmap, hints);
                    isReDecode = false;
                } catch (Exception e) {
                    isReDecode = true;
                }
            }

            if (isReDecode && source.isRotateSupported()) {
                try {
                    BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source.rotateCounterClockwise()));
                    result = reader.decode(binaryBitmap, hints);
                } catch (Exception e) {

                }
            }

            reader.reset();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result != null) {
            return result.getText();
        }

        return null;
    }

    /**
     * 压缩图片
     */
//    private static Bitmap compressBitmap(String path, int reqWidth, int reqHeight) {
//        BitmapFactory.Options newOpts = new BitmapFactory.Options();
//        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
//        newOpts.inJustDecodeBounds = true;//获取原始图片大小
//        BitmapFactory.decodeFile(path, newOpts);// 此时返回bm为空
//        float width = newOpts.outWidth;
//        float height = newOpts.outHeight;
//        // 缩放比，由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
//        int wSize = 1;// wSize=1表示不缩放
//        if (width > reqWidth) {// 如果宽度大的话根据宽度固定大小缩放
//            wSize = (int) (width / reqWidth);
//        }
//        int hSize = 1;// wSize=1表示不缩放
//        if (height > reqHeight) {// 如果高度高的话根据宽度固定大小缩放
//            hSize = (int) (height / reqHeight);
//        }
//        int size = Math.max(wSize, hSize);
//        if (size <= 0)
//            size = 1;
//        newOpts.inSampleSize = size;// 设置缩放比例
//        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
//        newOpts.inJustDecodeBounds = false;
//        return BitmapFactory.decodeFile(path, newOpts);
//    }

    /**
     * 获取RGBLuminanceSource
     */
//    private static RGBLuminanceSource getRGBLuminanceSource(Bitmap bitmap) {
//        int width = bitmap.getWidth();
//        int height = bitmap.getHeight();
//
//        int[] pixels = new int[width * height];
//        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
//        return new RGBLuminanceSource(width, height, pixels);
//
//    }


    /**
     * 解析二维码图片
     */
//    public static Result parseQRCodeResult(String bitmapPath, int reqWidth, int reqHeight, Map<DecodeHintType, ?> hints) {
//        Result result = null;
//        try {
//            QRCodeReader reader = new QRCodeReader();
//
//            RGBLuminanceSource source = getRGBLuminanceSource(compressBitmap(bitmapPath, reqWidth, reqHeight));
//            if (source != null) {
//
//                boolean isReDecode;
//                try {
//                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
//                    result = reader.decode(bitmap, hints);
//                    isReDecode = false;
//                } catch (Exception e) {
//                    isReDecode = true;
//                }
//
//                if (isReDecode) {
//                    try {
//                        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source.invert()));
//                        result = reader.decode(bitmap, hints);
//                        isReDecode = false;
//                    } catch (Exception e) {
//                        isReDecode = true;
//                    }
//                }
//
//                if (isReDecode) {
//                    try {
//                        BinaryBitmap bitmap = new BinaryBitmap(new GlobalHistogramBinarizer(source));
//                        result = reader.decode(bitmap, hints);
//                        isReDecode = false;
//                    } catch (Exception e) {
//                        isReDecode = true;
//                    }
//                }
//
//                if (isReDecode && source.isRotateSupported()) {
//                    try {
//                        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source.rotateCounterClockwise()));
//                        result = reader.decode(bitmap, hints);
//                    } catch (Exception e) {
//
//                    }
//                }
//
//                reader.reset();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }

}
