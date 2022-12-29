package com.zht.common.permission;

import android.content.Context;

import com.zht.common.util.SPUtils;

/**
 * @Date 2022/12/28 14:04
 * @Author zhanghaitao
 * @Description
 */
public class LocalPermissionCacheImpl implements LocalPermissionCacheInterface {

    public static final int GRANTED = 1;
    public static final int DENIED = -1;

    public int checkPermission(Context context, String permission) {
//        return (int) SPUtils.get(context, permission, 0);
        return GRANTED;
    }

    @Override
    public void cachePermission(Context context, String permission, boolean granted) {
        SPUtils.put(context, permission, granted ? GRANTED : DENIED);
    }

    @Override
    public boolean checkPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (checkPermission(context, permission) == DENIED) {
                //没有开启权限
                return false;
            }
        }
        return true;
    }

}
