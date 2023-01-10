package com.zht.common.util;

import android.util.Log;

import com.zht.common.BuildConfig;

/**
 * @Date 2023/1/6 14:37
 * @Author zhanghaitao
 * @Description
 */
public class Logger {

    public static void e(String msg) {
        if (msg == null || !BuildConfig.DEBUG) {
            return;
        }
        String TAG = generateCallClassInfo();
        Log.e(TAG, msg);
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
        String TAG = generateCallClassInfo();
        Log.d(TAG, msg);
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
        String TAG = generateCallClassInfo();
        Log.d(TAG, msg);
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
        String TAG = generateCallClassInfo();
        Log.w(TAG, msg);
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

    /**
     * 生成调用栈内容
     */
    private static void generateCallStackContent() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        String level = "";//缩进
        int stackOffset = getStackOffset(trace);
        for (int i = METHOD_COUNT; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if (stackIndex >= trace.length) {
                continue;
            }
            StringBuilder builder = new StringBuilder();

            builder.append(level)
                    .append(getSimpleClassName(trace[stackIndex].getClassName()))
                    .append(".")
                    .append(trace[stackIndex].getMethodName())
                    .append(" ")
                    .append(" (")
                    .append(trace[stackIndex].getFileName())
                    .append(":")
                    .append(trace[stackIndex].getLineNumber())
                    .append(")");
            level += "\t\t";
            Log.d("=", builder.toString());
        }
    }

    // 设置追踪几层继承的类
    private static final int METHOD_COUNT = 2;
    // 在两次原生调用之后从此类开始的最小堆栈跟踪索引
    private static final int MIN_STACK_OFFSET = 3;

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
