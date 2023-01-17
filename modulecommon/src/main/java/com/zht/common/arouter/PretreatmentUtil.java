package com.zht.common.arouter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.Postcard;
import com.zht.common.util.Logger;

import java.lang.reflect.Field;

/**
 * @Date 2023/1/17 12:17
 * @Author zhanghaitao
 * @Description
 */
public class PretreatmentUtil {

    public static boolean onPretreatment(Context context, Postcard postcard) {
        String path = postcard.getPath();
        if (!TextUtils.isEmpty(path)) {
            Class clazz = getClazzByPath(path);
            if (clazz != null) {
                try {
                    context.startActivity(new Intent(context, clazz));
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
        return true;
    }

    public static Class getClazzByPath(String path) {
        Class activityClass = null;
        try {
            Class clazz = Class.forName("com.zht.common.constant.ARoutePathConstants");
            clazz.newInstance();
            Class[] classes = clazz.getClasses();
            for (Class mInterface : classes) {
                Field moduleField = mInterface.getDeclaredField("MODULE");
                moduleField.setAccessible(true);
                String module = (String) moduleField.get(clazz);

                Field modulePackageNameField = mInterface.getDeclaredField("MODULE_PACKAGE_NAME");
                modulePackageNameField.setAccessible(true);
                String modulePackageName = (String) modulePackageNameField.get(clazz);

                if (!TextUtils.isEmpty(module) && !TextUtils.isEmpty(modulePackageName)) {
                    if (path.startsWith(module)) {
                        path = path.replace(module, modulePackageName).replace("/", ".");
                        activityClass = Class.forName(path);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activityClass;
    }

}
