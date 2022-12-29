package com.zht.common.permission;

import android.content.Context;

/**
 * @Date 2022/3/20
 * @Author ZhangHaitao
 * @Description 本地缓存权限授予状态：因有部分平台隐私合规要求用户拒绝过的权限，不能再次向申请权限。
 * 故需要缓存手机权限申请记录，用户明确拒绝的权限不能再次向用户申请。
 */
public interface LocalPermissionCacheInterface {


    /**
     * 缓存权限授予状态
     *
     * @param permission 权限名称
     * @param granted    true：权限授予；false：权限为授予
     */
    void cachePermission(Context context, String permission, boolean granted);


    /**
     * 获取本地缓存权限授予状态
     *
     * @param permission 权限名称
     * @return true：权限授予；false：权限为授予
     */
    boolean checkPermissions(Context context, String... permission);

}
