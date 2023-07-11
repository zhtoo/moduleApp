package com.zht.modulelibrary.gif;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.zht.modulelibrary.gif.decode.GifFrame;
import com.zht.modulelibrary.gif.decode.GifHeader;
import com.zht.modulelibrary.gif.decode.GifHeaderParser;
import com.zht.modulelibrary.gif.decode.StandardGifDecoder;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/7/7 16:54
 * @Author zhanghaitao
 * @Description
 */
public class GifDecodeHelper {

    public static GifMetadata createGifMetadata(Context context, File file, int maxSize) {
        GifMetadata gifMetadata = null;
        try {
            if (file != null && file.exists()) {
                ByteBuffer byteBuffer =  fromFile(file);
                GifHeaderParser gifHeaderParser = new GifHeaderParser();
                gifHeaderParser.setData(byteBuffer);
                GifHeader gifHeader = gifHeaderParser.parseHeader();
                List<GifFrame> frames = gifHeader.getFrames();

                long gifDuration = 0;
                for (GifFrame gifFrame : frames) {
                    gifDuration += gifFrame.delay;
                }
                gifMetadata = new GifMetadata();
                gifMetadata.setWidth(gifHeader.getWidth());
                gifMetadata.setHeight(gifHeader.getHeight());
                gifMetadata.setFrameCount(gifHeader.getNumFrames());
                gifMetadata.setDuration(gifDuration);
                gifMetadata.setFrameRate(getFrameRate(gifHeader.getNumFrames(), gifDuration));
                gifMetadata.setGctSize(gifHeader.gctSize);
                gifMetadata.setFileSize(file.length());

                // 抽取每一帧图片
                BitmapPool bitmapPool = Glide.get(context).getBitmapPool();
                GifBitmapProvider bitmapProvider = new GifBitmapProvider(bitmapPool);
                // Make sure sample size is a power of 2.
                int sampleSize = 1;
                StandardGifDecoder gifDecoder = new StandardGifDecoder(bitmapProvider, gifHeader, byteBuffer, sampleSize);

                List<Bitmap> bitmapList = new ArrayList<>();
                for (int i = 0; i < gifDecoder.getFrameCount(); i++) {
                    gifDecoder.advance();
                    Bitmap frame = gifDecoder.getNextFrame();
                    bitmapList.add(frame);
                }
                gifMetadata.setFrames(bitmapList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gifMetadata;
    }

    private static int getFrameRate(int frameCount, long duration) {
        float durationSeconds = duration / 1000.0f;
        return Math.round(frameCount / durationSeconds);
    }

    public static ByteBuffer fromFile(@NonNull File file) throws IOException {
        RandomAccessFile raf = null;
        FileChannel channel = null;
        try {
            long fileLength = file.length();
            if (fileLength > Integer.MAX_VALUE) {
                throw new IOException("File too large to map into memory");
            }
            if (fileLength == 0) {
                throw new IOException("File unsuitable for memory mapping");
            }
            raf = new RandomAccessFile(file, "r");
            channel = raf.getChannel();
            return channel.map(FileChannel.MapMode.READ_ONLY, 0, fileLength).load();
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {

                }
            }
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {

                }
            }
        }
    }

}
