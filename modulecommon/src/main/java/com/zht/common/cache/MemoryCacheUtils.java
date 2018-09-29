package com.zht.common.cache;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

/**
 * Created by zhanghaitao on 2017/8/11.
 * 内存缓存：通过HashMap来进行数据的存储
 * 存储在内存中，大小由  (int) (maxMemory/4)指定
 */
public class MemoryCacheUtils {

    static {
        //从 Android 2.3 (API Level 9)开始
        //垃圾回收器会更倾向于回收持有软引用或弱引用的对象
        //这让软引用和弱引用变得不再可靠。
        long maxMemory = Runtime.getRuntime().maxMemory();//获取Dalvik 虚拟机最大的内存大小：16M

        lruCache = new LruCache<String,Bitmap>((int) (maxMemory/4)){//指定内存缓存集合的大小
            //获取图片的大小
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //版本兼容
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
                    return value.getAllocationByteCount();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
                    return value.getByteCount();
                }
                // 在低版本中用一行的字节X高度
                return value.getRowBytes() * value.getHeight();                //更低版本
            }
        };
    }
    private static LruCache<String, Bitmap> lruCache;

    //写缓存
    public static void saveCache(Bitmap bitmap,String url){
        lruCache.put(url,bitmap);
    }
    //读缓存
    public static Bitmap readCache(String url){
        return lruCache.get(url);
    }
}
