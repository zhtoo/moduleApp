package com.zht.common.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 *  Created by zhanghaitao on 2017/8/11.
 * 磁盘缓存（保存在该应用程序的cache目录下的test_cache目录）
 */
public class LocalCacheUtils {

    /**
     * 将图片存储在本地磁盘
     * @param context 上下文
     * @param bitmap  需要存储的图片对应的bitmap
     * @param url  图片缓存的地址（实际上文件缓存的名称）
     */
    public static void saveCache(Context context,Bitmap bitmap, String url){
        //缓存目录
        File dir = new File(context.getCacheDir(),"picture_cache");
        if(!dir.exists()){
            dir.mkdirs();
        }

        //把图片缓存在缓存目录
        File file = new File(dir, MD5Util.encode(url));
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
    }


    /**
     * 读取存储在本地磁盘的图片
     * @param context 上下文
     * @param url 图片缓存的地址（实际上文件缓存的名称）
     * @return
     */
    public static Bitmap readCache(Context context,String url){
        File dir = new File(context.getCacheDir(),"picture_cache");
        if(!dir.exists()){
           return null;
        }
        File file = new File(dir, MD5Util.encode(url));
        if(!file.exists()){
            return null;
        }
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        //把数据缓存在内存中
        MemoryCacheUtils.saveCache(bitmap,url);
        return bitmap;
    }
}
