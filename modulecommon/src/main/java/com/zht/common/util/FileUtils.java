package com.zht.common.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

/**
 * @Date 2023/1/4 10:41
 * @Author zhanghaitao
 * @Description
 */
public class FileUtils {

    public static void copy(@NonNull File inputFile, @NonNull File outputFile) {
        try {
            copy(new FileInputStream(inputFile).getChannel(), new FileOutputStream(outputFile).getChannel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copy(@NonNull String inputPath, @NonNull String outputPath) {
        try {
            copy(new FileInputStream(inputPath).getChannel(), new FileOutputStream(outputPath).getChannel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * copy from androidx.room.util.FileUtils
     */
    @SuppressLint("LambdaLast")
    public static void copy(@NonNull ReadableByteChannel input, @NonNull FileChannel output)
            throws IOException {
        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                output.transferFrom(input, 0, Long.MAX_VALUE);
            } else {
                InputStream inputStream = Channels.newInputStream(input);
                OutputStream outputStream = Channels.newOutputStream(output);
                int length;
                byte[] buffer = new byte[1024 * 4];
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            }
            output.force(false);
        } finally {
            input.close();
            output.close();
        }
    }

    /**
     * 合并文件
     * @param fpaths 需要合并的文件地址
     * @param resultPath 输出文件地址
     * @return
     */
    public static boolean mergeFiles(String[] fpaths, String resultPath) {
        if (fpaths == null || fpaths.length < 1 || TextUtils.isEmpty(resultPath)) {
            return false;
        }
        if (fpaths.length == 1) {
            return new File(fpaths[0]).renameTo(new File(resultPath));
        }

        File[] files = new File[fpaths.length];
        for (int i = 0; i < fpaths.length; i++) {
            files[i] = new File(fpaths[i]);
            if (TextUtils.isEmpty(fpaths[i]) || !files[i].exists() || !files[i].isFile()) {
                return false;
            }
        }

        File resultFile = new File(resultPath);

        try {
            FileChannel resultFileChannel = new FileOutputStream(resultFile, true).getChannel();
            for (int i = 0; i < fpaths.length; i++) {
                FileChannel blk = new FileInputStream(files[i]).getChannel();
                resultFileChannel.transferFrom(blk, resultFileChannel.size(), blk.size());
                blk.close();
            }
            resultFileChannel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        for (int i = 0; i < fpaths.length; i++) {
            files[i].delete();
        }
        return true;
    }


}
