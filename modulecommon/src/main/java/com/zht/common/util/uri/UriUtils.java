package com.zht.common.util.uri;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import com.zht.common.util.Logger;

import java.util.Locale;

/**
 * Created by ZhangHaitao on 2019/5/29
 * 通过Uri获取对应文件的Path
 */
public class UriUtils {

    static final String TAG = "UriUtils";

    public static String getAbsolutePath(final Context context, final Uri uri) {
        if (context == null || uri == null) {
            return "";
        }
        Context appContext = context.getApplicationContext();
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT) {
            return UriToPathKitKat.getRealFilePath(appContext, uri);
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            return UriToPathQuinceTart.getRealFilePath(appContext, uri);
        }

        if (DocumentsContract.isDocumentUri(appContext, uri)) {
            String authority = uri.getAuthority();
            Uri contentUri = null;
            switch (authority) {
                case "com.android.externalstorage.documents":
                case "com.android.providers.media.documents":
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }

                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };
                    return getDataColumn(context, contentUri, selection, selectionArgs);
                case "com.android.providers.downloads.documents":
                    final String id = DocumentsContract.getDocumentId(uri);

                    long idLong = 0;
                    try {
                        idLong = Long.parseLong(id);
                    } catch (Exception e) {

                    }
                    contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), idLong);

                    return getDataColumn(context, contentUri, null, null);
                case "":
                    break;
            }
        }
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
            if ("com.google.android.apps.photos.content".equals(uri.getAuthority())) {
                Logger.e(TAG, "isGooglePhotosUri");
                return uri.getLastPathSegment();
            }
            return getDataColumn(context, uri, null, null);
        }
        // File
        if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return "";
    }


    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {
        Cursor cursor = null;
        String column = "_data";
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            boolean moveToFirst = cursor.moveToFirst();
            if (cursor != null && moveToFirst) {
                final int index  = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            } else {
                Logger.e(TAG, "cursor is " + cursor);
                Logger.e(TAG, "moveToFirst ->" + moveToFirst);
            }
        } catch (IllegalArgumentException ex) {
            Log.i(TAG, String.format(Locale.getDefault(), "getDataColumn: _data - [%s]", ex.getMessage()));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return "";
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}
