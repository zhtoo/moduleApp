package com.zht.modulelibrary.gif;

import android.graphics.Bitmap;

import java.util.List;

/**
 * @Date 2023/7/7 16:40
 * @Author zhanghaitao
 * @Description
 */
public class GifMetadata {

    /**
     * 宽度
     */
    private int width;
    /**
     * 高度
     */
    private int height;
    /**
     * 帧数
     */
    private int frameCount;
    /**
     * 时长
     */
    private long duration;
    /**
     * 帧率
     */
    private int frameRate;
    /**
     * 全局调色盘大小
     */
    private int gctSize;
    /**
     * 文件大小
     */
    private long fileSize;

    private List<Bitmap> frames;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public void setFrameCount(int frameCount) {
        this.frameCount = frameCount;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }

    public int getGctSize() {
        return gctSize;
    }

    public void setGctSize(int gctSize) {
        this.gctSize = gctSize;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public List<Bitmap> getFrames() {
        return frames;
    }

    public void setFrames(List<Bitmap> frames) {
        this.frames = frames;
    }
}
