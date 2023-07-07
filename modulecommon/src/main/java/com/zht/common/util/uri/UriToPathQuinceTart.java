package com.zht.common.util.uri;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;

import com.zht.common.util.FileUtils;
import com.zht.common.util.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Date 2023/7/7 14:22
 * @Author zhanghaitao
 * @Description
 */
public class UriToPathQuinceTart {

    /**
     * 用于android10以上版本获取文件路径
     */
    public static String getRealFilePath(Context context, Uri uri) {
        String contentUriPath = getFileFromContentUri(context, uri);
        if (!TextUtils.isEmpty(contentUriPath)) {
            return contentUriPath;
        }
        String scheme = uri.getScheme();
        if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            return new File(uri.getPath()).getAbsolutePath();
        }
        if (!ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            return "";
        }
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null) {
            return "";
        }
        if (!cursor.moveToFirst()) {
            cursor.close();
            return "";
        }
        String path = "";
        int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        if (index > -1) {
            String displayName = cursor.getString(index);
            try {
                //把文件复制到沙盒目录
                InputStream is = contentResolver.openInputStream(uri);
                File cache = new File(context.getExternalCacheDir().getAbsolutePath(), Math.round((Math.random() + 1) * 1000) + displayName);
                FileOutputStream fos = new FileOutputStream(cache);
                FileUtils.copy(is, fos);
                fos.close();
                is.close();
                path = cache.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return path;
    }

    /**
     * Android 10 以上适配,兼容安卓13通用实现
     */
    private static String getFileFromContentUri(Context context, Uri uri) {
        Logger.e("getFileFromContentUri");
        if (uri == null) {
            return "";
        }
        String[] filePathColumn = {MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME};
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(uri, filePathColumn, null,
                null, null);
        if (cursor == null) {
            return "";
        }
        if (!cursor.moveToFirst()) {
            cursor.close();
            return "";
        }
        String path = "";
        int index = cursor.getColumnIndex(filePathColumn[0]);
        if (index > -1) {
            path = cursor.getString(index);
        }
        cursor.close();
        return path;
    }


}
