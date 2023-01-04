package com.zht.common.storage;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.UUID;

/**
 * @Date 2023/1/4 13:51
 * @Author zhanghaitao
 * @Description
 */
public class StorageUtil {

    public static long getSystemTotalStorage() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //将取得SD CARD文件路径一般是/sdcard
            File path = Environment.getExternalStorageDirectory();
            //通过StatFs看文件系统空间使用状况
            StatFs statFs = new StatFs(path.getPath());
            //Block数量
            long blockSize = statFs.getBlockSizeLong();
            //总Block数量
            long totalBlocks = statFs.getBlockCountLong();
            return blockSize * totalBlocks;
        }
        return 0;
    }

    public static long getSystemAvailStorage() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //将取得SD CARD文件路径一般是/sdcard
            File path = Environment.getExternalStorageDirectory();
            //通过StatFs看文件系统空间使用状况
            StatFs statFs = new StatFs(path.getPath());
            //Block数量
            long blockSize = statFs.getBlockSizeLong();
            long availableBlocks = statFs.getAvailableBlocksLong();
            return availableBlocks * blockSize;
        }
        return 0;
    }

    public static long getTotalMemory(Context context) {
        ActivityManager.MemoryInfo memoryInfo = getMemoryInfo(context);
        return memoryInfo.totalMem;
    }

    public static long getAvailMemory(Context context) {
        ActivityManager.MemoryInfo memoryInfo = getMemoryInfo(context);
        return memoryInfo.availMem;
    }

    private static ActivityManager.MemoryInfo getMemoryInfo(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(memoryInfo);
        return memoryInfo;
    }

    //// App needs 10 MB within internal storage.
    //private static final long NUM_BYTES_NEEDED_FOR_MY_APP = 1024 * 1024 * 10L;
    //
    //StorageManager storageManager =
    //        getApplicationContext().getSystemService(StorageManager.class);
    //UUID appSpecificInternalDirUuid = storageManager.getUuidForPath(getFilesDir());
    //long availableBytes =
    //        storageManager.getAllocatableBytes(appSpecificInternalDirUuid);
    //if (availableBytes >= NUM_BYTES_NEEDED_FOR_MY_APP) {
    //    storageManager.allocateBytes(
    //            appSpecificInternalDirUuid, NUM_BYTES_NEEDED_FOR_MY_APP);
    //} else {
    //    // To request that the user remove all app cache files instead, set
    //    // "action" to ACTION_CLEAR_APP_CACHE.
    //    Intent storageIntent = new Intent();
    //    storageIntent.setAction(ACTION_MANAGE_STORAGE);
    //}

    public static long getAppAvailStorage(Context context) {
        try {
            long availableBytes = 0;
            StorageManager storageManager = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                storageManager = context.getApplicationContext().getSystemService(StorageManager.class);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    UUID appSpecificInternalDirUuid = storageManager.getUuidForPath(context.getFilesDir());
                    availableBytes = storageManager.getAllocatableBytes(appSpecificInternalDirUuid);
                }
            }
            return availableBytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String formatStorageSize(long size) {
        if (size < 1024) {
            return formatNum(size) + "byte";
        } else if (size < 1024 * 1024) {
            return formatNum(size / 1024F) + "kb";
        } else if (size < 1024 * 1024 * 1024) {
            return formatNum(size / 1024F / 1024F) + "M";
        } else {
            return formatNum(size / 1024F / 1024F / 1024F) + "G";
        }
    }

    private static String formatNum(float num) {
        DecimalFormat formatter = new DecimalFormat("0.##");
        //每3个数字分隔，如1,000
        formatter.setGroupingSize(3);
        return formatter.format(num);
    }

}
