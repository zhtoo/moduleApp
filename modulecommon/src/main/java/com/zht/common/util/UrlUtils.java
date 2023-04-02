package com.zht.common.util;

import android.text.TextUtils;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/3/31 13:56
 * @Author zhanghaitao
 * @Description
 */
public class UrlUtils {



    public static void ffff(String url){

    }

    public static Map<String, String> getParams(String url) {
        Map<String, String> map = new HashMap<String, String>();
        if (!TextUtils.isEmpty(url)) {
            int index = url.indexOf("?");
            if (index > 0) {
                url = url.substring(index + 1);
                String[] array = url.split("&");
                if (array != null && array.length > 0) {
                    String[] keyValue = null;
                    for (String item : array) {
                        if (item == null) {
                            continue;
                        }
                        keyValue = item.split("=");
                        if (keyValue.length >= 2) {
                            map.put(keyValue[0], keyValue[1]);
                        }
                    }
                }
            }
        }
        return map;
    }

    private static String getParams(String url, String name) {
        Map<String, String> params = getParams(url);
        return params.get(name);
    }

}
