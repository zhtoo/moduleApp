package com.zht.modulelibrary.bean;

import androidx.annotation.DrawableRes;

/**
 * @Date 2022/12/15 17:21
 * @Author zhanghaitao
 * @Description
 */
public class LibraryBean {

    private int icon;
    private String name;
    private String routerPath;

    public LibraryBean(@DrawableRes int icon, String name, String routerPath) {
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
