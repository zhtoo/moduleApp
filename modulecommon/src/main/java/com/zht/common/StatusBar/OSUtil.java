package com.zht.common.StatusBar;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * 操作系统
 */
public class OSUtil {
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private static final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
    private static final String KEY_EMUI_VERSION = "ro.build.version.emui";
    private static final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";
    private static final String KEY_YUNOS_VERSION = "ro.yunos.version";
    private static final String KEY_YUNOS_VM_NAME = "java.vm.name";

    /**
     * 是否华为
     *
     * @return
     */
    public static boolean isEmui() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop.getProperty(KEY_EMUI_API_LEVEL, null) != null
                || prop.getProperty(KEY_EMUI_VERSION, null) != null
                || prop.getProperty(KEY_EMUI_CONFIG_HW_SYS_VERSION, null) != null;
    }

    /**
     * 是否小米
     *
     * @return
     */
    public static boolean isMiui() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
    }

    /**
     * 是否魅族
     *
     * @return
     */
    public static boolean isFlyme() {
        return getSystemProperty("ro.build.display.id", "").toLowerCase().contains("flyme");
    }

    /**
     * 是否YunOs
     *
     * @return
     */
    public static boolean isYunOs() {
        return getSystemProperty(KEY_YUNOS_VM_NAME, "").toLowerCase().contains("lemur")
                || getSystemProperty(KEY_YUNOS_VERSION, "").trim().length() > 0;
    }

    private static String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String) get.invoke(clz, key, defaultValue);
        } catch (Exception e) {
        }
        return defaultValue;
    }
}