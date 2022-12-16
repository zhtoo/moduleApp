package com.zht.common.bean;

import androidx.annotation.DrawableRes;

/**
 * @Date 2022/12/16 09:35
 * @Author zhanghaitao
 * @Description
 */
public class ItemBean {

    private int icon;
    private String name;
    private String routerPath;

    public ItemBean( String name, String routerPath) {
        this.name = name;
        this.routerPath = routerPath;
    }

    public ItemBean(@DrawableRes int icon, String name, String routerPath) {
        this.icon = icon;
        this.name = name;
        this.routerPath = routerPath;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRouterPath() {
        return routerPath;
    }

    public void setRouterPath(String routerPath) {
        this.routerPath = routerPath;
    }

}
