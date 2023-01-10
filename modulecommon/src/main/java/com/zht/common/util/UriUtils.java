package com.zht.common.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

/**
 * Created by ZhangHaitao on 2019/5/29
 * 通过Uri获取对应文件的Path
 */
public class UriUtils {

    private static final String TAG = "UriUtils";

    public static String getPath(Context context, Uri uri) {
        String path = null;
        // 以 file:// 开头的
        if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
            Logger.e("以 file:// 开头");
            path = uri.getPath();
            return path;
        }
        // 以 content:// 开头的，比如 content://media/extenral/images/media/17766

        // 以 content:// 开头
        boolean startWithContent = ContentResolver.SCHEME_CONTENT.equals(uri.getScheme());

        if (startWithContent) {
            Logger.e("以 content:// 开头");
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                Logger.e("Api 19 以下设备");
                Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        if (columnIndex > -1) {
                            path = cursor.getString(columnIndex);
                        }
                    }
                    cursor.close();
                }
                return path;
            } else if (DocumentsContract.isDocumentUri(context, uri)) {
                Logger.e("Api 19 及以上设备");
                if (isExternalStorageDocument(uri)) {
                    // ExternalStorageProvider
                    Logger.e("ExternalStorageProvider");
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        path = Environment.getExternalStorageDirectory() + "/" + split[1];
                        return path;
                    }
                } else if (isDownloadsDocument(uri)) {
                    // DownloadsProvider
                    Logger.e("DownloadsProvider");
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                            Long.valueOf(id));
                    path = getDataColumn(context, contentUri, null, null);
                    return path;
                } else if (isMediaDocument(uri)) {
                    // MediaProvider
                    Logger.e("MediaProvider");
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        Logger.e("image");
                        contentUri = Uri.parse("content://media/external/images/media");
                    } else if ("video".equals(type)) {
                        Logger.e("video");
                        contentUri = Uri.parse("content://media/external/video/media");
                    } else if ("audio".equals(type)) {
                        Logger.e("audio");
                        contentUri = Uri.parse("content://media/external/audio/media");
                    }

                    if (contentUri == null) {
                        Logger.e("contentUri is null");
                    }
                    final String selection = "_id=?";
                    Logger.e("selection args is " + split[1]);
                    final String[] selectionArgs = new String[]{split[1]};
                    path = getDataColumn(context, contentUri, selection, selectionArgs);
                    return path;
                }
            }

        }

        return null;
    }

    private static String getDataColumn(
            Context context,
            Uri uri,
            String selection,
            String[] selectionArgs
    ) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            Logger.e("cursor is " + cursor);

            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                Logger.e("column_index is " + column_index);
                String string = cursor.getString(column_index);
                Logger.e("string is " + string);
                return string;
            }else {
                Logger.e("cursor.moveToFirst() is false" );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


}
