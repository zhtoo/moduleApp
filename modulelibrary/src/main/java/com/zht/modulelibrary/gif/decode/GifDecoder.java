package com.zht.modulelibrary.gif.decode;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Shared interface for GIF decoders.
 */
public interface GifDecoder {

    int STATUS_OK = 0;

    int STATUS_FORMAT_ERROR = 1;

    int STATUS_OPEN_ERROR = 2;

    int STATUS_PARTIAL_DECODE = 3;

    int TOTAL_ITERATION_COUNT_FOREVER = 0;

    interface BitmapProvider {

        @NonNull
        Bitmap obtain(int width, int height, @NonNull Bitmap.Config config);

        /**
         * Releases the given Bitmap back to the pool.
         */
        void release(@NonNull Bitmap bitmap);

        /**
         * Returns a byte array used for decoding and generating the frame bitmap.
         *
         * @param size the size of the byte array to obtain
         */
        @NonNull
        byte[] obtainByteArray(int size);

        /**
         * Releases the given byte array back to the pool.
         */
        void release(@NonNull byte[] bytes);

        /**
         * Returns an int array used for decoding/generating the frame bitmaps.
         */
        @NonNull
        int[] obtainIntArray(int size);

        /**
         * Release the given array back to the pool.
         */
        void release(@NonNull int[] array);
    }

    int getWidth();

    int getHeight();

    @NonNull
    ByteBuffer getData();

    /**
     * Returns the current status of the decoder.
     *
     * <p> Status will update per frame to allow the caller to tell whether or not the current frame
     * was decoded successfully and/or completely. Format and open failures persist across frames.
     * </p>
     */
    int getStatus();

    /**
     * Move the animation frame counter forward.
     */
    void advance();

    /**
     * Gets display duration for specified frame.
     *
     * @param n int index of frame.
     * @return delay in milliseconds.
     */
    int getDelay(int n);

    /**
     * Gets display duration for the upcoming frame in ms.
     */
    int getNextDelay();

    /**
     * Gets the number of frames read from file.
     *
     * @return frame count.
     */
    int getFrameCount();

    /**
     * Gets the current index of the animation frame, or -1 if animation hasn't not yet started.
     *
     * @return frame index.
     */
    int getCurrentFrameIndex();

    /**
     * Resets the frame pointer to before the 0th frame, as if we'd never used this decoder to
     * decode any frames.
     */
    void resetFrameIndex();

    /**
     * Gets the "Netscape" loop count, if any. A count of 0 means repeat indefinitely.
     *
     * @deprecated Use {@link #getNetscapeLoopCount()} instead.
     *             This method cannot distinguish whether the loop count is 1 or doesn't exist.
     * @return loop count if one was specified, else 1.
     */
    @Deprecated
    int getLoopCount();


    int getNetscapeLoopCount();


    int getTotalIterationCount();


    int getByteSize();

    @Nullable
    Bitmap getNextFrame();

    int read(@Nullable InputStream is, int contentLength);

    void clear();

    void setData(@NonNull GifHeader header, @NonNull byte[] data);

    void setData(@NonNull GifHeader header, @NonNull ByteBuffer buffer);

    void setData(@NonNull GifHeader header, @NonNull ByteBuffer buffer, int sampleSize);

    /**
     * Reads GIF image from byte array.
     *
     * @param data containing GIF file.
     * @return read status code (0 = no errors).
     */
    int read(@Nullable byte[] data);


    /**
     * Sets the default {@link android.graphics.Bitmap.Config} to use when decoding frames of a GIF.
     *
     * <p>Valid options are {@link android.graphics.Bitmap.Config#ARGB_8888} and
     * {@link android.graphics.Bitmap.Config#RGB_565}.
     * {@link android.graphics.Bitmap.Config#ARGB_8888} will produce higher quality frames, but will
     * also use 2x the memory of {@link android.graphics.Bitmap.Config#RGB_565}.
     *
     * <p>Defaults to {@link android.graphics.Bitmap.Config#ARGB_8888}
     *
     * <p>This value is not a guarantee. For example if set to
     * {@link android.graphics.Bitmap.Config#RGB_565} and the GIF contains transparent pixels,
     * {@link android.graphics.Bitmap.Config#ARGB_8888} will be used anyway to support the
     * transparency.
     */
    void setDefaultBitmapConfig(@NonNull Bitmap.Config format);
}

