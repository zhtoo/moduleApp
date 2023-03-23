package com.zht.common.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Date 2023/3/22 23:37
 * @Author zhanghaitao
 * @Description
 */
public class AndroidFileUtils {

    private static final String TAG = "FileHelper";

    /**
     * 保存文件到系统相册。为避免过多占用用户储存空间，保存成功后将删除源文件
     * MediaStore.Images : 图片，存储在 DCIM/ 和 Pictures/ 目录
     * MediaStore.Video ：视频，存储在 DCIM/、Movies/ 和 Pictures/ 目录
     * MediaStore.Audio ：音频，存储在 Alarms/、Audiobooks/、Music/、Notifications/、Podcasts/ 和 Ringtones/ 目录
     *
     * 公共访问区域：DCIM（系统相册）、Movie、Music、Pictures、Alarm、Ringtones、Download等。
     * @param mediaFilePath
     * @param context
     * @return
     */
    public static boolean saveToSystemAlbum(String mediaFilePath, Context context) {
        File mediaFile = new File(mediaFilePath);
        if (!mediaFile.exists()) {
            Log.d(TAG, "file:" + mediaFilePath + " is not exists!");
            return false;
        }
        boolean isImage = isImageFile(mediaFilePath);
        Log.e(TAG, ":" + isImage);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentResolver localContentResolver = context.getContentResolver();
            Uri localUri;
            if (isImage) {
                localUri = localContentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        createImageContentValues(mediaFile, System.currentTimeMillis()));
            } else {
                localUri = localContentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        createVideoContentValues(mediaFile, System.currentTimeMillis()));
            }

            if (localUri == null) {
                return false;
            }
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                inputStream = new FileInputStream(mediaFilePath);
                outputStream = context.getContentResolver().openOutputStream(localUri);
                FileUtils.copy(inputStream, outputStream);
                // 文件拷贝后，直接删除源文件
                FileUtils.deleteFile(mediaFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                FileUtils.safeClose(inputStream);
                FileUtils.safeClose(outputStream);
            }
            MediaScannerConnection.scanFile(context, new String[]{mediaFilePath}, null, null);
            return true;
        } else {
            String suffix = FileUtils.getFileSuffix(mediaFilePath);
            Log.e(TAG, suffix);
            String albumFilePath;
            if (isImage) {
                albumFilePath = getDCIMImageFile(suffix);
            } else {
                albumFilePath = getDCIMVideoFile(suffix);
            }
            Log.e(TAG, albumFilePath);
            // 将源文件移动到相册中
            FileUtils.moveFile(mediaFilePath, albumFilePath);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.parse("file://" + albumFilePath)));
        }
        return false;
    }

    /**
     * 通过Bitmap的宽来判断文件是否为图片
     *
     * @param filePath
     * @return
     */
    public static boolean isImageFile(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        if (options.outWidth == -1) {
            return false;
        }
        return true;
    }

    public static String getDCIMImageFile(String suffix) {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "DCIM"
                + File.separator + "Pictures"
                + File.separator + System.currentTimeMillis() + suffix;
    }

    public static String getDCIMVideoFile(String suffix) {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "DCIM"
                + File.separator + "Videos"
                + File.separator + System.currentTimeMillis() + suffix;
    }

    /**
     * 创建 图片 ContentValue
     *
     * @param paramFile
     * @param paramLong
     * @return
     */
    public static ContentValues createImageContentValues(File paramFile, long paramLong) {
        ContentValues localContentValues = createFileContentValues(paramFile, paramLong);

        if (paramFile.getAbsolutePath().endsWith(".png")) {
            localContentValues.put("mime_type", "image/png");
        } else {
            localContentValues.put("mime_type", "image/jpeg");
        }
        return localContentValues;
    }

    /**
     * 创建 视频 contentValue
     *
     * @param paramFile
     * @param paramLong
     * @return
     */
    public static ContentValues createVideoContentValues(File paramFile, long paramLong) {
        ContentValues localContentValues = createFileContentValues(paramFile, paramLong);
        localContentValues.put("mime_type", "video/mp4");//文件类型
        return localContentValues;
    }

    /**
     * 创建文件contentValue
     * @param paramFile
     * @param paramLong
     * @return
     */
    public static ContentValues createFileContentValues(File paramFile, long paramLong) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("title", paramFile.getName());//文件标题
        localContentValues.put("_display_name", paramFile.getName());//文件展示名称
        localContentValues.put("datetaken", Long.valueOf(paramLong));
        localContentValues.put("date_modified", Long.valueOf(paramLong));//文件修改时间
        localContentValues.put("date_added", Long.valueOf(paramLong));//文件添加时间
        localContentValues.put("_data", paramFile.getAbsolutePath());
        localContentValues.put("_size", Long.valueOf(paramFile.length()));//文件大小
        return localContentValues;
    }


}
