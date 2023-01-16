package com.zht.common.util;

import android.util.Log;
import com.zht.common.BuildConfig;

/**
 * @Date 2023/1/6 14:37
 * @Author zhanghaitao
 * @Description
 */
public class Logger {

    private static final String TAG = "Logger";
    private static final String headerFormat = "%s -> %s";

    public static void e(String msg) {
        if (msg == null || !BuildConfig.DEBUG) {
            return;
        }
        String headerMessage = generateCallClassInfo();
        Log.e(TAG, String.format(headerFormat,headerMessage,msg));
    }

    public static void e(String tag, String msg) {
        if (msg == null || !BuildConfig.DEBUG) {
            return;
        }
        Log.e(tag, msg);
    }

    public static void d(String msg) {
        if (msg == null || !BuildConfig.DEBUG) {
            return;
        }
        String headerMessage = generateCallClassInfo();
        Log.d(TAG, String.format(headerFormat,headerMessage,msg));
    }

    public static void d(String tag, String msg) {
        if (msg == null || !BuildConfig.DEBUG) {
            return;
        }
        Log.d(tag, msg);
    }

    public static void i(String msg) {
        if (msg == null) {
            return;
        }
        String headerMessage = generateCallClassInfo();
        Log.i(TAG, String.format(headerFormat,headerMessage,msg));
    }

    public static void i(String tag, String msg) {
        if (msg == null) {
            return;
        }
        Log.i(tag, msg);
    }

    public static void w(String msg) {
        if (msg == null || !BuildConfig.DEBUG) {
            return;
        }
        String headerMessage = generateCallClassInfo();
        Log.w(TAG, String.format(headerFormat,headerMessage,msg));
    }

    public static void w(String tag, String msg) {
        if (msg == null || !BuildConfig.DEBUG) {
            return;
        }
        Log.w(tag, msg);
    }

    /**
     * 生成调用类信息
     */
    private static String generateCallClassInfo() {
        StackTraceElement trace = Thread.currentThread().getStackTrace()[4];
        StringBuilder builder = new StringBuilder();
        builder.append(getSimpleClassName(trace.getClassName()))
                .append(".")
                .append(trace.getMethodName())
                .append(" ")
                .append(" (")
                .append(trace.getFileName())
                .append(":")
                .append(trace.getLineNumber())
                .append(")");
        return builder.toString();
    }

    public static void logCallStack(String msg) {
        if (msg == null || !BuildConfig.DEBUG) {
            return;
        }
        generateCallStackContent();
        e(TAG, msg);
    }

    // 设置追踪几层调用链，METHOD_COUNT小0时表示打印所有的调用链。调用链过长时会导致日志打印卡断，因此需要控制链的长度。
    private static final int METHOD_COUNT = -1;
    // 在两次原生调用之后从此类开始的最小堆栈跟踪索引。
    private static final int MIN_STACK_OFFSET = 3;
    private static final String LOG_SPLIT = "====================================================================================================";

    /**
     * 生成调用栈内容
     */
    private static void generateCallStackContent() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        int stackOffset = getStackOffset(trace);
        int methodCount = METHOD_COUNT < 0? trace.length:METHOD_COUNT;
        Log.e(TAG, LOG_SPLIT);
        for (int i = methodCount; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if (stackIndex >= trace.length) {
                continue;
            }
            StringBuilder builder = new StringBuilder();

            builder.append("->\t")
                    .append(getSimpleClassName(trace[stackIndex].getClassName()))
                    .append(".")
                    .append(trace[stackIndex].getMethodName())
                    .append(" -> ")
                    .append("(")
                    .append(trace[stackIndex].getFileName())
                    .append(":")
                    .append(trace[stackIndex].getLineNumber())
                    .append(")");
            Log.e(TAG, builder.toString());
        }
        Log.e(TAG, LOG_SPLIT);
    }

    private static int getStackOffset(StackTraceElement[] trace) {
        for (int i = MIN_STACK_OFFSET; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(Logger.class.getName())) {
                return --i;
            }
        }
        return -1;
    }

    private static String getSimpleClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }

}
