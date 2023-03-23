package com.zht.common.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Closeable;
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
import java.util.HashMap;

/**
 * @Date 2023/1/4 10:41
 * @Author zhanghaitao
 * @Description
 */
public class FileUtils {

    private static final String TAG ="FileUtils";

    // 缓存文件头信息-文件头信息
    public static final HashMap<String, String> mFileTypes = new HashMap<String, String>();

    // TODO: 2023/3/23 文件头类型待完善
    static {
        // images
       // mFileTypes.put("FFD8FF", "jpg");//JPEG (jpg)，文件头：FFD8FFE0或FFD8FFE1或FFD8FFE8
        mFileTypes.put("FFD8FFE0", "jpg");
        mFileTypes.put("FFD8FFE1", "jpg");
        mFileTypes.put("FFD8FFE8", "jpg");
        mFileTypes.put("89504E47", "png");
        mFileTypes.put("47494638", "gif");
        mFileTypes.put("49492A00", "tif");
//        mFileTypes.put("424D", "bmp");
        mFileTypes.put("424DC001", "bmp");
        mFileTypes.put("41433130", "dwg"); // CAD
        mFileTypes.put("38425053", "psd");
//        mFileTypes.put("7B5C727466", "rtf"); // 日记本
//        mFileTypes.put("3C3F786D6C", "xml");
//        mFileTypes.put("68746D6C3E", "html");
//        mFileTypes.put("44656C69766572792D646174653A", "eml"); // 邮件
        mFileTypes.put("D0CF11E0", "doc");
//        mFileTypes.put("5374616E64617264204A", "mdb");
//        mFileTypes.put("252150532D41646F6265", "ps");
//        mFileTypes.put("255044462D312E", "pdf");
        mFileTypes.put("504B0304", "docx");
        mFileTypes.put("52617221", "rar");
        mFileTypes.put("57415645", "wav");
        mFileTypes.put("41564920", "avi");
        mFileTypes.put("2E524D46", "rm");
        mFileTypes.put("000001BA", "mpg");
        mFileTypes.put("000001B3", "mpg");
        mFileTypes.put("6D6F6F76", "mov");
//        mFileTypes.put("3026B2758E66CF11", "asf");
        mFileTypes.put("4D546864", "mid");
//        mFileTypes.put("1F8B08", "gz");
        mFileTypes.put("4D5A9000", "exe");
        mFileTypes.put("75736167", "txt");
    }


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
                copy(Channels.newInputStream(input), Channels.newOutputStream(output));
            }
            output.force(false);
        } finally {
            input.close();
            output.close();
        }
    }

    public static void copy(@NonNull InputStream inputStream, @NonNull OutputStream outputStream)
            throws IOException {
        try {
            int length;
            byte[] buffer = new byte[1024 * 4];
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } finally {
            inputStream.close();
            outputStream.close();
        }
    }

    /**
     * 合并文件
     *
     * @param filePaths  需要合并的文件地址
     * @param resultPath 输出文件地址
     * @return 操作是否成功
     */
    public static boolean mergeFiles(String[] filePaths, String resultPath) {
        if (filePaths == null || filePaths.length < 1 || TextUtils.isEmpty(resultPath)) {
            return false;
        }
        if (filePaths.length == 1) {
            return new File(filePaths[0]).renameTo(new File(resultPath));
        }

        File[] files = new File[filePaths.length];
        for (int i = 0; i < filePaths.length; i++) {
            files[i] = new File(filePaths[i]);
            if (TextUtils.isEmpty(filePaths[i]) || !files[i].exists() || !files[i].isFile()) {
                return false;
            }
        }

        File resultFile = new File(resultPath);

        try {
            FileChannel resultFileChannel = new FileOutputStream(resultFile, true).getChannel();
            for (int i = 0; i < filePaths.length; i++) {
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

        for (int i = 0; i < filePaths.length; i++) {
            files[i].delete();
        }
        return true;
    }

    /**
     * 移动文件
     *
     * @param originFilePath 源文件路径
     * @param targetFilePath 目标文件路径
     * @return 操作是否成功
     */
    public static boolean moveFile(String originFilePath, @NonNull String targetFilePath) {
        File originFile = new File(originFilePath);
        if (!originFile.exists()) {
            //原文件不存在
            return false;
        }
        File targetFile = new File(targetFilePath);
        if (targetFile.isDirectory()) {
            //目标路径是文件夹
            targetFile = new File(targetFile, originFile.getName());
        }
        return originFile.renameTo(targetFile);
    }

    public static boolean deleteFile(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            String[] children = file.list();
            for (String child : children) {
                String childPath = path + "/" + child;
                boolean success = deleteFile(childPath);
                if (!success) {
                    Log.e(TAG, "delete fail : " + childPath);
                }
            }
        }
        if (file.exists()) {
            return file.delete();
        }
        return true;
    }

    public static String getFileSuffix(String filePath) {
        File file = new File(filePath);
        String fileName = file.getName();
        int index = fileName.lastIndexOf("?");
        if (index > 0) {
            fileName = fileName.substring(0, index);
        }
        index = fileName.lastIndexOf(".");
        if (index != -1 && fileName.length() > index) {
            return fileName.substring(index);
        } else {
            String fileHeader = getFileHeader(filePath);
            String suffix = mFileTypes.get(fileHeader);
            if (TextUtils.isEmpty(suffix)) {
                return "";
            } else {
                return "." + suffix;
            }
        }
    }

    public static String getFileHeader(String filePath) {
        FileInputStream inputStream = null;
        String value = "";
        try {
            inputStream = new FileInputStream(filePath);
            byte[] byteArray = new byte[4];
            inputStream.read(byteArray, 0, byteArray.length);
            value = bytesToHexString(byteArray);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            safeClose(inputStream);
        }
        return value;
    }

    /**
     * 将要读取文件头信息的文件的byte数组转换成string类型表示
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (byte b : src) {
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
            hv = Integer.toHexString(b & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }

    public static void safeClose(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
