package com.zht.common.util.uri;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

/**
 * @Date 2023/7/7 14:19
 * @Author zhanghaitao
 * @Description
 */
public class UriToPathKitKat {

    /**
     * 用于4.4以下的版本获取文件路径
     */
    public static String getRealFilePath(Context context, Uri uri) {
        if (null == uri) {
            return null;
        }
        String scheme = uri.getScheme();
        if (TextUtils.isEmpty(scheme)) {
            return uri.getPath();
        }
        if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            return uri.getPath();
        }
        if (!ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            return "";
        }
        String column = "_data";
        String[] projection = {column};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) {
            return "";
        }
        if (!cursor.moveToFirst()) {
            cursor.close();
            return "";
        }
        String path = "";
        int index = cursor.getColumnIndexOrThrow(column);
        if (index > -1) {
            path = cursor.getString(index);
        }
        cursor.close();
        return path;
    }

}
